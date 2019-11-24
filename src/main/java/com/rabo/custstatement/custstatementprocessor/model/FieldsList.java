package com.rabo.custstatement.custstatementprocessor.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FieldsList {
	private List<Fields> fieldList;

	public FieldsList() {
	}

	public FieldsList(List<Fields> fieldList) {
		super();
		this.fieldList = fieldList;
	}

	@XmlElement(name="record")
	public List<Fields> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<Fields> fieldList) {
		this.fieldList = fieldList;
	}

	
}
