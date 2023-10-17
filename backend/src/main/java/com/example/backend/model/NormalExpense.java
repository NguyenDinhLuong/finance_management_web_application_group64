package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private float amount;
    private String category;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String location;
    private String status;
    private String paymentMethod;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public NormalExpense(float amount, String category, Date date, String location, String status, String paymentMethod) {
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.location = location;
        this.status = status;
        this.paymentMethod = paymentMethod;
    }
}
