package com.ssh.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.dom4j.DocumentException;

import com.opensymphony.xwork2.ActionSupport;
import com.ssh.entities.Testcasebpel;
import com.ssh.entities.Testpathbpel;
import com.ssh.service.TestcasebpelService;
import com.ssh.service.TestpathbpelService;

public class TestbpelAction extends ActionSupport implements RequestAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TestpathbpelService testpathbpelService;
	private TestcasebpelService testcasebpelService;
	private Testpathbpel testpathbpel;
	private Testcasebpel testcasebpel;
	private static int testpathTotalNumber = 0;
	public void setTestpathbpelService(TestpathbpelService testpathbpelService){
		this.testpathbpelService = testpathbpelService;
	}
	public void setTestcasebpelService(TestcasebpelService testcasebpelService){
		this.testcasebpelService = testcasebpelService;
	}
	public Testpathbpel getTestpathbpel() {
		return testpathbpel;
	}
	public void setTestpathbpel(Testpathbpel testpathbpel) {
		this.testpathbpel = testpathbpel;
	}
	public Testcasebpel getTestcasebpel() {
		return testcasebpel;
	}
	public void setTestcasebpel(Testcasebpel testcasebpel) {
		this.testcasebpel = testcasebpel;
	}
	
	public String list() throws DocumentException, IOException
	{
		
		//���ɲ���·��֮ǰ�������Testpathbpel����Ϊ�����Լ������Ҫ�����Testcasebpel��
		/*testcasebpelService.deleteAll();
		testpathbpelService.deleteAll();*/
		
		//���ɲ���·������
		/*testpathbpelService.testPathGenerate();
		testcasebpelService.testcaseGenerate();*/
		
		List list = testcasebpelService.getAll();
		
		request.put("testpaths",testpathbpelService.getAll());
		request.put("testcases", list);
		testpathTotalNumber = list.size();
		request.put("testpathTotalNumber",testpathTotalNumber);
		return "list";
	}
	/**
	 * �����������ϰ��ղ���·��ɸѡ
	 * @return
	 */
	public String pathselect(){
		System.out.println("��������ɸѡ����Ϊ��"+ testpathbpel.getTestpath());
		request.put("testpaths",testpathbpelService.getAll());
		request.put("testcases", testcasebpelService.getSelectAll(testpathbpel));
		request.put("testpathTotalNumber",testpathTotalNumber);
		return "pathselect";
	}
	private Map<String,Object> request;
	
	@Override
	public void setRequest(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		this.request = arg0;
	}
}
