package com.database.expenses.ui.model.response;

public enum ErrorMessages {

	INTERNAL_SERVER_ERROR ("Internal server error"),
	INTERNAL_DATABASE_ERROR ("Internal database error"),
	MISSING_REQUIRED_FIELDS ("Missing required fields"),
	RECORD_ALREADY_EXISTS ("Record already exists"),
	RECORD_DOES_NOT_EXIST ("Record does not exist");
	
	private String errorMessage;
	
	ErrorMessages(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
}
