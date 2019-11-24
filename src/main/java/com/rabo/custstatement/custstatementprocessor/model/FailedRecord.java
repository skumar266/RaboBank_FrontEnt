package com.rabo.custstatement.custstatementprocessor.model;

public class FailedRecord {
	
	private int transRefNo;
	private String description;
	public int getTransRefNo() {
		return transRefNo;
	}
	public void setTransRefNo(int transRefNo) {
		this.transRefNo = transRefNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public FailedRecord(int transRefNo, String description) {
		super();
		this.transRefNo = transRefNo;
		this.description = description;
	}
	
	

}
