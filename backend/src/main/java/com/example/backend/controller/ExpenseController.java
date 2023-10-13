package com.example.backend.controller;

import com.example.backend.model.NormalExpense;
import com.example.backend.model.RecurringExpense;
import com.example.backend.security.services.ExpenseService;
import com.example.backend.security.services.RecurringExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private RecurringExpenseService recurringExpenseService;

    @PostMapping("/addExpense")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public NormalExpense addExpense (@RequestBody NormalExpense expense) {
        return expenseService.addExpense(expense);
    }

    @PostMapping("/addRecurringExpense")
    public RecurringExpense addRecurringExpense (@RequestBody RecurringExpense expense) {
        return recurringExpenseService.addRecurringExpense(expense);
    }
}
