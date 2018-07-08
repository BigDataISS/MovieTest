package Service;

import java.sql.SQLException;
import java.util.List;
import org.apache.spark.api.java.function.Function;
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
	
	public List<MovieBean> getThreeMovie() {
		// TODO Auto-generated method stub
		String sql = "select * from movie limit 3";
		return getForList(sql);
		
	} 
	public void deleteRecommendMovie(int i) {
		String sql = "delete from recommend where UserId=?";
		update(sql,i);
	}
	
	
	public List<MovieBean> getRecommendMovie(int i) throws SQLException {
		// TODO Auto-generated method stub
		Commend.commendProductsForUser(i);
		String sql = "select * from recommend join movie on recommend.movieid = movie.movieid where userId="+i;
		return getForList(sql);
	}
	
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
	
	/**
	 * ���ݵ�Ӱ���ƻ�ȡ��Ӱ
	 */
	public MovieBean getTheMovieByName(String name) {
		String sql="select * from movie where name = ?";
		return get(sql,name);
		
	}
	
	public void addRecommendMovie(int m,int n) {
		String sql="insert into recommend values (?,?)";
		update(sql,m,n);
	}
	
}
/**
 * END
 * @author ��־��
 */
 

