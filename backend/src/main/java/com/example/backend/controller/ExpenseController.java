package com.example.backend.controller;

import com.example.backend.model.NormalExpense;
import com.example.backend.security.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/addExpense")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public NormalExpense addExpense (@RequestBody NormalExpense expense) {
        return expenseService.addExpense(expense);
    }


}
