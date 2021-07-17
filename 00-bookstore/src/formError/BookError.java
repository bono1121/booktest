package formError;

public class BookError {	

	private String booknameErr;
	private String publisherErr;
	private String priceErr;
	private String descriptionErr;
	private boolean result; //true 에러 존재
	
	public String getBooknameErr() {
		return booknameErr;
	}
	public void setBooknameErr(String booknameErr) {
		this.booknameErr = booknameErr;
	}
	public String getPublisherErr() {
		return publisherErr;
	}
	public void setPublisherErr(String publisherErr) {
		this.publisherErr = publisherErr;
	}
	public String getPriceErr() {
		return priceErr;
	}
	public void setPriceErr(String priceErr) {
		this.priceErr = priceErr;
	}
	public String getDescriptionErr() {
		return descriptionErr;
	}
	public void setDescriptionErr(String descriptionErr) {
		this.descriptionErr = descriptionErr;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
		
}
