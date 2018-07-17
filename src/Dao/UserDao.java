package Dao;

import Bean.UserBean;

/**
 * Start
 * 接口类，提供对user表进行操作的方法
 * @author 宁志豪
 *
 */
public interface UserDao {
	public UserBean getUser(int i);

	void addUser(UserBean user);

	UserBean getUserByName(String name);
	
	void UpdateUserWithnewname(int i, String UserName, String Password, String sex, int age, String Profession, String Description);
	
	void UpdateUserWitholdname(int i, String Password, String sex, int age, String Profession, String Description);
}

/**
 * END
 * @author 宁志豪
 */