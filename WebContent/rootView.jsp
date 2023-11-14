<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Root page</title>
</head>
<body>

<div align = "center">
	
	<form action = "initialize">
		<input type = "submit" value = "Initialize the Database"/>
	</form>
	<a href="login.jsp"target ="_self" > logout</a><br><br> 

<h1>All Database Tables</h1>

    <div align="center">
        <table border="1" cellpadding="6">
            <caption><h2>List of Users</h2></caption>
            <tr>
                <th>Email</th>
                <th>Password</th>
                <th>Role</th>
            </tr>
            <c:forEach var="users" items="${listUser}">
                <tr style="text-align:center">
                    <td><c:out value="${users.email}" /></td>
                    <td><c:out value="${users.password}" /></td>
                    <td><c:out value="${users.role}" /></td>

            </c:forEach>
        </table>
	</div>
	
	<div align="center">
        <table border="1" cellpadding="6">
            <caption><h2>List of Quotes</h2></caption>
            <tr>
                <th>User Email</th>
                <th>Quote ID</th>
                <th>Quote Date</th>
                <th>Note</th>
                <th>Tree Size</th>
                <th>Tree Height</th>
                <th>Status</th>
            </tr>
            <c:forEach var="quotes" items="${listQuote}">
                <tr style="text-align:center">
                    <td><c:out value="${quotes.getUserEmail()}"/></td>
                    <td><c:out value="${quotes.getQuoteid()}"/></td>
                    <td><c:out value="${quotes.getQuoteDate()}"/></td>
                    <td><c:out value="${quotes.getNote()}"/></td>
                    <td><c:out value="${quotes.getTreeSize()}"/></td>
                    <td><c:out value="${quotes.getTreeHeight()}"/></td>
                    <td><c:out value="${quotes.getStatus()}"/></td>
                    
            </c:forEach>
            
            
        </table>
	</div>
	
</div>

</body>
</html>