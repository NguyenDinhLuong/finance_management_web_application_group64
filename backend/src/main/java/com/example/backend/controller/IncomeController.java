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
//
//    // Get a single income by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<Income> getIncomeById(@PathVariable Long id) {
//        Income income = incomeService.getIncomeById(id);
//        if (income != null) {
//            return ResponseEntity.ok(income);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    // Update an income by ID
//    @PutMapping("/{id}")
//    public ResponseEntity<Income> updateIncome(@PathVariable Long id, @RequestBody Income updatedIncome) {
//        Income income = incomeService.updateIncome(id, updatedIncome);
//        if (income != null) {
//            return ResponseEntity.ok(income);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    // Delete an income by ID
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
//        if (incomeService.deleteIncome(id)) {
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
}
