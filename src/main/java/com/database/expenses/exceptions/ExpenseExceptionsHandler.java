package com.database.expenses.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.database.expenses.ui.model.response.DetailedErrorResponse;
import com.database.expenses.ui.model.response.ErrorMessages;
import com.database.expenses.ui.model.response.ErrorResponse;

@ControllerAdvice
public class ExpenseExceptionsHandler {

    @ExceptionHandler(value = {ExpenseServiceException.class})
    public ResponseEntity<Object> handleExpenseServiceException(ExpenseServiceException ex, WebRequest request)
    {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleExceptions(Exception ex, WebRequest request)
    {
        DetailedErrorResponse errorResponse = new DetailedErrorResponse();
        errorResponse.setMessage(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
        errorResponse.setDetails(ex.getMessage());

        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
