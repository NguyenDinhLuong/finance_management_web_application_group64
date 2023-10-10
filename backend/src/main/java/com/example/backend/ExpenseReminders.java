package com.example.backend;

import java.util.ArrayList;

public class ExpenseReminders {

    public static void main(String[] args) {

    }

    public ArrayList<String> generateReminders(ArrayList<RecurringExpense> expenses) {

        // sort by expense size
        // this func still needs to be tested
        for(int n = 0; n < expenses.size(); n++) {
            for(int i = 0; i < expenses.size(); i++) {
                for(int j = expenses.size() - 1; j >=0; j--) {
                    if(expenses.get(i).daysUnilDue() > expenses.get(j).daysUnilDue()) {
                        RecurringExpense temp = expenses.get(i);
                        expenses.set(i, expenses.get(j));
                        expenses.set(j, temp);
                    }
                }
            }
        }

        return new ArrayList<String>();



    }
}
