package Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Bean.MovieBean;
import Dao.Dao;
import Dao.MovieDao;
import Spark.Commend;
/**
 * Start
 * 通过sql实现对movie相关表的操作
 * @author 锟斤拷志锟斤拷
 *
 */

public class MovieService extends Dao<MovieBean> implements MovieDao{

	@Override
	/**
	 * 获得movie表的所有信息并存储在List中
	 * @return 存储movie信息的List
	 */
	public List<MovieBean> getMovie() {
		// TODO Auto-generated method stub
		String sql = "select * from movie";
		return getForList(sql);
		
	}
	
	@Override
	/**
	 * 获得热门电影信息，即当前movie表的前三部电影，存储在List中
	 * @return 存储movie信息的List
	 */
	public List<MovieBean> getThreeMovie() {
		// TODO Auto-generated method stub
		String sql = "select * from movie limit 3";
		return getForList(sql);
		
	} 
	
	@Override
	/**
	 * 获得与当前电影同类型的高分电影
	 * @param type:当前电影类型
	 * @param movieId:当前电影的ID
	 * @return 存储movie信息的List
	 */
	public List<MovieBean> getThreeMovieByType(String type,int movieId) {
		// TODO Auto-generated method stub
		String[] types;
		types=type.split(" ");		//当前电影的类型可能会有多个，对它进行分割获得多个小标签
		List<MovieBean> mvbs=new ArrayList<MovieBean>();
		int hasAdd=0;
		int loopTime=0;
		
		/**
		 * 若当前电影有多个标签，则每个标签轮流取一个
		 * 直至取到三部电影
		 */
		while(hasAdd<3) {
			for(String str:types) {
				
				String sql = "select * from movie where type like '%"+str+"%' "
						+ "and movieid != "+movieId
						+ " order by ratingNum desc limit "+(loopTime+1)+" , "+(loopTime+2);
				System.out.println(sql);
				
				MovieBean mvb=get(sql);
				if(mvb!=null) {
					mvbs.add(mvb);
					System.out.println(mvb.getName());
					hasAdd++;
					System.out.println(hasAdd);
					if(hasAdd==3)
						break;
				}
			}
			/**
			 * 循环多次后依然无法取得三部，
			 * 说明数据库已无数据，打破循环
			 */
			loopTime++;
			if(loopTime>3)
				break;
		}
		
		return mvbs;
	} 
	
	
	@Override
	/**
	 * 从推荐表中获得推荐电影
	 * @param i:当前session用户的id
	 * @return 存储movie信息的List
	 */
	public List<MovieBean> getRecommendMovie(int i) throws SQLException {
		// TODO Auto-generated method stub
		//System.out.println("Start Recommending!");
		//Commend.commendProductsForUser(i);
		String sql = "select * from recommend join movie on recommend.movieid = movie.movieid where userId="+i;
		return getForList(sql);		
	}
	
	@Override
	/** 
	 * 根据电影的相关信息获得一系列电影，存储在List中
	 * @param name:用户输入的关键字
	 * @return 存储movie信息的List
	 */
	public List<MovieBean> getMovieByName(String name){		
		String newName="%";		
		
		for(int i=0;i<name.length();i++) {			
			newName+=name.charAt(i)+"%";			
		}
		String sql = "select * from movie where name like '"+newName+"' or "
				+ "type like '"+newName+"' or description like '"+newName+"' or"
				+ " direction  like '"+newName+"' or actors like '"+newName+"' or scenarist like '"+newName+"'";
		
		return getForList(sql);		
	}
	
	
	@Override
	/**
	 * 删除推荐表中用户的推荐信息
	 * @param id:当前session中的用户id
	 */
	public void deleteRecommendMovie(int id) {
		String sql="delete from recommend where userId=?";
		update(sql,id);
	}
	
	
	
	@Override
	/**
	 * 获取同类型的所有电影
	 * @param type:电影类型
	 * @return 同类型电影的List
	 */
	public List<MovieBean> getMovieByType(String type){
		
		String newType="%";		
		
		for(int i=0;i<type.length();i++) {			
			newType+=type.charAt(i)+"%";			
		}
		String sql = "select * from movie where tags like '"+newType+"' or "
				+"type like '"+newType+"'";
				
		return getForList(sql);
	}
	
	@Override
	/**
	 * 通过电影名称获取该部电影的所有信息
	 * @param name:电影名称
	 * @return 存储电影信息的MovieBean类
	 */
	public MovieBean getTheMovieByName(String name) {
		String sql="select * from movie where name = ?";
		return get(sql,name);
		
	}
	
	
	@Override
	/**
	 * 向推荐表中加入推荐的电影
	 * @param m:用户id
	 * @param n:电影id
	 */
	public void addRecommendMovie(int m,int n) {
		String sql="insert into recommend values (?,?)";
		update(sql,m,n);
	}
	
}
/**
 * END
 * @author 宁志豪
 */
 

