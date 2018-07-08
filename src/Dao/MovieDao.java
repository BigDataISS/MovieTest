package Dao;

import java.sql.SQLException;
import java.util.List;

import Bean.MovieBean;
/**
 * �ӿ���
 * @author ��־��
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
}
