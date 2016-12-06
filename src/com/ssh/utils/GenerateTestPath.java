package com.ssh.utils;


import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Stack;

import javax.wsdl.WSDLException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class GenerateTestPath {
	static Stack IeStack = new Stack();//单独负责存储需要被删除的条件
	static Stack Combo = new Stack(); //负责将条件组合起来
	static ArrayList al = new ArrayList();//存储约束条件
	static boolean flag = false; //判断是否有子条件
	static ArrayList bl = new ArrayList();//负责存储还有子结构的路径约束条件，通过对比 从最终条件中删除
	public GenerateTestPath(){
		al.clear();
	}
	public static void testDom4() throws DocumentException, IOException{ 		
		String t=Thread.currentThread().getContextClassLoader().getResource("").getPath();
		int num=t.indexOf(".metadata");
		String path=t.substring(1,num).replace('/', '\\')+"SSH-Test\\WebContent\\file\\CaculatorProcess.bpel";
		
		SAXReader saxR = new SAXReader(); 
	     Document doc = saxR.read(path);
	     Element root = doc.getRootElement();  //得到根元素
	     Iterator elemi = root.elementIterator();//遍历节点
	     for(;elemi.hasNext();){
	        	Element el =(Element)elemi.next();
	             if(el.getName().equals("sequence")){
	            	iterator(el);
	            }   
	      }   
	}
	private static void iterator(Element elem){
		for( Iterator i = elem.elementIterator(); i.hasNext();){
			Element eleme =(Element)i.next();
			if(eleme.getName().equals("if")){//遇到if，直接记录其条件
				haveStructure(eleme);
					ieCondition(eleme);
				iCondition(eleme);
			}
			if(eleme.getName().equals("elseif")){//不用判断elseif是否有子结构。遇到elseif，直接删除符合条件的if条件以及if之的子条件
				//要判断是否有子结构，若没有子结构，就直接弹出if的一个条件，再push
				//不用判断是否有子结构，直接删除if及其之前的条件
				haveStructure(eleme);
				deleteCondition(eleme);//有子结构， 先删除
				eCondition(eleme);
			}
			iterator(eleme);
		}
	}
	private static void haveStructure(Element elem){//判断是否还有子结构
		for( Iterator i = elem.elementIterator(); i.hasNext();){
			Element eleme=(Element)i.next();
			//System.out.println(eleme.getName());
			if(eleme.getName().equals("if")){//只需要有if就一定有子结构.如果有子结构，则把当前if的条件记录
				//System.out.println("11"); //正确
				flag = true;
			}
			haveStructure(eleme);
		}
	}
	private static void iCondition(Element elem){//if只需要得到条件加入队列就可以了
		for(Iterator i = elem.elementIterator();i.hasNext();){
			Element eleme = (Element)i.next();
			if(eleme.getName().equals("condition")){
				String a[] = eleme.getText().split(":");
				Combo.push(a[1]);
				if(flag==true){//这里只能删除条件数为1的，需要连接起来处理
					bl.add(Combo.toString());
					flag=false;
				}
				//al.add(a[1]);  不能直接add，否则只是单独的一个条件，无法把条件组合起来
				al.add(Combo.toString());
			}
		}
	}
	private static void eCondition(Element elem){
		for(Iterator i = elem.elementIterator();i.hasNext();){
			Element eleme = (Element)i.next();
			if(eleme.getName().equals("condition")){
				String a[] = eleme.getText().split(":");
				Combo.push(a[1]);
				if(flag==true){//这里只能删除条件数为1的，需要连接起来处理
					bl.add(Combo.toString());
					flag=false;
				}
				al.add(Combo.toString());
			}
		}
	}
	private static void ieCondition(Element elem){
		for( Iterator i = elem.elementIterator(); i.hasNext();){
			Element eleme=(Element)i.next();
			if(eleme.getName().equals("condition")){
				String a[] = eleme.getText().split(":");
				IeStack.push(a[1]); //将之后需要删除的条件压栈
				//System.out.println(a[1]);
			}
		}
	}
	private static void deleteCondition(Element elem){
		int b = Combo.search(IeStack.peek()); //不能直接用i<Combo.search(IeStack.peek())， 因为每弹一次，其值都会变
		for(int i=0;i<b;i++){//弹出其它路径的条件
			Combo.pop();
		}
		IeStack.pop(); //弹出已删除的条件
	}
	private static void reduceCondition(){ //将路径上的子条件删除，得到路径上的所有条件
		for(int i=0;i<bl.size();i++){
			String u = new String();
			u = (String)bl.get(i);
			for(int j=0;j<al.size();j++){
				String v = new String();
				v = (String)al.get(j);
//				v = v.replace("[","");//因为 bl也是压入栈，所以格式一样，不需要去除 [] 符号
//				v = v.replace("]","");
				if(u.equals(v)){
					al.remove(j); 
				}
			}
		}
	}
	public static ArrayList getPathList() throws DocumentException, IOException{
		testDom4();
		reduceCondition();
		return al;
	}
/*	public static void main(String[] args) throws DocumentException, IOException {
		testDom4();
		//System.out.println(al);
		reduceCondition();
		System.out.println(al);
		String[] varName = {"a","b","c","e","f","g","type"};
		GenerateTestCaseForBpel.parseStringArrayForList(al,varName);
	}*/

}
