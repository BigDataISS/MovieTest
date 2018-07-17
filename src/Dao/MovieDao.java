package Dao;

import java.sql.SQLException;
import java.util.List;

import Bean.MovieBean;
/**
 * Start
 * 接口类
 * 提供从数据库获取电影相关数据的方法
 * @author 宁志豪
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
	public void cancelCollectMovie(int movieId);

}
/**
 * END
 * @author 宁志豪
 */