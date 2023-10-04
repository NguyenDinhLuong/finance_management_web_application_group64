package com.example.backend.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
@Table(name = "recurringExpenses")
public class RecurringExpense{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseId;

    @NotBlank
    private double amount;

    @Size(max = 100)
    private String category;

    @DateTimeFormat
    private Date date;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @DateTimeFormat
    private Date dueDate;

    @Size(max = 150)
    private String description;

    private int frequency;


    public RecurringExpense(double amount, String description, String category,
                            Date date, User user, int frequency) {
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.date = date;
        this.user = user;
        this.frequency = frequency;
    }

    public RecurringExpense() {

    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }

    public Date getDate() {
        return date;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public int getFrequency() {
        return frequency;
    }

    public Long getExpenseId() {
        return expenseId;
    }

    public String getCategory() {
        return category;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
