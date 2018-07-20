package Service;

import Bean.UserBean;
import Dao.Dao;
import Dao.UserDao;

/**
 * Start 
 * 提供对user表操作的相关方法 
 * @author 宁志豪、马雨昂
 *
 */
public class UserService extends Dao<UserBean> implements UserDao {

	@Override
	/**
	 * 通过id获取当前用户信息
	 * @param i:用户id
	 * @return 存储用户信息的UserBean类
	 */
	public UserBean getUser(int i) {
		// TODO Auto-generated method stub
		String sql = "select * from user where UserID=?";
		return get(sql,i);
	}
	
	@Override
	/**
	 * 添加用户到user表
	 * @param user:存有用户信息的UserBean
	 */
	public void addUser(UserBean user) {
		// TODO Auto-generated method stub
		String sql = "insert into user(UserName,Sex,Age,Profession,Record,password) values (?,?,?,?,?,?)";
		update(sql, user.getUserName(), user.getSex(), user.getAge(), user.getProfession(), user.getRecord(),
				user.getPassword());

	}
	
	@Override
	/**
	 * 修改个人资料
	 * @param name:用户id，新密码，性别，年龄，职业，描述等个人信息
	 */
	public void UpdateUserWitholdname(int i, String Password, String sex, int age, String Profession, String Description) {
		String sql = "UPDATE user SET"
								+" Password='" + Password + "',"
								+" Sex='" + sex + "',"
								+" Age=" + age + ","
								+" Profession='" + Profession + "',"
								+" Description='" + Description + "' WHERE UserID=" + i + ";";
		update(sql);
		System.out.println("COMPLETE updating user information by using " + sql);
	}
	
	
	
	@Override
	/**
	 * 通过用户名称获得用户信息
	 * @param name:用户名称
	 * @return 存储用户信息的UserBean类
	 */
	public UserBean getUserByName(String name) {
		String sql="select * from user where UserName=?";
		return get(sql,name);
	}
}
/**
 * END
 * 
 * @author 宁志豪、马雨昂
 */