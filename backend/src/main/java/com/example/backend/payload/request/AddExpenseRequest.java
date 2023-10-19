package com.example.backend.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class AddExpenseRequest {
    private float amount;
    @NotBlank
    private String category;
    @NotBlank
    private String currency;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @NotBlank
    private String location;
    @NotBlank
    private String status;
    @NotBlank
    private String paymentMethod;

    private Long user_id;
}
