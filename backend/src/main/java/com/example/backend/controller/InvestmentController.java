package com.example.backend.controller;

import com.example.backend.model.Investment;
import com.example.backend.security.services.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/investment")
public class InvestmentController {
    @Autowired
    private InvestmentService investmentService;

    @PostMapping("/addInvestment")
    public Investment createInvestment(@RequestBody Investment investment) {
        return investmentService.addInvestment(investment);
    }
}

