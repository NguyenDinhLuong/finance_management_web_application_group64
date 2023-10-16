package com.example.backend;

import java.time.LocalDate;

public class Expenses {

    private final Long id;
    private final String category;
    private final Double amount;
    private final String currency;
    private final String description;
    private final LocalDate date;


    public Expenses(Long id, String category, Double amount, String currency, String description, LocalDate date) {
        this.id = id;
        this.category = category;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public Double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }









}
