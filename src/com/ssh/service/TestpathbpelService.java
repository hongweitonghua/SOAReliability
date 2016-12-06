package com.ssh.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;

import com.ssh.dao.TestpathbpelDao;
import com.ssh.entities.Restrition;
import com.ssh.entities.Testpathbpel;
import com.ssh.utils.GenerateTestData;
import com.ssh.utils.GenerateTestPath;

public class TestpathbpelService {
	private static TestpathbpelDao testpathbpelDao;
	public void setTestpathbpelDao(TestpathbpelDao testpathbpelDao){
		this.testpathbpelDao = testpathbpelDao;
	}
	
	public List<Testpathbpel> getAll(){
		System.out.println("TestpathbpelService");
		return testpathbpelDao.getAll();
	}
	public void deleteAll(){
		testpathbpelDao.deleteAll(); 
	}
	public void testPathInsert(Testpathbpel t){
		testpathbpelDao.testPathInsert(t);
	}
	public void testPathGenerate() throws DocumentException, IOException{
		
		String[] arr = {"type=0 && b>24","type=0 && b<24","type=1 && a>10","type=1 && a<10"};
		String[] varName = {"a","b","type"};
		/*for (String string : arr) {
			Testpathbpel t = new Testpathbpel();
			t.setTestpath(string);
			testpathbpelDao.testPathInsert(t);
		}*/
		GenerateTestPath g = new GenerateTestPath();
		ArrayList arrlist = g.getPathList();
		System.out.println("----------pathList=");
		for (Object object : arrlist) {
			String string  = (String)object;
			Testpathbpel t = new Testpathbpel();
			t.setTestpath(string);
			System.out.println(string);
			testpathbpelDao.testPathInsert(t);
		}
	}
}
	
