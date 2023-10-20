package com.example.backend.security.services;

import com.example.backend.model.Goal;
import com.example.backend.model.User;
import com.example.backend.payload.request.AddGoalRequest;
import com.example.backend.payload.request.UpdateGoalRequest;
import com.example.backend.repository.GoalRepository;
import com.example.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {

    private final GoalRepository goalRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private final CurrencyExchangeService currencyExchangeService;

    @Autowired
    public GoalService(GoalRepository goalRepository, CurrencyExchangeService currencyExchangeService) {
        this.goalRepository = goalRepository;
        this.currencyExchangeService = currencyExchangeService;
    }

    @Transactional
    public Goal saveGoal(AddGoalRequest addGoalRequest) {
        User user = userRepository.findById(addGoalRequest.getUser_id())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + addGoalRequest.getUser_id()));
        Goal goal = new Goal();
        goal.setTargetIncome(addGoalRequest.getTargetIncome());
        goal.setMaximumExpense(addGoalRequest.getMaximumExpense());
        goal.setMaximumInvestment(addGoalRequest.getMaximumInvestment());
        goal.setCurrency(addGoalRequest.getCurrency());
        goal.setUser(user);
        goalRepository.save(goal);
        return goal;
    }

    @Transactional
    public Goal updateGoal(Long id, UpdateGoalRequest updateGoalRequest) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Goal not found with id " + id));
        goal.setTargetIncome(updateGoalRequest.getTargetIncome());
        goal.setMaximumExpense(updateGoalRequest.getMaximumExpense());
        goal.setMaximumInvestment(updateGoalRequest.getMaximumInvestment());
        goal.setCurrency(updateGoalRequest.getCurrency());
        goalRepository.save(goal);
        return goal;
    }

    public List<Goal> getGoalByUserId(Long userId) {
        return goalRepository.findByUserId(userId);
    }

    public List<Goal> updateAllGoalsAfterCurrencyExchange(String inputCurrency, String outputCurrency) {
        List<Goal> goals = goalRepository.findAll();

        // Convert each income's amount based on the currency rates
        for (Goal goal : goals) {
            try {
                double convertedIncome = currencyExchangeService.convertCurrency(inputCurrency, outputCurrency, (double) goal.getTargetIncome());
                double convertedExpense = currencyExchangeService.convertCurrency(inputCurrency, outputCurrency, (double) goal.getMaximumExpense());
                double convertedInvestment = currencyExchangeService.convertCurrency(inputCurrency, outputCurrency, (double) goal.getMaximumInvestment());
                goal.setTargetIncome((float) convertedIncome);
                goal.setMaximumExpense((float) convertedExpense);
                goal.setMaximumInvestment((float) convertedInvestment);
                goal.setCurrency(outputCurrency);
                goalRepository.save(goal);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return goals;
    }
}
