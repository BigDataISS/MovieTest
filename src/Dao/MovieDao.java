package Dao;

import java.sql.SQLException;
import java.util.List;

import Bean.MovieBean;
import Bean.UserBean;
/**
 * Start
 * 接口类，提供对movie相关表进行操作的方法
 * @author 宁志豪
 *
 */

public interface MovieDao {
	public List<MovieBean> getMovie();
	public List<MovieBean> getMovieByName(String name);
	public List<MovieBean> getMovieByType(String type);
	public List<MovieBean> getThreeMovie();
	public List<MovieBean> getRecommendMovie(int i) throws SQLException;
	public MovieBean getTheMovieByName(String name);
	public void addRecommendMovie(int m,int n);
	public List<MovieBean> getThreeMovieByType(String type, int movieId);
	public void deleteRecommendMovie(int id);

}
/**
 * END
 * @author 宁志豪
 */