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
	static Stack IeStack = new Stack();//��������洢��Ҫ��ɾ��������
	static Stack Combo = new Stack(); //���������������
	static ArrayList al = new ArrayList();//�洢Լ������
	static boolean flag = false; //�ж��Ƿ���������
	static ArrayList bl = new ArrayList();//����洢�����ӽṹ��·��Լ��������ͨ���Ա� ������������ɾ��
	public GenerateTestPath(){
		al.clear();
	}
	public static void testDom4() throws DocumentException, IOException{ 		
		String t=Thread.currentThread().getContextClassLoader().getResource("").getPath();
		int num=t.indexOf(".metadata");
		String path=t.substring(1,num).replace('/', '\\')+"SSH-Test\\WebContent\\file\\CaculatorProcess.bpel";
		
		SAXReader saxR = new SAXReader(); 
	     Document doc = saxR.read(path);
	     Element root = doc.getRootElement();  //�õ���Ԫ��
	     Iterator elemi = root.elementIterator();//�����ڵ�
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
			if(eleme.getName().equals("if")){//����if��ֱ�Ӽ�¼������
				haveStructure(eleme);
					ieCondition(eleme);
				iCondition(eleme);
			}
			if(eleme.getName().equals("elseif")){//�����ж�elseif�Ƿ����ӽṹ������elseif��ֱ��ɾ������������if�����Լ�if֮��������
				//Ҫ�ж��Ƿ����ӽṹ����û���ӽṹ����ֱ�ӵ���if��һ����������push
				//�����ж��Ƿ����ӽṹ��ֱ��ɾ��if����֮ǰ������
				haveStructure(eleme);
				deleteCondition(eleme);//���ӽṹ�� ��ɾ��
				eCondition(eleme);
			}
			iterator(eleme);
		}
	}
	private static void haveStructure(Element elem){//�ж��Ƿ����ӽṹ
		for( Iterator i = elem.elementIterator(); i.hasNext();){
			Element eleme=(Element)i.next();
			//System.out.println(eleme.getName());
			if(eleme.getName().equals("if")){//ֻ��Ҫ��if��һ�����ӽṹ.������ӽṹ����ѵ�ǰif��������¼
				//System.out.println("11"); //��ȷ
				flag = true;
			}
			haveStructure(eleme);
		}
	}
	private static void iCondition(Element elem){//ifֻ��Ҫ�õ�����������оͿ�����
		for(Iterator i = elem.elementIterator();i.hasNext();){
			Element eleme = (Element)i.next();
			if(eleme.getName().equals("condition")){
				String a[] = eleme.getText().split(":");
				Combo.push(a[1]);
				if(flag==true){//����ֻ��ɾ��������Ϊ1�ģ���Ҫ������������
					bl.add(Combo.toString());
					flag=false;
				}
				//al.add(a[1]);  ����ֱ��add������ֻ�ǵ�����һ���������޷��������������
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
				if(flag==true){//����ֻ��ɾ��������Ϊ1�ģ���Ҫ������������
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
				IeStack.push(a[1]); //��֮����Ҫɾ��������ѹջ
				//System.out.println(a[1]);
			}
		}
	}
	private static void deleteCondition(Element elem){
		int b = Combo.search(IeStack.peek()); //����ֱ����i<Combo.search(IeStack.peek())�� ��Ϊÿ��һ�Σ���ֵ�����
		for(int i=0;i<b;i++){//��������·��������
			Combo.pop();
		}
		IeStack.pop(); //������ɾ��������
	}
	private static void reduceCondition(){ //��·���ϵ�������ɾ�����õ�·���ϵ���������
		for(int i=0;i<bl.size();i++){
			String u = new String();
			u = (String)bl.get(i);
			for(int j=0;j<al.size();j++){
				String v = new String();
				v = (String)al.get(j);
//				v = v.replace("[","");//��Ϊ blҲ��ѹ��ջ�����Ը�ʽһ��������Ҫȥ�� [] ����
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
