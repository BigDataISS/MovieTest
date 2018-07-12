package Service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Start 
 * 提供对数据库连接的方法 
 * @author 宁志豪
 *
 */

public class DbConnectionService {
	private static DataSource dataSource = null;
	private static ThreadLocal<Connection> threadLocal = null;
	static {
		dataSource = new ComboPooledDataSource("mysql");
		threadLocal = new ThreadLocal<Connection>();
	}

	/**
	 *	连接到数据库
	 * @return 连接
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		Connection connection = threadLocal.get();
		if (connection == null) {
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			threadLocal.set(connection);

		}
		return connection;
	}

	/**
	 * 关闭与数据库的连接
	 * @throws SQLException
	 */
	public static void closeConnection(Connection connection) {

		try {
			if (connection != null) {
				connection.commit();
				threadLocal.remove();
				connection.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 回滚
	 * @throws SQLException
	 */
	public static void rollback(Connection connection) {
		try {
			if (connection != null) {
				connection.rollback();
			}
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
}

/**
 * END 
 * @author 宁志豪
 */
