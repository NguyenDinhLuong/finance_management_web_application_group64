package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd") // changed to match LocalDate format
    @Column(nullable = false)
    private Date date;
    @Column(nullable = true)
    private Integer frequency;
    @Column(nullable = true)
    private Date endDate;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public NormalExpense(Double amount, String description, String category, Date date, Integer frequency, Date endDate) {
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.date = date;
        this.frequency = frequency;
        this.endDate = endDate;
    }
}
