package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.BaseDAO;
import dao.BookDAO;
import model.book.Book;
import model.page.PageRowResult;
import service.PageManager;

public class BookDAOImpl extends BaseDAO implements BookDAO {
	
	private static final String BOOK_SELECT_ALL_SQL = 
			"SELECT bookid, bookname, publisher, price, description, imagepath FROM book";
	
	public List<Book> selectAll() {
		List<Book> books = new ArrayList<Book>();
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(BOOK_SELECT_ALL_SQL);
			resultSet = pStatement.executeQuery();
			
			while (resultSet.next()) {
				Book book = new Book();
				
				book.setBookid(resultSet.getInt("bookid"));
				book.setBookname(resultSet.getString("bookname"));
				book.setPublisher(resultSet.getString("publisher"));
				book.setPrice(Integer.parseInt(resultSet.getString("price")));
				book.setDescription(resultSet.getString("description"));
				book.setImagepath(resultSet.getString("imagepath"));
				
				books.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(resultSet, pStatement, connection);
		}
		return books;
	}

	
	private static final String BOOK_SELECT_PAGE_SQL = 
			"SELECT bookid, bookname, publisher, price, description, imagepath "+
			"FROM (SELECT ROWNUM rn, books.* FROM (SELECT * FROM book ORDER BY bookid DESC) books) "+
			"WHERE rn BETWEEN ? AND ?";
	
	public List<Book> selectAll(int requestPage) {
		List<Book> books = new ArrayList<Book>();
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(BOOK_SELECT_PAGE_SQL);
			
			//페이징 처리
			PageManager pm = new PageManager(requestPage);
			PageRowResult pageRowResult = pm.getPageRowResult();
			pStatement.setInt(1, pageRowResult.getRowStartNumber());
			pStatement.setInt(2, pageRowResult.getRowEndNumber());
			
			resultSet = pStatement.executeQuery();
			
			while (resultSet.next()) {
				Book book = new Book();
				
				book.setBookid(resultSet.getInt("bookid"));
				book.setBookname(resultSet.getString("bookname"));
				book.setPublisher(resultSet.getString("publisher"));
				book.setPrice(Integer.parseInt(resultSet.getString("price")));
				book.setDescription(resultSet.getString("description"));
				book.setImagepath(resultSet.getString("imagepath"));
				
				books.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(resultSet, pStatement, connection);
		}
		return books;
	}
	
	private static final String BOOK_INSERT_SQL = 
			"INSERT INTO book (bookid, bookname, publisher, price, description, imagepath) " +
			"VALUES (seqBook.nextval, ?, ?, ?, ?, ?)";
			
	public void insert(Book book) {
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		
		try {
			connection = getConnection();
			
			pStatement = connection.prepareStatement(BOOK_INSERT_SQL);
			
			pStatement.setString(1, book.getBookname());
			pStatement.setString(2, book.getPublisher());
			pStatement.setInt(3, book.getPrice());
			pStatement.setString(4, book.getDescription());
			pStatement.setString(5, book.getImagepath());
			
			pStatement.execute();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}

	private static final String BOOK_SELECT_BY_ID_SQL = 
			"SELECT bookid, bookname, publisher, price, description, imagepath "
			+ "FROM book "
			+ "WHERE bookid=?";
	
	public Book selectByBookId(int bookId) {

		Book book = new Book();
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(BOOK_SELECT_BY_ID_SQL);
			
			pStatement.setInt(1, bookId);
			
			resultSet = pStatement.executeQuery();
			
			while (resultSet.next()) {
								
				book.setBookid(resultSet.getInt("bookid"));
				book.setBookname(resultSet.getString("bookname"));
				book.setPublisher(resultSet.getString("publisher"));
				book.setPrice(Integer.parseInt(resultSet.getString("price")));
				book.setDescription(resultSet.getString("description"));
				book.setImagepath(resultSet.getString("imagepath"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(resultSet, pStatement, connection);
		}
		return book;
	}	
	
	private static final String BOOK_SELECT_BY_BOOKNAME_SQL = 
			"SELECT bookid, bookname, publisher, price, description, imagepath "
			+ "FROM book "
			+ "WHERE bookname LIKE ?";
	
	public List<Book> selectByBookname(String bookname) {
		
		List<Book> books = new ArrayList<Book>();
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(BOOK_SELECT_BY_BOOKNAME_SQL);						
			pStatement.setString(1, "%"+bookname.trim()+"%");
			
			resultSet = pStatement.executeQuery();
			
			while (resultSet.next()) {
				Book book = new Book();
				
				book.setBookid(resultSet.getInt("bookid"));
				book.setBookname(resultSet.getString("bookname"));
				book.setPublisher(resultSet.getString("publisher"));
				book.setPrice(Integer.parseInt(resultSet.getString("price")));
				book.setDescription(resultSet.getString("description"));
				book.setImagepath(resultSet.getString("imagepath"));
				
				books.add(book);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(resultSet, pStatement, connection);
		}
		return books;
	}

	private static final String BOOK_UPDATE_SQL = 
			"UPDATE book "+
			"SET  bookname=?, publisher=?, price=?, description=?, imagepath=? "+
			"WHERE  bookid=?";
	
	//이미지 수정된 경우
	public void update(Book book) {
		Connection connection = null;
		PreparedStatement pStatement = null;
		
		try {
			connection = getConnection();
			
			pStatement = connection.prepareStatement(BOOK_UPDATE_SQL);
			
			pStatement.setString(1, book.getBookname());
			pStatement.setString(2, book.getPublisher());
			pStatement.setInt(3, book.getPrice());
			pStatement.setString(4, book.getDescription());
			pStatement.setString(5, book.getImagepath());
			pStatement.setInt(6, book.getBookid());
			
			pStatement.execute();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(null, pStatement, connection);
		}		
		
	}
	
	private static final String BOOK_UPDATE_EX_IMAGEPATH_SQL = 
			"UPDATE book "+
			"SET  bookname=?, publisher=?, price=?, description=? "+
			"WHERE  bookid=?";
	
	//이미지 수정 안된 경우
	public void updateEXImagepath(Book book) {
		Connection connection = null;
		PreparedStatement pStatement = null;
		
		try {
			connection = getConnection();
			
			pStatement = connection.prepareStatement(BOOK_UPDATE_EX_IMAGEPATH_SQL);
			
			pStatement.setString(1, book.getBookname());
			pStatement.setString(2, book.getPublisher());
			pStatement.setInt(3, book.getPrice());
			pStatement.setString(4, book.getDescription());
			pStatement.setInt(5, book.getBookid());
			
			pStatement.execute();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(null, pStatement, connection);
		}	
		
	}

	private static final String BOOK_DELETE_BY_BOOKID_SQL = 
			"DELETE FROM book "+
			"WHERE  bookid=?";
	
	@Override
	public void deleteByBookId(int bookId) {
		Connection connection = null;
		PreparedStatement pStatement = null;
		
		try {
			connection = getConnection();
			
			pStatement = connection.prepareStatement(BOOK_DELETE_BY_BOOKID_SQL);
			
			pStatement.setInt(1, bookId);
			
			pStatement.execute();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(null, pStatement, connection);
		}					
	}
}
