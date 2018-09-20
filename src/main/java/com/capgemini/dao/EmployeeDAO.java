package com.capgemini.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.capgemini.bean.Employee;
import com.capgemini.bean.Status;

@Repository("employeeDAO")
public class EmployeeDAO implements IEmployeeDAO{
	private static final Logger logger = Logger.getLogger(EmployeeDAO.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
    
	public void addEmployee(Employee emp) {
		
		try {
			
		logger.info("employee object............-----------------"+emp);
		entityManager.clear();
		entityManager.persist(emp);
		
		logger.info("method end");
		System.out.println("Entity saved.");
		} catch (Exception e) {
			System.out.println("Data Cannot be inserted"+e);
		}
		

		
		
	}

	public List<Employee> getAllEmp() {
		String str = "SELECT emp FROM Employee emp";
		TypedQuery<Employee> query = entityManager.createQuery(str,
				Employee.class);
		return query.getResultList();
	}

	public Employee getEmployeeById(long employeeId) {
		return entityManager.find(Employee.class, employeeId);

	}

	public void deleteEmployee(long empId) {
		logger.info("hi at Dao"+empId);
		Query query = entityManager
				.createNativeQuery("DELETE FROM Employee e WHERE e.empId =  " + empId);
		query.executeUpdate();
		logger.info("hi at end  Dao"+empId);
	}

	public void updateEmployee(Employee emp) {
		logger.info("hi at Dao"+emp);
	//return 	

		try {
			
		logger.info("employee object............-----------------"+emp);
		
		Employee ee=entityManager.merge(emp);
		//entityManager.persist(ee);
		
		entityManager.close();
		
		logger.info("method end"+ee);
		System.out.println("Entity saved.");
		} 
		catch (Exception e) {
			System.out.println("Data Cannot be inserted"+e);
		}
		
	}

	public void addStatus(Status status) {

		entityManager.persist(status);
	}

	public List<Status> getAllStatus() {
		String str = "SELECT st FROM Status st";
		TypedQuery<Status> query = entityManager.createQuery(str, Status.class);
		return query.getResultList();
	}
	public Status getStatusById(String statusId) {
		return entityManager.find(Status.class, statusId);

	}
	public Status editStatus(Status status)
	{
		return entityManager.merge(status);
	}
	public List<Status> getDetailsByDate(String sDate,String eDate,long empId)
	{
		String str= "SELECT st FROM Status st WHERE st.date BETWEEN '"+sDate+"' AND '"+eDate+"' "
				+ "AND st.empId="+ empId;
		TypedQuery<Status> query = entityManager.createQuery(str,
				Status.class);
		return query.getResultList();
		
	}

	
	public  List<Status>  getStatusByEmpId(long empId) {
		String str = "SELECT st FROM Status st WHERE st.empId= "+empId;
		TypedQuery<Status> query = entityManager.createQuery(str, Status.class);
		
		return query.getResultList();
		 
	}

}
