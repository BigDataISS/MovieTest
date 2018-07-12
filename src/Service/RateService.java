package Service;

import Bean.RateBean;
import Dao.Dao;
import Dao.RateDao;

/**
 * Start
 * ����sql���ʵ�ֶ����ݿ�Ĳ���
 * @author ��־��
 *
 */
public class RateService extends Dao<RateBean> implements RateDao {

	@Override
	/**
	 * ��ȡ�û�������
	 */
	public RateBean getRate(int userId, int movieId) {
		// TODO Auto-generated method stub
		String sql="select * from ratebyuser2 where userId=? and movieId=?";
		return get(sql,userId,movieId);		
	}
	
	@Override
	/**
	 * ����û������ֵ����ֱ�
	 */
	public void addRatingByUser(int userId,int movieId,double rate){
		String sql="insert into ratebyuser2 values (?,?,?)";
		update(sql,userId,movieId,rate);
	}
}
/**
 * END
 * @author ��־��
 */