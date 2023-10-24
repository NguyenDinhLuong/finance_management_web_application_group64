package com.example.backend.security;

import com.example.backend.controller.ExpenseController;
import com.example.backend.model.NormalExpense;
import com.example.backend.payload.request.AddExpenseRequest;
import com.example.backend.payload.request.UpdateExpenseRequest;
import com.example.backend.repository.ExpenseRepository;
import com.example.backend.security.services.ExpenseService;
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
class ExpenseControllerTest {

    @InjectMocks
    ExpenseController expenseController;

    @Mock
    ExpenseService expenseService;

    @Mock
    ExpenseRepository expenseRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(expenseController).build();
    }

    @Test
    void createExpense_Success() throws Exception {
        AddExpenseRequest request = new AddExpenseRequest();
        request.setAmount(1500.0f);
        request.setCategory("Food");
        request.setCurrency("USD");
        request.setStatus("Paid");
        request.setLocation("Los Angeles");
        request.setPaymentMethod("Credit Card");
        request.setUser_id(1L);

        mockMvc.perform(post("/api/expenses/addExpense")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Expense created successfully!"));
    }

    @Test
    void getExpensesByUserId_Success() throws Exception {
        List<NormalExpense> expenses = Arrays.asList(
                new NormalExpense(1500.0f, "Food", "USD", LocalDate.now(), "Los Angeles", "Paid", "Credit Card"),
                new NormalExpense(2000.0f, "Entertainment", "USD", LocalDate.now(), "San Francisco", "Paid", "Debit Card")
        );

        when(expenseService.getAllExpensesByUserId(any(Long.class))).thenReturn(expenses);

        mockMvc.perform(get("/api/expenses/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getExpenseById_Found() throws Exception {
        NormalExpense expense = new NormalExpense(1500.0f, "Food", "USD", LocalDate.now(), "Los Angeles", "Paid", "Credit Card");

        when(expenseRepository.findById(any(Long.class))).thenReturn(Optional.of(expense));

        mockMvc.perform(get("/api/expenses/specificExpense/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getExpenseById_NotFound() throws Exception {
        when(expenseRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/expenses/specificExpense/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTotalExpenseAmountByUserId_Success() throws Exception {
        when(expenseService.getTotalExpenseAmountByUserId(any(Long.class))).thenReturn(1500.0f);

        mockMvc.perform(get("/api/expenses/totalAmount/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1500.0"));
    }

    @Test
    void updateAllExpensesAfterCurrencyExchange_Success() throws Exception {
        List<NormalExpense> expenses = Arrays.asList(
                new NormalExpense(1500.0f, "Food", "EUR", LocalDate.now(), "Paris", "Paid", "Credit Card"),
                new NormalExpense(2000.0f, "Entertainment", "EUR", LocalDate.now(), "Berlin", "Paid", "Debit Card")
        );

        when(expenseService.updateAllExpensesAfterCurrencyExchange(any(String.class), any(String.class))).thenReturn(expenses);

        mockMvc.perform(put("/api/expenses/updateCurrencyExchange/USD/EUR"))
                .andExpect(status().isOk());
    }

    @Test
    void updateExpense_Success() throws Exception {
        UpdateExpenseRequest request = new UpdateExpenseRequest();
        request.setAmount(1500.0f);
        request.setCategory("Food");
        request.setCurrency("USD");
        request.setStatus("Paid");
        request.setLocation("Los Angeles");
        request.setPaymentMethod("Credit Card");

        when(expenseService.updateExpense(any(Long.class), any())).thenReturn(new NormalExpense());

        mockMvc.perform(put("/api/expenses/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteExpense_Success() throws Exception {
        NormalExpense expense = new NormalExpense();

        when(expenseRepository.findById(any(Long.class))).thenReturn(Optional.of(expense));

        mockMvc.perform(delete("/api/expenses/deleteExpense/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Expense deleted successfully!"));
    }

    @Test
    void deleteExpense_NotFound() throws Exception {
        when(expenseRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/expenses/deleteExpense/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Expense not found with the provided ID."));
    }
}
