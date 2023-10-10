package com.example.backend.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
@Table(name = "income")
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long incomeId;

    @NotBlank
    private double amount;

    @Size(max = 100)
    private String source;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
