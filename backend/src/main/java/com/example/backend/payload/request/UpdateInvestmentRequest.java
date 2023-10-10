package com.example.backend.payload.request;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class UpdateInvestmentRequest {
    @NotBlank
    private Long id;

    @NotBlank
    private String investmentType;

    @NotBlank
    @DateTimeFormat
    private String date;

    @NotBlank
    private double amount;

    @NotBlank
    private Long userID;

    @Size(max = 100)
    private String description;
}
