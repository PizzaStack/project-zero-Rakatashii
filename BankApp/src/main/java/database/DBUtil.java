package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	public boolean checkIfEmpty(String tableName) {
		try {
			Connection connection = DBConnection.getConnection();
			
			Statement statement = connection.createStatement();
		    //ResultSet rs = statement.executeQuery("SELECT * from " + tableName + ";");
		    DatabaseMetaData dbm = connection.getMetaData();
		    ResultSet rs = dbm.getTables(null, null, tableName, null);
	    
		    if (rs.next() == false) {
		    	System.out.println("ResultSet is empty for table " + tableName);
		    	return true;
		    }
		    
		    statement.close();
		    rs.close();
		} catch (SQLException e) {
			System.out.println("SQLException in DBUtil#checkIfEmpty");
			e.printStackTrace();
		}
		return false;
	}
}
