package com.example.backend.security.services;

import com.example.backend.model.NormalExpense;
import com.example.backend.model.RecurringExpense;
import com.example.backend.model.User;
import com.example.backend.payload.request.AddExpenseRequest;
import com.example.backend.payload.request.AddRecurringExpenseRequest;
import com.example.backend.repository.RecurringExpenseRepository;
import com.example.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RecurringExpenseService {
    @Autowired
    private final RecurringExpenseRepository recurringExpenseRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    public RecurringExpenseService(RecurringExpenseRepository recurringExpenseRepository) {
        this.recurringExpenseRepository = recurringExpenseRepository;
    }

    @Transactional
    public RecurringExpense saveRecurringExpense(AddRecurringExpenseRequest addRecurringExpenseRequest) {
        User user = userRepository.findById(addRecurringExpenseRequest.getUser_id())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + addRecurringExpenseRequest.getUser_id()));
        RecurringExpense recurringExpense = new RecurringExpense();

        recurringExpense.setAmount(addRecurringExpenseRequest.getAmount());
        recurringExpense.setCategory(addRecurringExpenseRequest.getCategory());
        recurringExpense.setStartDate(addRecurringExpenseRequest.getStartDate());
        recurringExpense.setLocation(addRecurringExpenseRequest.getLocation());
        recurringExpense.setCurrency(addRecurringExpenseRequest.getCurrency());
        recurringExpense.setFrequency(addRecurringExpenseRequest.getFrequency());
        recurringExpense.setEndDate(addRecurringExpenseRequest.getEndDate());
        recurringExpense.setUser(user);
        recurringExpenseRepository.save(recurringExpense);
        return recurringExpense;
    }

    public List<RecurringExpense> getAllRecurringExpenses() {
        return recurringExpenseRepository.findAll();
    }
}
