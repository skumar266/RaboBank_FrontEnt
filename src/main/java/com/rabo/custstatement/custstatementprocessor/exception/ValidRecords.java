package com.rabo.custstatement.custstatementprocessor.exception;

public class ValidRecords extends Exception {
	private static final long serialVersionUID = 1L;

	private String errorMessage;

	public String getErrorMessage() {

		return errorMessage;

	}

	public ValidRecords(String errorMessage) {

		super(errorMessage);

		this.errorMessage = errorMessage;

	}

	public ValidRecords() {

		super();

	}
}
