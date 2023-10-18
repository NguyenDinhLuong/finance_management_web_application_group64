package com.example.backend.controller;

import com.example.backend.model.Expense;
import com.example.backend.model.NormalExpense;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.security.services.ExpenseService;
import com.example.backend.security.services.StorageService;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/receipt")
public class ReceiptController {

    @Autowired
    private ExpenseService expenseService;
    private final StorageService storageService;

    @Autowired
    public ReceiptController(StorageService storageService){
        this.storageService = storageService;
    }

    @PostMapping("/uploadReceipt")
    public ResponseEntity<?> uploadReceipt(@RequestParam("file")MultipartFile file,
                                        RedirectAttributes redirectAttributes) throws Exception {
        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                file.getOriginalFilename() + " Receipt Uploaded.");

        String filePath = storageService.load(file.getName()).toString();

        CSVReader reader = new CSVReader(new FileReader(filePath));

        List<String[]> rows = reader.readAll();

        List<NormalExpense> expenses = new ArrayList<>();

        for(String[] row :rows){

            NormalExpense expense = new NormalExpense();

            expense.setAmount(Float.parseFloat(row[0]));
            expense.setCategory(row[1]);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            expense.setDate(format.parse(row[2]));
            expense.setLocation(row[3]);
            expense.setStatus(row[4]);
            expense.setPaymentMethod(row[5]);

            expenses.add(expense);

            expenseService.uploadExpenseFromReceipt(expense);
        }

        return ResponseEntity.ok(new MessageResponse(" Receipt Uploaded!"));
    }

//    @GetMapping("/downloadReceipt")
//    public ResponseEntity<?> downloadReceipt(String filename){
//
//    }
}
