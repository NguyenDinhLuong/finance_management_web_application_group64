package com.example.backend.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class AddRecurringExpenseRequest {
    private float amount;
    @NotBlank
    private String category;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @NotBlank
    private String location;
    @NotBlank
    private String frequency;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private Long user_id;
}
