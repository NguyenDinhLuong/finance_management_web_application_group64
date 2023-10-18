//package com.example.backend.controller;
//
//import com.example.backend.model.Income;
//import com.example.backend.payload.request.AddIncomeRequest;
//import jakarta.validation.Valid;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//public class IncomeAndExpenseController {
//
//    @PostMapping("/addIncome")
//    public ResponseEntity<?> addIncome(@Valid @RequestBody AddIncomeRequest addIncomeRequest){
//        Income income = new Income(addIncomeRequest.getAmount(),
//                addIncomeRequest.getSource(),
//                addIncomeRequest.getDate(),
//                addIncomeRequest.getDescription());
//    }
//}
