package com.ssh.action;

import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ssh.service.TestcasebpelService;

public class TestcasebpelAction extends ActionSupport implements RequestAware{
	private TestcasebpelService testcasebpelService;
	public void setTestcasebpelService(TestcasebpelService testcasebpelService){
		this.testcasebpelService = testcasebpelService;
	}
	public String list()
	{
		request.put("testcases", testcasebpelService.getAll());
		return "list";
	}
	private Map<String,Object> request;
	
	@Override
	public void setRequest(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		this.request = arg0;
	}
}
