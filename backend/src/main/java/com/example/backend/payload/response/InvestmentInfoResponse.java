package com.example.backend.payload.response;

import com.example.backend.model.Investment;
import lombok.Data;

@Data
public class InvestmentInfoResponse {
    private Long id;
    private String category;
    private double amount;
    private String risk;
    private String date;

    private String duration;


    public InvestmentInfoResponse(Investment investment){
        this.id = investment.getId();
        this.category = investment.getCategory();
        this.amount = investment.getAmount();
        this.risk = investment.getRisk();
        this.date = investment.getDate().toString();
        this.duration = investment.getDuration();
    }
}
