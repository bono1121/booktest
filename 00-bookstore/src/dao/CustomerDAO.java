package dao;

import java.util.List;

import model.customer.Customer;

public interface CustomerDAO {

	List<Customer> selectAll();	
	Customer selectById(String id);	
	void insert(Customer customer);	
	
	//아이디 중복체크시 필요한 메소드
	int selectCntById(String id);
}
