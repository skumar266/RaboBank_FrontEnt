package com.rabo.custstatement.custstatementprocessor.service.serviceImpl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.opencsv.CSVReader;
import com.rabo.custstatement.custstatementprocessor.constants.CustStatementConstants;
import com.rabo.custstatement.custstatementprocessor.exception.CustStmtException;
import com.rabo.custstatement.custstatementprocessor.model.Fields;
import com.rabo.custstatement.custstatementprocessor.model.Records;
import com.rabo.custstatement.custstatementprocessor.model.UploadFileResponse;
import com.rabo.custstatement.custstatementprocessor.service.GetCustStatementsService;

@Service
public class GetCustStatementsServiceImpl implements GetCustStatementsService{

	@Override
	public List<Fields> getCSVRecords(File file) throws Exception {
		String[] record = null;
		int rec_cnt=0;
		List<Fields> records=new ArrayList<>();
		CSVReader csvReader = new CSVReader(new FileReader(file));
		Iterator<String[]> iter=csvReader.iterator();
		while(iter.hasNext()) {
			if (rec_cnt == 0) {
				iter.next();
				rec_cnt++;
			}
			if (rec_cnt > 0) {
			record=iter.next();
			Fields fields=new Fields();
			validatenonNumericInput(record[0], CustStatementConstants.reference,rec_cnt);
			fields.setTransRef(Integer.parseInt(record[0]));
			validatenonNumericInput(record[1], CustStatementConstants.accnos,rec_cnt);
			fields.setAccno(record[1]);
			validatenonNumericInput(record[2],CustStatementConstants.descriptions, rec_cnt);
			fields.setDescription(record[2]);
			validatenonNumericInput(record[3],CustStatementConstants.startBalance, rec_cnt);
			fields.setStartBal(Double.parseDouble(record[3]));
			validatenonNumericInput(record[4], CustStatementConstants.mutations,rec_cnt);
			fields.setMutation(Double.parseDouble(record[4]));
			validatenonNumericInput(record[5], CustStatementConstants.endbal,rec_cnt);
			fields.setEndBal(Double.parseDouble(record[5]));
			records.add(fields);
			rec_cnt++;
			}
		}
		return records;
	}
	private void validatenonNumericInput(String record, String fieldname,
			int rec) throws CustStmtException {
		if (StringUtils.isEmpty(record)) {
			throw new CustStmtException("Invalid " + fieldname
					+ " for record no " + rec);
		}
	}
	@Override
	public List<Fields> getXMLRecords(MultipartFile file) throws CustStmtException,Exception {
		ObjectMapper objectMapper = new XmlMapper();
		Records records = null;
		Map<String, String> map = new HashMap<String, String>();
		
		try {
			records = objectMapper.readValue(StringUtils.toEncodedString(
					file.getBytes(), StandardCharsets.UTF_8), Records.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getXMLData(records);
	}
private List<Fields> getXMLData(Records records) throws CustStmtException{
	List<Fields> fields=new ArrayList<>();
	try {
		int rec_cnt=0;
	for (UploadFileResponse r : records.getRecord()) {
		Fields field=new Fields();
		field.setAccno(r.getAccountNumber());
		validatenonNumericInput(r.getReference(), CustStatementConstants.reference,rec_cnt);
		field.setTransRef(Integer.parseInt((r.getReference())));
		validatenonNumericInput(r.getDescription(), CustStatementConstants.descriptions,rec_cnt);
		field.setDescription((r.getDescription()));
		validatenonNumericInput(r.getStartBalance(),CustStatementConstants.startBalance, rec_cnt);
		field.setStartBal(Double.parseDouble(r.getStartBalance()));
		validatenonNumericInput(r.getMutation(), CustStatementConstants.mutations,rec_cnt);
		field.setMutation(Double.parseDouble(r.getMutation()));
		validatenonNumericInput(r.getEndBalance(), CustStatementConstants.endbal,rec_cnt);
		field.setEndBal(Double.parseDouble(r.getEndBalance()));
		fields.add(field);
		rec_cnt++;
}
	}catch (CustStmtException exception) {
	throw new CustStmtException(exception.getMessage());
}
	catch(Exception e) {
		e.printStackTrace();

	}
	return fields;
}
}
