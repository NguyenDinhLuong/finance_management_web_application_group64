package com.financemanagementwebapp.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class TaxRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taxId;

    @DateTimeFormat
    private Date date;

    @NotBlank
    @OneToOne
    @JoinColumn(name = "incomeId")
    private Income income;

    private double deduction;

//there should be an auto match for user to record tax with income
    public TaxRecord(Date date, Income income, double deduction){

        this.date = date;
        this.income = income;
        this.deduction = deduction;

    }

    public TaxRecord() {

    }

    public Date getDate() {
        return date;
    }

    public double getDeduction() {
        return deduction;
    }

    public Income getIncome() {
        return income;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDeduction(double deduction) {
        this.deduction = deduction;
    }

    public void setIncome(Income income) {
        this.income = income;
    }
}
