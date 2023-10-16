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
@Table(name = "incomes")
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float amount;
    private String source;
    private String category;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String status;
    private String location;
    private String currency;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public Income(float amount, String source, String category, Date date, String status, String location, String currency) {
        this.amount = amount;
        this.source = source;
        this.category = category;
        this.date = date;
        this.status = status;
        this.location = location;
        this.currency = currency;
    }
}
