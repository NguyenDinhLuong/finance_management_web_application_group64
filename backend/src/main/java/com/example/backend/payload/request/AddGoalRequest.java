package com.example.backend.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class AddGoalRequest {
    @NotBlank
    private float targetIncome;
    @NotBlank
    private float maximumExpense;
    @NotBlank
    private float maximumInvestment;
    @NotBlank
    private String currency;
    @NotBlank
    private Long user_id;
}
