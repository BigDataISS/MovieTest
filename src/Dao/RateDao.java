package Dao;

import Bean.RateBean;

/**
 * 接口类
 * @author 宁志豪
 *
 */
public interface RateDao {
	public RateBean getRate(int userId,int movieId);
	public void addRatingByUser(int userId, int movieId, double rate);
}
/**
 * END
 * @author 宁志豪
 */