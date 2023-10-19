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
    private String currency;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String status;
    private String location;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;


    public Income(float amount, String source, String category, String currency, Date date, String status, String location) {
        this.amount = amount;
        this.source = source;
        this.category = category;
        this.currency = currency;
        this.date = date;
        this.status = status;
        this.location = location;
    }
}
