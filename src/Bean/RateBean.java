package Bean;

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
