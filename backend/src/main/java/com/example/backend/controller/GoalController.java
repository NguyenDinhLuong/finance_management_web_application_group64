package com.example.backend.controller;

import com.example.backend.model.Goal;
import com.example.backend.payload.request.AddGoalRequest;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.security.services.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/goals")
public class GoalController {
    @Autowired
    GoalService goalService;

    // Create a new income
    @PostMapping("/addGoal")
    public ResponseEntity<?> createGoal(@RequestBody AddGoalRequest addGoalRequest) {
        Goal createGoal = goalService.saveGoal(addGoalRequest);
        if(createGoal == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Goal created unsuccessfully. Please check again!"));
        }
        return ResponseEntity.ok(new MessageResponse("Goal created successfully!"));
    }

    // Get the goal
    @GetMapping("/{userId}")
    public ResponseEntity<Goal> getIncomesByUserId(@PathVariable Long userId) {
        List<Goal> userGoals = goalService.getGoalByUserId(userId);
        if(userGoals.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no incomes are found for the user
        }
        return ResponseEntity.ok(userGoals.get(0)); // Return 200 OK with the list of incomes for the user
    }

    @PutMapping("/updateCurrencyExchange/{inputCurrency}/{outputCurrency}")
    public ResponseEntity<List<Goal>> updateAllGoalsAfterCurrencyExchange(@PathVariable String inputCurrency, @PathVariable String outputCurrency) {

        List<Goal> updatedGoals = goalService.updateAllGoalsAfterCurrencyExchange(inputCurrency, outputCurrency);

        if(updatedGoals.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no incomes are found/updated
        }

        return ResponseEntity.ok(updatedGoals); // Return 200 OK with the list of updated incomes
    }
}
