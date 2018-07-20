package Service;

import Bean.RateBean;
import Dao.Dao;
import Dao.RateDao;

/**
 * Start
 * 提供对评分表操作的方法
 * @author 宁志豪
 *
 */
public class RateService extends Dao<RateBean> implements RateDao {

	@Override
	/**
	 * 获得用户对电影的评分
	 * @param userId:用户id
	 * @param movieId:电影id
	 * @return 存储评分信息的RateBean类
	 */
	public RateBean getRate(int userId, int movieId) {
		// TODO Auto-generated method stub
		String sql="select * from ratebyuser2 where userId=? and movieId=?";
		return get(sql,userId,movieId);		
	}
	
	@Override
	/**
	 * 添加用户评分到评分表
	 * @param userId:用户id
	 * @param movieId:电影id
	 * @param rate:评分
	 */
	public void addRatingByUser(int userId,int movieId,double rate){
		String sql="insert into ratebyuser2 values (?,?,?)";
		update(sql,userId,movieId,rate);
	}
}
/**
 * END
 * @author 宁志豪
 */