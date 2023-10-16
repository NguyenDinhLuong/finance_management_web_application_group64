package com.example.backend.security.services;

import com.example.backend.model.Income;
import com.example.backend.model.Investment;
import com.example.backend.model.User;
import com.example.backend.payload.request.AddIncomeRequest;
import com.example.backend.payload.request.AddInvestmentRequest;
import com.example.backend.repository.InvestmentRepository;
import com.example.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
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
    public InvestmentService(InvestmentRepository investmentRepository) {
        this.investmentRepository = investmentRepository;
    }

    @Transactional
    public Investment saveInvestment(AddInvestmentRequest addInvestmentRequest) {
        User user = userRepository.findById(addInvestmentRequest.getUser_id())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + addInvestmentRequest.getUser_id()));
        Investment investment = new Investment();
        investment.setAmount(addInvestmentRequest.getAmount());
        investment.setCategory(addInvestmentRequest.getCategory());
        investment.setDate(addInvestmentRequest.getDate());
        investment.setDuration(addInvestmentRequest.getDuration());
        investment.setCurrency(addInvestmentRequest.getCurrency());
        investment.setRisk(addInvestmentRequest.getRisk());
        investment.setLiquidity(addInvestmentRequest.getLiquidity());
        investment.setUser(user);
        investmentRepository.save(investment);
        return investment;
    }

    public List<Investment> getAllInvestments() {
        return investmentRepository.findAll();
    }
}
