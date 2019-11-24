package com.rabo.custstatement.custstatementprocessor.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Fields {
	
	private int transRef;
	private String accno;
	private double startBal;
	private double mutation;
	private String description;
	private double endBal;
	public Fields(int transRef, String accno, double startBal, double mutation, String description, double endBal) {
		super();
		this.transRef = transRef;
		this.accno = accno;
		this.startBal = startBal;
		this.mutation = mutation;
		this.description = description;
		this.endBal = endBal;
	}
	
	
@Override
	public String toString() {
		return "Fields [transRef=" + transRef + ", accno=" + accno + ", startBal=" + startBal + ", mutation=" + mutation
				+ ", description=" + description + ", endBal=" + endBal + "]";
	}


public Fields() {
	
}
	@XmlAttribute(name="reference")
	public int getTransRef() {
		return transRef;
	}
	public void setTransRef(int transRef) {
		this.transRef = transRef;
	}
	
	@XmlElement(name="accountNumber")
	public String getAccno() {
		return accno;
	}
	
	public void setAccno(String accno) {
		this.accno = accno;
	}
	
	@XmlElement(name="startBalance")
	public double getStartBal() {
		return startBal;
	}
	
	public void setStartBal(double startBal) {
		this.startBal = startBal;
	}
	
	@XmlElement(name="mutation")
	public double getMutation() {
		return mutation;
	}
	
	public void setMutation(double mutation) {
		this.mutation = mutation;
	}
	
	@XmlElement(name="description")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@XmlElement(name="endBalance")
	public double getEndBal() {
		return endBal;
	}
	
	public void setEndBal(double endBal) {
		this.endBal = endBal;
	}
}
