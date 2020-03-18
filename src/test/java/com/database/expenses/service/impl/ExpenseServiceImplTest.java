package com.database.expenses.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import com.database.expenses.ExpenseRepository;
import com.database.expenses.io.entity.ExpenseEntity;
import com.database.expenses.shared.dto.ExpenseDto;

class ExpenseServiceImplTest {

    @InjectMocks
    ExpenseServiceImpl expenseService;

    @Mock
    ExpenseRepository expenseRepository;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetExpenseByExpenseId() {

        ExpenseEntity expenseEntity = new ExpenseEntity();
        expenseEntity.setId(1L);
        expenseEntity.setAmount(1500);
        expenseEntity.setCategory("test");
        expenseEntity.setSubCategory("sub");
        expenseEntity.setActor("actor");
        expenseEntity.setExpenseId("expenseId");

        when(expenseRepository.findByExpenseId(anyString())).thenReturn(expenseEntity);

        ExpenseDto expenseDto = expenseService.getExpenseByExpenseId("expenseId");
        assertNotNull(expenseDto);

        assertEquals(1500, expenseDto.getAmount());
    }

}
