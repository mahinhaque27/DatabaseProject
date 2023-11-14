<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Page</title>
</head>

<center><h1>Welcome! You Have Been Successfully Logged In</h1> </center>

 
	<body>
	 <center>
		 <a href="login.jsp"target ="_self" > logout</a><br><br> 
		 <p>Admin page, more stuff coming soon</p>
		 
		 <div align="center">
            <table border="1" cellpadding="6">
                <caption><h2>List of My Quotes</h2></caption>
                <tr>
                    <th>User Email</th>
                    <th>Quote ID</th>
                    <th>Quote Date</th>
                    <th>Note</th>
                    <th>Tree Size</th>
                    <th>Tree Height</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                <c:forEach var="quotes" items="${currentQuote}">
                    <form action="updateQuoteFromAdmin" method="post">
                        <tr style="text-align:center">
                            <td>
							    <input type="text" name="userEmail" value="${quotes.getUserEmail()}"/>
							</td>
                            <td><input type="text" name="quoteId" value="<c:out value="${quotes.getQuoteid()}"/>"></td>
                            <td><input type="date" name="quoteDate" value="<c:out value="${quotes.getQuoteDate()}"/>"></td>
                            <td><textarea name="note" rows="6" cols="42"><c:out value="${quotes.getNote()}"/></textarea></td>
                            <td><input type="text" name="treeSize" value="<c:out value="${quotes.getTreeSize()}"/>"></td>
                            <td><input type="text" name="treeHeight" value="<c:out value="${quotes.getTreeHeight()}"/>"></td>
                            <td>
				                <select name="status">
				                    <option value="N/A" <c:if test="${quotes.getStatus() eq 'N/A'}">selected</c:if>>N/A</option>
				                    <option value="Accepted" <c:if test="${quotes.getStatus() eq 'Accepted'}">selected</c:if>>Accepted</option>
				                    <option value="Rejected" <c:if test="${quotes.getStatus() eq 'Rejected'}">selected</c:if>>Rejected</option>
				                </select>
				            </td>
                            <td><input type="submit" value="Update"></td>
                            <input type="hidden" name="userEmail" value="<c:out value="${quotes.getUserEmail()}"/>">
                        </tr>
                    </form>
                </c:forEach>
            </table>
        </div>
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 </center>
	</body>
</html>