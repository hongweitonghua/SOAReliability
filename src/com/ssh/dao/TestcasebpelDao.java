package com.ssh.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.hibernate.Transaction;

import com.ssh.entities.Testcasebpel;
import com.ssh.entities.Testpathbpel;

public class TestcasebpelDao {
private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	private Session getSession(){
		return sessionFactory.openSession();
	}
	
	public List<Testcasebpel> getAll()
	{
		/*String sql="FROM Employee e LEFT OUTER JOIN FETCH e.dept";*/
		String hql="FROM Testcasebpel t LEFT OUTER JOIN FETCH t.testpathbpel";
		Session session = getSession();
		List<Testcasebpel> list = session.createQuery(hql).list();
		session.close();
		return list;
	}
	public List<Testcasebpel> getSelectAll(Testpathbpel testpathbpel)
	{
		Session session = getSession();
		String hql="FROM Testcasebpel t LEFT OUTER JOIN fetch t.testpathbpel where t.testpathbpel.id = ?";
		System.out.println(testpathbpel.getId());
		List<Testcasebpel> list = session.createQuery(hql).setInteger(0, testpathbpel.getId()).list();
		session.close();
		System.out.println("list.size()为"+list.size());
		return list;
	}
	//插入一条记录
	public void testcaseInsert(Testcasebpel t){
		Session session = getSession();
		Transaction tran = session.beginTransaction();
		session.save(t);	//执行
		tran.commit();  //提交
		session.close(); 
	}            
	//批量插入，此处是插入一个Map
	public void testcaseInterAll(Map<Testpathbpel, ArrayList> testCaseListMap){
		Session session = getSession();
		Transaction tran = getSession().beginTransaction();
		int i=0;
		boolean flag = false; //记录要插入的数据是否超过20条
		if(testCaseListMap.size()>20){
			flag = true;
		}
		for (Map.Entry<Testpathbpel, ArrayList> en: testCaseListMap.entrySet()) {
			ArrayList list = en.getValue();
			for (Object obj : list) {
				String str =(String)obj;
				//得到测试路径对应的id
				Testpathbpel tpath = en.getKey();
				//插入测试用例
				Testcasebpel tcase = new Testcasebpel();
				tcase.setTestpathbpel(tpath);
				tcase.setTestcase("("+str+")");
				session.save(tcase);	//执行
				i++;
				//手动将Session处的缓存数据写入数据库，并手动提交事务
				if (i % 20 == 0){ //超过20条，每20条提交1次，注意要关闭session的二级缓存
			       session.flush();
			       session.clear();
			       tran.commit();
			       tran = session.beginTransaction();
				 }
			}		
		}		
		if(flag == false) //未超过20条，最后提交1次
			tran.commit();
		session.close();
	}
		
	public void deleteAll(){
		String hql = "delete Testcasebpel";
		Session session = getSession();
		Query query=session.createQuery(hql);
	    query.executeUpdate();
	    session.close();
	}
	public void deleteByIds(List<?> ids) {
        String hql = "delete from Testcasebpel where id in (:ids)";
        getSession().createQuery(hql).setParameterList("ids", ids).executeUpdate();
    }
	
	
}
