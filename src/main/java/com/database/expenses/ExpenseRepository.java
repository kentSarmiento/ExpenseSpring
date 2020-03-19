package com.database.expenses;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.database.expenses.io.entity.ExpenseEntity;

@Repository
public interface ExpenseRepository extends PagingAndSortingRepository<ExpenseEntity, Long> {
    List<ExpenseEntity> findByDateAndAmount(String date, double amount);
    ExpenseEntity findByDateAndAmountAndCategoryAndSubCategory(String date, double amount, String category, String subCategory);
    ExpenseEntity findByExpenseId(String expenseId);
}
