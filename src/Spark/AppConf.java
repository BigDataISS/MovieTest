package Spark;

import java.util.Properties;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.recommendation.Rating;
import org.apache.spark.sql.SQLContext;

	/**
	Start
	Spark配置类
	@author 张子健
	 **/
@SuppressWarnings("deprecation")
public class AppConf {
	//Spark Configuration
	protected static String localClusterUrl = "local[*]";
	protected static String clusterMasterUrl = "unset";
	protected static SparkConf sparkConf;
	protected static JavaSparkContext javaSparkContext;
	protected static SQLContext sqlContext;
	//protected static HiveContext hiveContext;
	protected static String wareHouseLocation = System.getProperty("user.dir");
	protected static JavaRDD<Rating> ratingRdd;
	
	
	//JDBC connection properties
	protected static String jdbc = 
			"jdbc:mysql://localhost:3306/movie?characterEncoding=UTF-8";
	protected static String username = "root";
	protected static String password = "Nimakengdie1";
	protected static Properties prop = new Properties();
	
	public static void initialization() {
		sparkConf = new SparkConf().setMaster(localClusterUrl).
				setAppName("MovieCommend").set("spark.sql.warehouse.dir", wareHouseLocation);
		javaSparkContext = new JavaSparkContext(sparkConf);
		javaSparkContext.sc().setLogLevel("WARN");
		sqlContext = new SQLContext(javaSparkContext);
		//hiveContext = new HiveContext(javaSparkContext);
		prop.put("driver", "com.mysql.jdbc.Driver");
		prop.put("user", username);
		prop.put("password", password);
	}
	
}

	/**
	End
	Spark配置类
	@author 张子健
	 **/
