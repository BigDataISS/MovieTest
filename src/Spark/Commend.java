package Spark;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

import Service.DbConnectionService;
import Service.MovieService;

import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.sql.*;

public class Commend implements Serializable{
	
	private static Connection conn = null;
	private static SparkConf sparkConf = null;
	private static JavaSparkContext javaSparkContext = null;
	private static SQLContext sqlContext = null;
	
	/**
	Start
	Spark协同过滤ALS电影推荐算法
	@代码编辑和改错 张子健，王鑫科
	@算法分析 马雨昂
	 **/
	public static void commendProductsForUser(int userID) throws SQLException{
		//Configuration
		if(conn == null)
			conn = DbConnectionService.getConnection();
		String wareHouseLocation = System.getProperty("user.dir");
		if(sparkConf == null)
			sparkConf = new SparkConf().setMaster("local").
				setAppName("MovieCommend").set("spark.sql.warehouse.dir", wareHouseLocation);
		if(javaSparkContext == null)
			javaSparkContext = new JavaSparkContext(sparkConf);
		if(sqlContext == null)
			sqlContext = new SQLContext(javaSparkContext);
		
		//ReadData from database
		String url = "jdbc:mysql://192.168.154.89:3306/movie?characterEncoding=UTF-8";
		String fromTable = "ratebyuser";
		Properties props = new Properties();
		props.put("user", "CSuser");
		props.put("password", "123456");
		Dataset<Row> rows = sqlContext.read().jdbc(url,fromTable,props);
		
		//Generating RDD
		JavaRDD<Row> RatingDatas = rows.javaRDD();
		JavaRDD<Rating> ratings = RatingDatas.map(new Function<Row,Rating>(){
			public Rating call(Row row) {
				return new Rating((int)row.get(1),(int)row.get(2),(double)row.get(0));
			}
		});
		
		//Generating Model
		List<Rating> ratingList = new ArrayList<Rating>();
		MatrixFactorizationModel model = ALS.train(ratings.rdd(), 50, 5, 0.01);
		
		//Update recommend table
		MovieService ms = new MovieService();
		ms.deleteRecommendMovie(userID);
		for(Rating temp: model.recommendProducts(userID, 5)) {
			ms.addRecommendMovie(temp.user(), temp.product());	
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
