package com.example.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "goals")
public class FinancialGoal {
    @Id
    private Long FGId;

    private String description;

    private double targetAmount;

    private Date targetDate;

    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public String getDescription() {
        return description;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public Long getFGId() {
        return FGId;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }
}
