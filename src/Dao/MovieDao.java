package Dao;

import java.util.List;

import Bean.MovieBean;
import Bean.UserBean;
/**
 * 接口类
 * @author 宁志豪
 *
 */

public interface MovieDao {
	public List<MovieBean> getMovie();
	public List<MovieBean> getMovieByName(String name);
	public List<MovieBean> getMovieByType(String type);
	public List<MovieBean> getThreeMovie();
	public List<MovieBean> getRecommendMovie(int i);
	public MovieBean getTheMovieByName(String name);
	public void addRecommendMovie(int m,int n);
	public List<MovieBean> getThreeMovieByType(String type, int movieId);
	public void deleteRecommendMovie(int id);

}
/**
 * END
 * @author 宁志豪
 */