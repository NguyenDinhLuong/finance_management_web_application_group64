package com.example.backend.security.services;

import com.example.backend.model.Investment;
import com.example.backend.model.NormalExpense;
import com.example.backend.model.RecurringExpense;
import com.example.backend.model.User;
import com.example.backend.payload.request.AddInvestmentRequest;
import com.example.backend.repository.InvestmentRepository;
import com.example.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvestmentService {
    @Autowired
    private final InvestmentRepository investmentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private final CurrencyExchangeService currencyExchangeService;

    @Autowired
    public InvestmentService(InvestmentRepository investmentRepository, CurrencyExchangeService currencyExchangeService) {
        this.investmentRepository = investmentRepository;
        this.currencyExchangeService = currencyExchangeService;
    }

    @Transactional
    public Investment saveInvestment(AddInvestmentRequest addInvestmentRequest) {
        User user = userRepository.findById(addInvestmentRequest.getUser_id())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + addInvestmentRequest.getUser_id()));
        Investment investment = new Investment();
        investment.setAmount(addInvestmentRequest.getAmount());
        investment.setCategory(addInvestmentRequest.getCategory());
        investment.setCurrency(addInvestmentRequest.getCurrency());
        investment.setDate(addInvestmentRequest.getDate());
        investment.setDuration(addInvestmentRequest.getDuration());
        investment.setRisk(addInvestmentRequest.getRisk());
        investment.setLiquidity(addInvestmentRequest.getLiquidity());
        investment.setUser(user);
        investmentRepository.save(investment);
        return investment;
    }

    public List<Investment> getAllInvestmentsByUserId(Long userId) {
        return investmentRepository.findByUserId(userId);
    }

    public List<Investment> updateAllInvestmentsAfterCurrencyExchange(String inputCurrency,String outputCurrency) {
        List<Investment> investments = investmentRepository.findAll();

        // Convert each income's amount based on the currency rates
        for (Investment investment : investments) {
            try {
                double convertedAmount = currencyExchangeService.convertCurrency(inputCurrency, outputCurrency, (double) investment.getAmount());
                investment.setAmount((float) convertedAmount);
                investment.setCurrency(outputCurrency);
                investmentRepository.save(investment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return investments;
    }

    public float getTotalInvestmentAmountByUserId(Long userId) {
        List<Investment> investments = investmentRepository.findByUserId(userId);
        float totalAmount = 0;

        for (Investment investment : investments) {
            totalAmount += investment.getAmount();
        }
        return totalAmount;
    }
}