package Service;

import Bean.UserBean;
import Dao.Dao;
import Dao.UserDao;

/**
 * Start 提供对用户表进行的相关的操作的方法
 * 
 * @author 宁志豪
 *
 */
public class UserService extends Dao<UserBean> implements UserDao {

	@Override
	/**
	 * 从数据库获取当前用户的信息
	 */
	public UserBean getUser(String i) {
		// TODO Auto-generated method stub
		String sql = "select * from user where id=?";
		return get(sql, i);
	}

}
/**
 * END
 * 
 * @author 宁志豪
 */