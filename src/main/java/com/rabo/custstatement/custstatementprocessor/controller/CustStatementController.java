package com.rabo.custstatement.custstatementprocessor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabo.custstatement.custstatementprocessor.exception.CustStmtException;
import com.rabo.custstatement.custstatementprocessor.exception.ErrorResponse;
import com.rabo.custstatement.custstatementprocessor.exception.ValidRecords;
import com.rabo.custstatement.custstatementprocessor.model.RaboOutPut;
import com.rabo.custstatement.custstatementprocessor.service.serviceImpl.FailureDataGeneratorService;

@RestController
@RequestMapping("/rabo")
public class CustStatementController {
    Logger logger = LoggerFactory.getLogger(CustStatementController.class);

	@Autowired FailureDataGeneratorService failureDataGeneratorService;

	@GetMapping("/healthCheck")
	public RaboOutPut healthCheck() throws Exception {
		RaboOutPut raboOutPut = new RaboOutPut();
		return raboOutPut;
	}

	@RequestMapping(value = "/getFailureData", method = RequestMethod.POST)
	public RaboOutPut genereateFailureData(@RequestPart("file") MultipartFile mimeTypes) throws CustStmtException, ValidRecords {
		RaboOutPut raboOutPut=null;
		try {
			raboOutPut=failureDataGeneratorService.failureDataGenerator(mimeTypes);
		}catch (CustStmtException exception) {
			throw new CustStmtException(exception.getMessage());
		}catch (ValidRecords exception) {
			throw new ValidRecords(exception.getMessage());
		}
		catch (Exception exception) {
			exception.printStackTrace();
			throw new CustStmtException(exception.getMessage());
		}
		return raboOutPut;
	}
	
	@ExceptionHandler(CustStmtException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(CustStmtException ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);

	}
	
	@ExceptionHandler(ValidRecords.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(ValidRecords ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.ACCEPTED.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);

	}


}

