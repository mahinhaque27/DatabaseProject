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
	
	<div align="center">
        <table border="1" cellpadding="6">
            <caption><h2>List of Big Clients</h2></caption>
            <tr>
                <th>User Email</th>
                <th>Number of Trees Cut</th>
                
            </tr>
            <c:forEach var="bigClient" items="${bigClients}">
                <tr style="text-align:center">
                    <td><c:out value="${bigClient.getUser_email()}"/></td>
                    <td><c:out value="${bigClient.getQuoteCount()}"/></td>
                    
            </c:forEach>
            
            
        </table>
	</div>
	
	<div align="center">
        <table border="1" cellpadding="6">
            <caption><h2>List of Easy Clients</h2></caption>
            <tr>
                <th>User Email</th>
                <th>Amount of Times Client Negotiated</th>
                
            </tr>
            <c:forEach var="easyClient" items="${easyClients}">
                <tr style="text-align:center">
                    <td><c:out value="${easyClient.getUser_email()}"/></td>
                    <td><c:out value="${easyClient.getUpdateQuoteCount()}"/></td>
                    
            </c:forEach>
            
            
        </table>
	</div>
	
	<div align="center">
        <table border="1" cellpadding="6">
            <caption><h2>List of One Tree Clients</h2></caption>
            <tr>
                <th>User Email</th>
                <th>Number of Trees Cut</th>
                
            </tr>
            <c:forEach var="oneClient" items="${oneTreeClients}">
                <tr style="text-align:center">
                    <td><c:out value="${oneClient.getUser_email()}"/></td>
                    <td><c:out value="${oneClient.getQuoteCount()}"/></td>
                    
            </c:forEach>
            
            
        </table>
	</div>
	
	<div align="center">
        <table border="1" cellpadding="6">
            <caption><h2>List of One Prospective Clients</h2></caption>
            <tr>
                <th>User Email</th>
                <th>Current Quote Status</th>
                
            </tr>
            <c:forEach var="pros" items="${prosClients}">
                <tr style="text-align:center">
                    <td><c:out value="${pros.getUserEmail()}"/></td>
                    <td><c:out value="${pros.getStatus()}"/></td>
                    
            </c:forEach>
            
            
        </table>
	</div>
	
	<div align="center">
        <table border="1" cellpadding="6">
            <caption><h2>List of the Clients with the Tallest Tree</h2></caption>
            <tr>
                <th>User Email</th>
                <th>Tree Height</th>
                
            </tr>
            <c:forEach var="highTree" items="${highTreeClients}">
                <tr style="text-align:center">
                    <td><c:out value="${highTree.getUserEmail()}"/></td>
                    <td><c:out value="${highTree.getTreeHeight()}"/></td>
                    
            </c:forEach>
            
            
        </table>
	</div>
	
	<div align="center">
        <table border="1" cellpadding="6">
            <caption><h2>List of the Clients with a Overdue Bill</h2></caption>
            <tr>
                <th>User Email</th>
                <th>Bill Date</th>
                <th>Paid Date</th>
                
            </tr>
            <c:forEach var="overClient" items="${overdueClients}">
                <tr style="text-align:center">
                    <td><c:out value="${overClient.getUser_email()}"/></td>
                    <td><c:out value="${overClient.getBillDate()}"/></td>
                    <td><c:out value="${overClient.getPaidDate()}"/></td>
            </c:forEach>
            
            
        </table>
	</div>
	
	<div align="center">
        <table border="1" cellpadding="6">
            <caption><h2>List of Bad Clients (Never Working for them Again!!!)</h2></caption>
            <tr>
                <th>User Email</th>
                <th>Bill Date</th>
                <th>Paid Date</th>
                
            </tr>
            <c:forEach var="badClient" items="${badClients}">
                <tr style="text-align:center">
                    <td><c:out value="${badClient.getUser_email()}"/></td>
                    <td><c:out value="${badClient.getBillDate()}"/></td>
                    <td><c:out value="${badClient.getPaidDate()}"/></td>
            </c:forEach>
            
            
        </table>
	</div>
	
	<div align="center">
        <table border="1" cellpadding="6">
            <caption><h2>List of Good Clients</h2></caption>
            <tr>
                <th>User Email</th>
                <th>Bill Date</th>
                <th>Paid Date</th>
                
            </tr>
            <c:forEach var="goodClient" items="${goodClients}">
                <tr style="text-align:center">
                    <td><c:out value="${goodClient.getUser_email()}"/></td>
                    <td><c:out value="${goodClient.getBillDate()}"/></td>
                    <td><c:out value="${goodClient.getPaidDate()}"/></td>
            </c:forEach>
            
            
        </table>
	</div>
	
	<div align="center">
        <table border="1" cellpadding="6">
            <caption><h2>All Statistics</h2></caption>
            <tr>
                <th>Stat ID</th>
                <th>User Email</th>
                <th># of Quotes</th>
                <th># of Times User Updated their Quote</th>
                <th>Bill Amount</th>
                <th>Bill Status</th>
                <th>Bill Date</th>
                <th>Paid Date</th>
                <th>Paid Amount</th>
                
            </tr>
            <c:forEach var="stats" items="${allStats}">
                <tr style="text-align:center">
                    <td><c:out value="${stats.getStat_id()}"/></td>
                    <td><c:out value="${stats.getUser_email()}"/></td>
                    <td><c:out value="${stats.getQuoteCount()}"/></td>
                    <td><c:out value="${stats.getUpdateQuoteCount()}"/></td>
                    <td><c:out value="${stats.getBillAmount()}"/></td>
                    <td><c:out value="${stats.getBillStatus()}"/></td>
                    <td><c:out value="${stats.getBillDate()}"/></td>
                    <td><c:out value="${stats.getPaidDate()}"/></td>
                    <td><c:out value="${stats.getPaidAmount()}"/></td>
            </c:forEach>
            
            
        </table>
	</div>
	
</div>

</body>
</html>