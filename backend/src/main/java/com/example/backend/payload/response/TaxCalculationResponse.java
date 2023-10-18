package com.example.backend.payload.response;

import lombok.Data;

@Data
public class TaxCalculationResponse {
    private double tax;

    public TaxCalculationResponse(double tax){
        this.tax = tax;
    }
}
