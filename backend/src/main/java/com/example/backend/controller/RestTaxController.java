package com.example.backend.controller;

import com.example.backend.model.Income;
import com.example.backend.model.Investment;
import com.example.backend.model.InvestmentType;
import com.example.backend.payload.request.EnterIncomeRequest;
import com.example.backend.payload.response.AllIncomeResponse;
import com.example.backend.payload.response.AllInvestmentResponse;
import com.example.backend.payload.response.TaxCalculationResponse;
import jakarta.validation.Valid;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tax")
public class RestTaxController {
//    private SessionFactory factory = new Configuration()
//            .configure("src/main/resources/hibernate.config.xml")
//            .buildSessionFactory();

//    two ways of enter income
//    text field
//    get own income from cookie

    @PostMapping("/enterIncome")
    public ResponseEntity<?> getIncomeTax(@Valid @RequestBody EnterIncomeRequest incomeRequest){

        double tax;
        double income = incomeRequest.getIncome();

        if(incomeRequest.getResidential().equals("Non-Resident")){

            if(incomeRequest.getYear() <= 2002){
                tax = income*0.3;
            }else {
                tax = income*0.325;
            }

        }else{

            if(incomeRequest.getYear() <= 2002){
                tax = income*0.15;
            }else {
                tax = income*0.175;
            }

        }

        return ResponseEntity.ok(new TaxCalculationResponse(tax));
    }


//   should check the token/login state for security reason
//    @GetMapping("/getAllIncome")
//    public ResponseEntity<?> getAllIncome(){
//        Session session = factory.getCurrentSession();
//
//        try{
//
//            session.beginTransaction();
//            List<Income> incomeList = session.createQuery("FROM Income").getResultList();
//            session.getTransaction().commit();
//
//            return ResponseEntity.ok(new AllIncomeResponse(incomeList));
//
//        }catch (Exception e){
//
//            e.printStackTrace();
//            session.getTransaction().rollback();
//
//            return ResponseEntity.ok("No income has been created to your account.");
//        }
//    }

    @PostMapping("/getInvestmentTax")
    public ResponseEntity<?> getPortfolioTax(@RequestParam Investment investment, @RequestParam double taxRate){

        double tax;

        if(investment.getType().equals(InvestmentType.RealEstate)){

            tax = investment.getAmount()*0.003;

        }else{

            tax = investment.getAmount()*taxRate;

        }

        return ResponseEntity.ok(new TaxCalculationResponse(tax));

    }

//    @GetMapping("/getAllInvestment")
//    public ResponseEntity<?> getAllInvestment(){
//        Session session = factory.getCurrentSession();
//
//        try{
//
//            session.beginTransaction();
//            List<Investment> investmentList = session.createQuery("FROM Investment ").getResultList();
//            session.getTransaction().commit();
//
//            return ResponseEntity.ok(new AllInvestmentResponse(investmentList));
//
//        }catch (Exception e){
//
//            e.printStackTrace();
//            session.getTransaction().rollback();
//
//            return ResponseEntity.ok("No investment has been created to your account.");
//        }
//    }

}
