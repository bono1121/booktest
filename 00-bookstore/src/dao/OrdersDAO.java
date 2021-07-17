package dao;

import java.util.List;

import model.order.Order;


public interface OrdersDAO {
	
	int insert(Order order);	
	List<Order> selectAByCustomerid(int customerid);

}
