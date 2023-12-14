<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Client Page</title>
</head>
<body>
    <center>
        <h1>Welcome! You Have Been Successfully Logged In</h1>
        <a href="login.jsp" target="_self">Logout</a><br><br>
        <p>Client page, more stuff coming soon</p>

        <div align="center">
            <table border="1" cellpadding="6">
                <caption><h2>List of My Quotes</h2></caption>
                <tr>
                	<th>Quote ID</th>
                    <th>User Email</th>
                    <th>Quote Date</th>
                    <th>Note</th>
                    <th>Tree Size</th>
                    <th>Tree Height</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                <c:forEach var="quotes" items="${currentQuote}">
                    <form action="updateQuote" method="post">
                        <tr style="text-align:center">
                        	<td><input type="text" name="quoteid" value="<c:out value="${quotes.getQuoteid()}"/>"></td>
                            <td><c:out value="${quotes.getUserEmail()}"/></td>
                            <td><input type="date" name="quoteDate" value="<c:out value="${quotes.getQuoteDate()}"/>"></td>
                            <td><textarea name="note" rows="6" cols="42"><c:out value="${quotes.getNote()}"/></textarea></td>
                            <td><input type="text" name="treeSize" value="<c:out value="${quotes.getTreeSize()}"/>"></td>
                            <td><input type="text" name="treeHeight" value="<c:out value="${quotes.getTreeHeight()}"/>"></td>
                            <td><c:out value="${quotes.getStatus()}"/></td>
                            <td><input type="submit" value="Update"></td>
                            <input type="hidden" name="userEmail" value="<c:out value="${quotes.getUserEmail()}"/>">
                        </tr>
                    </form>
                </c:forEach>
                
                <form action = "addQuote">
					<input type = "submit" value = "Add New Quote"/>
				</form>
            </table>
            
            <table border="1" cellpadding="6">
            	<caption><h2>List of My Bills</h2></caption>
            	<tr>
            		<th>Stat ID</th>
            		<th>Email</th>
                	<th>Bill Amount</th>
                    <th>Total Paid</th>
                    <th>Bill Date</th>
                    <th>Paid Date</th>
                    <th>Bill Status</th>
                    <th>Bill Action</th>
                    
                </tr>
                
                <c:forEach var="bills" items="${currentBill}">
                    <form action="updateBill" method="post">
                        <tr style="text-align:center">
                        	<td><c:out value="${bills.getStat_id()}"/></td>
                            <td><c:out value="${bills.getUser_email()}"/></td>
                            <td><c:out value="${bills.getBillAmount()}"/></td>
                            <td><c:out value="${bills.getPaidAmount()}"/></td>
                            <td><c:out value="${bills.getBillDate()}"/></td>
                            <td><c:out value="${bills.getPaidDate()}"/></td>
                            <td><c:out value="${bills.getBillStatus()}"/></td>
                            <td><input type="submit" value="Pay Bill"></td>
                        </tr>
                    </form>
                </c:forEach>
            </table>
            
            
        </div>
    </center>
</body>
</html>
