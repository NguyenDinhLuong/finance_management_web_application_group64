package com.example.backend.security.services;

import com.example.backend.model.Income;
import com.example.backend.model.User;
import com.example.backend.payload.request.AddIncomeRequest;
import com.example.backend.repository.IncomeRepository;
import com.example.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IncomeService  {

    private final IncomeRepository incomeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private final CurrencyExchangeService currencyExchangeService;

    @Autowired
    public IncomeService(IncomeRepository incomeRepository, CurrencyExchangeService currencyExchangeService) {
        this.incomeRepository = incomeRepository;
        this.currencyExchangeService = currencyExchangeService;
    }

    @Transactional
    public Income saveIncome(AddIncomeRequest addIncomeRequest) {
        User user = userRepository.findById(addIncomeRequest.getUser_id())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + addIncomeRequest.getUser_id()));
        Income income = new Income();
        income.setAmount(addIncomeRequest.getAmount());
        income.setCategory(addIncomeRequest.getCategory());
        income.setSource(addIncomeRequest.getSource());
        income.setDate(addIncomeRequest.getDate());
        income.setStatus(addIncomeRequest.getStatus());
        income.setLocation(addIncomeRequest.getLocation());
        income.setUser(user);
        incomeRepository.save(income);
        return income;
    }

    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    public List<Income> updateAllIncomesAfterCurrencyExchange(String inputCurrency,String outputCurrency) {
        List<Income> incomes = incomeRepository.findAll();

        // Convert each income's amount based on the currency rates
        for (Income income : incomes) {
            try {
                double convertedAmount = currencyExchangeService.convertCurrency(inputCurrency, outputCurrency, (double) income.getAmount());
                income.setAmount((float) convertedAmount);
                incomeRepository.save(income);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return incomes;
    }
}

