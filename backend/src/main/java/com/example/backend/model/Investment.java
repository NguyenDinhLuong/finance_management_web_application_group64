package com.example.backend.model;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "investments", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "investmentId"
        }),

})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Investment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long investmentId;

    @ManyToOne
    private User user;

    private InvestmentType type;

    @Size(max = 100)
    private String description;

    private double amount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public Investment(User user, String investmentType, double amount, String date, String description){
        this.user = user;
        this.type = InvestmentType.valueOf(investmentType);
        this.amount = amount;
        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.description = description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setInvestmentId(Long investmentId) {
        this.investmentId = investmentId;
    }

    public void setType(String type) {
        this.type = InvestmentType.valueOf(type);
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public Long getInvestmentId() {
        return investmentId;
    }

    public InvestmentType getType() {
        return type;
    }

    //    @UniqueConstraint()
}
