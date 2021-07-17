package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDAO implements DAO {
	
	//jndi로 변경해보자
	public static final String driver = "oracle.jdbc.driver.OracleDriver";
	public static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	public static final String username = "madang";
	public static final String password = "madang";

	//db 연결 커넥션 객체 리턴해 주는 메소드 
	public Connection getConnection() {

		Connection connection = null;

		try {
			
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, password);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		return connection;

	}

	//db 연결 해제 메소드
	protected void closeDBObjects(ResultSet resultSet, Statement statement, Connection connection) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (Exception e) {
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (Exception e) {
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
			}
		}
	}
}