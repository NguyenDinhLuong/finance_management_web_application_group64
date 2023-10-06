package com.example.backend.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EnterIncomeRequest {

    @NotBlank
    private double income;
    private int year;
    private String residential;

}
