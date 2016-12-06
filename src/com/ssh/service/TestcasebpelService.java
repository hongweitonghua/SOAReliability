package com.ssh.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.DocumentException;

import com.ssh.dao.TestcasebpelDao;
import com.ssh.dao.TestpathbpelDao;
import com.ssh.entities.Restrition;
import com.ssh.entities.Testcasebpel;
import com.ssh.entities.Testpathbpel;
import com.ssh.utils.GenerateTestData;
import com.ssh.utils.GenerateTestPath;

public class TestcasebpelService {
	private TestcasebpelDao testcasebpelDao;
	private static TestpathbpelDao testpathbpelDao;
	/*private String[] arr;*/
	
	public void setTestcasebpelDao(TestcasebpelDao testcasebpelDao){
		this.testcasebpelDao = testcasebpelDao;
	}
	
	public void setTestpathbpelDao(TestpathbpelDao testpathbpelDao){
		this.testpathbpelDao = testpathbpelDao;
	}
	
	public List<Testcasebpel> getAll(){
		return testcasebpelDao.getAll();
	}
	
	public List<Testcasebpel> getSelectAll(Testpathbpel testpathbpel){
		if(testpathbpel==null||testpathbpel.getTestpath().equals("")){
			return testcasebpelDao.getAll();
		}else{
			testpathbpel.setTestpath(testpathbpel.getTestpath().trim());
			testpathbpel = testpathbpelDao.getObjByPath(testpathbpel);
			System.out.println("===testpathbpel.getId()==========="+testpathbpel.getId());
			return testcasebpelDao.getSelectAll(testpathbpel);
		}
	}
	
	public void deleteAll(){
		testcasebpelDao.deleteAll();
	}

	public void testcaseGenerate() throws DocumentException, IOException{
		/*String[] arr = {"type=0 && b>24","type=0 && b<24","type=1 && a>10","type=1 && a<10"};*/
		String[] varName = {"a","b","type"};
		List<Testpathbpel> pathlist = testpathbpelDao.getAll();
		for (Testpathbpel testpathbpel : pathlist) {
			System.out.println(testpathbpel.getTestpath());
		}
		Map<Testpathbpel,ArrayList> testCaseListMap = parseStringArray(pathlist,varName);
		/*for (Map.Entry<String, ArrayList> en: testCaseListMap.entrySet()) {
			ArrayList list = en.getValue();
			for (Object obj : list) {
				String str =(String)obj;
				//得到测试路径对应的id
				Testpathbpel tpath = new Testpathbpel();
				tpath.setId(82);
				tpath.setTestpath(en.getKey());
				tpath = testpathbpelDao.getObjByPath(tpath);
				//插入测试用例
				Testcasebpel tcase = new Testcasebpel();
				tcase.setTestpathbpel(tpath);
				tcase.setTestcase("("+str+")");
				testcasebpelDao.testcaseInsert(tcase);
			}		
		}*/
		System.out.println("testCaseListMap:---------------------");
		for(Entry<Testpathbpel,ArrayList> en : testCaseListMap.entrySet()) {
		    System.out.println(en.getKey());
		    for (Object object : en.getValue()) {
				String str = (String)object;
				System.out.println(str);
			}
		}
		testcasebpelDao.testcaseInterAll(testCaseListMap);
		
	}
	public static Map<Testpathbpel, ArrayList> parseStringArray(List<Testpathbpel> pathlist,String[] varName){
		Map<Testpathbpel,ArrayList> testCaseListMap = new HashMap<Testpathbpel,ArrayList>();
		String[] resName = {">=","<=","<",">","="};
		ArrayList<Restrition> list = new ArrayList<>();
		for(int i = 0; i< pathlist.size(); i++){ //"type=0^b>24"
			list.clear();		
			System.out.println("-----------针对测试路径"+ (i+1) +":");
			String newStr = pathlist.get(i).getTestpath().replace('[', ' ').replace(']', ' ').trim();
			String[] newArr = newStr.split(", ");
			for(int j=0; j<newArr.length; j++){ //"type=0
				int index;				
				for (String string : resName) {
					if((index =newArr[j].indexOf(string))!=-1){
						Restrition r = new Restrition();
						r.setName(newArr[j].substring(0, index));
						r.setRestritionName(string);
						if(string.equals(">=")||string.equals("<="))
							r.setRestritionValue(newArr[j].substring(index+2));
						else
							r.setRestritionValue(newArr[j].substring(index+1));
						list.add(r);
						break;
					}
				}
			}
			for (Restrition restrition : list) {
				System.out.println(restrition);
			}
			//针对每条测试路径，生成测试数据
			ArrayList<ArrayList> testDataList = generateTestData(list,varName);
			//笛卡尔积
			ArrayList testCaseList = GenerateTestData.generateOriginalTestCase(testDataList);
			
			//封装测试用例对应的tsatpath的id值
			Testpathbpel tpath = new Testpathbpel();
			tpath.setTestpath(pathlist.get(i).getTestpath());
			System.out.println("================="+pathlist.get(i));
			tpath = testpathbpelDao.getObjByPath(tpath);	
			testCaseListMap.put(tpath,testCaseList);
			
			testCasePrint(testCaseList);
		}
		return testCaseListMap;
	}
	public static void testCasePrint(ArrayList testCaseList){
		int count=0;
		System.out.println("++++++++++++++++++++笛卡尔积 ：");
		for (Object object : testCaseList) {
			System.out.println("["+object+"]");
			count++;
		}
		System.out.println("count="+count);
	}
	
	/**
	 * @param list 每条测试路径上解析到的约束list，如下面list展示                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
	 * *example：Restrition [name: type, restritionName: >=, restritionValue: 0]
		          Restrition [name: b, restritionName: >, restritionValue: 24]
	 * @param varName 变量集合
	 * @return 变量的测试用例集合
	 */
	public static ArrayList<ArrayList> generateTestData(ArrayList<Restrition> list,String[] varName ){
		ArrayList<ArrayList> testDataList = new ArrayList<ArrayList>();
		for(String str : varName){
			System.out.println(str+"的测试数据为：");
			int min= GenerateTestData.Button,max=GenerateTestData.Top;
			for (Restrition r : list) {	
				if(r.getName().equals(str)){		
					String resname = r.getRestritionName();
					int value = Integer.parseInt(r.getRestritionValue());
					if(resname.equals(">=")||resname.equals(">")){
						if(value>min)
							min=value;
					}else if(resname.equals("<=")||resname.equals("<")){
						if(value<max)
							max=value;
					}else if(resname.equals("=")){
						min=max=value;
					}
				}
			}
			ArrayList l = GenerateTestData.generateTestDataByRange(min,max);
			for (Object object : l) {
				System.out.print(object+" ");
			}
			System.out.println();
			testDataList.add(l);
		}
		return testDataList;
	}
}

