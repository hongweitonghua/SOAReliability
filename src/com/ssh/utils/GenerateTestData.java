package com.ssh.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class GenerateTestData {
	public static int Button = Integer.MIN_VALUE, Top = Integer.MAX_VALUE; 
	
	/**
	 * ���ɳ�ʼ������������List
	 * 
	 * @param testDataList �������в����Ĳ�������List
	 * @return ���в����������ݵѿ�������õ��Ĳ�����������List
	 */
	@SuppressWarnings("rawtypes")
	public static ArrayList generateOriginalTestCase(ArrayList<ArrayList> testDataList) {	
		ArrayList groupList = new ArrayList();
		ArrayList resultList = new ArrayList();
		resultList.add("");
		Iterator itr = testDataList.iterator();
		while (itr.hasNext()) {
			groupList = (ArrayList) itr.next();
			resultList = CircleTraverse(groupList, resultList);
		}
		return resultList;
	}

	/**
	 * ����ÿ��������Ĳ����������������generateOriginalTestCase()���ʹ��
	 * 
	 * @param list1
	 *            ��ǰ�����������ݼ�List
	 * @param list2
	 *            ��һ�������������ݱ�����õ��Ľ����List
	 * @return ������ǰ�����������ݼ�List1��õ��Ĳ������������
	 */
	private static ArrayList CircleTraverse(ArrayList list1, ArrayList list2) {
		ArrayList resultList = new ArrayList();
		for (Object obj2 : list2)
			for (Object obj1 : list1) {
				if(obj1=="")
					obj1 = "\"\"";		//����ǿգ�����ʾ������
				if (obj2 != "") {
					resultList.add("" + obj2 + "," + obj1);
				} else {
					resultList.add("" + obj1);
				}
			}
		return resultList;
	}
	/**
	 * ��ΧԼ�������ɲ�������
	 * @param min ��Χ����Сֵ
	 * @param max ��Χ�����ֵ
	 */
	public static ArrayList generateTestDataByRange(int min, int max) {
		ArrayList list = new ArrayList();
		HashSet<Integer> set = new HashSet<>();
		if (max < min)
			return null;
		int integerMin = Integer.MIN_VALUE;
		int integerMax = Integer.MAX_VALUE;
		/*set.add(min - 1);
		set.add(min);
		set.add(min + 1);
		set.add(max - 1);
		set.add(max);
		set.add(max + 1);
		set.add(0);
		set.add(-1);
		set.add(getRandomNum(integerMin, min));*/
		set.add(getRandomNum(min, max));
		/*set.add(getRandomNum(min, integerMax));*/
		list.addAll(set);
		
		/*set.add(getRandomNum(min, max));
		
		list.addAll(set);*/
		return list;
	}
	/**
	 * ���һ��������Χ���������
	 * 
	 * @param minNum
	 *            �����ķ�Χ��Сֵ
	 * @param maxNum
	 *            �����ķ�Χ���ֵ
	 * @return �ڴ˷�Χ�ڵ��������
	 */
	public static int getRandomNum(int minNum, int maxNum) {
		Random random = new Random();
		if(maxNum - minNum + 1 ==0 ){
			if(minNum==0)
				return random.nextInt();
			else
				return 0-random.nextInt();
		}		
		return (Math.abs(random.nextInt()) % (maxNum - minNum + 1)) + minNum;
	}
}
