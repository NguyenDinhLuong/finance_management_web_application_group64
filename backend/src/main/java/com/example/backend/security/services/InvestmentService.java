package com.example.backend.security.services;

import com.example.backend.model.Investment;
import com.example.backend.model.User;
<<<<<<< HEAD
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
=======
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
        investment.setDate(addInvestmentRequest.getDate());
        investment.setDuration(addInvestmentRequest.getDuration());
        investment.setRisk(addInvestmentRequest.getRisk());
        investment.setLiquidity(addInvestmentRequest.getLiquidity());
        investment.setUser(user);
        investmentRepository.save(investment);
        return investment;
    }

    public List<Investment> getAllInvestments() {
        return investmentRepository.findAll();
    }

    public List<Investment> updateAllInvestmentsAfterCurrencyExchange(String inputCurrency,String outputCurrency) {
        List<Investment> investments = investmentRepository.findAll();

        // Convert each income's amount based on the currency rates
        for (Investment investment : investments) {
            try {
                double convertedAmount = currencyExchangeService.convertCurrency(inputCurrency, outputCurrency, (double) investment.getAmount());
                investment.setAmount((float) convertedAmount);
                investmentRepository.save(investment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return investments;
>>>>>>> 2e2ca8a867449f98c42680b2e288cb6789361134
    }
}
