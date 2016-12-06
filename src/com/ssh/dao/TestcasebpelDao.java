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
		System.out.println("list.size()Ϊ"+list.size());
		return list;
	}
	//����һ����¼
	public void testcaseInsert(Testcasebpel t){
		Session session = getSession();
		Transaction tran = session.beginTransaction();
		session.save(t);	//ִ��
		tran.commit();  //�ύ
		session.close(); 
	}            
	//�������룬�˴��ǲ���һ��Map
	public void testcaseInterAll(Map<Testpathbpel, ArrayList> testCaseListMap){
		Session session = getSession();
		Transaction tran = getSession().beginTransaction();
		int i=0;
		boolean flag = false; //��¼Ҫ����������Ƿ񳬹�20��
		if(testCaseListMap.size()>20){
			flag = true;
		}
		for (Map.Entry<Testpathbpel, ArrayList> en: testCaseListMap.entrySet()) {
			ArrayList list = en.getValue();
			for (Object obj : list) {
				String str =(String)obj;
				//�õ�����·����Ӧ��id
				Testpathbpel tpath = en.getKey();
				//�����������
				Testcasebpel tcase = new Testcasebpel();
				tcase.setTestpathbpel(tpath);
				tcase.setTestcase("("+str+")");
				session.save(tcase);	//ִ��
				i++;
				//�ֶ���Session���Ļ�������д�����ݿ⣬���ֶ��ύ����
				if (i % 20 == 0){ //����20����ÿ20���ύ1�Σ�ע��Ҫ�ر�session�Ķ�������
			       session.flush();
			       session.clear();
			       tran.commit();
			       tran = session.beginTransaction();
				 }
			}		
		}		
		if(flag == false) //δ����20��������ύ1��
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
