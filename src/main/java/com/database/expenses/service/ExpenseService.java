package com.database.expenses.service;

import com.database.expenses.shared.dto.ExpenseDto;

public interface ExpenseService {

    ExpenseDto getExpenseByExpenseId(String userId);
    ExpenseDto addExpense(ExpenseDto expense);
    ExpenseDto updateExpense(String expenseId, ExpenseDto expense);
}
