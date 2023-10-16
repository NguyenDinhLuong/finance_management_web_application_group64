package com.example.backend.controller;

import com.example.backend.model.NormalExpense;
import com.example.backend.model.RecurringExpense;
import com.example.backend.payload.request.AddRecurringExpenseRequest;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.security.services.RecurringExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/recurringExpenses")
public class RecurringExpenseController {
    @Autowired
    private RecurringExpenseService recurringExpenseService;
    // Create a new recurring expense
    @PostMapping("/addRecurringExpense")
    public ResponseEntity<?> createRecurringExpense (@RequestBody AddRecurringExpenseRequest addRecurringExpenseRequest) {
        RecurringExpense createRecurringExpense = recurringExpenseService.saveRecurringExpense(addRecurringExpenseRequest);
        if(createRecurringExpense == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Recurring expense created unsuccessfully. Please check again!"));
        }
        return ResponseEntity.ok(new MessageResponse("Recurring Expense created successfully!"));
    }

    // Get all recurring expenses
    @GetMapping
    public ResponseEntity<List<RecurringExpense>> getAllExpenses() {
        List<RecurringExpense> recurringExpenses = recurringExpenseService.getAllRecurringExpenses();
        return ResponseEntity.ok(recurringExpenses);
    }
}
