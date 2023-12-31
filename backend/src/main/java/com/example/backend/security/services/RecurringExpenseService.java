package com.example.backend.security.services;

import com.example.backend.model.NormalExpense;
import com.example.backend.model.RecurringExpense;
import com.example.backend.model.User;
import com.example.backend.payload.request.AddRecurringExpenseRequest;
import com.example.backend.payload.request.UpdateExpenseRequest;
import com.example.backend.payload.request.UpdateRecurringExpenseRequest;
import com.example.backend.repository.RecurringExpenseRepository;
import com.example.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecurringExpenseService {
    @Autowired
    private final RecurringExpenseRepository recurringExpenseRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private final CurrencyExchangeService currencyExchangeService;

    @Autowired
    public RecurringExpenseService(RecurringExpenseRepository recurringExpenseRepository, CurrencyExchangeService currencyExchangeService) {
        this.recurringExpenseRepository = recurringExpenseRepository;
        this.currencyExchangeService = currencyExchangeService;
    }

    @Transactional
    public RecurringExpense saveRecurringExpense(AddRecurringExpenseRequest addRecurringExpenseRequest) {
        User user = userRepository.findById(addRecurringExpenseRequest.getUser_id())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + addRecurringExpenseRequest.getUser_id()));
        RecurringExpense recurringExpense = new RecurringExpense();

        recurringExpense.setAmount(addRecurringExpenseRequest.getAmount());
        recurringExpense.setCategory(addRecurringExpenseRequest.getCategory());
        recurringExpense.setCurrency(addRecurringExpenseRequest.getCurrency());
        recurringExpense.setStartDate(addRecurringExpenseRequest.getStartDate());
        recurringExpense.setLocation(addRecurringExpenseRequest.getLocation());
        recurringExpense.setFrequency(addRecurringExpenseRequest.getFrequency());
        recurringExpense.setEndDate(addRecurringExpenseRequest.getEndDate());
        recurringExpense.setUser(user);
        recurringExpenseRepository.save(recurringExpense);
        return recurringExpense;
    }

    @Transactional
    public RecurringExpense updateRecurringExpense (Long id, UpdateRecurringExpenseRequest updateRecurringExpenseRequest) {
        RecurringExpense recurringExpense = recurringExpenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recurring Expense not found with id " + id));
        recurringExpense.setAmount(updateRecurringExpenseRequest.getAmount());
        recurringExpense.setCategory(updateRecurringExpenseRequest.getCategory());
        recurringExpense.setCurrency(updateRecurringExpenseRequest.getCurrency());
        recurringExpense.setStartDate(updateRecurringExpenseRequest.getStartDate());
        recurringExpense.setLocation(updateRecurringExpenseRequest.getLocation());
        recurringExpense.setFrequency(updateRecurringExpenseRequest.getFrequency());
        recurringExpense.setEndDate(updateRecurringExpenseRequest.getEndDate());
        recurringExpenseRepository.save(recurringExpense);
        return recurringExpense;
    }

    public List<RecurringExpense> getAllRecurringExpensesByUserId(Long userId) {
        return recurringExpenseRepository.findByUserId(userId);
    }

    public List<RecurringExpense> updateAllRecurringExpensesAfterCurrencyExchange(String inputCurrency, String outputCurrency) {
        List<RecurringExpense> recurringExpenses = recurringExpenseRepository.findAll();

        // Convert each income's amount based on the currency rates
        for (RecurringExpense recurringExpense : recurringExpenses) {
            try {
                double convertedAmount = currencyExchangeService.convertCurrency(inputCurrency, outputCurrency, (double) recurringExpense.getAmount());
                recurringExpense.setAmount((float) convertedAmount);
                recurringExpense.setCurrency(outputCurrency);
                recurringExpenseRepository.save(recurringExpense);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return recurringExpenses;
    }

    public float getTotalRecurringExpenseAmountByUserId(Long userId) {
        List<RecurringExpense> recurringExpenses = recurringExpenseRepository.findByUserId(userId);
        float totalAmount = 0;

        for (RecurringExpense recurringExpense : recurringExpenses) {
            totalAmount += recurringExpense.getAmount();
        }

        return totalAmount;
    }
}