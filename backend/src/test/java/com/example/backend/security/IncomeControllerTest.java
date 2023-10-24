package com.example.backend.security;

import com.example.backend.controller.IncomeController;
import com.example.backend.model.Income;
import com.example.backend.payload.request.AddIncomeRequest;
import com.example.backend.payload.request.UpdateIncomeRequest;
import com.example.backend.repository.IncomeRepository;
import com.example.backend.security.services.IncomeService;
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
class IncomeControllerTest {
    @InjectMocks
    IncomeController incomeController;

    @Mock
    IncomeService incomeService;

    @Mock
    IncomeRepository incomeRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(incomeController).build();
    }

    @Test
    void createIncome_Success() throws Exception {
        AddIncomeRequest request = new AddIncomeRequest();
        request.setAmount(500.0f);
        request.setSource("Job");
        request.setCategory("Salary");
        request.setCurrency("USD");
        request.setStatus("Received");
        request.setLocation("New York");
        request.setUser_id(1L);

        mockMvc.perform(post("/api/incomes/addIncome")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Income created successfully!"));
    }

    @Test
    void getIncomesByUserId_Success() throws Exception {
        List<Income> incomes = Arrays.asList(new Income(500.0f, "source", "category", "currency", LocalDate.now(), "status", "location"), new Income(505.0f, "source2", "category2", "currency2", LocalDate.now(), "status2", "location2"));

        when(incomeService.getAllIncomesByUserId(any(Long.class))).thenReturn(incomes);

        mockMvc.perform(get("/api/incomes/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getIncomeById_Found() throws Exception {
        Income income = new Income(500.0f, "source", "category", "currency", LocalDate.now(), "status", "location");
        // populate the income

        when(incomeRepository.findById(any(Long.class))).thenReturn(Optional.of(income));

        mockMvc.perform(get("/api/incomes/specificIncome/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getIncomeById_NotFound() throws Exception {
        when(incomeRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/incomes/specificIncome/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTotalIncomeAmountByUserId_Success() throws Exception {
        when(incomeService.getTotalIncomeAmountByUserId(any(Long.class))).thenReturn(500.0f);

        mockMvc.perform(get("/api/incomes/totalAmount/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("500.0"));
    }

    @Test
    void updateAllIncomesAfterCurrencyExchange_Success() throws Exception {
        List<Income> incomes = Arrays.asList(new Income(500.0f, "source", "category", "currency", LocalDate.now(), "status", "location"), new Income(504.0f, "source4", "category4", "currency4", LocalDate.now(), "status4", "location4"));

        when(incomeService.updateAllIncomesAfterCurrencyExchange(any(String.class), any(String.class))).thenReturn(incomes);

        mockMvc.perform(put("/api/incomes/updateCurrencyExchange/USD/EUR"))
                .andExpect(status().isOk());
    }

    @Test
    void updateIncome_Success() throws Exception {
        UpdateIncomeRequest request = new UpdateIncomeRequest();
        request.setAmount(500.0f);
        request.setSource("Job");
        request.setCategory("Salary");
        request.setCurrency("USD");
        request.setStatus("Received");
        request.setLocation("New York");

        when(incomeService.updateIncome(any(Long.class), any())).thenReturn(new Income());

        mockMvc.perform(put("/api/incomes/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteIncome_Success() throws Exception {
        Income income = new Income(500.0f, "source", "category", "currency", LocalDate.now(), "status", "location");

        when(incomeRepository.findById(any(Long.class))).thenReturn(Optional.of(income));

        mockMvc.perform(delete("/api/incomes/deleteIncome/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Income deleted successfully!"));
    }

    @Test
    void deleteIncome_NotFound() throws Exception {
        when(incomeRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/incomes/deleteIncome/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Income not found with the provided ID."));
    }
}
