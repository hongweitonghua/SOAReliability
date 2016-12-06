package com.ssh.service;

import java.util.List;

import com.ssh.dao.EmployeeDao;
import com.ssh.entities.Employee;

public class EmployeeService {
	private EmployeeDao employeeDao;
	
	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	 public List<Employee> getAll()
	 {
		 return employeeDao.getAll();
	 }
	 
	 
	 public void deleteEmployee(Integer id)
	 {
		 employeeDao.deleteEmployee(id);
	 }
}
