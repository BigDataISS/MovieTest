package Spark;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.DoubleFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;
import scala.Tuple2;

	/**
	Start
	寻找最佳参数，训练最佳模型(均方差最小)
	@author 张子健
	**/
	
public class ModelTraining extends AppConf {
	@SuppressWarnings({"unchecked","rawtypes", "serial"})
	public static void main(String[] args){
		if(javaSparkContext == null)
			AppConf.initialization();
		//line=>(timestamp,rating)  用时间戳来区分训练数据集和测试数据集
		Function function_timestamp = new Function<String,Tuple2<Long,Rating>>(){

			@Override
			public Tuple2<Long, Rating> call(String s) throws Exception {
				String[] element = s.split("::");
				Rating r = new Rating(Integer.parseInt(element[0]),Integer.parseInt(element[1]),
						Double.parseDouble(element[2]));
				return new Tuple2(Long.parseLong(element[3])%10,r);
			}
			
		};
		
		//Tuple=>userID
		Function function_user = new Function<Tuple2<Long,Rating>,Integer>(){

			@Override
			public Integer call(Tuple2<Long, Rating> tuple) throws Exception {
				// TODO Auto-generated method stub
				return tuple._2.user();
			}

		};
		
		//Tuple=>movieID
		Function function_movie = new Function<Tuple2<Long,Rating>,Integer>(){

			@Override
			public Integer call(Tuple2<Long, Rating> tuple) throws Exception {
				// TODO Auto-generated method stub
				return tuple._2.product();
			}

		};
		
		//Tuple2<Long,Rating> => Tuple2<Long,Rating>
		Function function_train = new Function<Tuple2<Long,Rating>,Boolean>(){

			@Override
			public Boolean call(Tuple2<Long, Rating> tuple) throws Exception {
				// TODO Auto-generated method stub
				return tuple._1 < 6;
			}
			
		};
		
		//Tuple2<Long,Rating> => Tuple2<Long,Rating>
		Function function_validation = new Function<Tuple2<Long,Rating>,Boolean>(){

			@Override
			public Boolean call(Tuple2<Long, Rating> tuple) throws Exception {
				// TODO Auto-generated method stub
				return tuple._1 >= 6 && tuple._1 < 8;
			}
				
		};
		
		//Tuple2<Long,Rating> => Tuple2<Long,Rating>
		Function function_test = new Function<Tuple2<Long,Rating>,Boolean>(){

			@Override
			public Boolean call(Tuple2<Long, Rating> tuple) throws Exception {
				// TODO Auto-generated method stub
				return tuple._1 >= 8;
			}
			
	    };
	    
	  //Tuple2<Long,Rating> => Rating
	    Function mapToRating = new Function<Tuple2<Long,Rating>,Rating>(){

			@Override
			public Rating call(Tuple2<Long, Rating> tuple) throws Exception {
				// TODO Auto-generated method stub
				return tuple._2;
			}
	    	
	    };
		
		
		//读取评分数据 line=>(timestamp,rating)  用时间戳来区分训练数据集和测试数据集
		JavaRDD<Tuple2<Long,Rating>> ratings = 
				javaSparkContext.textFile("file:///home/hadoop5/eclipse-workspace/MyWebApp/data/ratings.dat")
				.map(function_timestamp);
		
		//获取基本信息并且输出
		Long numRatings = ratings.count();
		//Tuple=>userID
		Long users = ratings.map(function_user).distinct().count();
		//Tuple=>productID
		Long movies = ratings.map(function_movie).distinct().count();
		System.out.println("We got "+numRatings+" ratings from "+users+" users on "+movies+" movies.");
		
		//训练集:验证集：测试集=6：2：2 Tuple2<Long,Rating> =>(filting) Tuple2<Long,Rating> => Rating
		JavaRDD<Rating> training = ratings.filter(function_train).map(mapToRating);
		JavaRDD<Rating> validation = ratings.filter(function_validation).map(mapToRating);
		JavaRDD<Rating> test = ratings.filter(function_test).map(mapToRating);
		
		Long trainingNum = training.count();
		Long validationNum = validation.count();
		Long testNum = test.count();
		System.out.println("Training:"+trainingNum+" Validation:"+validationNum+" Test:"+testNum);
		
		int[] ranks = {25}; //{15,25,35};
		double[] lambdas = {0.15};//{0.05,0.1,0.15,0.2};
		int[] iterations = {10};//{5,10,20};
		
		Double bestRmes = Double.MAX_VALUE;
		int bestRank = 1;
		double bestLambda = 0.0;
		int bestIteration = 0;
		MatrixFactorizationModel bestModel = ALS.train(training.rdd(), bestRank, bestIteration, bestLambda);
		
		//训练多个模型，寻找最佳参数
		int time = 0;
		System.out.println("Start seeking for best params");
		for(int r: ranks) {
			for(double l: lambdas) {
				for(int i: iterations) {
					MatrixFactorizationModel model = ALS.train(training.rdd(), r, i, l);
					Double Rmes = computeRmse(model,validation);
					if(Rmes < bestRmes) {
						bestModel = model;
						bestRank = r;
						bestLambda = l;
						bestIteration = i;
						bestRmes = Rmes;
					}
					System.out.println(++time+"  "+Rmes+" ranks:"+r+" lambdas:"+l+" iterations:"+i);
				}
			}
		}
		
		System.out.println("The best rank lambda and iteration is "+bestRank+" "+bestLambda+" "+bestIteration);
		bestModel.save(javaSparkContext.sc(), "file:///home/hadoop5/eclipse-workspace/MyWebApp/model/");
	}
	
	@SuppressWarnings({"unchecked","rawtypes", "serial"})
	private static double computeRmse(MatrixFactorizationModel model, JavaRDD<Rating> data) {
		
		//Rating=>(userID,productID)
		PairFunction pairFunction1 = new PairFunction<Rating,Integer,Integer>(){
			
			@Override
			public Tuple2<Integer, Integer> call(Rating r) throws Exception {
				return new Tuple2(new Integer(r.user()),new Integer(r.product()));
			}
			
		};
		
		//Rating=>((userID,productID),rating)
		PairFunction pairFunction2 = new PairFunction<Rating,Tuple2<Integer,Integer>,Double>(){
			
			@Override
			public Tuple2<Tuple2<Integer, Integer>, Double> call(Rating r) throws Exception {
				// TODO Auto-generated method stub
				return new Tuple2(new Tuple2(new Integer(r.user()),new Integer(r.product()))
						,r.rating());
			}
		};
		
		//(rating,rating)=>Double
		DoubleFunction<Tuple2<Double, Double>> function = new DoubleFunction<Tuple2<Double,Double>>(){

			@Override
			public double call(Tuple2<Double, Double> pair) throws Exception {
				// TODO Auto-generated method stub
				return (pair._1-pair._2)*(pair._1-pair._2);
			}
		};
		
		//Rating=>(userID,productID)
		JavaRDD<Rating> predict = model.predict(data.mapToPair(pairFunction1));
		
		//Rating=>((userID,productID),rating
		JavaRDD<Tuple2<Double,Double>> predictionsAndRatings = 
				predict.mapToPair(pairFunction2).join(data.mapToPair(pairFunction2)).values();
		
		//(rating,rating)=>Double
		double MSE = predictionsAndRatings.mapToDouble(function).mean();
		return Math.sqrt(MSE);
	}
}

	/**
	End
	寻找最佳参数，训练最佳模型(均方差最小)
	@author 张子健
	 **/
