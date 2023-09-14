package com.financemanagementwebapp.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "investments", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "investmentId"
        }),

})
public class Investment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long investmentId;

    @ManyToOne
    private User user;

    private String type;

    private double amount;

    @DateTimeFormat
    private Date date;

    public Investment(){

    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setInvestmentId(Long investmentId) {
        this.investmentId = investmentId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public Long getInvestmentId() {
        return investmentId;
    }

    public String getType() {
        return type;
    }

    //    @UniqueConstraint()
}
