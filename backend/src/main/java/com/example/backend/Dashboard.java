package com.example.backend;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class Dashboard {

    /*
    TEST FUNCTIONS
    (int id, String category, Double amount, String currency, String description, LocalDate date)
     */

    public static void main(String[] args) {

        Dashboard d = new Dashboard();
        ArrayList<Expenses> e = d.generateTestData();

//        ArrayList<Expenses> expensesByMonth = d.getExpensesByMonth(e, 1);
//        HashMap<String, ArrayList<Expenses>> sortedCategories = d.getExpensesInCategories(e);
//
        d.generateBarChart(e);

//        d.generatePieChart(e);



        return;

    }

    public ArrayList<Expenses> generateTestData() {
        ArrayList<Expenses> testExpenses = new ArrayList<Expenses>();
        testExpenses.add(new Expenses(1L, "fuel", 20.0, "AUD", "sample desc1", LocalDate.of(2023, 10, 12)));
        testExpenses.add(new Expenses(2L, "fuel", 65.6, "AUD", "sample desc2", LocalDate.of(2023, 10, 13)));
        testExpenses.add(new Expenses(3L, "fuel", 40.5, "AUD", "sample desc3", LocalDate.of(2023, 10, 12)));
        testExpenses.add(new Expenses(4L, "shopping", 20.0, "AUD", "sample desc4", LocalDate.of(2023, 9, 1)));
        testExpenses.add(new Expenses(5L, "shopping", 234.0, "AUD", "sample desc5", LocalDate.of(2023, 9, 4)));
        testExpenses.add(new Expenses(6L, "shopping", 105.5, "AUD", "sample desc6", LocalDate.of(2023, 9, 14)));
        testExpenses.add(new Expenses(7L, "groceries", 233.4, "AUD", "sample desc7", LocalDate.of(2023, 8, 29)));
        testExpenses.add(new Expenses(8L, "groceries", 22.3, "AUD", "sample desc8", LocalDate.of(2023, 8, 19)));
        testExpenses.add(new Expenses(9L, "groceries", 5.5, "AUD", "sample desc9", LocalDate.of(2023, 8, 12)));

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


    /**
     * Sorts Expenses ArrayList by category
     * @param expenses ArrayList of expenses
     * @return sorted ArrayList. Each key in the hashmap is a category
     */
    public HashMap<String, Double> getExpensesInCategories(ArrayList<Expenses> expenses) {
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

        HashMap<String, Double> totalExpensesByCategory = new HashMap<String, Double>();

        for (Map.Entry<String, ArrayList<Expenses>> entry : expensesByCategory.entrySet()) {
            totalExpensesByCategory.put(entry.getKey(), sumOfExpenses(entry.getValue()));
        }

        return totalExpensesByCategory;
    }

    /**
     * Takes a list of expenses and returns the total sum of expenses for the year.
     * @param expenses list of expenses
     * @return total amount spent
     */
    public Double sumOfExpenses(ArrayList<Expenses> expenses) {
        Double totalExpenses = 0.0;

        for(Expenses expense : expenses) {
            totalExpenses += expense.getAmount();
        }

        DecimalFormat format = new DecimalFormat("0.00");
        totalExpenses = Double.parseDouble(format.format(totalExpenses));

        return totalExpenses;
    }

    public HashMap<String, Double> generateBarChart(ArrayList<Expenses> expenses) {

        // this list will store the month values of the past three months
        ArrayList<Integer> pastThreeMonthsValue = new ArrayList<Integer>();
        ArrayList<String> pastThreeMonthsLabel = new ArrayList<String>();

        // get this month.
        LocalDate date = LocalDate.now();
        pastThreeMonthsValue.add(date.getMonthValue());
        pastThreeMonthsLabel.add(String.valueOf(date.getMonth()));

        // get two prior months
        date = date.minusMonths(1);
        pastThreeMonthsValue.add(date.getMonthValue());
        pastThreeMonthsLabel.add(String.valueOf(date.getMonth()));

        date = date.minusMonths(1);
        pastThreeMonthsValue.add(date.getMonthValue());
        pastThreeMonthsLabel.add(String.valueOf(date.getMonth()));

        Collections.reverse(pastThreeMonthsValue);
        Collections.reverse(pastThreeMonthsLabel);

        ArrayList<ArrayList<Expenses>> expensesPastThreeMonths = new ArrayList<>();
        for (Integer month : pastThreeMonthsValue) {
            expensesPastThreeMonths.add(getExpensesByMonth(expenses, month));
        }

        HashMap<String, Double> expensesByMonth = new HashMap<String, Double>();

        for (int i = 0; i < expensesPastThreeMonths.size(); i++) {
            expensesByMonth.put(pastThreeMonthsLabel.get(i), sumOfExpenses(expensesPastThreeMonths.get(i)));

        }

        return expensesByMonth;
    }



}
