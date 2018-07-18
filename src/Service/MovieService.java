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
 * @author 宁志豪
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
	 * 鑾峰緱鐑棬鐢靛奖淇℃伅锛屽嵆褰撳墠movie琛ㄧ殑鍓嶄笁閮ㄧ數褰憋紝瀛樺偍鍦↙ist涓�
	 * @return 瀛樺偍movie淇℃伅鐨凩ist
	 */
	public List<MovieBean> getListMovie() {
		// TODO Auto-generated method stub
		String sql = "select * from movie limit 9";
		return getForList(sql);
		
	} 
	
	@Override
	/**
	 * 鑾峰緱涓庡綋鍓嶇數褰卞悓绫诲瀷鐨勯珮鍒嗙數褰�
	 * @param type:褰撳墠鐢靛奖绫诲瀷
	 * @param movieId:褰撳墠鐢靛奖鐨処D
	 * @return 瀛樺偍movie淇℃伅鐨凩ist
	 */
	public List<MovieBean> getListMovieByType(String type,int movieId) {
		// TODO Auto-generated method stub
		String[] types;
		types=type.split(" ");		//褰撳墠鐢靛奖鐨勭被鍨嬪彲鑳戒細鏈夊涓紝瀵瑰畠杩涜鍒嗗壊鑾峰緱澶氫釜灏忔爣绛�
		List<MovieBean> mvbs=new ArrayList<MovieBean>();
		int hasAdd=0;
		int loopTime=0;
		
		/**
		 * 鑻ュ綋鍓嶇數褰辨湁澶氫釜鏍囩锛屽垯姣忎釜鏍囩杞祦鍙栦竴涓�
		 * 鐩磋嚦鍙栧埌涓夐儴鐢靛奖
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
			 * 寰幆澶氭鍚庝緷鐒舵棤娉曞彇寰椾笁閮紝
			 * 璇存槑鏁版嵁搴撳凡鏃犳暟鎹紝鎵撶牬寰幆
			 */
			loopTime++;
			if(loopTime>9)
				break;
		}
		
		return mvbs;
	} 
	
	
	@Override
	/**
	 * 浠庢帹鑽愯〃涓幏寰楁帹鑽愮數褰�
	 * @param i:褰撳墠session鐢ㄦ埛鐨刬d
	 * @return 瀛樺偍movie淇℃伅鐨凩ist
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
	 * 鏍规嵁鐢靛奖鐨勭浉鍏充俊鎭幏寰椾竴绯诲垪鐢靛奖锛屽瓨鍌ㄥ湪List涓�
	 * @param name:鐢ㄦ埛杈撳叆鐨勫叧閿瓧
	 * @return 瀛樺偍movie淇℃伅鐨凩ist
	 */
	public List<MovieBean> getMovieByName(String name){		
		String newName="%";		
		String[] names;
		name.replace(",", " ");
		name.replace(";", " ");
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
		
		return movieList;		
	}
	
	
	@Override
	/**
	 * 鍒犻櫎鎺ㄨ崘琛ㄤ腑鐢ㄦ埛鐨勬帹鑽愪俊鎭�
	 * @param id:褰撳墠session涓殑鐢ㄦ埛id
	 */
	public void deleteRecommendMovie(int id) {
		String sql="delete from recommend where userId=?";
		update(sql,id);
	}
	
	
	
	@Override
	/**
	 * 鑾峰彇鍚岀被鍨嬬殑鎵�鏈夌數褰�
	 * @param type:鐢靛奖绫诲瀷
	 * @return 鍚岀被鍨嬬數褰辩殑List
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
	 * 閫氳繃鐢靛奖鍚嶇О鑾峰彇璇ラ儴鐢靛奖鐨勬墍鏈変俊鎭�
	 * @param name:鐢靛奖鍚嶇О
	 * @return 瀛樺偍鐢靛奖淇℃伅鐨凪ovieBean绫�
	 */
	public MovieBean getTheMovieByName(String name) {
		String sql="select * from movie where name = ?";
		return get(sql,name);
	}
	
	
	@Override
	/**
	 * 鍚戞帹鑽愯〃涓姞鍏ユ帹鑽愮殑鐢靛奖
	 * @param m:鐢ㄦ埛id
	 * @param n:鐢靛奖id
	 */
	public void addRecommendMovie(int m,int n) {
		String sql="insert into recommend values (?,?)";
		update(sql,m,n);
	}
	
	
	@Override
	/**
	 * ���û��������¼�������ݿ� 
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
 * @author 瀹佸織璞�
 */
 

