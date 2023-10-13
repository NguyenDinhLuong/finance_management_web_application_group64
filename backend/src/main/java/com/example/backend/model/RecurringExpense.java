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
@Table(name = "recurring_expenses")
public class RecurringExpense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private String description;
    private String category;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date date;
    private String frequency;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public RecurringExpense(Double amount, String description, String category, Date date, String frequency, Date endDate) {
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.date = date;
        this.frequency = frequency;
        this.endDate = endDate;
    }
}
