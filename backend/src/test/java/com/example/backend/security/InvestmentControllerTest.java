package com.example.backend.security;

import com.example.backend.controller.InvestmentController;
import com.example.backend.model.Investment;
import com.example.backend.payload.request.AddInvestmentRequest;
import com.example.backend.payload.request.UpdateInvestmentRequest;
import com.example.backend.repository.InvestmentRepository;
import com.example.backend.security.services.InvestmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class InvestmentControllerTest {
    @InjectMocks
    InvestmentController investmentController;

    @Mock
    InvestmentService investmentService;

    @Mock
    InvestmentRepository investmentRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(investmentController).build();
    }

    @Test
    void createInvestment_Success() throws Exception {
        AddInvestmentRequest request = new AddInvestmentRequest();
        request.setAmount(1500.0f);
        request.setCategory("Stocks");  // For example: Stocks, Bonds, Real Estate
        request.setDuration("5 years");  // Example duration the user intends to hold the investment
        request.setCurrency("USD");
        request.setRisk("Medium");  // For example: Low, Medium, High
        request.setLiquidity("High");  // For example: Low, Medium, High
        request.setUser_id(1L);  // Assuming a user with ID 1 is making the investment

        mockMvc.perform(post("/api/investments/addInvestment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Investment created successfully!"));
    }

    @Test
    void getInvestmentsByUserId_Success() throws Exception {
        List<Investment> investments = Arrays.asList(
                new Investment(5000.0f, "Stocks", LocalDate.of(2023, 5, 1), "5 years", "USD", "High", "Medium"),
                new Investment(2000.0f, "Bonds", LocalDate.of(2023, 5, 5), "10 years", "USD", "Low", "Low")
        );

        when(investmentService.getAllInvestmentsByUserId(any(Long.class))).thenReturn(investments);

        mockMvc.perform(get("/api/investments/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getInvestmentById_Found() throws Exception {
        Investment investment = new Investment(5000.0f, "Stocks", LocalDate.of(2023, 5, 1), "5 years", "USD", "High", "Medium");

        when(investmentRepository.findById(any(Long.class))).thenReturn(Optional.of(investment));

        mockMvc.perform(get("/api/investments/specificInvestment/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getInvestmentById_NotFound() throws Exception {
        when(investmentRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/investments/specificInvestment/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateInvestment_Success() throws Exception {
        UpdateInvestmentRequest request = new UpdateInvestmentRequest();
        request.setAmount(1500.0f);
        request.setCategory("Stocks");  // For example: Stocks, Bonds, Real Estate
        request.setDuration("5 years");  // Example duration the user intends to hold the investment
        request.setCurrency("USD");
        request.setRisk("Medium");  // For example: Low, Medium, High
        request.setLiquidity("High");  // For example: Low, Medium, High

        when(investmentService.updateInvestment(any(Long.class), any())).thenReturn(new Investment());

        mockMvc.perform(put("/api/investments/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteInvestment_Success() throws Exception {
        Investment investment = new Investment(5000.0f, "Stocks", LocalDate.of(2023, 5, 1), "5 years", "USD", "High", "Medium");

        when(investmentRepository.findById(any(Long.class))).thenReturn(Optional.of(investment));

        mockMvc.perform(delete("/api/investments/deleteInvestment/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Investment deleted successfully!"));
    }

    @Test
    void deleteInvestment_NotFound() throws Exception {
        when(investmentRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/investments/deleteInvestment/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Investment not found with the provided ID."));
    }
}
