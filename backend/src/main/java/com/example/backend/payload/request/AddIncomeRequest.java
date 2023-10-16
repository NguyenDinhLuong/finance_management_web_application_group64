package com.example.backend.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class AddIncomeRequest {
    @NotBlank
    private float amount;
    @NotBlank
    private String source;
    @NotBlank
    private String category;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @NotBlank
    private String status;
    @NotBlank
    private String location;
    @NotBlank
    private String currency;

    private Long user_id;
}
