package com.ssh.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ssh.entities.Testpathbpel;
import com.ssh.service.TestpathbpelService;

public class TestpathbpelAction extends ActionSupport implements RequestAware{
	
	private TestpathbpelService testpathbpelService;
	private Integer id;
	public void setId(Integer id) {
		this.id = id;
	}
	public void setTestpathbpelService(TestpathbpelService testpathbpelService){
		this.testpathbpelService = testpathbpelService;
	}
	public String list(){
		request.put("testpaths",testpathbpelService.getAll());
		return "list";
	}
	private Map<String,Object> request;
	
	@Override
	public void setRequest(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		this.request = arg0;
	}
}
