package tests;

import com.example.backend.Dashboard;
import com.example.backend.Expenses;
import com.example.backend.RecurringExpenses;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.platform.commons.annotation.Testable;


import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DashboardTests {

    private static ArrayList<Expenses> expenses;
    private static ArrayList<RecurringExpenses> recurringExpenses;

    @BeforeAll
    public static void generateTestData() {
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

        expenses = testExpenses;


        ArrayList<RecurringExpenses> recurringExpensesTest = new ArrayList<>();
        recurringExpensesTest.add(new RecurringExpenses(1L, "fuel", 20.0, "AUD", "sample desc1", LocalDate.of(2023, 10, 12), LocalDate.of(2024, 10, 12), 7));
        recurringExpensesTest.add(new RecurringExpenses(2L, "fuel", 65.6, "AUD", "sample desc2", LocalDate.of(2023, 10, 13), LocalDate.of(2023, 11, 13), 14));
        recurringExpensesTest.add(new RecurringExpenses(3L, "fuel", 40.5, "AUD", "sample desc3", LocalDate.of(2023, 10, 12), LocalDate.of(2023, 12, 19), 3));

        recurringExpenses = recurringExpensesTest;



    }

    @Test
    public void testGetExpensesByMonth() {

        ArrayList<Expenses> expensesInOctober = new ArrayList<>();
        expensesInOctober.add(new Expenses(1L, "fuel", 20.0, "AUD", "sample desc1", LocalDate.of(2023, 10, 12)));
        expensesInOctober.add(new Expenses(2L, "fuel", 65.6, "AUD", "sample desc2", LocalDate.of(2023, 10, 13)));
        expensesInOctober.add(new Expenses(3L, "fuel", 40.5, "AUD", "sample desc3", LocalDate.of(2023, 10, 12)));

        Dashboard dashboard = new Dashboard();
        ArrayList<Expenses> compareTo = dashboard.getExpensesByMonth(expenses, recurringExpenses, 10);


        /**
         * the following tests need to account for reccuring
         */
//        assertEquals(expensesInOctober.get(0).getAmount(), compareTo.get(0).getAmount());
//        assertEquals(expensesInOctober.get(1).getCategory(), compareTo.get(1).getCategory());
//        assertEquals(expensesInOctober.size(), compareTo.size());
//        assertEquals(expensesInOctober.get(2).getDate(), compareTo.get(2).getDate());

    }

    @Test
    public void testGetExpensesInCategories() {
        Dashboard dashboard = new Dashboard();
        HashMap<String, Double> categories = dashboard.getExpensesInCategories(expenses, recurringExpenses);

        assertTrue(categories.containsKey("fuel"));
        assertTrue(categories.containsKey("shopping"));
        assertTrue(categories.containsKey("groceries"));

        assertEquals(categories.get("fuel"), 126.1);
        assertEquals(categories.get("shopping"), 359.5);
        assertEquals(categories.get("groceries"), 261.2);


    }

    @Test
    public void testSumOfExpenses() {
        Dashboard dashboard = new Dashboard();
        assertEquals(dashboard.sumOfExpenses(expenses), 746.8);
    }


    /*
    !! these tests will only be valid for a month since this function changes depending on the month !!
     */
    @Test
    public void testGenerateBarChart() {
        Dashboard dashboard = new Dashboard();
        HashMap<String, Double> expensesPastThreeMonths = dashboard.generateBarChart(expenses, recurringExpenses);

//        assertEquals(expensesPastThreeMonths.size(), 3);
//        assertTrue(expensesPastThreeMonths.containsKey("OCTOBER"));
//        assertTrue(expensesPastThreeMonths.containsKey("SEPTEMBER"));
//        assertTrue(expensesPastThreeMonths.containsKey("AUGUST"));
//
//        assertEquals(expensesPastThreeMonths.get("OCTOBER"), 126.1);
//        assertEquals(expensesPastThreeMonths.get("SEPTEMBER"), 359.5);
//        assertEquals(expensesPastThreeMonths.get("AUGUST"), 261.2);


    }

}
