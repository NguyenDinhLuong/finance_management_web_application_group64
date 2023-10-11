package com.example.backend;

import com.sun.tools.javac.Main;

import java.awt.*;
import java.sql.Array;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.springframework.cglib.core.Local;

import java.io.File;
import java.io.IOException;
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
//        d.generateBarChart(e);

        d.generateBarChart(e);



        return;

    }

    public ArrayList<Expenses> generateTestData() {
        ArrayList<Expenses> testExpenses = new ArrayList<Expenses>();
        testExpenses.add(new Expenses(1, "fuel", 20.0, "AUD", "sample desc1", LocalDate.of(2023, 10, 12)));
        testExpenses.add(new Expenses(2, "fuel", 65.6, "AUD", "sample desc2", LocalDate.of(2023, 10, 13)));
        testExpenses.add(new Expenses(3, "fuel", 40.5, "AUD", "sample desc3", LocalDate.of(2023, 10, 12)));
        testExpenses.add(new Expenses(4, "shopping", 20.0, "AUD", "sample desc4", LocalDate.of(2023, 9, 1)));
        testExpenses.add(new Expenses(5, "shopping", 234.0, "AUD", "sample desc5", LocalDate.of(2023, 9, 4)));
        testExpenses.add(new Expenses(6, "shopping", 105.5, "AUD", "sample desc6", LocalDate.of(2023, 9, 14)));
        testExpenses.add(new Expenses(7, "groceries", 233.4, "AUD", "sample desc7", LocalDate.of(2023, 8, 29)));
        testExpenses.add(new Expenses(8, "groceries", 22.3, "AUD", "sample desc8", LocalDate.of(2023, 8, 19)));
        testExpenses.add(new Expenses(9, "groceries", 5.5, "AUD", "sample desc9", LocalDate.of(2023, 8, 12)));

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

    public void generatePieChart(ArrayList<Expenses> expenses) {
        HashMap<String, ArrayList<Expenses>> categorisedExpenses;
        categorisedExpenses = getExpensesInCategories(expenses);
        ArrayList<String> keys = new ArrayList<String>(categorisedExpenses.keySet());

        DefaultPieDataset dataset = new DefaultPieDataset();

        for(String key : keys) {
            Double sum = sumOfExpenses(categorisedExpenses.get(key));
            dataset.setValue(key, sum);
        }

        JFreeChart pieChart = ChartFactory.createPieChart("Spending by Category", dataset);
        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setLabelGenerator(null);

//
//        plot.setSectionPaint("Category A", Color.decode("#FF5733"));
//        plot.setSectionPaint("Category B", Color.decode("#00FF00"));
//        plot.setSectionPaint("Category C", Color.decode("#0000FF"));
//        plot.setSectionPaint("Category D", Color.decode("#FFD700"));

        BufferedImage bufferedImage = pieChart.createBufferedImage(800, 600);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            // DEBUG ONLY
            ImageIO.write(bufferedImage, "png", new File("/Users/jordantanti/Desktop/finance_management_web_application_group64/backend/src/main/resources/image.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }


    public ByteArrayOutputStream generateBarChart(ArrayList<Expenses> expenses) {

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

        ArrayList<ArrayList<Expenses>> expensesPastThreeMonths = new ArrayList<>();
        for(Integer month : pastThreeMonthsValue) {
            expensesPastThreeMonths.add(getExpensesByMonth(expenses, month));
        }


        ArrayList<Double> expensesByMonth = new ArrayList<Double>();

        for (ArrayList<Expenses> expense : expensesPastThreeMonths) {
            expensesByMonth.add(sumOfExpenses(expense));
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < expensesByMonth.size(); i++) {
            dataset.addValue(expensesByMonth.get(i), pastThreeMonthsLabel.get(i), "Total Expenses");
        }

        JFreeChart chart = ChartFactory.createBarChart("Recent Monthly Expenses", "Month", "Total Expenses",
                dataset);
        // Remove chart background and border
        chart.setBackgroundPaint(null);
        chart.setBorderStroke(null);


        // Remove axis labels and tick marks
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.getDomainAxis().setTickLabelsVisible(false);
        plot.getRangeAxis().setTickLabelsVisible(true);


        // Remove gradient shading
        BarRenderer barRenderer = (BarRenderer) plot.getRenderer();
        barRenderer.setBarPainter(new StandardBarPainter());
        barRenderer.setSeriesPaint(0, Color.decode("#264653"));
        barRenderer.setSeriesPaint(1, Color.decode("#2a9d8f"));
        barRenderer.setSeriesPaint(2, Color.decode("#e76f51"));

        // Add borders to the bars
        barRenderer.setDrawBarOutline(true);


        // Create a plot panel to display the chart
        CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        BufferedImage bufferedImage = chart.createBufferedImage(800, 600);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            // DEBUG ONLY
            ImageIO.write(bufferedImage, "png", new File("/Users/jordantanti/Desktop/finance_management_web_application_group64/backend/src/main/resources/image.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return byteArrayOutputStream;

    }



}
