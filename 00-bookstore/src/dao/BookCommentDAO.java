package dao;

import java.util.List;

import model.comment.BookComment;

public interface BookCommentDAO {
	List<BookComment> selectAll(int bookid);	
	
	//페이징 처리 메소드
	List<BookComment> selectAll(int bookid, int requestPage);
	BookComment selectByCommentid(int commentid);
	
	int insert(BookComment bookComment);		
	boolean deleteByCommentid(int commentid);	

}
