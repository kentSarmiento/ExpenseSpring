package com.database.expenses;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.database.expenses.io.entity.ExpenseEntity;

@Repository
public interface ExpenseRepository extends CrudRepository<ExpenseEntity, Long> {
    List<ExpenseEntity> findByDateAndAmount(String date, double amount);
    ExpenseEntity findByExpenseId(String expenseId);
}
