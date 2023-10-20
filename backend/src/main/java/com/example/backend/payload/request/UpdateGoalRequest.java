package com.example.backend.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateGoalRequest {
    @NotBlank
    private float targetIncome;
    @NotBlank
    private float maximumExpense;
    @NotBlank
    private float maximumInvestment;
    @NotBlank
    private String currency;
}
