package Dao;

import Bean.UserBean;

/**
 * Start
 * 接口类，提供对user表进行操作的方法
 * @author ��־��
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