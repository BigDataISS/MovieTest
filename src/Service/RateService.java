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
<<<<<<< HEAD
		String sql="select * from ratingbyuser where userId=? and movieId=?";
=======
		String sql="select * from ratebyuser where userId=? and movieId=?";
>>>>>>> edcb20628bc7d340e0f4e0eb53f0513ce7e7b60e
		return get(sql,userId,movieId);		
	}
	
	@Override
	/**
	 * ����û������ֵ����ֱ�
	 */
	public void addRatingByUser(int userId,int movieId,double rate){
<<<<<<< HEAD
		String sql="insert into RatingByUser values (?,?,?)";
		update(sql,userId,movieId,rate);
=======
		String sql="insert into ratebyuser values (?,?,?)";
		update(sql,rate,userId,movieId);
>>>>>>> edcb20628bc7d340e0f4e0eb53f0513ce7e7b60e
	}
}
/**
 * END
 * @author ��־��
 */