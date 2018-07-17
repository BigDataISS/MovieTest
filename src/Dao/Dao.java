package Dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import Service.DbConnectionService;

/**
 * 模板类。总体控制与数据库的连接以及对sql语句的处理
 * 提供从数据库查询数据并赋值到实体类的方法模板
 * @author 宁志豪
 *
 * @param <T>
 */

public class Dao<T> {
	public Class<T> clazz;

	private QueryRunner queryRunner = new QueryRunner();

	/**
	 * 初始化函数
	 */
	public Dao() {
		Type superClass = getClass().getGenericSuperclass();
		if (superClass instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) superClass;
			Type[] typeArgs = parameterizedType.getActualTypeArguments();
			if (typeArgs != null && typeArgs.length > 0) {
				if (typeArgs[0] instanceof Class) {
					clazz = (Class<T>) typeArgs[0];
				}
			}
		}
	}

	/**
	 * 执行对数据库进行更新的sql语句
	 */
	public void update(String sql, Object... objects) {
		Connection connection = null;
		try {
			connection = DbConnectionService.getConnection();
			queryRunner.update(connection, sql, objects);

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			DbConnectionService.closeConnection(connection);

		}
	}

	/**
	 * 从数据库获得单行数据并赋值给单个Bean类
	 * @param sql:sql语句
	 * @param objects:sql语句中需要的数据
	 * @return
	 */
	public T get(String sql, Object... objects) {
		Connection connection = null;
		try {
			connection = DbConnectionService.getConnection();
			return queryRunner.query(connection, sql, new BeanHandler<>(clazz), objects);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			DbConnectionService.closeConnection(connection);
		}
		return null;
	}

	/**
	 * 从数据库获得多行数据并赋值给Bean类的List
	 * @param sql:sql语句
	 * @param objects:sql语句中需要的数据
	 * @return
	 */
	public List<T> getForList(String sql, Object... objects) {
		Connection connection = null;
		try {
			connection = DbConnectionService.getConnection();
			return queryRunner.query(connection, sql, new BeanListHandler<>(clazz), objects);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnectionService.closeConnection(connection);
		}
		return null;

	}
}
/**
 * END
 * @author 宁志豪
 */