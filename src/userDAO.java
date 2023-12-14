import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * Servlet implementation class Connect
 */
@WebServlet("/userDAO")
public class userDAO 
{
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public userDAO(){}
	
	/** 
	 * @see HttpServlet#HttpServlet()
     */
    protected void connect_func() throws SQLException {
    	//uses default connection to the database
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testdb?allowPublicKeyRetrieval=true&useSSL=false&user=john&password=john1234");
            System.out.println(connect);
        }
    }
    
    public boolean database_login(String email, String password) throws SQLException{
    	try {
    		connect_func("root","pass1234");
    		String sql = "select * from user where email = ?";
    		preparedStatement = connect.prepareStatement(sql);
    		preparedStatement.setString(1, email);
    		ResultSet rs = preparedStatement.executeQuery();
    		return rs.next();
    	}
    	catch(SQLException e) {
    		System.out.println("failed login");
    		return false;
    	}
    }
	//connect to the database 
    public void connect_func(String username, String password) throws SQLException {
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager
  			      .getConnection("jdbc:mysql://127.0.0.1:3306/userdb?"
  			          + "useSSL=false&user=" + username + "&password=" + password);
            System.out.println(connect);
        }
    }
    
    public List<user> listAllUsers() throws SQLException {
        List<user> listUser = new ArrayList<user>();        
        String sql = "SELECT * FROM User";    
        
        connect_func();      
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            String email = resultSet.getString("email");
            String password = resultSet.getString("password"); 
            String role = resultSet.getString("role");
            
            user users = new user(email, password, role);
            listUser.add(users);
        }        
        resultSet.close();
        disconnect();        
        return listUser;
    }
    
    protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }
    
    public void insert(user users) throws SQLException {
    	connect_func("root","pass1234");         
		String sql = "insert into User(email, password, role) values (?, ?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
			preparedStatement.setString(1, users.getEmail());
			preparedStatement.setString(2, users.getPassword());
			preparedStatement.setString(3, users.getRole());
			

		preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    
    public boolean delete(String email) throws SQLException {
        String sql = "DELETE FROM User WHERE email = ?";        
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
         
        boolean rowDeleted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return rowDeleted;     
    }
     
    public boolean update(user users) throws SQLException {
        String sql = "update User set password = ? where email = ?";
        connect_func();
        
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, users.getEmail());
		preparedStatement.setString(2, users.getPassword());
		preparedStatement.setString(3, users.getRole());
		
         
        boolean rowUpdated = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return rowUpdated;     
    }
    
    public user getUser(String email) throws SQLException {
    	user user = null;
        String sql = "SELECT * FROM User WHERE email = ?";
         
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
         
        ResultSet resultSet = preparedStatement.executeQuery();
         
        if (resultSet.next()) {
            String password = resultSet.getString("password");
            user = new user(email, password);
        }
         
        resultSet.close();
        statement.close();
         
        return user;
    }
    
    public boolean checkEmail(String email) throws SQLException {
    	boolean checks = false;
    	String sql = "SELECT * FROM User WHERE email = ?";
    	connect_func();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        System.out.println(checks);	
        
        if (resultSet.next()) {
        	checks = true;
        }
        
        System.out.println(checks);
    	return checks;
    }
    
    public boolean checkPassword(String password) throws SQLException {
    	boolean checks = false;
    	String sql = "SELECT * FROM User WHERE password = ?";
    	connect_func();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        System.out.println(checks);	
        
        if (resultSet.next()) {
        	checks = true;
        }
        
        System.out.println(checks);
       	return checks;
    }
    
    
    
    public boolean isValid(String email, String password) throws SQLException
    {
    	String sql = "SELECT * FROM User";
    	connect_func();
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(sql);
    	
    	resultSet.last();
    	
    	int setSize = resultSet.getRow();
    	resultSet.beforeFirst();
    	
    	for(int i = 0; i < setSize; i++)
    	{
    		resultSet.next();
    		if(resultSet.getString("email").equals(email) && resultSet.getString("password").equals(password)) {
    			return true;
    		}		
    	}
    	return false;
    }
    
    
    public void init() throws SQLException, FileNotFoundException, IOException{
    	connect_func();
        statement =  (Statement) connect.createStatement();
        
        String[] INITIAL = {"drop database if exists testdb; ",
					        "create database testdb; ",
					        "use testdb; ",
					        "drop table if exists User; ",
					        ("CREATE TABLE if not exists User( " +
					            "email VARCHAR(50) NOT NULL, " + 
					            "password VARCHAR(20) NOT NULL, " +
					            "role VARCHAR(20) NOT NULL, " +
					            "PRIMARY KEY (email) "+"); ")
        					};
        String[] TUPLES = {
        		("insert into User(email, password, role)"+
        			"values ('susie@gmail.com', 'susie1234', 'client'),"+
			    		 	"('don@gmail.com', 'don1234', 'client'),"+
			    	 	 	"('margarita@gmail.com', 'margarita1234', 'client'),"+
			    		 	"('jo@gmail.com', 'jo1234', 'client'),"+
			    		 	"('david@gmail.com', 'david1234', 'admin'),"+
			    		 	"('wallace@gmail.com', 'wallace1234', 'client'),"+
			    		 	"('amelia@gmail.com', 'amelia1234', 'client'),"+
			    			"('sophie@gmail.com', 'sophie1234', 'client'),"+
			    			"('angelo@gmail.com', 'angelo1234', 'client'),"+
			    			"('rudy@gmail.com', 'rudy1234', 'client'),"+
			    			"('tahmid@gmail.com', 'tahmid1234', 'client'),"+
			    			"('jeannette@gmail.com', 'jeannette1234', 'client'),"+
			    			"('root', 'pass1234', 'admin');")
			    			};
        String[] QUOTE = {"use testdb; ",
		        		  "drop table if exists Quote; ",
		        		  ("CREATE TABLE if not exists Quote( " +
						   "quote_id INT PRIMARY KEY AUTO_INCREMENT, " + 
						   "quote_date DATE DEFAULT (CURRENT_DATE), " +
						   "note VARCHAR(255), " +
						   "treesize INT DEFAULT 0, " +
						   "treeheight INT DEFAULT 0, " +
						   "status VARCHAR(10) DEFAULT 'N/A', " +
						   "user_email VARCHAR(50), " +
						   "FOREIGN KEY (user_email) REFERENCES user(email) "+"); ")
        					};
        String[] QUOTE_TUPLES = {
        		("insert into Quote(user_email, note)"+
        				"values ('susie@gmail.com', 'Enter Message'),"+
		    		 	"('don@gmail.com', 'Enter Message'),"+
		    	 	 	"('margarita@gmail.com', 'Enter Message'),"+
		    		 	"('jo@gmail.com', 'Enter Message'),"+
		    		 	"('wallace@gmail.com', 'Enter Message'),"+
		    		 	"('amelia@gmail.com', 'Enter Message'),"+
		    			"('sophie@gmail.com', 'Enter Message'),"+
		    			"('angelo@gmail.com', 'Enter Message'),"+
		    			"('rudy@gmail.com', 'Enter Message'),"+
		    			"('tahmid@gmail.com', 'Enter Message'),"+
		    			"('jeannette@gmail.com', 'Enter Message');")

			    	};
        
        String[] STAT = {"use testdb; ",
      		  "drop table if exists Stat; ",
      		  ("CREATE TABLE if not exists Stat( " +
				   "stat_id INT PRIMARY KEY AUTO_INCREMENT, " + 
				   "user_email VARCHAR(50), " +
				   "quoteCount INT DEFAULT 1, " +
				   "updateQuoteCount INT DEFAULT 0, " +
				   "billAmount INT DEFAULT 100, " +
				   "billStatus VARCHAR(10) DEFAULT 'N/A', " +
				   "billDate DATE DEFAULT (CURRENT_DATE), " +
				   "paidDate DATE DEFAULT '2023-01-01', " +
				   "paidAmount INT DEFAULT 0, " +
				   "FOREIGN KEY (user_email) REFERENCES user(email) "+"); ")
					};
        //updateQuoteCount stores the amount of times the user presses update
        String[] STAT_TUPLES = {
        		("insert into Stat(user_email)"+
        				"values ('susie@gmail.com'),"+
		    		 	"('don@gmail.com'),"+
		    	 	 	"('margarita@gmail.com'),"+
		    		 	"('jo@gmail.com'),"+
		    		 	"('wallace@gmail.com'),"+
		    		 	"('amelia@gmail.com'),"+
		    			"('sophie@gmail.com'),"+
		    			"('angelo@gmail.com'),"+
		    			"('rudy@gmail.com'),"+
		    			"('tahmid@gmail.com'),"+
		    			"('jeannette@gmail.com');")

			    	};
        
        
        //for loop to put these in database
        for (int i = 0; i < INITIAL.length; i++)
        	statement.execute(INITIAL[i]);
        for (int i = 0; i < TUPLES.length; i++)	
        	statement.execute(TUPLES[i]);
        for (int i = 0; i < QUOTE.length; i++)	
        	statement.execute(QUOTE[i]);
        for (int i = 0; i < QUOTE_TUPLES.length; i++)	
        	statement.execute(QUOTE_TUPLES[i]);
        for (int i = 0; i < STAT.length; i++)	
        	statement.execute(STAT[i]);
        for (int i = 0; i < STAT_TUPLES.length; i++)	
        	statement.execute(STAT_TUPLES[i]);
        disconnect();
    }
    
    public String getRole(String email) throws SQLException {
    	String role = null;
    	String sql = "SELECT * FROM User WHERE email = ?";
    	connect_func();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
        
        ResultSet resultSet = preparedStatement.executeQuery();
        
        if (resultSet.next()) {
            role = resultSet.getString("role");
        }
        
        resultSet.close();
        //statement.close();
        
    	return role;
    }
    
    public List<quote> listAllQuotes() throws SQLException {
        // Possibly reduntant function, will fix later
    	List<quote> listQuote = new ArrayList<quote>();        
        String sql = "SELECT * FROM Quote";    
        
        connect_func();      
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            String quote_id = resultSet.getString("quote_id");
            String quote_date = resultSet.getString("quote_date"); 
            String note = resultSet.getString("note");
            String treesize = resultSet.getString("treesize");
            String treeheight = resultSet.getString("treeheight");
            String status = resultSet.getString("status");
            String user_email = resultSet.getString("user_email");
            
            quote quotes = new quote(note, user_email, treesize, treeheight, status, quote_id, quote_date);
            listQuote.add(quotes);
        }        
        resultSet.close();
        disconnect();        
        return listQuote;
    }
    
    public void insertQuote(user users) throws SQLException {
    	// Insert a new quote upon registration
    	connect_func();         
		String sql = "insert into Quote(user_email, note) values (?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
			preparedStatement.setString(1, users.getEmail());
			preparedStatement.setString(2, "Enter Message");
			
			

		preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    
    public List<quote> currentUserQuote(String email) throws SQLException {
        // Function used to show all the quotes of the logged in user
    	List<quote> listQuote = new ArrayList<quote>();        
        String sql = "SELECT * FROM Quote WHERE user_email = ?";
        
        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
        
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String quote_id = resultSet.getString("quote_id");
            String quote_date = resultSet.getString("quote_date"); 
            String note = resultSet.getString("note");
            String treesize = resultSet.getString("treesize");
            String treeheight = resultSet.getString("treeheight");
            String status = resultSet.getString("status");
            String user_email = resultSet.getString("user_email");
            
            quote quotes = new quote(note, user_email, treesize, treeheight, status, quote_id, quote_date);
            listQuote.add(quotes);
        }

        resultSet.close();
        disconnect();

        return listQuote;
    }
    
    public void updateQuote(quote quotes) throws SQLException {
    	// Function used to modify the logged in users quote
    	connect_func();         
    	String sql = "UPDATE quote SET note=?, quote_date=?, treesize=?, treeheight=? WHERE quote_id=? AND user_email=?";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
			preparedStatement.setString(1, quotes.getNote());
			preparedStatement.setString(2, quotes.getQuoteDate());
			preparedStatement.setString(3, quotes.getTreeSize());
			preparedStatement.setString(4, quotes.getTreeHeight());
			preparedStatement.setString(5, quotes.getQuoteid());
			preparedStatement.setString(6, quotes.getUserEmail());
			
			

		preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    
    public List<quote> allQuotes() throws SQLException {
    	// List all quotes in the database to be viewed when logged in as admin
    	List<quote> listQuote = new ArrayList<>();

        
        String sql = "SELECT * FROM Quote";

        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String quote_id = resultSet.getString("quote_id");
            String quote_date = resultSet.getString("quote_date"); 
            String note = resultSet.getString("note");
            String treesize = resultSet.getString("treesize");
            String treeheight = resultSet.getString("treeheight");
            String status = resultSet.getString("status");
            String user_email = resultSet.getString("user_email");
            
            quote quotes = new quote(note, user_email, treesize, treeheight, status, quote_id, quote_date);
            listQuote.add(quotes);
        }

        resultSet.close();
        disconnect();

        return listQuote;
    }
    
    public void updateQuoteFromAdmin(quote quotes) throws SQLException {
    	// Function for modifying a user quote when logged in as an admin
    	connect_func();         
    	String sql = "UPDATE quote SET note=?, quote_date=?, treesize=?, treeheight=?, quote_id=?, status=? WHERE quote_id=? AND user_email=?";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
			preparedStatement.setString(1, quotes.getNote());
			preparedStatement.setString(2, quotes.getQuoteDate());
			preparedStatement.setString(3, quotes.getTreeSize());
			preparedStatement.setString(4, quotes.getTreeHeight());
			preparedStatement.setString(5, quotes.getQuoteid());
			preparedStatement.setString(6, quotes.getStatus());
			preparedStatement.setString(7, quotes.getQuoteid());
			preparedStatement.setString(8, quotes.getUserEmail());
			
			

		preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    
    // Part 3 Functions
    public void insertStat(String email) throws SQLException {
    	// Insert a new stat record upon registration
    	connect_func();         
		String sql = "insert into Stat(user_email) values (?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
			preparedStatement.setString(1, email);
			
			

		preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    
    public void incrementQuoteCount(String email) throws SQLException {
    	// Insert quoteCount value after someone adds a new quote
    	connect_func();         
    	String sql = "UPDATE Stat SET quoteCount = quoteCount + 1 WHERE user_email = ?";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
			preparedStatement.setString(1, email);
			
			

		preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    
    public void incrementUpdateQuoteCount(String email) throws SQLException {
    	// increment updateQuoteCount value in table
    	connect_func();         
    	String sql = "UPDATE Stat SET updateQuoteCount = updateQuoteCount + 1 WHERE user_email = ?";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
			preparedStatement.setString(1, email);
			
			

		preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    
    public void incrementBillAmount(String email) throws SQLException {
    	// Insert billAmount in table by 100
    	connect_func();         
    	String sql = "UPDATE Stat SET billAmount = billAmount + 100 WHERE user_email = ?";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
			preparedStatement.setString(1, email);
			
			

		preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    
    public List<stat> statInfo(String email) throws SQLException {
        // Function used to show the bill of the logged in user
    	List<stat> listStat = new ArrayList<stat>();        
        String sql = "SELECT * FROM Stat WHERE user_email = ?";
        
        connect_func();
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
        
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String stat_id = resultSet.getString("stat_id");
            String user_email = resultSet.getString("user_email"); 
            String quoteCount = resultSet.getString("quoteCount");
            String updateQuoteCount = resultSet.getString("updateQuoteCount");
            String billAmount = resultSet.getString("billAmount");
            String billStatus = resultSet.getString("billStatus");
            String billDate = resultSet.getString("billDate");
            String paidDate = resultSet.getString("paidDate");
            String paidAmount = resultSet.getString("paidAmount");
            
            stat stats = new stat(stat_id, user_email, quoteCount, updateQuoteCount, billAmount, billStatus, billDate, paidDate, paidAmount);
            listStat.add(stats);
        }

        resultSet.close();
        disconnect();

        return listStat;
    }
    
    public void payBill(String email) throws SQLException {
    	// Pay off the bill based off the email
    	connect_func();       
    	
    	String sql = "UPDATE Stat SET paidAmount = billAmount WHERE user_email = ?";
    	String sql2 = "UPDATE Stat SET billAmount = 0 WHERE user_email = ?";
    	String sql3 = "UPDATE Stat SET billStatus = 'Paid' WHERE user_email = ?";
    	String sql4 = "UPDATE Stat SET paidDate = CURRENT_DATE() WHERE user_email = ?";
    	
    	
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, email);
		preparedStatement.executeUpdate();
        preparedStatement.close();
        
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql2);
		preparedStatement.setString(1, email);
		preparedStatement.executeUpdate();
        preparedStatement.close();
        
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql3);
		preparedStatement.setString(1, email);
		preparedStatement.executeUpdate();
        preparedStatement.close();
        
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql4);
		preparedStatement.setString(1, email);
		preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    
    public List<stat> bigClients() throws SQLException {
        // Function used to show the clients that had the most trees cut
    	List<stat> listStat = new ArrayList<stat>();        
    	// Get the maximum value of quoteCount
        String maxQuoteCountSql = "SELECT MAX(quoteCount) AS maxQuoteCount FROM Stat";

        connect_func();
        preparedStatement = connect.prepareStatement(maxQuoteCountSql);
        ResultSet maxQuoteCountResult = preparedStatement.executeQuery();
        
        int maxQuoteCount = 0;
        if (maxQuoteCountResult.next()) {
            maxQuoteCount = maxQuoteCountResult.getInt("maxQuoteCount");
        }

        maxQuoteCountResult.close();

        // Select rows where quoteCount matches the maximum value
        String sql = "SELECT * FROM Stat WHERE quoteCount = ?";
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, maxQuoteCount);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
         
            String stat_id = resultSet.getString("stat_id");
            String user_email = resultSet.getString("user_email");
            String quoteCount = resultSet.getString("quoteCount");
            String updateQuoteCount = resultSet.getString("updateQuoteCount");
            String billAmount = resultSet.getString("billAmount");
            String billStatus = resultSet.getString("billStatus");
            String billDate = resultSet.getString("billDate");
            String paidDate = resultSet.getString("paidDate");
            String paidAmount = resultSet.getString("paidAmount");

            stat stats = new stat(stat_id, user_email, quoteCount, updateQuoteCount, billAmount, billStatus, billDate, paidDate, paidAmount);
            listStat.add(stats);
        }

        resultSet.close();
        disconnect();

        return listStat;
    }
    
    public List<stat> easyClients() throws SQLException {
       // Function used to show the clients that accepted the first quote without negotiation
    		List<stat> listStat = new ArrayList<stat>();        
    	 	String sql = "SELECT * FROM Stat WHERE updateQuoteCount = ?";
    	    
    	    connect_func();
    	    preparedStatement = connect.prepareStatement(sql);
    	    preparedStatement.setString(1, "1"); 

    	    ResultSet resultSet = preparedStatement.executeQuery();

    	    while (resultSet.next()) {
    	        // Retrieve values from the result set and create Stat objects
    	        String stat_id = resultSet.getString("stat_id");
    	        String user_email = resultSet.getString("user_email");
    	        String quoteCount = resultSet.getString("quoteCount");
    	        String updateQuoteCount = resultSet.getString("updateQuoteCount");
    	        String billAmount = resultSet.getString("billAmount");
    	        String billStatus = resultSet.getString("billStatus");
    	        String billDate = resultSet.getString("billDate");
    	        String paidDate = resultSet.getString("paidDate");
    	        String paidAmount = resultSet.getString("paidAmount");

    	        stat stats = new stat(stat_id, user_email, quoteCount, updateQuoteCount, billAmount, billStatus, billDate, paidDate, paidAmount);
    	        listStat.add(stats);
    	    }

    	    resultSet.close();
    	    disconnect();

    	    return listStat;
    	}
    
    public List<stat> oneTree() throws SQLException {
        // Function used to show the clients that accepted the first quote without negotiation
     		List<stat> listStat = new ArrayList<stat>();        
     	 	String sql = "SELECT * FROM Stat WHERE quoteCount = ?";
     	    
     	    connect_func();
     	    preparedStatement = connect.prepareStatement(sql);
     	    preparedStatement.setString(1, "1"); 

     	    ResultSet resultSet = preparedStatement.executeQuery();

     	    while (resultSet.next()) {
     	        // Retrieve values from the result set and create Stat objects
     	        String stat_id = resultSet.getString("stat_id");
     	        String user_email = resultSet.getString("user_email");
     	        String quoteCount = resultSet.getString("quoteCount");
     	        String updateQuoteCount = resultSet.getString("updateQuoteCount");
     	        String billAmount = resultSet.getString("billAmount");
     	        String billStatus = resultSet.getString("billStatus");
     	        String billDate = resultSet.getString("billDate");
     	        String paidDate = resultSet.getString("paidDate");
     	        String paidAmount = resultSet.getString("paidAmount");

     	        stat stats = new stat(stat_id, user_email, quoteCount, updateQuoteCount, billAmount, billStatus, billDate, paidDate, paidAmount);
     	        listStat.add(stats);
     	    }

     	    resultSet.close();
     	    disconnect();

     	    return listStat;
     	}
    
    public List<quote> prospectiveClient() throws SQLException {
        // Function used to show the clients where their quotes haven't been accepted yet
     		List<quote> listQuote = new ArrayList<quote>();        
     	 	String sql = "SELECT * FROM Quote WHERE status = ?";
     	    
     	    connect_func();
     	    preparedStatement = connect.prepareStatement(sql);
     	    preparedStatement.setString(1, "N/A"); 

     	    ResultSet resultSet = preparedStatement.executeQuery();

     	    while (resultSet.next()) {
     	        // Retrieve values from the result set and create Quote objects
     	    	String quote_id = resultSet.getString("quote_id");
                String quote_date = resultSet.getString("quote_date"); 
                String note = resultSet.getString("note");
                String treesize = resultSet.getString("treesize");
                String treeheight = resultSet.getString("treeheight");
                String status = resultSet.getString("status");
                String user_email = resultSet.getString("user_email");
                
                quote quotes = new quote(note, user_email, treesize, treeheight, status, quote_id, quote_date);
                listQuote.add(quotes);
     	    }

     	    resultSet.close();
     	    disconnect();

     	    return listQuote;
     	}
    
    public List<quote> highestTreeClient() throws SQLException {
    	// Function used to show the clients with the tallest tree
    	List<quote> listQuote = new ArrayList<quote>();        
    	// Get the maximum value of quoteCount
        String maxTreeHeightSql = "SELECT MAX(treeheight) AS maxTreeHeightCount FROM Quote";

        connect_func();
        preparedStatement = connect.prepareStatement(maxTreeHeightSql);
        ResultSet maxTreeHeightResult = preparedStatement.executeQuery();
        
        int maxTreeHeightCount = 0;
        if (maxTreeHeightResult.next()) {
            maxTreeHeightCount = maxTreeHeightResult.getInt("maxTreeHeightCount");
        }

        maxTreeHeightResult.close();

        // Select rows where treeheight matches the maximum value
        String sql = "SELECT * FROM Quote WHERE treeheight = ?";
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, maxTreeHeightCount);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
         
        	// Retrieve values from the result set and create Quote objects
 	    	String quote_id = resultSet.getString("quote_id");
            String quote_date = resultSet.getString("quote_date"); 
            String note = resultSet.getString("note");
            String treesize = resultSet.getString("treesize");
            String treeheight = resultSet.getString("treeheight");
            String status = resultSet.getString("status");
            String user_email = resultSet.getString("user_email");
            
            quote quotes = new quote(note, user_email, treesize, treeheight, status, quote_id, quote_date);
            listQuote.add(quotes);
        }

        resultSet.close();
        disconnect();

        return listQuote;
     	}
    
    public List<stat> overdueBill() throws SQLException {
        List<stat> listStat = new ArrayList<>();

        String sql = "SELECT * FROM Stat WHERE DATEDIFF(paidDate, billDate) >= 7";
        
        connect_func();
        preparedStatement = connect.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            // Retrieve values from the result set and create Stat objects
            String stat_id = resultSet.getString("stat_id");
            String user_email = resultSet.getString("user_email");
            String quoteCount = resultSet.getString("quoteCount");
            String updateQuoteCount = resultSet.getString("updateQuoteCount");
            String billAmount = resultSet.getString("billAmount");
            String billStatus = resultSet.getString("billStatus");
            String billDate = resultSet.getString("billDate");
            String paidDate = resultSet.getString("paidDate");
            String paidAmount = resultSet.getString("paidAmount");

            stat stats = new stat(stat_id, user_email, quoteCount, updateQuoteCount, billAmount, billStatus, billDate, paidDate, paidAmount);
            listStat.add(stats);
        }

        resultSet.close();
        disconnect();

        return listStat;
    }
    
    public List<stat> goodClients() throws SQLException {
        List<stat> listStat = new ArrayList<>();

        String sql = "SELECT * FROM Stat WHERE DATEDIFF(paidDate, billDate) = 1";
        
        connect_func();
        preparedStatement = connect.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            // Retrieve values from the result set and create Stat objects
            String stat_id = resultSet.getString("stat_id");
            String user_email = resultSet.getString("user_email");
            String quoteCount = resultSet.getString("quoteCount");
            String updateQuoteCount = resultSet.getString("updateQuoteCount");
            String billAmount = resultSet.getString("billAmount");
            String billStatus = resultSet.getString("billStatus");
            String billDate = resultSet.getString("billDate");
            String paidDate = resultSet.getString("paidDate");
            String paidAmount = resultSet.getString("paidAmount");

            stat stats = new stat(stat_id, user_email, quoteCount, updateQuoteCount, billAmount, billStatus, billDate, paidDate, paidAmount);
            listStat.add(stats);
        }

        resultSet.close();
        disconnect();

        return listStat;
    }
    
    public List<stat> allStats() throws SQLException {
        List<stat> listStat = new ArrayList<>();

        String sql = "SELECT * FROM Stat";
        
        connect_func();
        preparedStatement = connect.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            // Retrieve values from the result set and create Stat objects
            String stat_id = resultSet.getString("stat_id");
            String user_email = resultSet.getString("user_email");
            String quoteCount = resultSet.getString("quoteCount");
            String updateQuoteCount = resultSet.getString("updateQuoteCount");
            String billAmount = resultSet.getString("billAmount");
            String billStatus = resultSet.getString("billStatus");
            String billDate = resultSet.getString("billDate");
            String paidDate = resultSet.getString("paidDate");
            String paidAmount = resultSet.getString("paidAmount");

            stat stats = new stat(stat_id, user_email, quoteCount, updateQuoteCount, billAmount, billStatus, billDate, paidDate, paidAmount);
            listStat.add(stats);
        }

        resultSet.close();
        disconnect();

        return listStat;
    }
    
    
    
    
}
