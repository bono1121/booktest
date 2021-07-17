package model.order;

public class OrderItem {
	
	private int orderItemid;
	private int quantity;
	private int bookid;
	private int orderid;
	
	public int getOrderItemid() {
		return orderItemid;
	}
	public void setOrderItemid(int orderItemid) {
		this.orderItemid = orderItemid;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getBookid() {
		return bookid;
	}
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	@Override
	public String toString() {
		return "OrderItem [orderItemid=" + orderItemid + ", quantity=" + quantity + ", bookid=" + bookid + ", orderid="
				+ orderid + "]";
	}	
}
