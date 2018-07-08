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
 * 
 * @author 宁志豪
 *
 * @param <T>
 */


public class Dao<T> {
	public Class<T> clazz;
	
	private QueryRunner queryRunner = new QueryRunner();
	
	public Dao(){
		Type superClass = getClass().getGenericSuperclass();
		if(superClass instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType)superClass;
			Type[] typeArgs = parameterizedType.getActualTypeArguments();
			if(typeArgs != null && typeArgs.length > 0) {
				if(typeArgs[0] instanceof Class) {
					clazz = (Class<T>) typeArgs[0];
				}
			}
		}
	}
	
	
	public void update(String sql,Object... objects) {
		Connection connection = null;
		/*for(Object o : objects) {
			System.out.println(o);
		}*/
		try {
			connection = DbConnectionService.getConnection();
			//System.out.println("��ʼִ��");
			queryRunner.update(connection,sql,objects);
			//System.out.println("�Ѿ��ύ");
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			DbConnectionService.closeConnection(connection);

		}
	}

	/**
	 * 
	 * @param sql
	 * @param objects
	 * @return
	 */
	public T get(String sql,Object ... objects) {
		Connection connection = null;
		try {
			connection = DbConnectionService.getConnection();
			return queryRunner.query(connection,sql, new BeanHandler<>(clazz), objects);
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			
			DbConnectionService.closeConnection(connection);
		}
		return null;
	}
	
	/**
	 * 
	 * @param sql
	 * @param objects
	 * @return
	 */
	public List<T> getForList(String sql,Object ... objects){
		
		Connection connection = null;
		try {
			connection = DbConnectionService.getConnection();
			return queryRunner.query(connection,sql, new BeanListHandler<>(clazz), objects);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {

			DbConnectionService.closeConnection(connection);
		}
		return null;
		
	}
}
