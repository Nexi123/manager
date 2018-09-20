<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
    <head>
         <title>Add Your Status</title>
    </head>
    <body>
		  <h1>${Ename} portal</h1>
		  <a href="empViewStatus" >View Status</a>		  
		  <a href="editEmpStatus${empId}" >Edit Status</a>
		  <a href="index.jsp">Log Out</a>
		         <br>
		         <br>
		         <br>
		         <br>
		<form:form action="${contextPath}/Manager/EmployeeStatusInserted" method="post" modelAttribute="status" >
		       Employee Id:<form:input path="empId" value="${status.empId}" />
			   Employee Name:<form:input path="ename" value="${status.ename}" />
			   Status:	<form:input path="status" type="text"/>
			   <input type="submit" value="Add My Status" >
		  </form:form>
    </body>
</html>