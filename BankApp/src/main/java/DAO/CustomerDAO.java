package DAO;

import java.io.IOException;
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

public class CustomerDAO implements CustomerDAOInterface {
	private Connection connection;
	private PreparedStatement ps;
	private Helpers helper;
	static final Logger log = Logger.getLogger(CustomerDAO.class);
	
	public CustomerDAO() {
		helper = new Helpers();
	}

	public boolean addCustomerWithAccount(Customer customer, boolean toSampleTable) {
		String tableName = (toSampleTable) ? "sample_customers_with_accounts" : "customers_with_accounts";
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO " + tableName + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			ps = connection.prepareStatement(sql);
			ps.setInt(1,  customer.getID());
			ps.setString(2, customer.getUsername());
			ps.setString(3, customer.getPassword());
			ps.setString(4, customer.getFirstname());
			ps.setString(5, customer.getLastname());
			ps.setString(6, customer.getTelephone());
			ps.setString(7, customer.getEmail());
			ps.setBoolean(8, customer.getIsCitizen());
			ps.setBoolean(9, customer.getIsEmployed());
			ps.setString(10, customer.getEmployer());
			if (customer.hasSavingsAccount() == false) {
				customer.makeNewAccounts();
			} 
			ps.setString(11, customer.getSavingsAccount().getID());
			ps.setDouble(12, customer.getSavingsAccount().getBalance());
			ps.setString(13, customer.getCheckingAccount().getID());
			ps.setDouble(14, customer.getCheckingAccount().getBalance());
			ps.setBoolean(15, customer.isFlagged());
			ps.setBoolean(16, customer.hasJointAccounts());
			ps.setInt(17, customer.getJointCustomerID());
			
			if (ps.executeUpdate() != 0) {
				if (!toSampleTable) log.debug("Inserted Into " + tableName + " Values(" + customer.getID() + ", " 
					+ customer.getUsername() + ", ... )");
				ps.close();
				return true;
			} else {
				if (!toSampleTable) log.debug("Failed To Insert Into " + tableName + " Customer With customer_id = " + customer.getID() 
					+ ", username = " + customer.getUsername() + ", ... ");
				ps.close();
				return false;
			} 
		} catch (SQLException e) {
			//e.printStackTrace(); System.out.println();
			//System.out.println("SQLException in CustomerDAO#addCustomer"); System.out.println();
			return false;
		}
	}
	
	public void updateCustomerAndAccounts(Customer customer, boolean toSampleTable) {
		String tableName = (toSampleTable) ? "sample_customers_with_accounts" : "customers_with_accounts";
		SavingsAccount savings = customer.getSavingsAccount();
		CheckingAccount checking = customer.getCheckingAccount();

		try {
	    	connection = DBConnection.getConnection();
	    	
		    String sql = "UPDATE " + tableName + " SET "
		    		+ "customer_id = ?, username = ?, password = ?, "
		    		+ "first_name = ?, last_name = ?, telephone = ?, email = ?, "
		    		+ "us_citizen = ?, employed = ?, employer = ?, "
		    		+ "savings_number = ?, savings_amount = ?, checking_number = ?, checking_amount = ?, "
		    		+ "flagged = ?, joint = ?, joint_customer_id = ? "
			    	+ "WHERE customer_id = ?;";
		    
		    PreparedStatement ps = connection.prepareStatement(sql);
		    ps.setInt(1, customer.getCustomerID());
		    ps.setString(2, customer.getUsername());
		    ps.setString(3, customer.getPassword());
		    ps.setString(4, customer.getFirstname());
		    ps.setString(5,  customer.getLastname());
		    ps.setString(6, customer.getTelephone());
		    ps.setString(7,  customer.getEmail());
		    ps.setBoolean(8, customer.getIsCitizen());
		    ps.setBoolean(9,  customer.getIsEmployed());
		    ps.setString(10,  customer.getEmployer());
		    if (savings != null) {
		    	ps.setString(11, savings.getID());
			    ps.setDouble(12,  savings.getBalance());
		    } else {
		    	ps.setString(11, "null");
			    ps.setDouble(12,  0.0);
		    }
		    if (checking != null) {
		    	ps.setString(13,  checking.getID());
			    ps.setDouble(14,  checking.getBalance());
		    } else {
		    	ps.setString(13,  "null");
			    ps.setDouble(14,  0.0);
		    }
		    ps.setBoolean(15,  customer.isFlagged());
		    ps.setBoolean(16,  customer.hasJointAccounts());
		    ps.setInt(17, customer.getJointCustomerID());
		    
		    ps.setInt(18, customer.getCustomerID());
		    
		    ps.executeUpdate();
		    if (!toSampleTable) log.debug("Updated " + tableName + " Where customer_id = " + customer.getID() + " And username = " 
					+ customer.getUsername() + "...");
		    ps.close();
		} catch (SQLException e) {
			if (!toSampleTable) log.debug("Failed To Update " + tableName + " Where customer_id = " + customer.getID() + " And username = " 
					+ customer.getUsername() + "...");
			e.printStackTrace(); System.out.println();
		}
	}	
	
    public void deleteCustomer(Customer customer, boolean fromSampleTable) {
    	String tableName = (fromSampleTable) ? "sample_customers_with_accounts" : "customers_with_accounts";
        String sql = "DELETE FROM " + tableName + " WHERE customer_id = ?";
        int customerID = customer.getID();
 
        try {
        	Connection connection = DBConnection.getConnection();
        	PreparedStatement ps = connection.prepareStatement(sql);
 
            ps.setInt(1, customerID);
            ps.executeUpdate();
 
            if (!fromSampleTable) log.debug("Deleted From " + tableName + " Where customer_id = " + customer.getID() + " And username = " 
					+ customer.getUsername() + "...");
            ps.close();
        } catch (SQLException e) {
        	if (!fromSampleTable) log.debug("Failed To Delete From " + tableName + " Where customer_id = " + customer.getID() + " And username = " 
					+ customer.getUsername() + "...");
        	//e.printStackTrace(); System.out.println();
            //System.out.println(e.getMessage()); System.out.println();
        }
    }
	@Override
	public int getNumCustomers(boolean fromSampleTable) {
		String tableName = (fromSampleTable) ? "sample_customers_with_accounts" : "customers_with_accounts";
		
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT COUNT(*) AS count FROM " + tableName + ";";
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			rs.next();
			int count = rs.getInt("count");
			statement.close(); rs.close();
			if (!fromSampleTable) log.debug("Current Count For " + tableName + " Is " + count);
			return count;
		} catch (SQLException e) {
			if (!fromSampleTable) log.debug("Could Not Get Count For Table " + tableName);
			e.printStackTrace(); System.out.println();
		}
		return 0;
	}
	
	public ArrayList<String> getAllRecords(boolean fromSampleTable) {
		String tableName = (fromSampleTable) ? "sample_customers" : "customers";
		ArrayList<String> records = new ArrayList<String>();
		try {
			Connection connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " ORDER BY customer_id;";
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			String record = null;
			while (rs.next()) {
				record = String.format("%-10d%-20s%-20s%-15s%-15s%-15s%-40s%-10s%-10s%-35s%-10s", 
						rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5), rs.getString(6), rs.getString(7), 
						helper.boolToString(rs.getBoolean(8)), helper.boolToString(rs.getBoolean(9)), 
						rs.getString(10), helper.boolToString(rs.getBoolean(11)));
				records.add(record);
			}
			statement.close(); rs.close();
			return records;
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
		return records;
	}
	
	public ArrayList<Customer> getAllCustomers(boolean fromSampleTable) {
		String tableName = (fromSampleTable) ? "sample_customers_with_accounts" : "customers_with_accounts";
		ArrayList<Customer> customers = new ArrayList<Customer>();
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " ORDER BY customer_id;";
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				Customer customer = new CustomerBuilder()
						.withID(rs.getInt(1))
						.withUsername(rs.getString(2))
						.withPassword(rs.getString(3)) 
						.withFirstName(rs.getString(4))
						.withLastName(rs.getString(5))
						.withTelephone(rs.getString(6))
						.withEmail(rs.getString(7))
						.withIsCitizen(rs.getBoolean(8))
						.withIsEmployed(rs.getBoolean(9))
						.withEmployer(rs.getString(10)) 
						.withIsJoint(rs.getBoolean(16))
						.withJointCustomerID(rs.getInt(17))
						.makeCustomer();
				if (customer != null) {
					SavingsAccount savings = new SavingsAccount(rs.getString(11), rs.getDouble(12), customer);
					customer.setSavingsAccount(savings);
					CheckingAccount checking = new CheckingAccount(rs.getString(13), rs.getDouble(14), customer);
					customer.setCheckingAccount(checking);
					boolean recordIsFlagged = rs.getBoolean(15);
					if (recordIsFlagged) customer.flag();
					customers.add(customer);
				}
			}
			statement.close(); rs.close();
			return customers;
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
		return customers;
	}
	
	public Customer findCustomerByID(int id, boolean fromSampleTable) {
		String tableName = (fromSampleTable) ? "sample_customers_with_accounts" : "customers_with_accounts";
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " WHERE customer_id = " + id + ";";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				Customer customer = new CustomerBuilder()
						.withID(rs.getInt(1))
						.withUsername(rs.getString(2))
						.withPassword(rs.getString(3)) 
						.withFirstName(rs.getString(4))
						.withLastName(rs.getString(5))
						.withTelephone(rs.getString(6))
						.withEmail(rs.getString(7))
						.withIsCitizen(rs.getBoolean(8))
						.withIsEmployed(rs.getBoolean(9))
						.withEmployer(rs.getString(10)) 
						.withIsJoint(rs.getBoolean(16))
						.withJointCustomerID(rs.getInt(17))
						.makeCustomer();

				if (customer != null) {
					SavingsAccount savings = new SavingsAccount(rs.getString(11), rs.getDouble(12), customer);
					customer.setSavingsAccount(savings);
					CheckingAccount checking = new CheckingAccount(rs.getString(13), rs.getDouble(14), customer);
					customer.setCheckingAccount(checking);
					boolean recordIsFlagged = rs.getBoolean(15);
					if (recordIsFlagged) customer.flag();
					statement.close(); rs.close();
					if (!fromSampleTable) log.debug("Customer Found In Table " + tableName + " With customer_id = " + id);
					return customer;
				} else if (!fromSampleTable) log.debug("No Customer Found In Table " + tableName + " With customer_id = " + id);
			}
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
		return null;
	}
	
	public boolean checkIfCustomerExists(int id, boolean fromSampleTable) {
		String tableName = (fromSampleTable) ? "sample_customers_with_accounts" : "customers_with_accounts";
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " WHERE customer_id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				ps.close(); rs.close();
				if (!fromSampleTable) log.debug("Customer With ID = " + id + " Exists In Table " + tableName);
				return true;
			} else {
				ps.close(); rs.close();
				if (!fromSampleTable) log.debug("Customer With ID = " + id + " Does Not Exists In Table " + tableName);
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
		return false;
	}

	public void printAllCustomers(boolean fromSampleTable) {
		ArrayList<String> sampleCustomerRecords = getAllRecords(fromSampleTable);
		for (String sampleCustomerRecord : sampleCustomerRecords) {
			System.out.println(sampleCustomerRecord);
		}
	}
	
	public ArrayList<Integer> getOpenIDs(boolean fromSampleTable){
		ArrayList<Integer> openIDs = new ArrayList<Integer>();
		String tableName = (fromSampleTable) ? "sample_customers" : "customers";
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " ORDER BY customer_id;";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			int id, i = -1;
			while (rs.next() && i < getNumCustomers(fromSampleTable)) {
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
		String tableName = (inSampleTable) ? "sample_customers" : "customers";
		int maxID = 0;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT MAX(customer_id) FROM " + tableName + ";";
		
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			
			if (rs.next()) maxID = rs.getInt(1);
			statement.close(); rs.close();
			return maxID;
			
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
		return maxID;
	}
	
}
