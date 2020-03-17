package com.database.expenses.io.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="expenses")
public class ExpenseEntity implements Serializable {

    private static final long serialVersionUID = 5347094794432637737L;

    @Id
    @GeneratedValue
    private long id;
    
    @Column(nullable=false, unique=true)
    private String expenseId;
    
    @Column(nullable=false, length=24)
    private String category;

    @Column(length=24)
    private String subCategory;

    @Column(nullable=false)
    private String date;

    @Column(nullable=false)
    private String day;

    @Column(nullable=false, length=24)
    private String actor;

    @Column(nullable=false)
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
