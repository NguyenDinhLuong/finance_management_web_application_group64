package com.example.backend.controller;

import com.example.backend.model.Income;
import com.example.backend.security.services.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {
    @Autowired
    private IncomeService incomeService;

    @PostMapping("/addIncome")
    public Income createIncome(@RequestBody Income income) {
        return incomeService.addIncome(income);
    }
}


