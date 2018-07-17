package Dao;

import Bean.RateBean;

/**
 * Start
 * 接口类，提供对评分表进行相关操作的方法
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