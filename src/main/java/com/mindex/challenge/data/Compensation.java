package com.mindex.challenge.data;

import java.time.Instant;
import java.util.Date;

public class Compensation {
	private String employeeId;
	private float salary;

	//@JsonFormat(pattern = "yyyy/MM/dd")
	private Instant effectiveDate;

	public Compensation() {
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public void setEffectiveDate(Instant date) {
		this.effectiveDate = date;
	}

	public String getEmployeeId() {
		return this.employeeId;
	}

	public float getSalary() {
		return this.salary;
	}

	public Instant getEffectiveDate() {
		return this.effectiveDate;
	}

}