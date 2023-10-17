package com.example.backend.controller;

import com.example.backend.model.Income;
import com.example.backend.payload.request.AddIncomeRequest;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.security.services.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/incomes")
public class IncomeController {
    @Autowired
    IncomeService incomeService;

    // Create a new income
    @PostMapping("/addIncome")
    public ResponseEntity<?> createIncome(@RequestBody AddIncomeRequest addIncomeRequest) {
        Income createIncome = incomeService.saveIncome(addIncomeRequest);
        if(createIncome == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Income created unsuccessfully. Please check again!"));
        }
        return ResponseEntity.ok(new MessageResponse("Income created successfully!"));
    }

    // Get all incomes
    @GetMapping
    public ResponseEntity<List<Income>> getAllIncomes() {
        List<Income> incomes = incomeService.getAllIncomes();
        return ResponseEntity.ok(incomes);
    }

    @PutMapping("/updateCurrencyExchange/{inputCurrency}/{outputCurrency}")
    public ResponseEntity<List<Income>> updateAllIncomesAfterCurrencyExchange(@PathVariable String inputCurrency, @PathVariable String outputCurrency) {

        List<Income> updatedIncomes = incomeService.updateAllIncomesAfterCurrencyExchange(inputCurrency, outputCurrency);

        if(updatedIncomes.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no incomes are found/updated
        }

        return ResponseEntity.ok(updatedIncomes); // Return 200 OK with the list of updated incomes
    }
}
