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
 * ����sql���ʵ�ֶ����ݿ�Ĳ���
 * @author ��־��
 *
 */

public class MovieService extends Dao<MovieBean> implements MovieDao{

	@Override
	/**
	 * �����ݿ��ȡ���е�Ӱ��Ϣ  
	 */
	public List<MovieBean> getMovie() {
		// TODO Auto-generated method stub
		String sql = "select * from movie";
		return getForList(sql);
		
	}
	
	@Override
	/**
	 * �����ݿ��ȡ���ŵ�Ӱ��Ϣ  
	 */
	public List<MovieBean> getThreeMovie() {
		// TODO Auto-generated method stub
		String sql = "select * from movie limit 3";
		return getForList(sql);
		
	} 
	
	@Override
	/**
	 * �����ݿ��ȡ�뵱ǰ��Ӱ��صĵ�Ӱ��Ϣ  
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
	 * �����ݿ��ȡ�Ƽ���Ӱ��Ϣ  
	 */
	public List<MovieBean> getRecommendMovie(int i) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println("Start Recommending!");
		Commend.commendProductsForUser(i);
		String sql = "select * from recommend join movie on recommend.movieid = movie.movieid where userId="+i;
		return getForList(sql);		
	}
	
	@Override
	/** 
	 * �����û�����Ĺؼ��ֻ�ȡ��Ӧ�ĵ�Ӱ��ģ����ѯ��
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
	 * �����ݿ�ɾ���Ƽ���Ӱ��Ϣ  
	 */
	public void deleteRecommendMovie(int id) {
		String sql="delete from recommend where userId=?";
		update(sql,id);
	}
	
	
	
	@Override
	/**
	 * ���ݵ�Ӱ���ͻ�ȡ��Ӧ�ĵ�Ӱ��Ϣ	
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
	 * ���ݵ�Ӱ���ƻ�ȡ��Ӱ
	 */
	public MovieBean getTheMovieByName(String name) {
		String sql="select * from movie where name = ?";
		return get(sql,name);
		
	}
	
	
	@Override
	/**
	 * ����������Ƽ���Ӱ��Ϣ  
	 */
	public void addRecommendMovie(int m,int n) {
		String sql="insert into recommend values (?,?)";
		update(sql,m,n);
	}
	
}
/**
 * END
 * @author ��־��
 */
 

