package com.example.backend.repository;

import com.example.backend.model.NormalExpense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<NormalExpense, Long> {
    List<NormalExpense> findByUserId(Long user_id);
}
