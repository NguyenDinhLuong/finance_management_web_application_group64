package com.example.backend.payload.response;

import com.example.backend.model.Income;
import lombok.Data;

import java.util.List;

@Data
public class AllIncomeResponse {

    private List<Income> incomeList;

    public AllIncomeResponse(List<Income> incomeList){
        this.incomeList = incomeList;
    }
}
