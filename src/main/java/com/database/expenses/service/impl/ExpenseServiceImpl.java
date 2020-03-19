package com.database.expenses.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

        ExpenseEntity storedAlready = findDuplicateEntity(expense);
        if (storedAlready != null) {
            throw new RuntimeException("Same record already exist");
        }

        ExpenseEntity expenseEntity = new ExpenseEntity();
        BeanUtils.copyProperties(expense,expenseEntity);

        expenseEntity.setExpenseId(randomGenerator.generateExpenseId(32));

        ExpenseEntity storedEntity = expenseRepository.save(expenseEntity);

        ExpenseDto retVal = new ExpenseDto();
        BeanUtils.copyProperties(storedEntity,retVal);

        return retVal;
    }

    // Check uniqueness of record to be added
    private ExpenseEntity findDuplicateEntity(ExpenseDto expense) {
        ExpenseEntity storedAlready = expenseRepository.findByDateAndAmountAndCategoryAndSubCategory(
                                                            expense.getDate(), expense.getAmount(),
                                                            expense.getCategory(), expense.getSubCategory());

        return storedAlready;
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

    @Override
    public void deleteExpenses(String expenseId) {
        ExpenseEntity expenseEntity = expenseRepository.findByExpenseId(expenseId);
        if (expenseEntity == null) {
            System.out.println("haha");
            // TODO: throw new UsernameNotFoundException(userId);
        }
        expenseRepository.delete(expenseEntity);
    }

    @Override
    public List<ExpenseDto> getExpenses(int page, int limit) {
        List<ExpenseDto> returnValue = new ArrayList<>();

        if (page>0) page = page-1; // 0 is start of database index

        Pageable pageable = PageRequest.of(page, limit);
        Page<ExpenseEntity> expensePages = expenseRepository.findAll(pageable);

        List<ExpenseEntity> expenseEntities = expensePages.getContent();
        for (ExpenseEntity expenseEntity : expenseEntities) {
            ExpenseDto expenseDto = new ExpenseDto();
            BeanUtils.copyProperties(expenseEntity, expenseDto);
            returnValue.add(expenseDto);
        }

        return returnValue;
    }
}
