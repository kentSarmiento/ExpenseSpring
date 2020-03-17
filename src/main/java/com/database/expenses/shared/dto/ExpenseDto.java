package com.database.expenses.shared.dto;

import java.io.Serializable;

public class ExpenseDto implements Serializable {

    private static final long serialVersionUID = -3134764755763047826L;
    private long id;
    private String expenseId;
    private String category;
    private String subCategory;
    private String date;
    private String day;
    private String actor;
    private double amount;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getExpenseId() {
        return expenseId;
    }
    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getSubCategory() {
        return subCategory;
    }
    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }
    public String getActor() {
        return actor;
    }
    public void setActor(String actor) {
        this.actor = actor;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

}
