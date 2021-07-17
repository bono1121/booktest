package dao;

import java.util.List;

import model.order.OrderInfo;
import model.order.OrderItem;


public interface OrderItemDAO {
	
	List<OrderInfo> selectByOrderid(int orderid);	
	void insert(OrderItem Orderitem);	


}
