package com.database.expenses.service;

import java.util.List;

import com.database.expenses.shared.dto.ExpenseDto;

public interface ExpenseService {

    ExpenseDto getExpenseByExpenseId(String userId);
    ExpenseDto addExpense(ExpenseDto expense);
    ExpenseDto updateExpense(String expenseId, ExpenseDto expense);
    void deleteExpenses(String expenseId);

    List<ExpenseDto> getExpenses(int page, int limit);
}
