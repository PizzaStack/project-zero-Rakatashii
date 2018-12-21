package database;

import java.sql.*;

import org.h2.tools.Csv;
import org.h2.tools.SimpleResultSet;

public class H2Test {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.h2.Driver";
	static final String DB_URL = "jdbc:h2:~/test";

	// Database credentials
	static final String USER = "christianmeyer";
	static final String PASS = "";
	public static void H2(){
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 1: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 2: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 3: Execute a query
			System.out.println("Creating table in given database...");
			stmt = conn.createStatement();
			String sql = "CREATE TABLE klassy" + "(id INTEGER not NULL, " + " first VARCHAR(255), "
					+ " last VARCHAR(255), " + " age INTEGER, " + " PRIMARY KEY ( id ))";
			stmt.executeUpdate(sql);
			System.out.println("Created table in given database...");

			// STEP 4: Clean-up environment
			stmt.close();
			conn.close();
			System.out.println("Connected database successfully...");  
	   /* } catch(SQLException se) { 
	         // Handle errors for JDBC 
	         se.printStackTrace(); 
	      } catch(Exception e) { 
	         // Handle errors for Class.forName 
	         e.printStackTrace(); 
	      } finally { 
	         // finally block used to close resources 
	         try { 
	            if(stmt!=null) stmt.close();  
	         } catch(SQLException se2) { 
	         } // nothing we can do 
	         try { 
	            if(conn!=null) conn.close(); 
	         } catch(SQLException se) { 
	            se.printStackTrace(); 
	         } // end finally try 
	      } // end try 
	      System.out.println("Goodbye!"); */
		} catch (SQLException se) { 
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
	}
}

/*
 * { public static void TestH2() throws Exception { Class.forName
 * ("org.h2.Driver"); Connection conn =
 * DriverManager.getConnection("jdbc:h2:~/", "christianmeyer", "");
 * SimpleResultSet rs = new SimpleResultSet(); Statement s = conn. rs.addRow(1,
 * "admin", "password", 1); new
 * Csv().write("/c/Users/Associate/java/project-zero-Rakatashii/H2data.csv", rs,
 * null); rs.close(); conn.close(); } }
 */