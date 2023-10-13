package com.example.backend.security.services;

import com.example.backend.model.Investment;
import com.example.backend.repository.InvestmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvestmentService {
    @Autowired
    private final InvestmentRepository investmentRepository;

    @Autowired
    public InvestmentService(InvestmentRepository investmentRepository) {
        this.investmentRepository = investmentRepository;
    }

    public Investment addInvestment(Investment investment) {
        return investmentRepository.save(investment);
    }
}
