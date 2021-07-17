package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.BaseDAO;
import dao.BookCommentDAO;
import model.comment.BookComment;
import model.page.PageRowResult;
import service.PageManager;

public class BookCommentDAOImpl extends BaseDAO implements BookCommentDAO {

	private static final String BOOKCOMMENT_SELECT_BY_BOOKID_SQL = 
			"SELECT commentid, writer, content, datetime, bookid FROM bookcomment "+
			"WHERE bookid=? "+
			"ORDER BY commentid";

	public List<BookComment> selectAll(int bookid) {
		System.out.println("selectAll bookid:"+bookid);
		List<BookComment> comments = new ArrayList<BookComment>();

		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;

		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(BOOKCOMMENT_SELECT_BY_BOOKID_SQL);
			
			pStatement.setInt(1, bookid);
			
			resultSet = pStatement.executeQuery();

			while (resultSet.next()) {
				BookComment comment = new BookComment();

				comment.setCommentid(resultSet.getInt("commentid"));
				comment.setWriter(resultSet.getString("writer"));
				comment.setContent(resultSet.getString("content"));
				comment.setDatetime(resultSet.getString("datetime"));
				comment.setBookid(resultSet.getInt("bookid"));

				comments.add(comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(resultSet, pStatement, connection);
		}
		return comments;
	}

	private static final String BOOKCOMMENT_SELECT_BY_BOOKID_PAGE_SQL = "SELECT commentid, writer, content, datetime, bookid "
			+ "FROM (SELECT ROWNUM rn, bookcomments.* FROM (SELECT * FROM bookcomment ORDER BY commentid DESC) bookcomments) "
			+ "WHERE bookid=? " + "AND rn BETWEEN ? AND ? ORDER BY commentid";

	// 페이징 처리 메소드
	public List<BookComment> selectAll(int bookid, int requestPage) {
		List<BookComment> comments = new ArrayList<BookComment>();

		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;

		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(BOOKCOMMENT_SELECT_BY_BOOKID_PAGE_SQL);

			pStatement.setInt(1, bookid);

			// 페이징 처리
			PageManager pm = new PageManager(requestPage);
			PageRowResult pageRowResult = pm.getPageRowResult();

			pStatement.setInt(2, pageRowResult.getRowStartNumber());
			pStatement.setInt(3, pageRowResult.getRowEndNumber());

			resultSet = pStatement.executeQuery();

			while (resultSet.next()) {
				BookComment comment = new BookComment();

				comment.setCommentid(resultSet.getInt("commentid"));
				comment.setWriter(resultSet.getString("writer"));
				comment.setContent(resultSet.getString("content"));
				comment.setDatetime(resultSet.getString("datetime"));
				comment.setBookid(resultSet.getInt("bookid"));

				comments.add(comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(resultSet, pStatement, connection);
		}
		return comments;
	}
	
	private static final String BOOKCOMMENT_SELECT_BY_COMMENTID_SQL = "SELECT commentid, writer, content, datetime, bookid "
			+ "FROM  bookcomment "
			+ "WHERE commentid=? ";
	@Override
	public BookComment selectByCommentid(int commentid) {
	
		BookComment comment = new BookComment();

		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;

		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(BOOKCOMMENT_SELECT_BY_COMMENTID_SQL);
			pStatement.setInt(1, commentid);
			
			resultSet = pStatement.executeQuery();

			while (resultSet.next()) {
				comment.setCommentid(resultSet.getInt("commentid"));
				comment.setWriter(resultSet.getString("writer"));
				comment.setContent(resultSet.getString("content"));
				comment.setDatetime(resultSet.getString("datetime"));
				comment.setBookid(resultSet.getInt("bookid"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(resultSet, pStatement, connection);
		}
		return comment;
	}
	

	private static final String BOOKCOMMENT_INSERT_SQL = "INSERT INTO bookcomment (commentid, writer, content, datetime, bookid) "
			+ "VALUES (seqBookComment.nextval, ?, ?, sysdate, ?)";

	@Override
	public int insert(BookComment bookComment) {
		//추가된 row의 pk값이 들어가는 변수
		int commentid = 0;

		Connection connection = null;
		PreparedStatement pStatement = null;

		try {

			connection = getConnection();
			String[] generatedId = { "commentid" };

			pStatement = connection.prepareStatement(BOOKCOMMENT_INSERT_SQL, generatedId);

			pStatement.setString(1, bookComment.getWriter());
			pStatement.setString(2, bookComment.getContent());
			pStatement.setInt(3, bookComment.getBookid());

			int insertCount = pStatement.executeUpdate();

			if (insertCount > 0) {

				ResultSet rs = pStatement.getGeneratedKeys();

				if (rs.next()) {
					commentid = rs.getInt(1);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(null, pStatement, connection);
		}

		return commentid;
	}

	private static final String BOOKCOMMENT_DELETE_BY_COMMENTID_SQL = 
			"DELETE FROM bookcomment "+
			"WHERE commentid = ?";

	public boolean deleteByCommentid(int commentid){
		boolean result = false;
		
		Connection connection = null;
		PreparedStatement pStatement = null;
		
		try {
			connection = getConnection();		
			pStatement = connection.prepareStatement(BOOKCOMMENT_DELETE_BY_COMMENTID_SQL);
			
			pStatement.setInt(1, commentid);
			
			int deleteCount = pStatement.executeUpdate();
			
			System.out.println("deleteCount="+deleteCount);
			
			if(deleteCount>0){
				result = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(null, pStatement, connection);
		}			
		return result;
	}

}
