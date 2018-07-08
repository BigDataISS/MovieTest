package Service;

import Bean.UserBean;
import Dao.Dao;
import Dao.UserDao;

public class UserService extends Dao<UserBean> implements UserDao{

	@Override
	public UserBean getUser(String i) {
		// TODO Auto-generated method stub
		String sql="select * from user where id=?";
		return get(sql,i);
	}

}
