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
@Table(name = "investment")
public class Investment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float amount;
    private String category;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String duration;
    private String currency;
    private String risk;
    private String liquidity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public Investment(float amount, String category, Date date, String duration, String currency, String risk, String liquidity) {
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.duration = duration;
        this.currency = currency;
        this.risk = risk;
        this.liquidity = liquidity;
    }
}
