package com.database.expenses.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.database.expenses.service.ExpenseService;
import com.database.expenses.shared.dto.ExpenseDto;
import com.database.expenses.ui.model.request.ExpenseRequestModel;
import com.database.expenses.ui.model.response.ExpenseResponse;

@RestController
@RequestMapping("expenses") // http://localhost:8080/expenses
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @GetMapping(path="/{id}")
    public ExpenseResponse getExpenses(@PathVariable String id) {
        ExpenseResponse retVal = new ExpenseResponse();

        ExpenseDto expenseDto = expenseService.getExpenseByExpenseId(id);
        BeanUtils.copyProperties(expenseDto, retVal);

        return retVal;
    }

    @PostMapping
    public ExpenseResponse addExpenses(@RequestBody ExpenseRequestModel expenseDetails) {
        ExpenseResponse retVal = new ExpenseResponse();

        ExpenseDto expenseDto = new ExpenseDto();
        BeanUtils.copyProperties(expenseDetails, expenseDto);

        ExpenseDto addedExpense = expenseService.addExpense(expenseDto);
        BeanUtils.copyProperties(addedExpense, retVal);

        return retVal;
    }

    @PutMapping(path="/{id}")
    public ExpenseResponse updateExpenses(@PathVariable String id, @RequestBody ExpenseRequestModel expenseDetails) {
        ExpenseResponse retVal = new ExpenseResponse();

        ExpenseDto expenseDto = new ExpenseDto();
        BeanUtils.copyProperties(expenseDetails, expenseDto);

        ExpenseDto updatedExpense = expenseService.updateExpense(id, expenseDto);
        BeanUtils.copyProperties(updatedExpense, retVal);

        return retVal;
    }

    @DeleteMapping(path="/{id}")
    public String deleteExpenses() {
        return "deleteExpenses called";
    }
}
