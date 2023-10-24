package com.example.backend.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
public class AddInvestmentRequest {
    private float amount;
    @NotBlank
    private String category;
    @NotBlank
    private String currency;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @NotBlank
    private String duration;
    @NotBlank
    private String risk;
    @NotBlank
    private String liquidity;
    private Long user_id;
}
