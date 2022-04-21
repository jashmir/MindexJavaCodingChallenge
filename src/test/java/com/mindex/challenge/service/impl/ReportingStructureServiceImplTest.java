package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {
	private String reportingStructureUrl;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ReportingStructureService resportStructureService;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setup() {
		reportingStructureUrl = "http://localhost:" + port + "/reportingStructure/{id}";
	}

	@Test
	public void testCreateReadUpdate() {
		Employee employee = employeeRepository.findByEmployeeId("03aa1462-ffa9-4978-901b-7c001562cf6f");
		ReportingStructure expectedReportingStructure = new ReportingStructure(employee, 2);

		ReportingStructure actualReportingStructure = restTemplate
				.getForEntity(reportingStructureUrl, ReportingStructure.class, employee.getEmployeeId()).getBody();
		assertNotNull(actualReportingStructure);
		assertReportingStructureEquals(expectedReportingStructure, actualReportingStructure);
	}

	private void assertReportingStructureEquals(ReportingStructure expectedStructure,
			ReportingStructure actualStructure) {
		assertEquals(expectedStructure.getNumberOfReports(), actualStructure.getNumberOfReports());
		
		Employee expectedEmployee = expectedStructure.getEmployee();
		Employee actualEmployee = actualStructure.getEmployee();
		assertEquals(expectedEmployee.getEmployeeId(), actualEmployee.getEmployeeId());
		assertEquals(expectedEmployee.getDirectReports().size(), actualEmployee.getDirectReports().size());

		assertEquals(expectedEmployee.getDirectReports().get(0).getEmployeeId(),
				actualEmployee.getDirectReports().get(0).getEmployeeId());
		assertNull(expectedEmployee.getDirectReports().get(0).getDirectReports());
		assertNull(actualEmployee.getDirectReports().get(0).getDirectReports());

		assertEquals(expectedEmployee.getDirectReports().get(1).getEmployeeId(),
				actualEmployee.getDirectReports().get(1).getEmployeeId());
		assertNull(expectedEmployee.getDirectReports().get(1).getDirectReports());
		assertNull(actualEmployee.getDirectReports().get(1).getDirectReports());
	}
}