package com.capgemini.service;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.bean.Employee;
import com.capgemini.bean.Status;
import com.capgemini.controller.ManagerController;
import com.capgemini.dao.IEmployeeDAO;

@Service("employeeService")
public class EmployeeService implements IEmployeeService {
	
	private static final Logger logger = Logger.getLogger(EmployeeService.class);
    @Autowired
	private IEmployeeDAO iEmployeeDAO;
    @Transactional(readOnly = false)
	public void addEmployee(Employee emp) {
		iEmployeeDAO.addEmployee(emp);
	}
  
	public List<Employee> getAllEmployee() {
		return iEmployeeDAO.getAllEmp();
	}
  
	public Employee getEmployeeById(long empId) {
		return iEmployeeDAO.getEmployeeById(empId);

	}
	 @Transactional(readOnly = false)
	public void deleteEmployee(long empId) {
		iEmployeeDAO.deleteEmployee(empId);
	}
    @Transactional(readOnly = false)
	public  void updateEmployee(Employee emp) {
		logger.info("hi how are you");
        iEmployeeDAO.updateEmployee(emp);
	}
    @Transactional(readOnly = false)
	public void addStatus(Status status) {
		iEmployeeDAO.addStatus(status);
	}
  
	public List<Status> getAllStatus() {
		return iEmployeeDAO.getAllStatus();
	}
   
	public Status getStatusById(String statusId) {
		return iEmployeeDAO.getStatusById(statusId);
	}
	 @Transactional(readOnly = false)
	public Status editStatus(Status status)
	{
		return iEmployeeDAO.editStatus(status);
	}
   
	public List<Status> getDetailsByDate(String sDate,String eDate,long empId)
	{
		return iEmployeeDAO.getDetailsByDate(sDate, eDate,empId);
	}

	@Override
	public  List<Status>  getStatusByEmpId(long empId) {
		return iEmployeeDAO.getStatusByEmpId(empId);
		
	}
}
