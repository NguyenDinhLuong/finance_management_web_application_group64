package com.financemanagementwebapp.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EnterIncomeRequest {
    @NotBlank
    private int year;

    @NotBlank
    private String resident;

    @NotBlank
    private double Income;
}
