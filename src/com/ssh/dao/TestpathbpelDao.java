package com.ssh.dao;

import java.util.List;



import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ssh.entities.Testpathbpel;

public class TestpathbpelDao {
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	private Session getSession(){
		return sessionFactory.openSession();
	}
	
	public List<Testpathbpel> getAll(){
		String hql = "from Testpathbpel";
		return getSession().createQuery(hql).list();
	}
	//根据testpath得到对应的id号
	public Testpathbpel getObjByPath(Testpathbpel tMsg){
		String hql = "from Testpathbpel t where t.testpath = ?";
		Session session =getSession();
		Query query=session.createQuery(hql).setString(0, tMsg.getTestpath());
		List<Testpathbpel> list= query.list();
		Integer id = list.get(0).getId();
		tMsg.setId(id);
		System.out.println("============id="+id);
		session.close();
		return tMsg;
	}
	
	public void deleteAll(){
		/*Transaction tran = getSession().beginTransaction();*/
		String hql = "delete Testpathbpel";
		Session session =getSession();
		Query query=session.createQuery(hql);
	    query.executeUpdate();
	    session.close();
	}
	
	public void testPathInsert(Testpathbpel t){
		Transaction tran = getSession().beginTransaction();
		Session session =getSession();
		session.save(t);	//执行
		tran.commit();  //提交
		session.close();
	}
}
