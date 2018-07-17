package Service;

import Bean.UserBean;
import Dao.Dao;
import Dao.UserDao;

/**
 * Start �ṩ���û�����е���صĲ����ķ���
 * 
 * @author ��־��
 *
 */
public class UserService extends Dao<UserBean> implements UserDao {

	@Override
	/**
	 * �����ݿ��ȡ��ǰ�û�����Ϣ
	 */
	public UserBean getUser(int i) {
		// TODO Auto-generated method stub
		String sql = "select * from user where UserID=?";
		return get(sql,i);
	}
	
	@Override
	/**
	 * ���û�ע�����Ϣ���뵽���ݿ�
	 */
	public void addUser(UserBean user) {
		// TODO Auto-generated method stub
		String sql = "insert into user(UserName,Sex,Age,Profession,Record,password) values (?,?,?,?,?,?)";
		update(sql, user.getUserName(), user.getSex(), user.getAge(), user.getProfession(), user.getRecord(),
				user.getPassword());

	}
	
	public void UpdateUserWithnewname(int i, String UserName, String Password, String sex, int age, String Profession, String Description) {
		String sql = "UPDATE user SET UserName='" + UserName +"',"
								+" Password='" + Password + "',"
								+" Sex='" + sex + "',"
								+" Age=" + age + ","
								+" Profession='" + Profession + "',"
								+" Description='" + Description + "' WHERE UserID=" + i + ";";
		update(sql);
		System.out.println("COMPLETE updating user information by using " + sql);
	}
	
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
	 * �����û����������ݿ����û���Ϣ
	 */
	public UserBean getUserByName(String name) {
		String sql="select * from user where UserName=?";
		return get(sql,name);
	}
}
/**
 * END
 * 
 * @author ��־��
 */