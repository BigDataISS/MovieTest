package Spark;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.storage.StorageLevel;

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
	private static String url = "jdbc:mysql://localhost:3306/movie?characterEncoding=UTF-8";
	private static String fromTable = "ratebyuser";
	private static String fromTable2 = "ratebyuser2";
	@SuppressWarnings("rawtypes")
	private static Function ratingMap = new Function<Row,Rating>(){
		public Rating call(Row row) {
			return new Rating((int)row.get(0),(int)row.get(1),(double)row.get(2));
		}
	};
	
	public static void initialization() {
		long startTime = System.currentTimeMillis();
		AppConf.initialization();
		long startTime2 = System.currentTimeMillis();
		System.out.println(startTime2-startTime+"ms AppConf initialization..");
    }
	
	@SuppressWarnings("unchecked")
	public static void loadRdd() {
		//ReadData from database
		Dataset<Row> rows = sqlContext.read().jdbc(url,fromTable,prop);
		long startTime2 = System.currentTimeMillis();
		//Generating RDD
		JavaRDD<Row> RatingDatas = rows.javaRDD();
		ratingRdd = RatingDatas.map(ratingMap);
		StorageLevel newLevel = new StorageLevel();
		ratingRdd.persist(newLevel);
		long startTime3 = System.currentTimeMillis();
		System.out.println(startTime3-startTime2+"ms RDD loading...");
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized void commendProductsForUser(int userID) throws SQLException{
		long startTime = System.currentTimeMillis();
		if(sqlContext == null)
			Commend.initialization();
		if(ratingRdd == null)
			loadRdd();
		Dataset<Row> rows2 = sqlContext.read().jdbc(url,fromTable2,prop).where("userId="+userID);
		JavaRDD<Row> RatingDatas2 = rows2.javaRDD();
		JavaRDD<Rating> markedMovies = RatingDatas2.map(ratingMap);
		JavaRDD<Integer> markedMovieId = markedMovies.map(
				new Function<Rating,Integer>(){

					@Override
					public Integer call(Rating r) throws Exception {
						// TODO Auto-generated method stub
						return new Integer(r.product());
					}
					
				}
				);
		List<Integer> markedMoviesList = markedMovieId.collect();
		int markedMoviesNum = (int)markedMovies.count();
		//Union two tables
		Dataset<Row> rows = sqlContext.read().jdbc(url,fromTable2,prop);
		JavaRDD<Row> RatingDatas = rows.javaRDD();
		JavaRDD<Rating> ratingRdd2 = RatingDatas.map(ratingMap);
		JavaRDD<Rating> ratings = ratingRdd2.union(ratingRdd);
		//Generating Model
		model = ALS.train(ratings.rdd(), 25, 5, 0.15);
		//Clear the recommend table where userID = userId
		MovieService ms = new MovieService();
		ms.deleteRecommendMovie(userID);
		int num = 0;
		for(Rating temp: model.recommendProducts(userID, markedMoviesNum+6)) {
			//Filt movies the client has marked
			if(!markedMoviesList.contains(temp.product())) {
				num++;
				ms.addRecommendMovie(temp.user(), temp.product());	
				System.out.println(temp.user()+"  "+temp.product());
				if(num == 6)
					break;
			}
		}
		long endTime = System.currentTimeMillis();
		//Rdd回收
		rows.unpersist();rows2.unpersist();
		RatingDatas.unpersist();RatingDatas2.unpersist();
		ratingRdd2.unpersist();markedMovies.unpersist();
		ratings.unpersist();markedMovieId.unpersist();
		System.out.println("Traning "+(endTime-startTime)+"ms consumed");
		System.out.println("Done.");
	}
	/**
	END
	Spark协同过滤ALS电影推荐算法
	@代码编辑和改错 张子健，王鑫科
	@算法分析 马雨昂
	 **/
}