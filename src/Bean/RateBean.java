package Bean;

/**
 * Start
 * 实体类，用于存储评分表的相关信息
 * @author 宁志豪
 **/
public class RateBean {
	private int UserId;
	private int MovieId;
	private double RatingNum;
	public int getUserId() {
		return UserId;
	}
	public void setUserId(int userId) {
		UserId = userId;
	}
	public int getMovieId() {
		return MovieId;
	}
	public void setMovieId(int movieId) {
		MovieId = movieId;
	}
	public double getRatingNum() {
		return RatingNum;
	}
	public void setRatingNum(double ratingNum) {
		RatingNum = ratingNum;
	}
	
}
/**
 * END
 * @author 宁志豪
 */