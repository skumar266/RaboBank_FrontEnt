package com.rabo.custstatement.custstatementprocessor.model;

import java.util.List;

public class RaboOutPut {
	private String raboOutPutMsg;
	private int raboOutputCode;
	private List<FailedRecord> FieldsList;
	public String getRaboOutPutMsg() {
		return raboOutPutMsg;
	}
	public void setRaboOutPutMsg(String raboOutPutMsg) {
		this.raboOutPutMsg = raboOutPutMsg;
	}
	public int getRaboOutputCode() {
		return raboOutputCode;
	}
	public void setRaboOutputCode(int raboOutputCode) {
		this.raboOutputCode = raboOutputCode;
	}
	
	public List<FailedRecord> getFieldsList() {
		return FieldsList;
	}
	public void setFieldsList(List<FailedRecord> fieldsList) {
		FieldsList = fieldsList;
	}
	@Override
	public String toString() {
		return "RaboOutPut [raboOutPutMsg=" + raboOutPutMsg + ", raboOutputCode=" + raboOutputCode + ", FieldsList="
				+ FieldsList + "]";
	}

	

	
}