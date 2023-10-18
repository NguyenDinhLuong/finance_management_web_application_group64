package com.example.backend.security.services;

import com.example.backend.model.Income;
import com.example.backend.model.NormalExpense;
import com.example.backend.model.User;
import com.example.backend.payload.request.AddExpenseRequest;
import com.example.backend.repository.ExpenseRepository;
import com.example.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {
    @Autowired
    private final ExpenseRepository expenseRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private final CurrencyExchangeService currencyExchangeService;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository, CurrencyExchangeService currencyExchangeService) {
        this.expenseRepository = expenseRepository;
        this.currencyExchangeService = currencyExchangeService;
    }

    @Transactional
    public NormalExpense saveExpense(AddExpenseRequest addExpenseRequest) {
        User user = userRepository.findById(addExpenseRequest.getUser_id())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + addExpenseRequest.getUser_id()));
        NormalExpense expense = new NormalExpense();

        expense.setAmount(addExpenseRequest.getAmount());
        expense.setCategory(addExpenseRequest.getCategory());
        expense.setDate(addExpenseRequest.getDate());
        expense.setLocation(addExpenseRequest.getLocation());
        expense.setStatus(addExpenseRequest.getStatus());
        expense.setPaymentMethod(addExpenseRequest.getPaymentMethod());
        expense.setUser(user);
        expenseRepository.save(expense);
        return expense;
    }

    public List<NormalExpense> getAllExpensesByUserId(Long userId) {
        return expenseRepository.findByUserId(userId);
    }

    public List<NormalExpense> updateAllExpensesAfterCurrencyExchange(String inputCurrency,String outputCurrency) {
        List<NormalExpense> expenses = expenseRepository.findAll();

        // Convert each income's amount based on the currency rates
        for (NormalExpense expense : expenses) {
            try {
                double convertedAmount = currencyExchangeService.convertCurrency(inputCurrency, outputCurrency, (double) expense.getAmount());
                expense.setAmount((float) convertedAmount);
                expenseRepository.save(expense);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return expenses;
    }

    public float getTotalExpenseAmountByUserId(Long userId) {
        List<NormalExpense> expenses = expenseRepository.findByUserId(userId);
        float totalAmount = 0;

        for (NormalExpense normalExpense : expenses) {
            totalAmount += normalExpense.getAmount();
        }

        return totalAmount;
    }
}
