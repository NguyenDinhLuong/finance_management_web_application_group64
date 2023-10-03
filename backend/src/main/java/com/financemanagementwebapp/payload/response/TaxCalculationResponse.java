package com.financemanagementwebapp.payload.response;

import lombok.Data;

@Data
public class TaxCalculationResponse {
    private double afterTax;

    public TaxCalculationResponse(double afterTax){
        this.afterTax = afterTax;
    }
}
