package dao;

import java.util.List;

import model.book.Book;

public interface BookDAO {
	List<Book> selectAll();	
	//페이징 처리 메소드
	List<Book> selectAll(int requestPage);
	
	Book selectByBookId(int bookId);
	List<Book> selectByBookname(String bookname);
	
	void insert(Book book);	
	void update(Book book);	
	void updateEXImagepath(Book book);
	void deleteByBookId(int bookId);	

}
