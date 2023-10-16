package com.example.backend.security.services;

import com.example.backend.model.Income;
import com.example.backend.model.NormalExpense;
import com.example.backend.model.User;
import com.example.backend.payload.request.AddExpenseRequest;
import com.example.backend.repository.ExpenseRepository;
import com.example.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {
    @Autowired
    private final ExpenseRepository expenseRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Transactional
    public NormalExpense saveExpense(AddExpenseRequest addExpenseRequest) {
        User user = userRepository.findById(addExpenseRequest.getUser_id())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + addExpenseRequest.getUser_id()));
        NormalExpense expense = new NormalExpense();

        expense.setAmount(addExpenseRequest.getAmount());
        expense.setCategory(addExpenseRequest.getCategory());
        expense.setDate(addExpenseRequest.getDate());
        expense.setLocation(addExpenseRequest.getLocation());
        expense.setCurrency(addExpenseRequest.getCurrency());
        expense.setStatus(addExpenseRequest.getStatus());
        expense.setPaymentMethod(addExpenseRequest.getPaymentMethod());
        expense.setUser(user);
        expenseRepository.save(expense);
        return expense;
    }

    public List<NormalExpense> getAllExpenses() {
        return expenseRepository.findAll();
    }
}
