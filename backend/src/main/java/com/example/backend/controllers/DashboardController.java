package com.example.backend.controllers;

import com.example.backend.Currency;
import com.example.backend.Dashboard;
import com.example.backend.Expenses;
import com.example.backend.controller.ExpenseController;
import com.example.backend.repository.ExpenseRepository;
import com.example.backend.security.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class DashboardController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/barchart")
    public HashMap<String, Double> getBarChart(@RequestParam ArrayList<Expenses> expenses) {

        Dashboard dashboard = new Dashboard();

        return dashboard.generateBarChart(expenses);

    }

    @GetMapping("/piechart")
    public HashMap<String, Double> getPieChart(@RequestParam ArrayList<Expenses> expenses) {

        expenseService.retrieveExpenses()

        Dashboard dashboard = new Dashboard();

        return dashboard.getExpensesInCategories(expenses);
    }



}
