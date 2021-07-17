package form;

public class BookForm {	

	private String bookname;
	private String publisher;
	private String price;
	private String description;

	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "BookForm [bookname=" + bookname + ", publisher=" + publisher + ", price=" + price + ", description="
				+ description + "]";
	}	
}
