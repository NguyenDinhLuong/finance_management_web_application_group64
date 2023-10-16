package com.example.backend.security.services;

import com.example.backend.model.Income;
import com.example.backend.model.User;
import com.example.backend.payload.request.AddIncomeRequest;
import com.example.backend.payload.request.UpdateUserRequest;
import com.example.backend.repository.IncomeRepository;
import com.example.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IncomeService  {

    private final IncomeRepository incomeRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @Transactional
    public Income saveIncome(AddIncomeRequest addIncomeRequest) {
        User user = userRepository.findById(addIncomeRequest.getUser_id())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + addIncomeRequest.getUser_id()));
        Income income = new Income();
        income.setAmount(addIncomeRequest.getAmount());
        income.setCategory(addIncomeRequest.getCategory());
        income.setSource(addIncomeRequest.getSource());
        income.setDate(addIncomeRequest.getDate());
        income.setStatus(addIncomeRequest.getStatus());
        income.setLocation(addIncomeRequest.getLocation());
        income.setCurrency(addIncomeRequest.getCurrency());
        income.setUser(user);
        incomeRepository.save(income);
        return income;
    }

    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    public Income getIncomeById(Long id) {
        return incomeRepository.findById(id).orElse(null);
    }

    public Income updateIncome(Long id, Income income) {
        if (incomeRepository.existsById(id)) {
            income.setId(id); // Ensure the ID is set on the income object
            return incomeRepository.save(income);
        }
        return null;
    }

    public boolean deleteIncome(Long id) {
        if (incomeRepository.existsById(id)) {
            incomeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

