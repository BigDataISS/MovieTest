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
	public UserBean getUser(String i) {
		// TODO Auto-generated method stub
		String sql = "select * from user where id=?";
		return get(sql, i);
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