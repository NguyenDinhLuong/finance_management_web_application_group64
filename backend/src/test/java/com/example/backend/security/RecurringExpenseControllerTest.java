package com.example.backend.security;

import com.example.backend.controller.RecurringExpenseController;
import com.example.backend.model.RecurringExpense;
import com.example.backend.payload.request.AddRecurringExpenseRequest;
import com.example.backend.payload.request.UpdateRecurringExpenseRequest;
import com.example.backend.repository.RecurringExpenseRepository;
import com.example.backend.security.services.RecurringExpenseService;
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
class RecurringExpenseControllerTest {

    @InjectMocks
    RecurringExpenseController recurringExpenseController;

    @Mock
    RecurringExpenseService recurringExpenseService;

    @Mock
    RecurringExpenseRepository recurringExpenseRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(recurringExpenseController).build();
    }

    @Test
    void createRecurringExpense_Success() throws Exception {
        AddRecurringExpenseRequest request = new AddRecurringExpenseRequest();
        request.setAmount(1000.0f);
        request.setCategory("Rent");
        request.setCurrency("USD");
        request.setFrequency("Monthly");
        request.setLocation("San Francisco");


        mockMvc.perform(post("/api/recurringExpenses/addRecurringExpense")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Recurring Expense created successfully!"));
    }

    @Test
    void getRecurringExpensesByUserId_Success() throws Exception {
        List<RecurringExpense> expenses = Arrays.asList(
                new RecurringExpense(1200.0f, "Rent", "USD", "Monthly", "New York", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31)),
                new RecurringExpense(50.0f, "Internet", "USD", "Monthly", "New York", LocalDate.of(2023, 1, 5), LocalDate.of(2023, 12, 5))
        );

        when(recurringExpenseService.getAllRecurringExpensesByUserId(any(Long.class))).thenReturn(expenses);

        mockMvc.perform(get("/api/recurringExpenses/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getRecurringExpenseById_Found() throws Exception {
        RecurringExpense expense = new RecurringExpense();
        expense.setAmount(1050.0f);
        expense.setCategory("Utilities");
        expense.setCurrency("USD");
        expense.setFrequency("Monthly");
        expense.setLocation("Los Angeles");
        expense.setStartDate(LocalDate.now());
        expense.setEndDate(LocalDate.now().plusMonths(1));

        when(recurringExpenseRepository.findById(any(Long.class))).thenReturn(Optional.of(expense));

        mockMvc.perform(get("/api/recurringExpenses/specificRecurringExpense/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getRecurringExpenseById_NotFound() throws Exception {
        when(recurringExpenseRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/recurringExpenses/specificRecurringExpense/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTotalRecurringExpenseAmountByUserId_Success() throws Exception {
        when(recurringExpenseService.getTotalRecurringExpenseAmountByUserId(any(Long.class))).thenReturn(1000.0f);

        mockMvc.perform(get("/api/recurringExpenses/totalAmount/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1000.0"));
    }

    @Test
    void updateAllRecurringExpensesAfterCurrencyExchange_Success() throws Exception {
        List<RecurringExpense> expenses = Arrays.asList(new RecurringExpense(), new RecurringExpense());
        // populate the expenses

        when(recurringExpenseService.updateAllRecurringExpensesAfterCurrencyExchange(any(String.class), any(String.class))).thenReturn(expenses);

        mockMvc.perform(put("/api/recurringExpenses/updateCurrencyExchange/USD/EUR"))
                .andExpect(status().isOk());
    }

    @Test
    void updateRecurringExpense_Success() throws Exception {
        UpdateRecurringExpenseRequest request = new UpdateRecurringExpenseRequest();
        request.setAmount(1050.0f);
        request.setCategory("Utilities");
        request.setCurrency("USD");
        request.setFrequency("Monthly");
        request.setLocation("Los Angeles");


        when(recurringExpenseService.updateRecurringExpense(any(Long.class), any())).thenReturn(new RecurringExpense());

        mockMvc.perform(put("/api/recurringExpenses/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteRecurringExpense_Success() throws Exception {
        RecurringExpense expense = new RecurringExpense();
        // populate the expense
        expense.setAmount(1050.0f);
        expense.setCategory("Utilities");
        expense.setCurrency("USD");
        expense.setFrequency("Monthly");
        expense.setLocation("Los Angeles");
        expense.setStartDate(LocalDate.now());
        expense.setEndDate(LocalDate.now().plusMonths(1));

        when(recurringExpenseRepository.findById(any(Long.class))).thenReturn(Optional.of(expense));

        mockMvc.perform(delete("/api/recurringExpenses/deleteRecurringExpense/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Recurring Expense deleted successfully!"));
    }

    @Test
    void deleteRecurringExpense_NotFound() throws Exception {
        when(recurringExpenseRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/recurringExpenses/deleteRecurringExpense/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Recurring Expense not found with the provided ID."));
    }
}
