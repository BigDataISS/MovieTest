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

}
/**
 * END
 * 
 * @author ��־��
 */