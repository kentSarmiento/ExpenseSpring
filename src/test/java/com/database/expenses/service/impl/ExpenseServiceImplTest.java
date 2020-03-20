package com.database.expenses.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import com.database.expenses.ExpenseRepository;
import com.database.expenses.io.entity.ExpenseEntity;
import com.database.expenses.shared.RandomGenerator;
import com.database.expenses.shared.dto.ExpenseDto;

class ExpenseServiceImplTest {

    @InjectMocks
    ExpenseServiceImpl expenseService;

    @Mock
    ExpenseRepository expenseRepository;

    @Mock
    RandomGenerator randomGenerator;

    private final long FIXED_ID = 1L;
    private final double FIXED_AMOUNT = 10000;
    private final String FIXED_DATE = "fixed-date";
    private final String FIXED_DAY = "fixed-day";
    private final String FIXED_CATEGORY = "fixed-category";
    private final String FIXED_SUB_CATEGORY = "fixed-sub-category";
    private final String FIXED_ACTOR = "fixed-actor";
    private final String FIXED_EXPENSE_ID = "fixed-expense-id";

    private final String UNIQUE_EXPENSE_ID = "duplicate-expense-id";

    private ExpenseEntity fixedExpenseEntity;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        fixedExpenseEntity = new ExpenseEntity();

        fixedExpenseEntity.setId(FIXED_ID);
        fixedExpenseEntity.setAmount(FIXED_AMOUNT);
        fixedExpenseEntity.setDate(FIXED_DATE);
        fixedExpenseEntity.setDay(FIXED_DAY);
        fixedExpenseEntity.setCategory(FIXED_CATEGORY);
        fixedExpenseEntity.setSubCategory(FIXED_SUB_CATEGORY);
        fixedExpenseEntity.setActor(FIXED_ACTOR);
        fixedExpenseEntity.setExpenseId(FIXED_EXPENSE_ID);
    }

    @Test
    void testGetExpenseByExpenseId() {
        // Mock setup
        when(expenseRepository.findByExpenseId(FIXED_EXPENSE_ID)).thenReturn(fixedExpenseEntity);

        // Test
        ExpenseDto expenseDto = expenseService.getExpenseByExpenseId(FIXED_EXPENSE_ID);

        // Assertions
        assertAll(
           () -> {
             assertNotNull(expenseDto);

             assertEquals(FIXED_ID, expenseDto.getId());
             assertEquals(FIXED_AMOUNT, expenseDto.getAmount());
             assertEquals(FIXED_DATE, expenseDto.getDate());
             assertEquals(FIXED_DAY, expenseDto.getDay());
             assertEquals(FIXED_CATEGORY, expenseDto.getCategory());
             assertEquals(FIXED_SUB_CATEGORY, expenseDto.getSubCategory());
             assertEquals(FIXED_ACTOR, expenseDto.getActor());
             assertEquals(FIXED_EXPENSE_ID, expenseDto.getExpenseId());
           }
        );
    }

    @Test
    void testGetExpenseNoRecordExists() {
        // Mock setup
        when(expenseRepository.findByExpenseId(UNIQUE_EXPENSE_ID)).thenReturn(null);

        // Test and Assertion
        assertThrows(RuntimeException.class,
           () -> {
               expenseService.getExpenseByExpenseId(UNIQUE_EXPENSE_ID);
           }
        );
    }

    @Test
    void testAddExpenseUnique() {
        // Mock setup
        when(expenseRepository.findByDateAndAmountAndCategoryAndSubCategory(
                                FIXED_DATE, FIXED_AMOUNT, FIXED_CATEGORY, FIXED_SUB_CATEGORY)).
                                thenReturn(null);
        when(expenseRepository.save(any())).thenReturn(fixedExpenseEntity);
        when(randomGenerator.generateExpenseId(anyInt())).thenReturn(FIXED_EXPENSE_ID);

        // Test setup
        ExpenseDto expenseDto = new ExpenseDto();
        BeanUtils.copyProperties(fixedExpenseEntity, expenseDto);

        // Test
        ExpenseDto addedExpenseDto = expenseService.addExpense(expenseDto);

        // Assertion
        assertAll(
           () -> {
             assertNotNull(addedExpenseDto);

             assertEquals(FIXED_ID, addedExpenseDto.getId());
             assertEquals(FIXED_AMOUNT, addedExpenseDto.getAmount());
             assertEquals(FIXED_DATE, addedExpenseDto.getDate());
             assertEquals(FIXED_DAY, addedExpenseDto.getDay());
             assertEquals(FIXED_CATEGORY, addedExpenseDto.getCategory());
             assertEquals(FIXED_SUB_CATEGORY, addedExpenseDto.getSubCategory());
             assertEquals(FIXED_ACTOR, addedExpenseDto.getActor());
             assertEquals(FIXED_EXPENSE_ID, addedExpenseDto.getExpenseId());
           }
        );
    }

    @Test
    void testAddExpenseSameRecordExists() {
        // Mock setup
        when(expenseRepository.findByDateAndAmountAndCategoryAndSubCategory(
                                FIXED_DATE, FIXED_AMOUNT, FIXED_CATEGORY, FIXED_SUB_CATEGORY)).
                                thenReturn(fixedExpenseEntity);

        // Test setup
        ExpenseDto expenseDto = new ExpenseDto();
        BeanUtils.copyProperties(fixedExpenseEntity, expenseDto);

        // Test
        assertThrows(RuntimeException.class,
           () -> {
               expenseService.addExpense(expenseDto);
           }
        );
    }

    @Test
    void testUpdateExpense() {
        // Mock setup
        when(expenseRepository.findByExpenseId(FIXED_EXPENSE_ID)).thenReturn(fixedExpenseEntity);
        when(expenseRepository.save(any())).thenReturn(fixedExpenseEntity);

        // Test setup
        ExpenseDto expenseDto = new ExpenseDto();
        BeanUtils.copyProperties(fixedExpenseEntity, expenseDto);

        expenseDto.setAmount(FIXED_AMOUNT+1);

        // Test
        ExpenseDto updatedExpenseDto = expenseService.updateExpense(FIXED_EXPENSE_ID, expenseDto);

        // Assertion
        assertAll(
           () -> {
             assertNotNull(updatedExpenseDto);

             assertEquals(FIXED_ID, updatedExpenseDto.getId());
             assertEquals(expenseDto.getAmount(), updatedExpenseDto.getAmount());
             assertEquals(FIXED_DATE, updatedExpenseDto.getDate());
             assertEquals(FIXED_DAY, updatedExpenseDto.getDay());
             assertEquals(FIXED_CATEGORY, updatedExpenseDto.getCategory());
             assertEquals(FIXED_SUB_CATEGORY, updatedExpenseDto.getSubCategory());
             assertEquals(FIXED_ACTOR, updatedExpenseDto.getActor());
             assertEquals(FIXED_EXPENSE_ID, updatedExpenseDto.getExpenseId());
           }
        );
    }

    @Test
    void testUpdateExpenseNoRecordExists() {
        // Mock setup
        when(expenseRepository.findByExpenseId(UNIQUE_EXPENSE_ID)).thenReturn(null);

        // Test and Assertion
        assertThrows(RuntimeException.class,
           () -> {
               expenseService.updateExpense(UNIQUE_EXPENSE_ID, new ExpenseDto());
           }
        );
    }

    @Test
    void testDeleteExpense() {
        // Mock setup
        when(expenseRepository.findByExpenseId(FIXED_EXPENSE_ID)).thenReturn(fixedExpenseEntity);

        // Test
        expenseService.deleteExpenses(FIXED_EXPENSE_ID); // TODO: How to verify?
    }


    @Test
    void testDeleteExpenseNoRecordExists() {
        // Mock setup
        when(expenseRepository.findByExpenseId(UNIQUE_EXPENSE_ID)).thenReturn(null);

        // Test and Assertion
        assertThrows(RuntimeException.class,
           () -> {
               expenseService.deleteExpenses(UNIQUE_EXPENSE_ID);
           }
        );
    }

}