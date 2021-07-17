package model.comment;

import java.util.List;

public class BookCommentListResult {
	
	private boolean result;
	private String message;
	private List<BookComment> commentItem;
	
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
	public List<BookComment> getCommentItem() {
		return commentItem;
	}
	public void setCommentItem(List<BookComment> commentItem) {
		this.commentItem = commentItem;
	}

	
	
}
