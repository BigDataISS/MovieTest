package Dao;

import Bean.RateBean;

/**
 * �ӿ���
 * @author ��־��
 *
 */
public interface RateDao {
	public RateBean getRate(int userId,int movieId);
	public void addRatingByUser(int userId, int movieId, double rate);
}
/**
 * END
 * @author ��־��
 */