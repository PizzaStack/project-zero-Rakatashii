package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import accounts.CheckingAccount;
import accounts.SavingsAccount;
import customers.Customer;
import customers.CustomerBuilder;
import customers.UnverifiedCustomer;
import database.DBConnection;
import database.DBUtil;
import utility.Helpers;

public class UnverifiedCustomerDAO implements UnverifiedCustomerDAOInterface {
	private Connection connection;
	private PreparedStatement ps;
	DBUtil util;
	Helpers helper;
	static final Logger log = Logger.getLogger(UnverifiedCustomerDAO.class);
	
	public UnverifiedCustomerDAO() {
		util = new DBUtil();
		helper = new Helpers();
	}
	
	@Override
	public boolean addUnverifiedCustomer(UnverifiedCustomer unverifiedCustomer, boolean toSampleTable) {
		String tableName = (toSampleTable) ? "sample_unverified_customers" : "unverified_customers";
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO " + tableName + " VALUES(?,?,?,?,?,?,?,?);";
			ps = connection.prepareStatement(sql);
			
			ps.setInt(1,  unverifiedCustomer.getID());
			ps.setString(2, unverifiedCustomer.getFirstname());
			ps.setString(3, unverifiedCustomer.getLastname());
			ps.setString(4, unverifiedCustomer.getTelephone());
			ps.setString(5, unverifiedCustomer.getEmail());
			ps.setBoolean(6, unverifiedCustomer.getIsCitizen());
			ps.setBoolean(7, unverifiedCustomer.getIsEmployed());
			ps.setString(8, unverifiedCustomer.getEmployer());
		
			if (ps.executeUpdate() != 0) {
				if (!toSampleTable) log.debug("Inserted Into " + tableName + " Values(" + unverifiedCustomer.getID() + ", " 
						+ unverifiedCustomer.getUsername() + ", ... )");
				ps.close();
				return true;
			} else {
				if (!toSampleTable) log.debug("Failed To Insert Into " + tableName + " UnverifiedCustomer With unverified_id = " + unverifiedCustomer.getID() 
					+ ", username = " + unverifiedCustomer.getUsername() + ", ... ");
				ps.close();
				return false;
			} 
		} catch (SQLException e) {
			//System.out.println("SQLException in UnverifiedCustomerDAO#addUnverifiedCustomer");
			//e.printStackTrace(); System.out.println();
			return false;
		}
	}
	
	public void updateUnverifiedCustomer(UnverifiedCustomer customer, boolean toSampleTable) {
		String tableName = (toSampleTable) ? "sample_unverified_customers" : "unverified_customers";
	    
		try {
	    	connection = DBConnection.getConnection();
	    	
		    String sql = "UPDATE " + tableName + " SET "
		    		+ "unverified_id = ?, first_name = ?, last_name = ?, telephone = ?, "
		    		+ "email = ?, us_citizen = ?, employed = ?, employer = ? "
			    	+ "WHERE unverified_id = ?;";
		    
		    PreparedStatement ps = connection.prepareStatement(sql);
		    ps.setInt(1, customer.getID());
		    ps.setString(2, customer.getFirstname());
		    ps.setString(3,  customer.getLastname());
		    ps.setString(4, customer.getTelephone());
		    ps.setString(5,  customer.getEmail());
		    ps.setBoolean(6, customer.getIsCitizen());
		    ps.setBoolean(7,  customer.getIsEmployed());
		    ps.setString(8,  customer.getEmployer());
		    
		    ps.setInt(9,  customer.getID());
		    
		    ps.executeUpdate();
		    if (!toSampleTable) log.debug("Updated " + tableName + " Where unverified_id = " + customer.getID() + " And email = " 
					+ customer.getEmail() + "...");
		    ps.close();
		} catch (SQLException e) {
			if (!toSampleTable) log.debug("Failed To Update " + tableName + " Where unverified_id = " + customer.getID() + " And email = " 
					+ customer.getUsername() + "...");
			e.printStackTrace(); System.out.println();
		}
	}
	
    public void deleteUnverifiedCustomer(UnverifiedCustomer unverified, boolean fromSampleTable) {
    	String tableName = (fromSampleTable) ? "sample_unverified_customers" : "unverified_customers";
        String sql = "DELETE FROM " + tableName + " WHERE unverified_id = ?";
        int unverifiedID = unverified.getID();
 
        try {
        	Connection connection = DBConnection.getConnection();
        	PreparedStatement ps = connection.prepareStatement(sql);
 
            ps.setInt(1, unverifiedID);
            ps.executeUpdate();
 
            if (!fromSampleTable) log.debug("Deleted From " + tableName + " Where unverified_id = " + unverified.getID() + " And email = " 
					+ unverified.getUsername() + "...");
            ps.close();
        } catch (SQLException e) {
        	if (!fromSampleTable) log.debug("Failed To Delete From " + tableName + " Where unverified_id = " + unverified.getID() + " And email = " 
					+ unverified.getUsername() + "...");
        	//e.printStackTrace(); System.out.println();
            //System.out.println(e.getMessage()); System.out.println();
        }
    }
    
    public void deleteUnverifiedCustomerWithID(int id, boolean fromSampleTable) {
    	String tableName = (fromSampleTable) ? "sample_unverified_customers" : "unverified_customers";
        String sql = "DELETE FROM " + tableName + " WHERE unverified_id = ?";
 
        try {
        	Connection connection = DBConnection.getConnection();
        	PreparedStatement ps = connection.prepareStatement(sql);
 
            ps.setInt(1, id);
            ps.executeUpdate();
 
            if (!fromSampleTable) log.debug("Deleted From " + tableName + " Where unverified_id = " + id);
            ps.close();
        } catch (SQLException e) {
        	if (!fromSampleTable) log.debug("Failed To Delete From " + tableName + " Where unverified_id = " + id);
        	//e.printStackTrace(); System.out.println();
            //System.out.println(e.getMessage()); System.out.println();
        }
    }
    
	@Override
	public int getNumUnverifiedCustomers(boolean fromSampleTable) {
		String tableName = (fromSampleTable) ? "sample_unverified_customers" : "unverified_customers";
		int count = 0;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT COUNT(*) AS count FROM " + tableName + ";";
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next()) count = rs.getInt("count");
			else {
				if (!fromSampleTable) log.debug("Could Not Get Count For Table " + tableName);
				statement.close(); rs.close();
				return count;
			}
			if (!fromSampleTable) log.debug("Current Count For " + tableName + " Is " + count);
			statement.close(); rs.close();
			return count;
		} catch (SQLException e) {
			if (!fromSampleTable) log.debug("Could Not Get Count For Table " + tableName);
			//e.printStackTrace(); System.out.println();
		}
		return 0;
	}
	
	@Override
	public ArrayList<String> getAllRecords(boolean fromSampleTable) {
		String tableName = (fromSampleTable) ? "sample_unverified_customers" : "unverified_customers";
		Connection connection;
		ArrayList<String> records = new ArrayList<String>();
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " ORDER BY unverified_id;";
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			String record = null;
			while (rs.next()) {
				record = String.format("%-10d%-15s%-15s%-15s%-40s%-10s%-10s%-35s", 
						rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5), helper.boolToString(rs.getBoolean(5)), helper.boolToString(rs.getBoolean(7)), 
						rs.getString(8));
				records.add(record);
				record = null;
			}
			statement.close(); rs.close();
			return records;
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
		return records;
	}
	
	public ArrayList<UnverifiedCustomer> getAllUnverifiedCustomers(boolean fromSampleTable) {
		String tableName = (fromSampleTable) ? "sample_unverified_customers" : "unverified_customers";
		ArrayList<UnverifiedCustomer> unverifiedCustomers = new ArrayList<UnverifiedCustomer>();
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " ORDER BY unverified_id;";
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				UnverifiedCustomer unverified = new CustomerBuilder()
						.withID(rs.getInt(1))
						.withFirstName(rs.getString(2))
						.withLastName(rs.getString(3))
						.withTelephone(rs.getString(4))
						.withEmail(rs.getString(5))
						.withIsCitizen(rs.getBoolean(6))
						.withIsEmployed(rs.getBoolean(7))
						.withEmployer(rs.getString(8)) 
						.makeUnverifiedCustomer();
				if (unverified != null) {
					unverifiedCustomers.add(unverified);
				}
			}
			statement.close(); rs.close();
			return unverifiedCustomers;
		} catch (SQLException e) {
			//e.printStackTrace(); System.out.println();
		}
		return unverifiedCustomers;
	}
	
	@Override
	public void printAllUnverifiedCustomers(boolean fromSampleTable) {
		ArrayList<String> unverifiedCustomerRecords = getAllRecords(fromSampleTable);
		for (String unverifiedCustomerRecord : unverifiedCustomerRecords) {
			System.out.println(unverifiedCustomerRecord);
		}
	}

	public ArrayList<Integer> getOpenIDs(boolean fromSampleTable){
		ArrayList<Integer> openIDs = new ArrayList<Integer>();
		String tableName = (fromSampleTable) ? "sample_unverified_customers" : "unverified_customers";
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " ORDER BY unverified_id;";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			int id, i = -1;
			while (rs.next() && i < getNumUnverifiedCustomers(fromSampleTable)) {
				id = rs.getInt(1);
				if (++i != id) openIDs.add(i);
				i = id;
			}
			statement.close(); rs.close();
			return openIDs;
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
		return openIDs;
	}

	public int getMaxID(boolean inSampleTable) {
		String tableName = (inSampleTable) ? "sample_unverified_customers" : "unverified_customers";
		int maxID = 0;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT MAX(unverified_id) FROM " + tableName + ";";
		
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			
			if (rs.next()) maxID = rs.getInt(1);
			statement.close(); rs.close();
			return maxID;
			
		} catch (SQLException e) {
			//e.printStackTrace(); System.out.println();
		}
		return maxID;
	}
}
