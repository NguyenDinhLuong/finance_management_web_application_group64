package com.example.backend.controller;

import com.example.backend.model.RecurringExpense;
import com.example.backend.payload.request.AddRecurringExpenseRequest;
import com.example.backend.payload.request.UpdateRecurringExpenseRequest;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.repository.RecurringExpenseRepository;
import com.example.backend.security.services.RecurringExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/recurringExpenses")
public class RecurringExpenseController {
    @Autowired
    private RecurringExpenseService recurringExpenseService;
    @Autowired
    RecurringExpenseRepository recurringExpenseRepository;
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
    @GetMapping("/{userId}")
    public ResponseEntity<List<RecurringExpense>> getRecurringExpensesByUserId(@PathVariable Long userId) {
        List<RecurringExpense> recurringExpenses = recurringExpenseService.getAllRecurringExpensesByUserId(userId);
        if(recurringExpenses.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no incomes are found for the user
        }
        return ResponseEntity.ok(recurringExpenses); // Return 200 OK with the list of incomes for the user
    }

    // Get a specific expense by expenseId
    @GetMapping("/specificRecurringExpense/{recurringExpenseId}")
    public ResponseEntity<RecurringExpense> getRecurringExpenseById(@PathVariable Long recurringExpenseId) {
        Optional<RecurringExpense> recurringExpense = recurringExpenseRepository.findById(recurringExpenseId);

        return recurringExpense.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/totalAmount/{userId}")
    public ResponseEntity<Float> getTotalRecurringExpenseAmountByUserId(@PathVariable Long userId) {
        float totalAmount = recurringExpenseService.getTotalRecurringExpenseAmountByUserId(userId);
        return ResponseEntity.ok(totalAmount);
    }

    @PutMapping("/updateCurrencyExchange/{inputCurrency}/{outputCurrency}")
    public ResponseEntity<List<RecurringExpense>> updateAllRecurringExpensesAfterCurrencyExchange(@PathVariable String inputCurrency, @PathVariable String outputCurrency) {

        List<RecurringExpense> updatedRecurringExpenses = recurringExpenseService.updateAllRecurringExpensesAfterCurrencyExchange(inputCurrency, outputCurrency);

        if(updatedRecurringExpenses.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no incomes are found/updated
        }
        return ResponseEntity.ok(updatedRecurringExpenses); // Return 200 OK with the list of updated incomes
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RecurringExpense> updateRecurringExpense(@PathVariable Long id, @RequestBody UpdateRecurringExpenseRequest updateRecurringExpenseRequest) {
        try {
            RecurringExpense recurringExpense = recurringExpenseService.updateRecurringExpense(id, updateRecurringExpenseRequest);
            return new ResponseEntity<>(recurringExpense, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteRecurringExpense/{id}")
    public ResponseEntity<?> deleteRecurringExpense(@PathVariable Long id) {
        // Check if the recurring expense with the given id exists
        Optional<RecurringExpense> recurringExpenseData = recurringExpenseRepository.findById(id);

        if (recurringExpenseData.isPresent()) {
            recurringExpenseRepository.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("Recurring Expense deleted successfully!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Recurring Expense not found with the provided ID."));
        }
    }
}
