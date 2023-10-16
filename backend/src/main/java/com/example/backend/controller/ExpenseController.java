package com.example.backend.controller;

import com.example.backend.model.Income;
import com.example.backend.model.NormalExpense;
import com.example.backend.model.RecurringExpense;
import com.example.backend.payload.request.AddExpenseRequest;
import com.example.backend.payload.request.AddRecurringExpenseRequest;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.security.services.ExpenseService;
import com.example.backend.security.services.RecurringExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    // Create a new expense
    @PostMapping("/addExpense")
    public ResponseEntity<?> createExpense(@RequestBody AddExpenseRequest addExpenseRequest) {
        NormalExpense createExpense = expenseService.saveExpense(addExpenseRequest);
        if(createExpense == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Investment created unsuccessfully. Please check again!"));
        }
        return ResponseEntity.ok(new MessageResponse("Expense created successfully!"));
    }

    // Get all expenses
    @GetMapping
    public ResponseEntity<List<NormalExpense>> getAllExpenses() {
        List<NormalExpense> expenses = expenseService.getAllExpenses();
        return ResponseEntity.ok(expenses);
    }
}
