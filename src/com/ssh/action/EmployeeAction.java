package com.ssh.action;

import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.springframework.web.context.request.RequestAttributes;

import com.ssh.service.EmployeeService;
import com.opensymphony.xwork2.ActionSupport;



public class EmployeeAction extends ActionSupport implements RequestAware{

	private static final long serialVersionUID = 1L;
	private EmployeeService employeeService;
	private Integer id;
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	public String list()
	{
		request.put("employees", employeeService.getAll());
		request.put("111", employeeService.getAll());
		return "list";
	}
	
	public String delete()
	{
		employeeService.deleteEmployee(id);
		return "delete";
	}

	private Map<String,Object> request;
	@Override
	public void setRequest(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		this.request=arg0;
	}

	
}
