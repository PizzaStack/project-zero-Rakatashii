package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DBConnection {
	private static Connection connection = null;
	
	private static String url;
	private static String username;
	private static String password;
	public static boolean connectionEstablished;
	
	final static String H2JDBC_DRIVER = "org.h2.Driver";
	final static String H2DB_URL = "jdbc:h2:tcp://localhost/~/test";
	
	public DBConnection() { }
	
	public static void startConnection() throws SQLException {
		getCredentials();
		//DriverManager.registerDriver(new org.postgresql.Driver());
		//connection = DriverManager.getConnection(url,  username,  password);
		
		DriverManager.registerDriver(new org.h2.Driver());
		connection = DriverManager.getConnection(H2DB_URL,  "christianmeyer", "");
	}
	
	public static Connection getConnection() throws SQLException {
		if (connection == null) startConnection();
		return connection;
	}
	
	public static void closeConnection(){
		if (connection != null)
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		connectionEstablished = false;
	}
	
	public static void getCredentials() {
		final String dbconnectionfile = "preferences/dbconnection.txt";
		File file = new File(dbconnectionfile);
		Scanner in;
		try {
			in = new Scanner(file);
			url = in.nextLine();
			//System.out.println("url="+url);
			username = in.nextLine();
			//System.out.println("username="+username);
			password = in.nextLine();
			//System.out.println("password="+password);
		} catch (FileNotFoundException e) {
			System.out.println("Unable to verify db credentials");
			e.printStackTrace();
		}
	}
}
