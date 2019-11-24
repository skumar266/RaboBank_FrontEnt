package com.rabo.custstatement.custstatementprocessor.service;

import java.util.List;

import com.rabo.custstatement.custstatementprocessor.model.Fields;

public interface CustomerStatementValidationService {

public List<Fields> getduplicateData(List<Fields> fields);
	
	public List<Fields> getBalanceDiscrepancyData(List<Fields> fields);
}
