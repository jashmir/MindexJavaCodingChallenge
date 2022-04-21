package com.mindex.challenge.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

	private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public ReportingStructure read(String id) {
		LOG.debug("Creating reporting structure with employee id [{}]", id);

		Employee employee = employeeRepository.findByEmployeeId(id);

		if (employee == null) {
			throw new RuntimeException("Invalid employeeId: " + id);
		}

		ReportingStructure reportingStructure = new ReportingStructure(employee);

		reportingStructure.setNumberOfReports(getDirectReportsCount(employee.getDirectReports()));

		return reportingStructure;
	}

	private int getDirectReportsCount(List<Employee> employees) {
		int numberOfReports = 0;
		
		if(employees == null)
		{
			return 0;
		}
		
		for (Employee employee : employees) {
			LOG.debug(employee.getEmployeeId());
				numberOfReports += 1 + getDirectReportsCount(
						employeeRepository.findByEmployeeId(employee.getEmployeeId()).getDirectReports());
		}

		return numberOfReports;
	}

}
