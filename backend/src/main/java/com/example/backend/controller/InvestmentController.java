package com.example.backend.controller;

import com.example.backend.model.Investment;
import com.example.backend.model.User;
import com.example.backend.payload.request.InvestmentRequest;
import com.example.backend.payload.request.UpdateInvestmentRequest;
import com.example.backend.payload.response.AllInvestmentResponse;
import com.example.backend.payload.response.InvestmentInfoResponse;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.repository.InvestmentRepository;
import com.example.backend.security.services.InvestmentService;
import com.example.backend.security.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/investment")
public class InvestmentController {

    @Autowired
    UserService userService;

    @Autowired
    InvestmentService investmentService;

    @Autowired
    InvestmentRepository investmentRepository;

    @PostMapping("/add")
    public ResponseEntity<?> AddInvestment(@Valid @RequestBody InvestmentRequest investmentRequest){

        User user = userService.findUserById(investmentRequest.getUserID());

        Investment investment = new Investment(user,
                investmentRequest.getInvestmentType(),
                investmentRequest.getAmount(),
                investmentRequest.getDate(),
                investmentRequest.getDescription());

        investmentRepository.save(investment);

        return ResponseEntity.ok(new InvestmentInfoResponse(investment));
    }

    @PostMapping("/update")
    public ResponseEntity<?> UpdateInvestment(@Valid @RequestBody UpdateInvestmentRequest updateInvestmentRequest){

        try{
            Investment investment = investmentService.updateInvestment(updateInvestmentRequest);
            investmentRepository.save(investment);

            return ResponseEntity.ok(new InvestmentInfoResponse(investment));

        }catch (EntityNotFoundException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Investment not exist."));
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteInvestment(@Valid @RequestParam Long id){
        try{
            Investment investment = investmentService.findInvestmentById(id);
            investmentRepository.delete(investment);

            return ResponseEntity.ok(new MessageResponse("Investment successfully deleted" + id));

        }catch (EntityNotFoundException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Investment not found" + id));
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllInvestment(){
        List<Investment> investmentList = investmentRepository.findAll();
        return ResponseEntity.ok(new AllInvestmentResponse(investmentList));
    }

    @GetMapping("/getById")
    public ResponseEntity<?> getInvestmentById(@Valid @RequestParam Long id){
        try{
            Investment investment = investmentService.findInvestmentById(id);

            return ResponseEntity.ok(new InvestmentInfoResponse(investment));

        }catch (EntityNotFoundException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Investment not found" + id));
        }
    }

}
