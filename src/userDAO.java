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
			    		 	"('don@gmail.com', 'don123', 'client'),"+
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
        //for loop to put these in database
        for (int i = 0; i < INITIAL.length; i++)
        	statement.execute(INITIAL[i]);
        for (int i = 0; i < TUPLES.length; i++)	
        	statement.execute(TUPLES[i]);
        for (int i = 0; i < QUOTE.length; i++)	
        	statement.execute(QUOTE[i]);
        for (int i = 0; i < QUOTE_TUPLES.length; i++)	
        	statement.execute(QUOTE_TUPLES[i]);
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
    	connect_func("root","pass1234");         
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
    	String sql = "UPDATE quote SET note=?, quote_date=?, treesize=?, treeheight=? WHERE user_email=?";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
			preparedStatement.setString(1, quotes.getNote());
			preparedStatement.setString(2, quotes.getQuoteDate());
			preparedStatement.setString(3, quotes.getTreeSize());
			preparedStatement.setString(4, quotes.getTreeHeight());
			preparedStatement.setString(5, quotes.getUserEmail());
			
			

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
    	String sql = "UPDATE quote SET note=?, quote_date=?, treesize=?, treeheight=?, quote_id=?, status=? WHERE user_email=?";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
			preparedStatement.setString(1, quotes.getNote());
			preparedStatement.setString(2, quotes.getQuoteDate());
			preparedStatement.setString(3, quotes.getTreeSize());
			preparedStatement.setString(4, quotes.getTreeHeight());
			preparedStatement.setString(5, quotes.getQuoteid());
			preparedStatement.setString(6, quotes.getStatus());
			preparedStatement.setString(7, quotes.getUserEmail());
			
			

		preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    
}
