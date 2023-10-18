package com.example.backend.repository;

import com.example.backend.model.NormalExpense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface
ExpenseRepository extends JpaRepository<NormalExpense, Long> {

}
