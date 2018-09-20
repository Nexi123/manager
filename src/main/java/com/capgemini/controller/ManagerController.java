package com.capgemini.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.capgemini.bean.Employee;
import com.capgemini.bean.Status;
import com.capgemini.service.IEmployeeService;

@Controller
public class ManagerController {
	
	private static final Logger logger = Logger.getLogger(ManagerController.class);


	@Autowired
	IEmployeeService iEmployeeService;
	boolean booleanStatus = false;
	

	@RequestMapping(value="/login")
	
	public ModelAndView login(@RequestParam(value = "username") String uname,
			@RequestParam("password") String pass, HttpServletRequest request,
			HttpSession session) {
	
		
		//boolean valid = true;
		List<Employee> empList = iEmployeeService.getAllEmployee();
		for (Employee e : empList) {
			if (uname.equals(e.getUname()) && pass.equals(e.getPass())) {
				session.setAttribute("name", uname);
		   //	valid = false;
				session.setAttribute("mgrId", e.getEmpId());
				session.setAttribute("jobRole", e.getJob());
				session.setAttribute("empId", e.getEmpId());
				session.setAttribute("Ename", e.getEname());				
				return new ModelAndView("login");
			}
		}
		 
		return new ModelAndView("ErrorPage", "errorPage", "Error is Occured Please Do login Again");
		
	}

	@RequestMapping(value="/addEmployee")
	public ModelAndView addEmployee(
		
			@ModelAttribute("employee") Employee employee,
			@RequestParam(value = "added",required = false, defaultValue = "false") String st)
			throws IOException {
		return new ModelAndView("addEmployee", "addedStatus", st);

	}

	@RequestMapping(value="/addNewEmployee", method= RequestMethod.POST)
	public ModelAndView addNewEmployee(HttpServletResponse response,
			HttpSession session, @ModelAttribute("employee") Employee employee,
			BindingResult result, Model model
			) throws IOException {
		logger.info("model daata is-------------------------------------"+employee);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		long empId=employee.getEmpId();
		try {
			
		
			iEmployeeService.addEmployee(employee);
			
			logger.info("result");
		} catch (Exception e) {
			out.println("alert('Data Cannot be inserted');");
		}
		response.sendRedirect("listdetails");
		return  new ModelAndView("addEmployee");

	}

	/* List all employees */
	@RequestMapping(value="/listdetails")
	public ModelAndView listAllEmployees(Model model) {
		model.addAttribute("employee", new Employee());
		model.addAttribute("employeeList", this.iEmployeeService.getAllEmployee());

		return new ModelAndView("listAllEmployees");
	}

	// Get employee by ID 
	@RequestMapping(value="/getEmployeeById")
	public ModelAndView getEmployee() {
		return new ModelAndView("getEmployee");
	}

	@RequestMapping(value="/showEmployee")
	public ModelAndView ShowEmployee(Model model, Employee employee,
			@RequestParam("empId") long employeeId) {
		Employee emp = iEmployeeService.getEmployeeById(employeeId);
		System.out.println(emp);
		return new ModelAndView("getEmployeeById", "employeeData", emp);
	}

	//removing an employee 
	@RequestMapping("/remove/{id}")
	public ModelAndView  removeEmployee(@PathVariable("id") long id,Model model,
			HttpServletResponse response) throws Exception {
		model.addAttribute("employee", new Employee());
		model.addAttribute("employee", this.iEmployeeService.getEmployeeById(id));
		Employee emp=iEmployeeService.getEmployeeById(id);
		iEmployeeService.deleteEmployee(id);
	

		return new ModelAndView("deleteEmployee", "emp",emp);
		
	}
	
	@RequestMapping(value="/DeleteEmpDetails")
	public void  DeleteEmpDetails(@ModelAttribute("employee") Employee emp,
			HttpServletResponse response) throws Exception
	{
		//iEmployeeService.deleteEmployee(emp.getEmpId());
		response.sendRedirect("listdetails");
		
	}
	

	
	@RequestMapping(value="/edit/{id}")
	public ModelAndView editEmployee(@PathVariable("id") long empId,
			Model model) {
		model.addAttribute("employee", new Employee());
		model.addAttribute("employeeList", this.iEmployeeService.getAllEmployee());
		logger.info("data of edit= ........................"+empId);
		
		return new ModelAndView("editEmployee", "emp",
				iEmployeeService.getEmployeeById(empId));
	} 
	
	@RequestMapping(value="/upadteEditEmpDetails",method= RequestMethod.POST)
	public void editEmployee(@ModelAttribute("emp") Employee employee,
			HttpServletResponse response)
			throws IOException {
		logger.info("data of edit-----------------------------= "+employee);
		
		//employee.setJob(job);
		iEmployeeService.updateEmployee(employee);
		
		response.sendRedirect("listdetails");
	}
	@RequestMapping(value="/addStatus")
	public ModelAndView addStatus(
			Model model,
			HttpSession session,
			@RequestParam(value = "statusExists", required = false, defaultValue = "false") String statusExists) {
		long id = (long) session.getAttribute("empId");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.now();
		String today = dtf.format(localDate);

		String statusId = id + today;
		if (iEmployeeService.getStatusById(statusId) != null) {
			statusExists = "true";
		}
		model.addAttribute("statusExists", statusExists);
		return new ModelAndView("addStatus", "emp",
				iEmployeeService.getEmployeeById(id));
	}

	// add Status of an employee 
	@RequestMapping("/addStatus/{id}")
	public ModelAndView addStatusEmployee(@PathVariable("id") long empId,
			Model model, @ModelAttribute("employee") Employee employee,
			HttpServletResponse response) throws IOException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.now();
		String today = dtf.format(localDate);

		String statusId = empId + today;
		System.out.println(statusId);
		if (iEmployeeService.getStatusById(statusId) != null) {
			Employee emp = iEmployeeService.getEmployeeById(empId);
			
			return new ModelAndView("empViewStatus","emp", emp);
			}
		Employee emp = iEmployeeService.getEmployeeById(empId);
		Status status=new Status();
		status.setEmpId(emp.getEmpId());
		status.setEname(emp.getEname());
	return new ModelAndView("addEmpStatus", "status", status);
	}

	@RequestMapping(value="/EmployeeStatusInserted",method= RequestMethod.POST)
	public ModelAndView EmployeeStatusInserted( 
			@ModelAttribute("status") Status status, Model model,
			 HttpServletResponse response)
			throws IOException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.now();
		
		long empId = status.getEmpId();
		String today = dtf.format(localDate);
		String statusId = empId + today;

		Status st = new Status();
		st.setEmpId(empId);
		st.setEname(status.getEname());
		st.setDate(today);
		st.setStatus(status.getStatus());
		st.setStatusId(statusId);

		iEmployeeService.addStatus(st);
		model.addAttribute("emp",iEmployeeService.getEmployeeById(empId));
	Employee	emp=iEmployeeService.getEmployeeById(empId);
		return new ModelAndView("empViewStatus","emp",emp);

	}

	@RequestMapping("/statusAdded")
	public void statusAdded(@RequestParam("date") String date,
			@RequestParam("status") String statusValue,
			@ModelAttribute("employee") Employee employee,
			HttpServletResponse response) throws IOException {
		boolean statusMatched = false;
		String statusId = employee.getEmpId() + date;
		List<Status> statusList = iEmployeeService.getAllStatus();
		for (Status sl : statusList) {
			if (statusId.equals(sl.getStatusId())) {
				statusMatched = true;
				response.sendRedirect("viewStatus");
			}
		}
		if (statusMatched == false) {
			Status status = new Status();

			status.setStatusId(statusId);
			status.setEmpId(employee.getEmpId());
			status.setEname(employee.getEname());
			status.setDate(date);
			status.setStatus(statusValue);

			iEmployeeService.addStatus(status);
			response.sendRedirect("addStatus");
		}
	}

	
	// View the status 
	@RequestMapping("/viewStatus")
	public ModelAndView viewStatus(Model model) {
		model.addAttribute("employee", new Employee());
		model.addAttribute("employeeList", this.iEmployeeService.getAllEmployee());

		return new ModelAndView("viewStatus");
	}
	@RequestMapping("/editEmpStatus{id}")
	public ModelAndView editEmployeeStatus(@PathVariable("id") long empId,
			Model model, @ModelAttribute("status") Status status) {
		model.addAttribute("empId", empId);
		return new ModelAndView("editEmpStatus","status",status);
	}

	@RequestMapping("/findStatus")
	public ModelAndView findEmployeeStatus(@RequestParam("id") long empId,
			Model model, @ModelAttribute("status") Status status,
			@RequestParam("date") String date) {
		String statusId = empId + date;
		Status status1 = iEmployeeService.getStatusById(statusId);
		model.addAttribute("empId", empId);
		if (status1 == null)
			model.addAttribute("emptyStatus", "true");
		return new ModelAndView("editEmpStatus", "status1", status1);
	}

	@RequestMapping("/submitEditStatus{id}")
	public void submitEditStatus(@ModelAttribute("status") Status status,
			@RequestParam("statusValue") String statusValue,@PathVariable("id") Long empId,
			HttpServletResponse response) throws IOException {
		String statusId = status.getEmpId() + status.getDate();
		Status st = new Status();
		st.setStatusId(statusId);
		st.setDate(status.getDate());
		st.setEmpId(status.getEmpId());
		st.setStatus(statusValue);
		System.out.println(st);
		iEmployeeService.editStatus(st);
		response.sendRedirect("empViewStatus");
	}


	
	@RequestMapping("/getDetailsByDate")
	public ModelAndView getDetailsByDate(Model model,
			@RequestParam("startDate") String sdate,
			@RequestParam("endDate") String edate, HttpSession session,
			HttpServletResponse response ) throws IOException
	{
		long id =  (long) session.getAttribute("empId");
		List<Status> statusList = iEmployeeService.getDetailsByDate(sdate, edate, id);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		if (statusList.isEmpty()) {
			System.out.println("s");

		}
		return new ModelAndView("viewByDate", "statusList", statusList);

	}
	

	// view status by date 
	@RequestMapping("/viewStatusByDate{id}")
	public ModelAndView viewStatusByDate(@PathVariable("id") long empId,Model model)
	{
		model.addAttribute("statusList",this.iEmployeeService.getStatusByEmpId(empId));
		return new ModelAndView("viewStatusByDate");
	}

	// Employee activities 
	@RequestMapping("/empViewStatus")
	public ModelAndView empViewStatus(Model model, HttpSession session) {
		long id = (long) session.getAttribute("empId");

		return new ModelAndView("empViewStatus", "emp",
				iEmployeeService.getEmployeeById(id));
	}
	
	@RequestMapping("/viewByDate")
	public ModelAndView viewByDate() {
		return new ModelAndView("viewByDate");
	}

	


}
