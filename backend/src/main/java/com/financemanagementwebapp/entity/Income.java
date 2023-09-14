package com.financemanagementwebapp.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long incomeId;

    @NotBlank
    private double amount;

    @Size(max = 100)
    private String source;

    @DateTimeFormat
    private Date date;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Income(){

    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSource() {
        return source;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setIncomeId(Long incomeId) {
        this.incomeId = incomeId;
    }

    public double getAmount() {
        return amount;
    }

    public User getUser() {
        return user;
    }

    public Date getDate() {
        return date;
    }

    public Long getIncomeId() {
        return incomeId;
    }
}
