package Service;

import Bean.RateBean;
import Dao.Dao;
import Dao.RateDao;

/**
 * Start
 * 利用sql语句实现对数据库的操作
 * @author 宁志豪
 *
 */
public class RateService extends Dao<RateBean> implements RateDao {

	@Override
	/**
	 * 获取用户的评分
	 */
	public RateBean getRate(int userId, int movieId) {
		// TODO Auto-generated method stub
		String sql="select * from ratingbyuser where userId=? and movieId=?";
		return get(sql,userId,movieId);		
	}
	
	@Override
	/**
	 * 添加用户的评分到评分表
	 */
	public void addRatingByUser(int userId,int movieId,double rate){
		String sql="insert into RatingByUser values (?,?,?)";
		update(sql,userId,movieId,rate);
	}
}
/**
 * END
 * @author 宁志豪
 */