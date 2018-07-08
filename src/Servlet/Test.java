package Servlet;

import java.sql.SQLException;
import java.util.List;
import Spark.Commend;
import Bean.MovieBean;
import Service.MovieService;

//������
public class Test {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		MovieService movieService = new MovieService();
		List<MovieBean> movieList = movieService.getMovie();
		System.out.println(movieList.size());
		System.out.println(movieList.get(1).getRatingNum());
		Commend.commendProductsForUser(1);
	}

}
