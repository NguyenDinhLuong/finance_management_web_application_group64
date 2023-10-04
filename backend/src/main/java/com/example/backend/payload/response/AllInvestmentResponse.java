package com.example.backend.payload.response;

import com.example.backend.model.Income;
import com.example.backend.model.Investment;
import lombok.Data;

import java.util.List;

@Data
public class AllInvestmentResponse {

    private List<Investment> investmentList;

    public AllInvestmentResponse(List<Investment> investmentList){
        this.investmentList = investmentList;
    }
}
