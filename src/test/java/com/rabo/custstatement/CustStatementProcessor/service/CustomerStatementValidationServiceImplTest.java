package com.rabo.custstatement.CustStatementProcessor.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import com.rabo.custstatement.custstatementprocessor.model.Fields;
import com.rabo.custstatement.custstatementprocessor.service.serviceImpl.CustomerStatementValidationServiceImpl;

@RunWith(SpringRunner.class)
public class CustomerStatementValidationServiceImplTest {
	
	@InjectMocks
	CustomerStatementValidationServiceImpl customerStatementValidationServiceImpl;
	
	private static List<Fields> realTimeData() {
		List<Fields> csvdata = new ArrayList<>();
		try {
			Fields fieldData1 =new Fields();
			fieldData1.setAccno("NL91RABO0315273637");
			fieldData1.setDescription("Clothes from Jan Bakker");
			fieldData1.setEndBal(148);
			fieldData1.setMutation(23);
			fieldData1.setStartBal(125);
			fieldData1.setTransRef(194261);
			csvdata.add(fieldData1);
			
			Fields fieldData2 =new Fields();
			fieldData2.setAccno("NL91RABO0315273637");
			fieldData2.setDescription("Clothes from Ramasamy");
			fieldData2.setEndBal(68);
			fieldData2.setMutation(23);
			fieldData2.setStartBal(45);
			fieldData2.setTransRef(19426);
			csvdata.add(fieldData2);
			
			Fields fieldData3 =new Fields();
			fieldData3.setAccno("NL91RABO0315273");
			fieldData3.setDescription("Clothes from Kandasamy");
			fieldData3.setEndBal(51);
			fieldData3.setMutation(48);
			fieldData3.setStartBal(3);
			fieldData3.setTransRef(45);
			csvdata.add(fieldData3);

		} catch (Exception e) {
			e.getMessage();
		}
		return csvdata;
	}
	
	@Test
	public void getduplicateDataTest() {
		List<Fields> actual = customerStatementValidationServiceImpl.getduplicateData(realTimeData());
		assert(actual.isEmpty());
	}
	
	@Test
	public void getBalanceDiscrepancyDataTest() {
		List<Fields> actual = customerStatementValidationServiceImpl.getBalanceDiscrepancyData(realTimeData());
		assert(actual.isEmpty());
	}
	
}
