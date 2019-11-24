package com.rabo.custstatement.custstatementprocessor.service.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rabo.custstatement.custstatementprocessor.model.Fields;
import com.rabo.custstatement.custstatementprocessor.service.CustomerStatementValidationService;

@Service
public class CustomerStatementValidationServiceImpl implements CustomerStatementValidationService{
    Logger logger = LoggerFactory.getLogger(CustomerStatementValidationServiceImpl.class);

	@Override
	public List<Fields> getduplicateData(List<Fields> data) {
		List<Fields> duplicateData = new ArrayList<>();
		try {
		Set<Integer> refno=new HashSet<>();
		Iterator<Fields> it=data.iterator();
		while (it.hasNext()) {
			Fields iteratedata=it.next();
			if(refno.contains(iteratedata.getTransRef())) {
				duplicateData.add(iteratedata);
			}else {
				refno.add(iteratedata.getTransRef());
			}
		}
			
		}catch(Exception exp) {
			logger.error("Exception in getduplicateData method in CustomerStatementValidationServiceImpl");
		}
		return duplicateData;
	}

	@Override
	public List<Fields> getBalanceDiscrepancyData(List<Fields> fields) {
		List<Fields> endBalanceErrorRecords = new ArrayList<Fields>();
		try {
		for (Fields field : fields) {
			if(!endBalValid(String.valueOf(field.getStartBal()),String.valueOf(field.getMutation()),String.valueOf(field.getEndBal())))
				endBalanceErrorRecords.add(field);
		}
	}catch(Exception exp) {
		logger.error("Exception in getBalanceDiscrepancyData method in CustomerStatementValidationServiceImpl");
	}
		return endBalanceErrorRecords;
	}
	
	private boolean endBalValid(String strt, String mut, String end) {
		boolean isendBalret = false;
		try {
		BigDecimal startBalvalue = BigDecimal.valueOf(Double.valueOf(strt));
		BigDecimal mutValue = BigDecimal.valueOf(Double.valueOf(mut));
		BigDecimal endBalValue = BigDecimal.valueOf(Double.valueOf(end));
		
		BigDecimal expectedEndBalance = startBalvalue.add(mutValue);
		isendBalret = expectedEndBalance.compareTo(endBalValue) == 0;
	}catch(Exception exp) {
		logger.error("Exception in endBalValid method in CustomerStatementValidationServiceImpl");
	}
		return isendBalret;
	}

}
