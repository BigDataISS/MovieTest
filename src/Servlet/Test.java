package Servlet;

import java.util.List;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;

import Bean.MovieBean;
import Service.MovieService;
import Spark.AppConf;

//������
public class Test extends AppConf{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AppConf.initialization();
		MatrixFactorizationModel model = MatrixFactorizationModel.load(javaSparkContext.sc(), "/home/hadoop5/eclipse-workspace/MyWebApp/model");
		for(Rating temp: model.recommendProducts(1, 5)) {
			System.out.println(temp.user()+" "+temp.product());
		}
	}

}
