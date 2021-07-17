package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.BaseDAO;
import dao.OrderItemDAO;
import model.order.OrderInfo;
import model.order.OrderItem;

public class OrderItemDAOImpl extends BaseDAO implements OrderItemDAO {
	
	private static final String ORDERITEM_SELECT_ALL_SQL = 
			"SELECT orderitemid, quantity, bookid, orderid FROM orderitem";
	
	public List<OrderItem> selectAll() {
		List<OrderItem> orderitems = new ArrayList<OrderItem>();
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(ORDERITEM_SELECT_ALL_SQL);
			resultSet = pStatement.executeQuery();
			
			while (resultSet.next()) {
				OrderItem orderitem = new OrderItem();
				
				orderitem.setOrderItemid(resultSet.getInt("orderitemid"));
				orderitem.setQuantity(Integer.parseInt(resultSet.getString("quantity")));
				orderitem.setBookid(Integer.parseInt(resultSet.getString("bookid")));
				orderitem.setOrderid(Integer.parseInt(resultSet.getString("orderid")));
				
				orderitems.add(orderitem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(resultSet, pStatement, connection);
		}
		return orderitems;
	}

	
	private static final String ORDERITEM_INSERT_SQL = 
			"INSERT INTO orderitem (orderitemid, quantity, bookid, orderid) " +
			"VALUES (seqOrderItem.nextval, ?, ?, ?)";
	
	public void insert(OrderItem orderitem) {
		
		Connection connection = null;				
		PreparedStatement pStatement = null;
		
		try {
			connection = getConnection();
						
			pStatement = connection.prepareStatement(ORDERITEM_INSERT_SQL);
			
			pStatement.setInt(1, orderitem.getQuantity());
			pStatement.setInt(2, orderitem.getBookid());
			pStatement.setInt(3, orderitem.getOrderid());
			
			pStatement.execute();			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}

	private static final String ORDERITEM_SELECT_BY_ORDERID_SQL = 	
			"SELECT orderitemid, quantity, bookname, price, imagepath "+
			"FROM orderitem, book "+
			"WHERE orderitem.bookid = book.bookid "+
			"AND orderid = ? "+
			"ORDER BY orderitemid DESC";
	
	@Override
	public List<OrderInfo> selectByOrderid(int orderid) {
		List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(ORDERITEM_SELECT_BY_ORDERID_SQL);
			
			pStatement.setInt(1, orderid);
			
			resultSet = pStatement.executeQuery();
			
			while (resultSet.next()) {
				OrderInfo orderInfo = new OrderInfo();
				
				orderInfo.setOrderitemid(resultSet.getInt("orderitemid"));
				orderInfo.setQuantity(resultSet.getInt("quantity"));
				orderInfo.setBookname(resultSet.getString("bookname"));
				orderInfo.setPrice(resultSet.getInt("price"));	
				orderInfo.setImagepath(resultSet.getString("imagepath"));

				orderInfos.add(orderInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(resultSet, pStatement, connection);
		}
		return orderInfos;
	}	

}
