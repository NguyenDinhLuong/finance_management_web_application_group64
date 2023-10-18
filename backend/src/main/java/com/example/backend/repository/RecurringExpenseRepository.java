package com.example.backend.repository;

import com.example.backend.model.RecurringExpense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecurringExpenseRepository extends JpaRepository<RecurringExpense, Long> {
    List<RecurringExpense> findByUserId(Long user_id);
}
