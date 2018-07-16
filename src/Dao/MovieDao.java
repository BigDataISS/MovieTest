package Dao;

import java.sql.SQLException;
import java.util.List;

import Bean.MovieBean;
/**
 * Start
 * 鎺ュ彛绫伙紝鎻愪緵瀵筸ovie鐩稿叧琛ㄨ繘琛屾搷浣滅殑鏂规硶
 * @author 瀹佸織璞�
 *
 */

public interface MovieDao {
	public List<MovieBean> getMovie();
	public List<MovieBean> getMovieByName(String name);
	public List<MovieBean> getMovieByType(String type);
	public List<MovieBean> getListMovie();
	public List<MovieBean> getRecommendMovie(int i) throws SQLException;
	public MovieBean getTheMovieByName(String name);
	public void addRecommendMovie(int m,int n);
	public List<MovieBean> getListMovieByType(String type, int movieId);
	public void deleteRecommendMovie(int id);
	public void addViewRecordMovie(int userId,int movieId,String time);
	public List<MovieBean> getAllMovieFromViewRecord(int userId);
	public void addCollectMovie(int userId,int movieId);
	public List<MovieBean> getAllMovieFromCollect(int userId);

}
/**
 * END
 * @author 瀹佸織璞�
 */