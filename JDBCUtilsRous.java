package news.tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JDBCUtilsRous {
public static Connection conn = null;
public static PreparedStatement st = null;
public static ResultSet rs = null;
	private JDBCUtilsRous() {

	}

	/**
	 * 通过jndi获取Connection对象
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		DataSource dataSource = null;
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context
					.lookup("java:comp/env/mysqlDataSource");// 获取数据库池
		} catch (NamingException e) {
			e.printStackTrace();
		}

		return dataSource.getConnection();// 返回Connection对象
	}

	/**
	 * 关闭连接数据库资源
	 * 
	 * @param conn
	 * @param st
	 * @param rs
	 */
	public static void free() {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {

				e.printStackTrace();
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException e) {

						e.printStackTrace();
					}
			}
		}
	}

	/**
	 * 进行增，删，改操作
	 * 
	 * @param sql
	 *            sql语句
	 * @param pStr
	 *            ?
	 * @return 返回int ，sql语句影响表格的行数
	 */
	public static int exeSql(String sql, Object... pStr) {
		int sta = 0;
		
		try {
			conn = JDBCUtilsRous.getConnection();
			st = conn.prepareStatement(sql);
			for (int i = 0; i < pStr.length; i++) {
				st.setObject(i + 1, pStr[i]);
			}
			sta = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			JDBCUtilsRous.free();
		}
		return sta;
	}
	public static ResultSet sqlDQL(String sql,Object...pObjects){
		
		try {
			conn = JDBCUtilsRous.getConnection();
			st = conn.prepareStatement(sql);
			for (int i = 0; i < pObjects.length; i++) {
				st.setObject(i + 1, pObjects[i]);
			}
			rs = st.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return rs;
	}
}
