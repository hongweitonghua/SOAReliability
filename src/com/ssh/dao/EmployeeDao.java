package com.ssh.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ssh.entities.Employee;

public class EmployeeDao {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession()
	{
		//return sessionFactory.getCurrentSession();
		return sessionFactory.openSession();
	}
	
	public List<Employee> getAll()
	{
		String sql="FROM Employee e LEFT OUTER JOIN FETCH e.dept";
		return getSession().createQuery(sql).list();
	}
	
	public  void deleteEmployee(Integer id)
	{
		String sql="delete from Employee e where e.id = ?";
		Query query=getSession().createQuery(sql).setInteger(0, id);
	    query.executeUpdate();
	}
}
