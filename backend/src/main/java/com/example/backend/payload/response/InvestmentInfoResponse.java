package com.example.backend.payload.response;

import com.example.backend.model.Investment;
import lombok.Data;

@Data
public class InvestmentInfoResponse {
    private Long id;
    private String type;
    private double amount;
    private String description;
    private String date;

    public InvestmentInfoResponse(Investment investment){
        this.id = investment.getInvestmentId();
        this.type = investment.getType().toString();
        this.amount = investment.getAmount();
        this.description = investment.getDescription();
        this.date = investment.getDate().toString();
    }
}
