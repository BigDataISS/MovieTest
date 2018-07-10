package Dao;

import Bean.UserBean;

/**
 * 接口类
 * @author 宁志豪
 *
 */
public interface UserDao {
	public UserBean getUser(String i);

	void addUser(UserBean user);

	UserBean getUserByName(String name);
}

/**
 * END
 * @author 宁志豪
 */