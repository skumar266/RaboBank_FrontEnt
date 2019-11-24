package com.rabo.custstatement.CustStatementProcessor.service;

import static org.junit.Assert.assertNotEquals;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import com.rabo.custstatement.custstatementprocessor.model.RaboOutPut;
import com.rabo.custstatement.custstatementprocessor.service.CustomerStatementValidationService;
import com.rabo.custstatement.custstatementprocessor.service.GetCustStatementsService;
import com.rabo.custstatement.custstatementprocessor.service.serviceImpl.FailureDataGeneratorService;

@RunWith(SpringRunner.class)
public class FailureDataGeneratorServiceTest {
	@Mock
	 CustomerStatementValidationService customerStatementValidationService;

	@Mock
	 MultipartFile mockMultipartFile;
	
	@Mock
	 GetCustStatementsService getCustStatementsService;
	
	@Mock
	 FailureDataGeneratorService failureDataGeneratorService;
	
	@Test
	public void failureDataGeneratorTest() throws IOException, Exception {
		RaboOutPut raboOutPut=failureDataGeneratorService.failureDataGenerator(mockMultipartFile);
		assertNotEquals(RaboOutPut.class, raboOutPut);
	}
	
	
}
