package model.order;

public class Order {
	
	private int orderid;
	private String orderdate;
	private int amount;
	private String sendYN;
	private int payoption;
	private int customerid;
	
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public String getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getSendYN() {
		return sendYN;
	}
	public void setSendYN(String sendYN) {
		this.sendYN = sendYN;
	}
		
	public int getPayoption() {
		return payoption;
	}
	public void setPayoption(int payoption) {
		this.payoption = payoption;
	}
	public int getCustomerid() {
		return customerid;
	}
	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}
	@Override
	public String toString() {
		return "Order [orderid=" + orderid + ", orderdate=" + orderdate + ", amount=" + amount + ", sendYN=" + sendYN
				+ ", payoption=" + payoption + ", customerid=" + customerid + "]";
	}	
}
