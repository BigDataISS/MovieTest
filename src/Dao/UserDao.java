package Dao;

import Bean.UserBean;

/**
 * �ӿ���
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
 * @author ��־��
 */