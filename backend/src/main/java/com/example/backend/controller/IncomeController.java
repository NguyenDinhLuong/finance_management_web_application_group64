package com.example.backend.controller;

import com.example.backend.model.Income;
import com.example.backend.payload.request.AddIncomeRequest;
import com.example.backend.payload.request.UpdateIncomeRequest;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.repository.IncomeRepository;
import com.example.backend.security.services.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/incomes")
public class IncomeController {
    @Autowired
    IncomeService incomeService;
    @Autowired
    IncomeRepository incomeRepository;

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
    @GetMapping("/{userId}")
    public ResponseEntity<List<Income>> getIncomesByUserId(@PathVariable Long userId) {
        List<Income> userIncomes = incomeService.getAllIncomesByUserId(userId);
        if(userIncomes.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no incomes are found for the user
        }
        return ResponseEntity.ok(userIncomes); // Return 200 OK with the list of incomes for the user
    }

    // Get a specific income by incomeId
    @GetMapping("/specificIncome/{incomeId}")
    public ResponseEntity<Income> getIncomeById(@PathVariable Long incomeId) {
        Optional<Income> incomeData = incomeRepository.findById(incomeId);

        return incomeData.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/totalAmount/{userId}")
    public ResponseEntity<Float> getTotalIncomeAmountByUserId(@PathVariable Long userId) {
        float totalAmount = incomeService.getTotalIncomeAmountByUserId(userId);
        return ResponseEntity.ok(totalAmount);
    }

    @PutMapping("/updateCurrencyExchange/{inputCurrency}/{outputCurrency}")
    public ResponseEntity<List<Income>> updateAllIncomesAfterCurrencyExchange(@PathVariable String inputCurrency, @PathVariable String outputCurrency) {

        List<Income> updatedIncomes = incomeService.updateAllIncomesAfterCurrencyExchange(inputCurrency, outputCurrency);

        if(updatedIncomes.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no incomes are found/updated
        }

        return ResponseEntity.ok(updatedIncomes); // Return 200 OK with the list of updated incomes
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Income> updateIncome(@PathVariable Long id, @RequestBody UpdateIncomeRequest updateIncomeRequest) {
        try {
            Income income = incomeService.updateIncome(id, updateIncomeRequest);
            return new ResponseEntity<>(income, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteIncome/{id}")
    public ResponseEntity<?> deleteIncome(@PathVariable Long id) {
        // Check if the income with the given id exists
        Optional<Income> incomeData = incomeRepository.findById(id);

        if (incomeData.isPresent()) {
            incomeRepository.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("Income deleted successfully!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Income not found with the provided ID."));
        }
    }
}
