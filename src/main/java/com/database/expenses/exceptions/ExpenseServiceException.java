package com.database.expenses.exceptions;

public class ExpenseServiceException extends RuntimeException {

    private static final long serialVersionUID = -4692645812258043899L;

    public ExpenseServiceException(String message) {
        super(message);
    }
}
