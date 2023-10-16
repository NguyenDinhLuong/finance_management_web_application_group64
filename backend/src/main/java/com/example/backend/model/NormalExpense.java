package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "expenses")
public class NormalExpense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Double amount;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String currency;
    @DateTimeFormat(pattern = "yyyy-MM-dd") // changed to match LocalDate format
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = true)
    private Integer frequency;
    @Column(nullable = true)
    private LocalDate endDate;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public NormalExpense(Double amount, String description, String category, String currency, LocalDate date, Integer frequency, LocalDate endDate) {
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.currency = currency;
        this.date = date;
        this.frequency = frequency;
        this.endDate = endDate;
    }
}
