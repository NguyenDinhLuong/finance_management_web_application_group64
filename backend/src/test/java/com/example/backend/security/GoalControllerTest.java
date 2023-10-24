package com.example.backend.security;

import com.example.backend.controller.GoalController;
import com.example.backend.model.Goal;
import com.example.backend.payload.request.AddGoalRequest;
import com.example.backend.payload.request.UpdateGoalRequest;
import com.example.backend.security.services.GoalService;
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
import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class GoalControllerTest {

    @InjectMocks
    private GoalController goalController;

    @Mock
    private GoalService goalService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(goalController).build();
    }

    @Test
    void createGoal_Success() throws Exception {
        AddGoalRequest request = new AddGoalRequest();
        // Populate the request as needed
        request.setTargetIncome(5000.00f);
        request.setMaximumExpense(1500.00f);
        request.setMaximumRecurringExpense(300.00f);
        request.setMaximumInvestment(2000.00f);
        request.setCurrency("USD");
        request.setUser_id(1L);

        when(goalService.saveGoal(any(AddGoalRequest.class))).thenReturn(new Goal());

        mockMvc.perform(post("/api/goals/addGoal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Goal created successfully!"));
    }

    @Test
    void updateGoal_Success() throws Exception {
        UpdateGoalRequest request = new UpdateGoalRequest();
        // Populate the request as needed
        request.setTargetIncome(5000.00f);
        request.setMaximumExpense(1500.00f);
        request.setMaximumRecurringExpense(300.00f);
        request.setMaximumInvestment(2000.00f);
        request.setCurrency("USD");

        when(goalService.updateGoal(any(Long.class), any())).thenReturn(new Goal());

        mockMvc.perform(put("/api/goals/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void getIncomesByUserId_Success() throws Exception {
        Long userId = 1L;

        Goal userGoal = new Goal();
        userGoal.setId(2L);
        userGoal.setTargetIncome(5000);
        userGoal.setMaximumExpense(1500.00f);
        userGoal.setMaximumRecurringExpense(300.00f);
        userGoal.setMaximumInvestment(2000.00f);
        userGoal.setCurrency("USD");

        given(goalService.getGoalByUserId(userId)).willReturn(Collections.singletonList(userGoal));

        mockMvc.perform(get("/api/goals/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.targetIncome").value(5000))
                .andExpect(jsonPath("$.maximumExpense").value(1500.00f))
                .andExpect(jsonPath("$.maximumRecurringExpense").value(300.00f))
                .andExpect(jsonPath("$.maximumInvestment").value(2000.00f));
    }

    @Test
    void updateAllGoalsAfterCurrencyExchange_Success() throws Exception {
        String inputCurrency = "USD";
        String outputCurrency = "EUR";

        Goal goalBeforeUpdate = new Goal();
        goalBeforeUpdate.setId(1L);
        goalBeforeUpdate.setCurrency(inputCurrency);

        Goal goalAfterUpdate = new Goal();
        goalAfterUpdate.setId(1L);
        goalAfterUpdate.setCurrency(outputCurrency);

        given(goalService.updateAllGoalsAfterCurrencyExchange(inputCurrency, outputCurrency))
                .willReturn(Collections.singletonList(goalAfterUpdate));

        mockMvc.perform(put("/api/goals/updateCurrencyExchange/" + inputCurrency + "/" + outputCurrency))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].currency").value(outputCurrency));
    }
}

