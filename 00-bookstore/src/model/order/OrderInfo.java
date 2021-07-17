package model.order;

public class OrderInfo {

	private int orderitemid; 
	private int quantity;
	private String bookname; 
	private int price; 
	private String imagepath;
	

	public int getOrderitemid() {
		return orderitemid;
	}
	public void setOrderitemid(int orderitemid) {
		this.orderitemid = orderitemid;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getImagepath() {
		return imagepath;
	}
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}
	
	@Override
	public String toString() {
		return "OrderInfo [orderitemid=" + orderitemid + ", quantity=" + quantity + ", bookname=" + bookname
				+ ", price=" + price + ", imagepath=" + imagepath + "]";
	}	
}
