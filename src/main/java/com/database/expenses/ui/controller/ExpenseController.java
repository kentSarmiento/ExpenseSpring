package com.database.expenses.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping(path="/{id}",
                produces = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE } )
    public ExpenseResponse getExpenses(@PathVariable String id) {
        ExpenseResponse retVal = new ExpenseResponse();

        ExpenseDto expenseDto = expenseService.getExpenseByExpenseId(id);
        BeanUtils.copyProperties(expenseDto, retVal);

        return retVal;
    }

    @PostMapping(
                consumes = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE },
                produces = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE } )
    public ExpenseResponse addExpenses(@RequestBody ExpenseRequestModel expenseDetails) {
        ExpenseResponse retVal = new ExpenseResponse();

        ExpenseDto expenseDto = new ExpenseDto();
        BeanUtils.copyProperties(expenseDetails, expenseDto);

        ExpenseDto addedExpense = expenseService.addExpense(expenseDto);
        BeanUtils.copyProperties(addedExpense, retVal);

        return retVal;
    }

    @PutMapping(path="/{id}",
                consumes = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE },
                produces = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE } )
    public ExpenseResponse updateExpenses(@PathVariable String id, @RequestBody ExpenseRequestModel expenseDetails) {
        ExpenseResponse retVal = new ExpenseResponse();

        ExpenseDto expenseDto = new ExpenseDto();
        BeanUtils.copyProperties(expenseDetails, expenseDto);

        ExpenseDto updatedExpense = expenseService.updateExpense(id, expenseDto);
        BeanUtils.copyProperties(updatedExpense, retVal);

        return retVal;
    }

    @DeleteMapping(path="/{id}",
                produces = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE } )
    public String deleteExpenses(@PathVariable String id) {
        expenseService.deleteExpenses(id);
        String returnValue = new String("User " + id + " deleted");
        return returnValue;
    }

    @GetMapping(
                produces = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE } )
    public List<ExpenseResponse> getExpenses(@RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "limit", defaultValue = "5") int limit) {
        List<ExpenseResponse> returnValue = new ArrayList<>();

        List<ExpenseDto> expenses = expenseService.getExpenses(page, limit);
        for (ExpenseDto expenseDto : expenses) {
          ExpenseResponse expenseSingleResponse = new ExpenseResponse();
          BeanUtils.copyProperties(expenseDto, expenseSingleResponse);
          returnValue.add(expenseSingleResponse);
        }

        return returnValue;
    }
}