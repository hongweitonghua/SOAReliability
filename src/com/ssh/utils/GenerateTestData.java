package com.ssh.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class GenerateTestData {
	public static int Button = Integer.MIN_VALUE, Top = Integer.MAX_VALUE; 
	
	/**
	 * 生成初始测试用例集合List
	 * 
	 * @param testDataList 含有所有参数的测试数据List
	 * @return 所有参数测试数据笛卡尔积后得到的测试用例集合List
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
	 * 生成每个参数后的测试用例结果集，与generateOriginalTestCase()配合使用
	 * 
	 * @param list1
	 *            当前参数测试数据集List
	 * @param list2
	 *            上一个参数测试数据遍历后得到的结果集List
	 * @return 遍历当前参数测试数据集List1后得到的测试用例结果集
	 */
	private static ArrayList CircleTraverse(ArrayList list1, ArrayList list2) {
		ArrayList resultList = new ArrayList();
		for (Object obj2 : list2)
			for (Object obj1 : list1) {
				if(obj1=="")
					obj1 = "\"\"";		//如果是空，则显示空引号
				if (obj2 != "") {
					resultList.add("" + obj2 + "," + obj1);
				} else {
					resultList.add("" + obj1);
				}
			}
		return resultList;
	}
	/**
	 * 范围约束，生成测试数据
	 * @param min 范围的最小值
	 * @param max 范围的最大值
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
	 * 获得一个给定范围的随机整数
	 * 
	 * @param minNum
	 *            给定的范围最小值
	 * @param maxNum
	 *            给定的范围最大值
	 * @return 在此范围内的随机整数
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
