package com.example.backend.security.services;

import com.example.backend.model.NormalExpense;
import com.example.backend.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {
    @Autowired
    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public NormalExpense addExpense(NormalExpense expense) {
        return expenseRepository.save(expense);
    }

    public ArrayList<Expense>
}
