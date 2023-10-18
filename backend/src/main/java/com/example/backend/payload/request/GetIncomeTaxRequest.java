package com.example.backend.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


@Data
public class GetIncomeTaxRequest {
    @NotBlank
    private double amount;

    @NotBlank
    @Size(max = 140)
    private String source;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String date;

    @NotBlank
    private Long userId;

    }
