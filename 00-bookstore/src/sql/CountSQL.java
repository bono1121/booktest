package sql;

public class CountSQL {
	
	public static final String BOOK_SELECT_ALL_COUNT_SQL = 
			"SELECT COUNT(*) AS cnt FROM book";

//파라미터에 관련된 것 까지 처리하는 게 불편
/*	public static final String BOOKCOMMENT_SELECT_BY_BOOKID_SQL = 
			"SELECT COUNT(*) AS cnt FROM bookcomment WHERE bookid=?";*/

}
