<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

  <title>Delete Employee</title>
</head>
<body>
	<h1>${Ename} Portal</h1>
	            <a href="${contextPath}/Manager/listdetails">View All Employees</a>
				<a href="${contextPath}/Manager/addEmployee" >Add Employee</a>
				 <a href="${contextPath}/Manager/listdetails" >Remove Employee</a>
                <a href="${contextPath}/Manager/addStatus" >Add Status</a>
                <a href="${contextPath}/Manager/viewStatus" >View Status</a>
                <a href="${contextPath}/Manager/editEmpStatus${empId}">Edit Status</a>
				<a href="${contextPath}/Manager/index.jsp">Log Out</a>
				<a href="${contextPath}/Managerlistdetails">View All Employees</a>
				<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
				<form:form action="${contextPath}/DeleteEmpDetails" method="delete"
								modelAttribute="emp" id="deleteForm"> 
					<h1> The  details have been deleted successfully</h1>
					
									<table border="1" style="background: #dcddde; margin-top: 30px;">
				<tr class="w3-dark-grey">
					<th width="80">Employee ID</th>
					<th width="120">Name</th>
					<th width="120">Email</th>
					<th width="120">UserName</th>
					<th width="120">Password</th>
					<th width="120">Manager</th>
					<th width="120">Job</th>
					<th width="120">Salary</th>
				</tr>
				<tr>
						<td>${emp.empId}</td>
						<td>${emp.ename}</td>
						<td>${emp.email}</td>
						<td>${emp.uname}</td>
						<td>${emp.pass}</td>
						<td>${emp.mgr}</td>
						<td>${emp.job}</td>
						<td>${emp.sal}</td>
						</table>
				<input type="submit" value="ok" />
				</form:form>
				
				</body>
</html>
