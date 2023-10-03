package com.financemanagementwebapp.entity;

import javax.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long budgetId;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private String category;

    private double amount;

    private double limit;

    public void setId(Long id) {
        this.budgetId = id;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public User getUser() {
        return user;
    }

    public Long getBudgetId() {
        return budgetId;
    }

    public double getLimit() {
        return limit;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }
}
