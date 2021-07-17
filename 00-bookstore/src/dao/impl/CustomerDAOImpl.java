package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.BaseDAO;
import dao.CustomerDAO;
import model.customer.Customer;

public class CustomerDAOImpl extends BaseDAO implements CustomerDAO {
	
	private static final String CUSTOMER_SELECT_ALL_SQL = 
			"SELECT customerid, id, name, password, postcode, address, address2, phone, email FROM customer";
	
	public List<Customer> selectAll() {
		List<Customer> customers = new ArrayList<Customer>();
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(CUSTOMER_SELECT_ALL_SQL);
			resultSet = pStatement.executeQuery();
			
			while (resultSet.next()) {
				Customer customer = new Customer();
				
				customer.setCustomerid(resultSet.getInt("customerid"));
				customer.setId(resultSet.getString("id"));
				customer.setName(resultSet.getString("name"));
				customer.setPassword(resultSet.getString("password"));
				customer.setPostcode(resultSet.getString("postcode"));
				customer.setAddress(resultSet.getString("address"));
				customer.setAddress2(resultSet.getString("address2"));
				customer.setPhone(resultSet.getString("phone"));
				customer.setEmail(resultSet.getString("email"));
				
				customers.add(customer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(resultSet, pStatement, connection);
		}
		return customers;
	}
	
	private static final String CUSTOMER_INSERT_SQL = 
			"INSERT INTO customer (customerid, id, name, password, postcode, address, address2, phone, email) " +
			"VALUES (seqCustomer.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";
			
	public void insert(Customer customer) {
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		
		try {
			connection = getConnection();
			
			pStatement = connection.prepareStatement(CUSTOMER_INSERT_SQL);
			
			pStatement.setString(1, customer.getId());
			pStatement.setString(2, customer.getName());
			pStatement.setString(3, customer.getPassword());
			pStatement.setString(4, customer.getPostcode());
			pStatement.setString(5, customer.getAddress());
			pStatement.setString(6, customer.getAddress2());
			pStatement.setString(7, customer.getPhone());
			pStatement.setString(8, customer.getEmail());	
			
			pStatement.execute();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}

	private static final String CUSTOMER_SELECT_BY_ID_SQL = 
			"SELECT customerid, id, name, password, postcode, address, address2, phone, email "
			+ "FROM customer "
			+ "WHERE id=?";
	
	public Customer selectById(String id) {

		Customer customer = new Customer();
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(CUSTOMER_SELECT_BY_ID_SQL);
			
			pStatement.setString(1, id);
			
			resultSet = pStatement.executeQuery();
			
			while (resultSet.next()) {
				
				customer.setCustomerid(resultSet.getInt("customerid"));
				customer.setId(resultSet.getString("id"));
				customer.setName(resultSet.getString("name"));
				customer.setPassword(resultSet.getString("password"));
				customer.setPostcode(resultSet.getString("postcode"));
				customer.setAddress(resultSet.getString("address"));
				customer.setAddress2(resultSet.getString("address2"));
				customer.setPhone(resultSet.getString("phone"));
				customer.setEmail(resultSet.getString("email"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(resultSet, pStatement, connection);
		}
		return customer;
	}

	
	private static final String CUSTOMER_SELECT_CNT_BY_ID_SQL = 
			"SELECT COUNT(*) AS cnt "
			+ "FROM customer "
			+ "WHERE id=?";
	
	@Override
	public int selectCntById(String id) {
		int resultCount = 0;
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(CUSTOMER_SELECT_CNT_BY_ID_SQL);
			
			pStatement.setString(1, id);
			
			resultSet = pStatement.executeQuery();
			
			while (resultSet.next()) {				
				resultCount= resultSet.getInt("cnt");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(resultSet, pStatement, connection);
		}
		return resultCount;
	}	
}
