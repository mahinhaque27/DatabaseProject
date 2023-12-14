import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;


public class ControlServlet extends HttpServlet {
	    private static final long serialVersionUID = 1L;
	    private userDAO userDAO = new userDAO();
	    private String currentUser;
	    private HttpSession session=null;
	    
	    public ControlServlet()
	    {
	    	
	    }
	    
	    public void init()
	    {
	    	userDAO = new userDAO();
	    	currentUser= "";
	    }
	    
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doGet(request, response);
	    }
	    
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String action = request.getServletPath();
	        System.out.println(action);
	    
	    try {
        	switch(action) {  
        	case "/login":
        		login(request,response);
        		break;
        	case "/register":
        		register(request, response);
        		break;
        	case "/initialize":
        		userDAO.init();
        		System.out.println("Database successfully initialized!");
        		rootPage(request,response,"");
        		break;
        	case "/root":
        		rootPage(request,response, "");
        		break;
        	case "/logout":
        		logout(request,response);
        		break;
        	 case "/list": 
                 System.out.println("The action is: list");
                 listUser(request, response);           	
                 break;
        	 case "/updateQuote":
        		updateQuote(request, response);
        	 	break;
        	 case "/updateQuoteFromAdmin":
        		 updateQuoteFromAdmin(request, response);
        		 break;
        	 case "/addQuote":
        		 addQuote(request,response);
        		 break;
        	 case "/updateBill":
        		 payBill(request,response);
        		 break;
	    	}
	    }
	    catch(Exception ex) {
        	System.out.println(ex.getMessage());
	    	}
	    }
        	
	    private void listUser(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	        System.out.println("listUser started: 00000000000000000000000000000000000");

	     
	        List<user> listUser = userDAO.listAllUsers();
	        request.setAttribute("listUser", listUser);       
	        RequestDispatcher dispatcher = request.getRequestDispatcher("UserList.jsp");       
	        dispatcher.forward(request, response);
	     
	        System.out.println("listPeople finished: 111111111111111111111111111111111111");
	    }
	    	        
	    private void rootPage(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException{
	    	System.out.println("root view");
			request.setAttribute("listUser", userDAO.listAllUsers());
			request.setAttribute("listQuote", userDAO.listAllQuotes());
			request.setAttribute("bigClients", userDAO.bigClients());
			request.setAttribute("easyClients", userDAO.easyClients());
			request.setAttribute("oneTreeClients", userDAO.oneTree());
			request.setAttribute("prosClients", userDAO.prospectiveClient());
			request.setAttribute("highTreeClients", userDAO.highestTreeClient());
			request.setAttribute("overdueClients", userDAO.overdueBill());
			request.setAttribute("badClients", userDAO.overdueBill());
			request.setAttribute("goodClients", userDAO.goodClients());
			request.setAttribute("allStats", userDAO.allStats());
	    	request.getRequestDispatcher("rootView.jsp").forward(request, response);
	    	
	    }
	    
	    
	    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	 String email = request.getParameter("email");
	    	 String password = request.getParameter("password");
	    	 String role = userDAO.getRole(email);
	    	 
	    	 if (email.equals("root") && password.equals("pass1234")) {
				 System.out.println("Login Successful! Redirecting to root");
				 session = request.getSession();
				 session.setAttribute("username", email);
				 rootPage(request, response, "");
	    	 }
	    	 else if(userDAO.isValid(email, password)) 
	    	 {
			 	 if(role.equals("client")) {
			 		 currentUser = email;
			 		 request.setAttribute("currentQuote", userDAO.currentUserQuote(email));
			 		 request.setAttribute("currentBill", userDAO.statInfo(email));
					 System.out.println("Login Successful! Redirecting");
					 request.getRequestDispatcher("activitypage.jsp").forward(request, response);
			 	 }
			 	 else if(role.equals("admin")) {
			 		 currentUser = email;
			 		 request.setAttribute("currentQuote", userDAO.allQuotes());
					 System.out.println("Login Successful! Redirecting");
					 request.getRequestDispatcher("admin.jsp").forward(request, response);
			 	 }
			 	 
			 			 			 			 
	    	 }
	    	 else {
	    		 request.setAttribute("loginStr","Login Failed: Please check your credentials.");
	    		 request.getRequestDispatcher("login.jsp").forward(request, response);
	    	 }
	    }
	           
	    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	String email = request.getParameter("email");
	    	String confirm = request.getParameter("confirmation");
	   	 	String password = request.getParameter("password");
	   	    String role = request.getParameter("role");
	   	 	
	   	 	if (password.equals(confirm)) {
	   	 		if (!userDAO.checkEmail(email)) {
		   	 		System.out.println("Registration Successful! Added to database");
		            user users = new user(email, password, role);
		            // If client register and a quote, if admin only register
		            if(role.equals("client")) {
		            	userDAO.insert(users);
			   	 		userDAO.insertQuote(users);
			   	 		userDAO.insertStat(email);
		            }
		            else {
		            	userDAO.insert(users);
		            }
		   	 		
		   	 		response.sendRedirect("login.jsp");
	   	 		}
		   	 	else {
		   	 		System.out.println("Username taken, please enter new username");
		    		 request.setAttribute("errorOne","Registration failed: Username taken, please enter a new username.");
		    		 request.getRequestDispatcher("register.jsp").forward(request, response);
		   	 	}
	   	 	}
	   	 	else {
	   	 		System.out.println("Password and Password Confirmation do not match");
	   		 request.setAttribute("errorTwo","Registration failed: Password and Password Confirmation do not match.");
	   		 request.getRequestDispatcher("register.jsp").forward(request, response);
	   	 	}
	    }    
	    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    	currentUser = "";
        		response.sendRedirect("login.jsp");
        }
	
	    private void updateQuote(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
	    		String user_email = currentUser;
	    		String quote_date = request.getParameter("quoteDate");
	    		String treesize = request.getParameter("treeSize");
	    		String treeHeight = request.getParameter("treeHeight");
	    		String note = request.getParameter("note");
	    		String quote_id = request.getParameter("quoteid");
	    		
	    		quote quotes = new quote(quote_id, note, user_email, treesize, treeHeight, quote_date);
	    		
	    		try {
					userDAO.updateQuote(quotes);
					userDAO.incrementUpdateQuoteCount(user_email);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		request.setAttribute("currentQuote", userDAO.currentUserQuote(user_email));
	    		request.setAttribute("currentBill", userDAO.statInfo(user_email));
	    		request.getRequestDispatcher("activitypage.jsp").forward(request, response);
        }
	    
	    private void updateQuoteFromAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
    		String user_email = request.getParameter("userEmail");
    		String quote_date = request.getParameter("quoteDate");
    		String treesize = request.getParameter("treeSize");
    		String treeHeight = request.getParameter("treeHeight");
    		String note = request.getParameter("note");
    		
    		String quote_id = request.getParameter("quoteId");
    		String status = request.getParameter("status");
    		
    		quote quotes = new quote(note, user_email, treesize, treeHeight, status, quote_id, quote_date);
    		
    		try {
				userDAO.updateQuoteFromAdmin(quotes);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		request.setAttribute("currentQuote", userDAO.allQuotes());
    		request.getRequestDispatcher("admin.jsp").forward(request, response);
	    }
	    
	    private void addQuote(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
    		String user_email = currentUser;
    		
    		
    		user users = new user(user_email);
    		
    		try {
				userDAO.insertQuote(users);
				userDAO.incrementQuoteCount(user_email);
				userDAO.incrementBillAmount(user_email);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		request.setAttribute("currentQuote", userDAO.currentUserQuote(user_email));
    		request.setAttribute("currentBill", userDAO.statInfo(user_email));
    		request.getRequestDispatcher("activitypage.jsp").forward(request, response);
	    }
	    
	    private void payBill(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
    		String user_email = currentUser;
    		
    		try {
				userDAO.payBill(user_email);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		request.setAttribute("currentBill", userDAO.statInfo(user_email));
    		request.setAttribute("currentQuote", userDAO.currentUserQuote(user_email));
    		request.getRequestDispatcher("activitypage.jsp").forward(request, response);
	    }

	     
        
	    
	    
	    
	    
	    
}
	        
	        
	    
	        
	        
	        
	    


