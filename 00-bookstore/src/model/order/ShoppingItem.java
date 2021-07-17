package model.order;

import java.io.Serializable;

import model.book.Book;

public class ShoppingItem implements Serializable{

	private static final long serialVersionUID = 1L;

	private static int shoppingItemStaticInedx = 0;	
	
	private int shoppingItemInedx;
	private Book book;
    private int quantity;
      
	public ShoppingItem(Book book, int quantity) {
		
		super();
		
		this.shoppingItemInedx=++shoppingItemStaticInedx;		
		this.book = book;
		this.quantity = quantity;
	}
	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getShoppingItemInedx() {
		return shoppingItemInedx;
	}

	public void setShoppingItemInedx(int shoppingItemInedx) {
		this.shoppingItemInedx = shoppingItemInedx;
	}

	@Override
	public String toString() {
		return "ShoppingItem [shoppingItemInedx=" + shoppingItemInedx + ", book=" + book + ", quantity=" + quantity
				+ "]";
	}
}
