package com.rabo.custstatement.custstatementprocessor.service.serviceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rabo.custstatement.custstatementprocessor.constants.CustStatementConstants;
import com.rabo.custstatement.custstatementprocessor.exception.CustStmtException;
import com.rabo.custstatement.custstatementprocessor.exception.ValidRecords;
import com.rabo.custstatement.custstatementprocessor.model.FailedRecord;
import com.rabo.custstatement.custstatementprocessor.model.Fields;
import com.rabo.custstatement.custstatementprocessor.model.RaboOutPut;
import com.rabo.custstatement.custstatementprocessor.service.CustomerStatementValidationService;
import com.rabo.custstatement.custstatementprocessor.service.GetCustStatementsService;

@Service
public class FailureDataGeneratorService {
    Logger logger = LoggerFactory.getLogger(FailureDataGeneratorService.class);

	@Autowired
	private CustomerStatementValidationService customerStatementValidationService;

	@Autowired
	private GetCustStatementsService getCustStatementsService;
	
	private File convertMultiPartToFile(MultipartFile file ) throws IOException
    {
        File convFile = new File( file.getOriginalFilename() );
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( file.getBytes() );
        fos.close();
        return convFile;
    }
	
	private static String getSubTypeOf(String mimeType) {
		int index = mimeType.indexOf('/');
		String subType = mimeType.substring(index + 1);
		if (StringUtils.contains(subType, CustStatementConstants.EXCEL)) {
			return CustStatementConstants.CSV;
		} else {
			return subType;
		}
	}
	
	private RaboOutPut validationProcess(MultipartFile mimeTypes,String contentType) throws IOException, Exception {
		RaboOutPut raboOutPut = new RaboOutPut();
		try {
		List<Fields> discrePancyRecords = new ArrayList<Fields>();
		List<Fields> extractedRecords = new ArrayList<Fields>();
		if (CustStatementConstants.CSV.equalsIgnoreCase(contentType)) {
		 extractedRecords = getCustStatementsService.getCSVRecords(convertMultiPartToFile(mimeTypes));
		}else if (CustStatementConstants.XML.equalsIgnoreCase(contentType)) {
			 extractedRecords = getCustStatementsService.getXMLRecords(mimeTypes);
		}
		discrePancyRecords.addAll(customerStatementValidationService.getduplicateData(extractedRecords));
		discrePancyRecords.addAll(customerStatementValidationService.getBalanceDiscrepancyData(extractedRecords));
		if (!discrePancyRecords.isEmpty()) {
			raboOutPut.setRaboOutputCode(CustStatementConstants.TWOHUNDRED);
			raboOutPut.setRaboOutPutMsg(CustStatementConstants.ERROR_IN_VALIDATION);
			raboOutPut.setFieldsList(displayRefNoDescription(discrePancyRecords));
		} else {
			throw new ValidRecords("All Records are Valid");
		}
		}
		catch (CustStmtException exception) {
			throw new CustStmtException(exception.getMessage());
		}catch (ValidRecords exception) {
			throw new ValidRecords("All Records are Valid");
		}
		catch(Exception exp) {
			logger.error("Exception in validationProcess method in FailureDataGeneratorService");
		}
		return raboOutPut;
	}
	public RaboOutPut failureDataGenerator(MultipartFile mimeTypes) throws IOException, Exception {
		RaboOutPut raboOutPut=null;
		try {
		String contentType=getSubTypeOf(mimeTypes.getContentType());
		if (CustStatementConstants.CSV.equalsIgnoreCase(contentType)) {
			raboOutPut=validationProcess(mimeTypes,CustStatementConstants.CSV);
		}
		else if (CustStatementConstants.XML.equalsIgnoreCase(contentType)) {
			raboOutPut=validationProcess(mimeTypes,CustStatementConstants.XML);
		}
	}catch (CustStmtException exception) {
		throw new CustStmtException(exception.getMessage());
	}catch (ValidRecords exception) {
		throw new ValidRecords("All records are valid");
	}
		catch(Exception exp) {
		logger.error("Exception in failureDataGenerator method in FailureDataGeneratorService");
	}
		return raboOutPut;

	}
	
	private List<FailedRecord> displayRefNoDescription(List<Fields> discrePancyRecords){
		List<FailedRecord> failedRecordList=new ArrayList<>();
		try {
		Set<String> failedRecord=new HashSet<>();
		Iterator<Fields> failedRecordIterator=discrePancyRecords.iterator();
		while(failedRecordIterator.hasNext())
		{
			Fields fields=failedRecordIterator.next();
			if(failedRecord.add(String.valueOf(fields.getTransRef()).trim()+fields.getDescription().trim())) {
				failedRecordList.add(new FailedRecord(fields.getTransRef(),fields.getDescription()));
			}
		}
	}catch(Exception exp) {
		logger.error("Exception in displayRefNoDescription method in FailureDataGeneratorService");
	}
		return failedRecordList;
		
	}

}
