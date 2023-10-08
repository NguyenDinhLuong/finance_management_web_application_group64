package com.example.backend;

import com.sun.tools.javac.Main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Dashboard {

    /*
    TEST FUNCTIONS
    (int id, String category, Double amount, String currency, String description, LocalDate date)
     */

    public static void Main(String[] args) {

        Dashboard d = new Dashboard();
        ArrayList<Expenses> e = d.generateTestData();

        ArrayList<Expenses> expensesByMonth = d.getExpensesByMonth(e, 1);
    }

    public ArrayList<Expenses> generateTestData() {
        ArrayList<Expenses> testExpenses = new ArrayList<Expenses>();
        testExpenses.add(new Expenses(1, "fuel", 20.0, "AUD", "sample desc1", LocalDate.of(2023, 1, 12)));
        testExpenses.add(new Expenses(2, "fuel", 65.6, "AUD", "sample desc2", LocalDate.of(2023, 1, 13)));
        testExpenses.add(new Expenses(3, "fuel", 40.5, "AUD", "sample desc3", LocalDate.of(2023, 2, 12)));
        testExpenses.add(new Expenses(4, "shopping", 20.0, "AUD", "sample desc4", LocalDate.of(2023, 3, 1)));
        testExpenses.add(new Expenses(5, "shopping", 234.0, "AUD", "sample desc5", LocalDate.of(2023, 4, 4)));
        testExpenses.add(new Expenses(6, "shopping", 105.5, "AUD", "sample desc6", LocalDate.of(2023, 2, 14)));
        testExpenses.add(new Expenses(7, "groceries", 233.4, "AUD", "sample desc7", LocalDate.of(2023, 1, 29)));
        testExpenses.add(new Expenses(8, "groceries", 22.3, "AUD", "sample desc8", LocalDate.of(2023, 3, 19)));
        testExpenses.add(new Expenses(9, "groceries", 5.5, "AUD", "sample desc9", LocalDate.of(2023, 2, 12)));

        return testExpenses;
    }


    /**
     * returns an array of expenses in a certain month
     * @param expenses array of all expenses
     * @param targetMonth month to get expenses. In numerical form e.g. Jan = 1
     * @return ArrayList of expenses in that target month
     */
    public ArrayList<Expenses> getExpensesByMonth(ArrayList<Expenses> expenses, int targetMonth) {

        if(expenses.isEmpty()) {
            System.out.println("No expenses");
            return new ArrayList<Expenses>();
        }

        if(targetMonth <= 0 || targetMonth > 12) {
            System.out.println("Invalid target month");
            return new ArrayList<Expenses>();
        }

        ArrayList<Expenses> expensesInMonth;
        expensesInMonth = new ArrayList<Expenses>();

        for (Expenses thisExpense : expenses) {
            if (thisExpense.getDate().getMonthValue() == targetMonth) {
                expensesInMonth.add(thisExpense);
            }
        }

        return  expensesInMonth;

    }


    public HashMap<String, ArrayList<Expenses>> getExpensesInCategories(ArrayList<Expenses> expenses) {
        if(expenses.isEmpty()) {
            return new HashMap<>();
        }

        HashMap<String, ArrayList<Expenses>> expensesByCategory;
        expensesByCategory = new HashMap<String, ArrayList<Expenses>>();

        for(Expenses thisExpense : expenses) {
            String category = thisExpense.getCategory();
            if(expensesByCategory.containsKey(category)) {
                ArrayList<Expenses> categoryExpenses = expensesByCategory.get(category);
                categoryExpenses.add(thisExpense);
            } else {
                ArrayList<Expenses> newExpenseList = new ArrayList<Expenses>();
                newExpenseList.add(thisExpense);
                expensesByCategory.put(category, newExpenseList);
            }
        }

        return expensesByCategory;


    }


}
