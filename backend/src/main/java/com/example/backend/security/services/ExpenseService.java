package com.example.backend.security.services;

import com.example.backend.model.NormalExpense;
import com.example.backend.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.Expenses;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {
    @Autowired
    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public NormalExpense addExpense(NormalExpense expense) {
        return expenseRepository.save(expense);
    }


    /**
     * retrieve expenses from database
     * this does not include recurring expenses
     * @return ArrayList<Expenses>
     */
    public ArrayList<Expenses> retrieveExpenses() {
        List<NormalExpense> expenses = expenseRepository.findAll();

        ArrayList<Expenses> allExpenses = new ArrayList<>();

        for(NormalExpense expense : expenses) {
            if(expense.getEndDate() == null) { // only if not recurring expense
                Expenses e = new Expenses(
                        expense.getId(),
                        expense.getCategory(),
                        expense.getAmount(),
                        expense.getCurrency(),
                        expense.getDescription(),
                        expense.getDate()
                );
                allExpenses.add(e);
            }
        }
        return allExpenses;
    }


}
