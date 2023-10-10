package com.example.backend.security.services;

import com.example.backend.model.Investment;
import com.example.backend.model.User;
import com.example.backend.payload.request.UpdateInvestmentRequest;
import com.example.backend.repository.InvestmentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvestmentService {

    private final InvestmentRepository investmentRepository;

    @Autowired
    public InvestmentService(InvestmentRepository investmentRepository){
        this.investmentRepository = investmentRepository;
    }

    @Transactional
    public Investment updateInvestment(UpdateInvestmentRequest request){
        Investment investment = investmentRepository.findById(request.getId()).
                orElseThrow(()-> new EntityNotFoundException("Investment" + request.getId() + "does not exist."));

        investment.setDate(request.getDate());
        investment.setAmount(request.getAmount());
        investment.setType(request.getInvestmentType());
        investment.setDescription(request.getDescription());

        return investment;
    }

    @Transactional
    public Investment findInvestmentById(Long id){
        return investmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Investment not found with id " + id));
    }
}
