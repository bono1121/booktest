package model.comment;

public class BookComment {
	
	private int commentid;                 
	private String writer;              
	private String content;             
	private String datetime;            
    private int bookid;  

	public int getCommentid() {
		return commentid;
	}
	public void setCommentid(int commentid) {
		this.commentid = commentid;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public int getBookid() {
		return bookid;
	}
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
	
	@Override
	public String toString() {
		return "BookComment [commentid=" + commentid + ", writer=" + writer + ", content=" + content + ", datetime="
				+ datetime + ", bookid=" + bookid + "]";
	}

    
}
