package com.rabo.custstatement.custstatementprocessor.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.rabo.custstatement.custstatementprocessor.model.Fields;

public interface GetCustStatementsService {

	public List<Fields> getCSVRecords(File file) throws Exception;

	public List<Fields> getXMLRecords(MultipartFile file) throws Exception;

}
