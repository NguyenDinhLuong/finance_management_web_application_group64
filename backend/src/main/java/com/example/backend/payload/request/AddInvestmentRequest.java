package com.example.backend.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class AddInvestmentRequest {
    private float amount;
    @NotBlank
    private String category;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @NotBlank
    private String duration;
    @NotBlank
    private String currency;
    @NotBlank
    private String risk;
    @NotBlank
    private String liquidity;
    private Long user_id;
}
