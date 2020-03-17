package com.database.expenses.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.database.expenses.ExpenseRepository;
import com.database.expenses.io.entity.ExpenseEntity;
import com.database.expenses.service.ExpenseService;
import com.database.expenses.shared.RandomGenerator;
import com.database.expenses.shared.dto.ExpenseDto;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    RandomGenerator randomGenerator;

    @Override
    public ExpenseDto addExpense(ExpenseDto expense) {

        // Check uniqueness of record to be added
        List<ExpenseEntity> storedAlready = expenseRepository.findByDateAndAmount(expense.getDate(), expense.getAmount());
        if (storedAlready != null) {
            for (ExpenseEntity eachStoredAlready : storedAlready) {
                try {
                    if (eachStoredAlready.getCategory().equals(expense.getCategory())) {
                        if (eachStoredAlready.getSubCategory().equals(expense.getSubCategory())) {
                            throw new RuntimeException("Same record already exist");
                        }
                    }
                } catch (NullPointerException e) {
                    // Disregard null strings
                }
            }
        }

        ExpenseEntity expenseEntity = new ExpenseEntity();
        BeanUtils.copyProperties(expense,expenseEntity);

        expenseEntity.setExpenseId(randomGenerator.generateExpenseId(32));

        ExpenseEntity storedEntity = expenseRepository.save(expenseEntity);

        ExpenseDto retVal = new ExpenseDto();
        BeanUtils.copyProperties(storedEntity,retVal);

        return retVal;
    }

    @Override
    public ExpenseDto getExpenseByExpenseId(String expenseId) {

        ExpenseEntity expenseEntity = expenseRepository.findByExpenseId(expenseId);
        if (expenseEntity == null) {
            System.out.println("ha");
            // TODO: throw new UsernameNotFoundException(userId);
        }

        ExpenseDto retVal = new ExpenseDto();
        BeanUtils.copyProperties(expenseEntity, retVal);

        return retVal;
    }

    @Override
    public ExpenseDto updateExpense(String expenseId, ExpenseDto expense) {
        ExpenseEntity expenseEntity = expenseRepository.findByExpenseId(expenseId);
        if (expenseEntity == null) {
            System.out.println("ha");
            // TODO: throw new UsernameNotFoundException(userId);
        }

        if (expense.getActor() != null && !expense.getActor().equals("")) {
            expenseEntity.setActor(expense.getActor());
        }
        if (expense.getAmount() != 0) {
            expenseEntity.setAmount(expense.getAmount());
        }
        if (expense.getCategory() != null && !expense.getCategory().equals("")) {
            expenseEntity.setCategory(expense.getCategory());
        }
        if (expense.getSubCategory() != null && !expense.getSubCategory().equals("")) {
            expenseEntity.setSubCategory(expense.getSubCategory());
        }
        if (expense.getDate() != null && !expense.getDate().equals("")) {
            expenseEntity.setDate(expense.getDate());
        }
        if (expense.getDay() != null && !expense.getDay().equals("")) {
            expenseEntity.setDay(expense.getDay());
        }

        ExpenseEntity updatedExpenseEntity = expenseRepository.save(expenseEntity);

        ExpenseDto retVal = new ExpenseDto();
        BeanUtils.copyProperties(updatedExpenseEntity, retVal);

        return retVal;
    }

}
