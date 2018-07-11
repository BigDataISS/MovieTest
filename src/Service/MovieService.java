package Service;

import java.util.ArrayList;
import java.util.List;

import Bean.MovieBean;
import Dao.Dao;
import Dao.MovieDao;
/**
 * Start
 * 利用sql语句实现对数据库的操作
 * @author 宁志豪
 *
 */

public class MovieService extends Dao<MovieBean> implements MovieDao{

	@Override
	/**
	 * 从数据库获取所有电影信息  
	 */
	public List<MovieBean> getMovie() {
		// TODO Auto-generated method stub
		String sql = "select * from movie";
		return getForList(sql);
		
	}
	
	@Override
	/**
	 * 从数据库获取热门电影信息  
	 */
	public List<MovieBean> getThreeMovie() {
		// TODO Auto-generated method stub
		String sql = "select * from movie limit 3";
		return getForList(sql);
		
	} 
	
	@Override
	/**
	 * 从数据库获取与当前电影相关的电影信息  
	 */
	public List<MovieBean> getThreeMovieByType(String type,int movieId) {
		// TODO Auto-generated method stub
		String[] types;
		types=type.split(" ");
		List<MovieBean> mvbs=new ArrayList<MovieBean>();
		int hasAdd=0;
		int loopTime=0;
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
			
			loopTime++;
			if(loopTime>3)
				break;
		}
		
		return mvbs;
	} 
	
	
	@Override
	/**
	 * 从数据库获取推荐电影信息  
	 */
	public List<MovieBean> getRecommendMovie(int i) {
		// TODO Auto-generated method stub
		String sql = "select * from recommend join movie on recommend.movieid = movie.movieid where userId="+i;
		return getForList(sql);		
	}
	
	@Override
	/** 
	 * 根据用户输入的关键字获取相应的电影（模糊查询）
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
	 * 从数据库删除推荐电影信息  
	 */
	public void deleteRecommendMovie(int id) {
		String sql="delete from recommend where movieId=?";
		update(sql,id);
	}
	
	
	
	@Override
	/**
	 * 根据电影类型获取相应的电影信息	
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
	 * 根据电影名称获取电影
	 */
	public MovieBean getTheMovieByName(String name) {
		String sql="select * from movie where name = ?";
		return get(sql,name);
		
	}
	
	
	@Override
	/**
	 * 向数据添加推荐电影信息  
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
 

