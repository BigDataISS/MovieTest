package Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Bean.MovieBean;
import Dao.Dao;
import Dao.MovieDao;
/**
 * Start
 * 实现从数据库获得数据的方法
 * @author 宁志豪、李耀鹏
 *
 */

public class MovieService extends Dao<MovieBean> implements MovieDao{

	@Override
	/**
	 * 从数据库获得所有电影的数据
	 * @return 存有电影数据的MovieList
	 */
	public List<MovieBean> getMovie() {
		// TODO Auto-generated method stub
		String sql = "select * from movie";
		return getForList(sql);
		
	}
	
	@Override
	/**
	 * 获得热门电影信息，即当前movie表的前九部电影，存储在List中
	 * @return 存储movie信息的List
	 */
	public List<MovieBean> getListMovie() {
		// TODO Auto-generated method stub
		String sql = "select * from movie limit 9";
		return getForList(sql);
		
	} 
	
	@Override
	/**
	 * 获得与当前电影同类型的高分电影
	 * @param type:当前电影类型
	 * @param movieId:当前电影的ID
	 * @return 存储movie信息的List
	 */
	public List<MovieBean> getListMovieByType(String type,int movieId) {
		// TODO Auto-generated method stub
		String[] types;
		//当前电影的类型可能会有多个，对它进行分割获得多个小标签
		types=type.split(" ");		
		List<MovieBean> mvbs=new ArrayList<MovieBean>();
		int hasAdd=0;
		int loopTime=0;
		
		/**
		 * 若当前电影有多个标签，则每个标签轮流取一个
		 * 直至取至九部电影
		 */
		while(hasAdd<9) {
			for(String str:types) {
				
				String sql = "select * from movie where type like '%"+str+"%' "
						+ "and movieid != "+movieId
						+ " order by ratingNum desc limit "+(loopTime+1)+" , "+(loopTime+2);
				System.out.println(sql);
				
				boolean isdiff=true;
				MovieBean mvb=get(sql);
				//
				if(mvb!=null) {
					for(MovieBean mv:mvbs) {
						if(mv.getMovieId()==mvb.getMovieId()) {
							isdiff=false;
							break;
						}
					}
				}
				if(isdiff&&mvb!=null) {
					mvbs.add(mvb);
					System.out.println(mvb.getName());
					hasAdd++;
					System.out.println(hasAdd);
					if(hasAdd==9)
						break;
				}
			}
			/**
			 * 循环多次后依然无法取得九部，
			 * 说明数据库已无数据，打破循环
			 */
			loopTime++;
			if(loopTime>9)
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
		String[] names;
		name=name.replace(",", " ");
		name=name.replace(";", " ");
		name=name.replace("'", "");
		names=name.split(" ");
		
		for(String str:names) {			
			newName+=str+"%";			
		}
		/**
		 * 设置显示顺序优先度，由电影名、导演、演员、类型、简介的相关排序
		 */
		String sql = "select * from movie where name like '"+newName+"'";
		List<MovieBean> movieList=getForList(sql);
		sql="select * from movie where Direction like '"+newName+"'";
		movieList.addAll(getForList(sql));
		sql="select * from movie where Actors like '"+newName+"'";
		movieList.addAll(getForList(sql));
		sql="select * from movie where Type like '"+newName+"'";
		movieList.addAll(getForList(sql));
		sql="select * from movie where Description like '"+newName+"'";
		movieList.addAll(getForList(sql));
		//除去重复数据
		for(int i=0;i<movieList.size();i++) {
			for(int j=movieList.size()-1;j>i;j--) {
				if(movieList.get(i).getMovieId()==movieList.get(j).getMovieId())
					movieList.remove(j);
			}
		}
		movieList=movieList.subList(0, 300);
		return movieList;		
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
	
	
	@Override
	/**
	 * 向浏览记录表中添加电影
	 */
	public void addViewRecordMovie(int userId,int movieId,String time) {
		String sql="insert into viewrecord values (?,?,?)";
		update(sql,userId,movieId,time);
	}
	
	@Override
	/**
	 * 从viewRecord表中读取用户浏览的电影列表
	 */
	public List<MovieBean> getAllMovieFromViewRecord(int userId) {
		String sql = "select * from viewrecord join movie on viewrecord.movieid = movie.movieid where userId="+userId +" ORDER BY viewtime DESC limit 9";
		//String sql = "select * from movie where movieid in (SELECT viewRecord.movieid from viewRecord where userId=" +userId + "ORDER BY viewRecord.viewtime)" ;
		return getForList(sql);	
	}
	
	
	@Override
	/**
	 * 把用户收藏的电影加入collection表中
	 */
	public void addCollectMovie(int userId,int movieId) {
		String sql="insert into collection values (?,?)";
		update(sql,userId,movieId);
	}

	@Override
	/**
	 * 从collection表中读取用户收藏的电影列表
	 */	
	public List<MovieBean> getAllMovieFromCollect(int userId) {
		String sql = "select * from collection join movie on collection.movieid = movie.movieid where userId="+userId;
		return getForList(sql);	
	}
	
	@Override
	/**
	 * 从collection表中删除指定的电影
	 */	
	public void cancelCollectMovie(int movieId) {
		String sql = "delete from collection where movieId=?";
		update(sql,movieId);
	}
}
/**
 * END
 * @author 宁志豪、李耀鹏
 */
 

