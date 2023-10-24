package com.example.backend.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
public class AddIncomeRequest {
    @NotBlank
    private float amount;
    @NotBlank
    private String source;
    @NotBlank
    private String category;
    @NotBlank
    private String currency;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @NotBlank
    private String status;
    @NotBlank
    private String location;
    private Long user_id;
}
