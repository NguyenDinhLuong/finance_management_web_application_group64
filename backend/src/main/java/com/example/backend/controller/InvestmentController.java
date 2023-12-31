package com.example.backend.controller;

import com.example.backend.model.Income;
import com.example.backend.model.Investment;
import com.example.backend.payload.request.AddInvestmentRequest;
import com.example.backend.payload.request.UpdateIncomeRequest;
import com.example.backend.payload.request.UpdateInvestmentRequest;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.repository.InvestmentRepository;
import com.example.backend.security.services.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/investment")
public class InvestmentController {
    @Autowired
    private InvestmentService investmentService;
    @Autowired
    InvestmentRepository investmentRepository;

    // Create a new investment
    @PostMapping("/addInvestment")
    public ResponseEntity<?>  createInvestment(@RequestBody AddInvestmentRequest addInvestmentRequest) {
        Investment createInvestment = investmentService.saveInvestment(addInvestmentRequest);
        if(createInvestment == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Investment created unsuccessfully. Please check again!"));
        }
        return ResponseEntity.ok(new MessageResponse("Investment created successfully!"));
    }

    // Get a specific investment by investmentId
    @GetMapping("/specificInvestment/{investmentId}")
    public ResponseEntity<Investment> getInvestmentById(@PathVariable Long investmentId) {
        Optional<Investment> investmentData = investmentRepository.findById(investmentId);

        return investmentData.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Get all investments
    @GetMapping("/{userId}")
    public ResponseEntity<List<Investment>> getInvestmentsByUserId(@PathVariable Long userId) {
        List<Investment> userInvestments = investmentService.getAllInvestmentsByUserId(userId);
        if(userInvestments.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no incomes are found for the user
        }
        return ResponseEntity.ok(userInvestments); // Return 200 OK with the list of incomes for the user
    }

    @GetMapping("/totalAmount/{userId}")
    public ResponseEntity<Float> getTotalInvestmentAmountByUserId(@PathVariable Long userId) {
        float totalAmount = investmentService.getTotalInvestmentAmountByUserId(userId);
        return ResponseEntity.ok(totalAmount);
    }

    @PutMapping("/updateCurrencyExchange/{inputCurrency}/{outputCurrency}")
    public ResponseEntity<List<Investment>> updateAllInvestmentsAfterCurrencyExchange(@PathVariable String inputCurrency, @PathVariable String outputCurrency) {

        List<Investment> updatedInvestments = investmentService.updateAllInvestmentsAfterCurrencyExchange(inputCurrency, outputCurrency);

        if(updatedInvestments.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no incomes are found/updated
        }

        return ResponseEntity.ok(updatedInvestments); // Return 200 OK with the list of updated incomes
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Investment> updateInvestment(@PathVariable Long id, @RequestBody UpdateInvestmentRequest updateInvestmentRequest) {
        try {
            Investment investment = investmentService.updateInvestment(id, updateInvestmentRequest);
            return new ResponseEntity<>(investment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteInvestment/{id}")
    public ResponseEntity<?> deleteInvestment(@PathVariable Long id) {
        // Check if the investment with the given id exists
        Optional<Investment> investmentData = investmentRepository.findById(id);

        if (investmentData.isPresent()) {
            investmentRepository.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("Investment deleted successfully!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Investment not found with the provided ID."));
        }
    }
}

