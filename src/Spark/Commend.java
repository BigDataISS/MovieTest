package Spark;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import Service.MovieService;

import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;
import java.io.Serializable;
import java.util.List;
import java.sql.*;

@SuppressWarnings("serial")
public class Commend extends AppConf implements Serializable{
	/**
	Start
	Spark协同过滤ALS电影推荐算法
	@代码编辑和改错 张子健，王鑫科
	@算法分析 马雨昂
	 **/
	
	private static MatrixFactorizationModel model;
	private static String url = "jdbc:mysql://192.168.154.89:3306/movie?characterEncoding=UTF-8";
	private static String fromTable = "ratebyuser";
	
	public static void initialization() {
		AppConf.initialization();
		//ReadData from database
		Dataset<Row> rows = sqlContext.read().jdbc(url,fromTable,prop);
				
		//Generating RDD
		JavaRDD<Row> RatingDatas = rows.javaRDD();
		JavaRDD<Rating> ratings = RatingDatas.map(new Function<Row,Rating>(){
			public Rating call(Row row) {
				return new Rating((int)row.get(1),(int)row.get(2),(double)row.get(0));
			}
		});
		System.out.println("Training from "+ratings.count()+" data");
		//Generating Model
		model = ALS.train(ratings.rdd(), 25, 5, 0.15);
	}
	
	public static void commendProductsForUser(int userID) throws SQLException{
		if(javaSparkContext == null)
			Commend.initialization();
		//Update recommend table
		MovieService ms = new MovieService();
		ms.deleteRecommendMovie(userID);
		Dataset<Row> rows = sqlContext.read().jdbc(url,fromTable,prop).where("userId="+userID);
		JavaRDD<Row> RatingDatas = rows.javaRDD();
		JavaRDD<Integer> markedMovies = RatingDatas.map(new Function<Row,Integer>(){
			public Integer call(Row row) {
				return new Integer((int) row.get(2));
			}
		});
		List<Integer> markedMoviesList = markedMovies.collect();
		int markedMoviesNum = (int)markedMovies.count();
		//Filt movies the client has marked
		int num = 0;
		for(Rating temp: model.recommendProducts(userID, markedMoviesNum+6)) {
			if(!markedMoviesList.contains(temp.product())) {
				num++;
				ms.addRecommendMovie(temp.user(), temp.product());	
				System.out.println(temp.user()+"  "+temp.product());
				if(num == 6)
					break;
			}
		}
		System.out.println("Done.");
	}
	/**
	END
	Spark协同过滤ALS电影推荐算法
	@代码编辑和改错 张子健，王鑫科
	@算法分析 马雨昂
	 **/
}