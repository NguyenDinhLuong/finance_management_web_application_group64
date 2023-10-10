package com.example.backend;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class RecurringExpense extends Expenses {

    private LocalDate dueDate;
    private Integer interval; // amount of days expense re-occurs
    private Integer reminder; // days before to remind
    private Boolean paid;

    public RecurringExpense(int id, String category, Double amount, String currency, String description, LocalDate date, LocalDate dueDate, Integer interval, Integer reminder) {
        super(id, category, amount, currency, description, date);
        this.interval = interval;
        this.reminder = reminder;
        this.dueDate = dueDate;
    }

    public RecurringExpense(int id, String category, Double amount, String currency, String description, LocalDate date, LocalDate dueDate, Integer interval, Integer reminder, Boolean paid) {
        super(id, category, amount, currency, description, date);
        this.interval = interval;
        this.reminder = reminder;
        this.dueDate = dueDate;
        this.paid = paid;
    }

    /**
     * This function checks if the due date has passed.
     * If due date has passed, adjust the due date to the next bill.
     */
    public void updateDueDate() {
        LocalDate thisDate = LocalDate.now();
        if(thisDate.isAfter(dueDate) || paid) {
            dueDate = dueDate.plusDays(interval);
        }
    }

    public Boolean remind() {
        LocalDate thisDate = LocalDate.now();
    }

}
