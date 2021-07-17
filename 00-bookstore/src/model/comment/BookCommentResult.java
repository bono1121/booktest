package model.comment;

public class BookCommentResult {
	
	private boolean result;
	private String message;
	private BookComment comment;
	
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public BookComment getComment() {
		return comment;
	}
	public void setComment(BookComment comment) {
		this.comment = comment;
	}


}
