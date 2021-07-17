package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.BaseDAO;
import dao.OrdersDAO;
import model.order.Order;

public class OrdersDAOImpl extends BaseDAO implements OrdersDAO {
		
	private static final String ORDERS_INSERT_SQL = 
			"INSERT INTO orders (orderid, orderdate, amount, sendyn, payoption, customerid) " +
			"VALUES (seqOrders.nextval, sysdate, ?, 'N', ?, ?)";
		
	private static final String SEQORDERS_SELECT_SQL = 
			"SELECT seqOrders.currval AS orderid FROM dual";
	
	//같은 session이 아닌 경우, currval 결과가 나오질 않아 쿼리 두개 실행후, 현재주문번호 리턴함
	public int insert(Order order) {
				
		Connection connection = null;
		PreparedStatement pStatement = null;
		PreparedStatement pStatement_seq = null;
		ResultSet resultSet = null;
		//현재주문번호
		int orderid=0;
		
		try {
			connection = getConnection();
			
			//Orders 테이블에 insert
			pStatement = connection.prepareStatement(ORDERS_INSERT_SQL);
			
			pStatement.setInt(1, order.getAmount());
			pStatement.setInt(2, order.getPayoption());//1:카드결제 2:통장이체
			pStatement.setInt(3, order.getCustomerid());
			
			pStatement.execute();			
			
			pStatement_seq= connection.prepareStatement(SEQORDERS_SELECT_SQL);			
			resultSet = pStatement_seq.executeQuery();
			
			resultSet.next();
			
			orderid = resultSet.getInt("orderid");				
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(resultSet, pStatement_seq, null);
			closeDBObjects(null, pStatement, connection);
		}
		return orderid;
	}

	private static final String ORDERS_SELECT_BY_CUSTOMERID_SQL = 	
			"SELECT orderid, orderdate, amount, sendyn, payoption "+
			"FROM orders, customer "+
			"WHERE orders.customerid = customer.customerid "+
			"AND customer.customerid = ? "+
			"ORDER BY orders.orderid DESC";
	
	public List<Order> selectAByCustomerid(int customerid) {
		List<Order> orders = new ArrayList<Order>();
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(ORDERS_SELECT_BY_CUSTOMERID_SQL);
			
			pStatement.setInt(1, customerid);
			
			resultSet = pStatement.executeQuery();
			
			while (resultSet.next()) {
				Order order = new Order();
				
				order.setOrderid(resultSet.getInt("orderid"));
				order.setOrderdate(resultSet.getString("orderdate"));
				order.setAmount(resultSet.getInt("amount"));
				order.setSendYN(resultSet.getString("sendyn"));
				order.setPayoption(resultSet.getInt("payoption"));
				
				orders.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(resultSet, pStatement, connection);
		}
		return orders;
	}	
}
