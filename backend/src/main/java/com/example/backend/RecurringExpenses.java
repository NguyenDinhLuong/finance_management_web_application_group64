package com.example.backend;

import java.time.LocalDate;

public class RecurringExpenses extends Expenses {

    private LocalDate endDate;
    private Integer frequency;

    public RecurringExpenses(Long id, String category, Double amount, String currency, String description, LocalDate date, LocalDate endDate, Integer frequency) {
        super(id, category, amount, currency, description, date);
        this.endDate = endDate;
        this.frequency = frequency;
    }

    public LocalDate getEndDate() { return this.endDate; }

    public Integer getFrequency() { return this.frequency; }

    public Expenses convertToStaticExpense() { return new Expenses(this.getId(), this.getCategory(), this.getAmount(), this.getCurrency(), this.getDescription(), this.getDate()); }

    public Expenses convertToStaticExpense(LocalDate date) { return new Expenses(this.getId(), this.getCategory(), this.getAmount(), this.getCurrency(), this.getDescription(), date); }

}
