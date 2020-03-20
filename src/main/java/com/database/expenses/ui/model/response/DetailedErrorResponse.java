package com.database.expenses.ui.model.response;

public class DetailedErrorResponse extends ErrorResponse {

    private String details;

    public DetailedErrorResponse() {}

    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
}
