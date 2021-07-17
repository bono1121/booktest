package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.BaseDAO;
import dao.PageDAO;

public class PageDAOImpl extends BaseDAO implements PageDAO{


	public int getCount(String sql) {
		int cnt = 0;

		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;

		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			
			resultSet = pStatement.executeQuery();

			while (resultSet.next()) {
				cnt = resultSet.getInt("cnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(resultSet, pStatement, connection);
		}
		return cnt;
	}
		
}
