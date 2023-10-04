package com.financemanagementwebapp.controller;

import com.financemanagementwebapp.entity.Income;
import com.financemanagementwebapp.entity.User;
import com.financemanagementwebapp.payload.request.EnterIncomeRequest;
import com.financemanagementwebapp.payload.response.TaxCalculationResponse;
import jakarta.validation.Valid;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

@RestController
public class RestTaxController {

    Double tax;
    private SessionFactory factory = new Configuration()
            .configure("src/main/resources/hibernate.config.xml")
            .buildSessionFactory();

//    two ways of enter income
//    text field
//    get own income from cookie

    @PostMapping("/enterIncome")
    public ResponseEntity<?> enterIncomeTax(@RequestParam Long income,
                            @RequestParam int year,
                            @RequestParam String residentialOption){

        if(residentialOption.equals("Non-Resident")){

            if(year <= 2002){
                tax = income*0.3;
            }else {
                tax = income*0.325;
            }

        }else{

            if(year <= 2002){
                tax = income*0.15;
            }else {
                tax = income*0.175;
            }

        }

        return ResponseEntity.ok(new TaxCalculationResponse(tax));
    }


    @GetMapping
    public ResponseEntity<?> getAllIncome(){
        Session session = factory.getCurrentSession();

        try{
            session.beginTransaction();
            session.getTransaction();
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
