package Servlet;

import java.util.ArrayList;
import java.util.List;

import Bean.MovieBean;

//”√”⁄≤‚ ‘
public class TestGetMovie {

	public static List<MovieBean> GetMovie(){
		List<MovieBean> movieList = new ArrayList<MovieBean>();
		List<String> arr=new ArrayList<String>();
		arr.add("Background");
		arr.add("girl");
		arr.add("hs");
		arr.add("nms");
		arr.add("spman2");
		arr.add("ut");
		for(int i=0;i<6;i++) {
			MovieBean movie=new MovieBean();
			movie.setName(arr.get(i));
			movie.setReleaseDate(arr.get(i));
			movie.setRatingNum((float) (1.5+i));
			movie.setType(arr.get(i));
			movie.setDescription(arr.get(i)+arr.get(5-i));
			movie.setRuntime(Integer.toString(100+10*i));
			movieList.add(movie);
		}
			
		return movieList;
		
	}
	
	public static List<MovieBean> getMovieByName(String name,List<MovieBean> movieList){
		List<MovieBean> mvList = new ArrayList<MovieBean>();
		for(MovieBean movie: movieList) {
			if(movie.getName().indexOf(name)!=-1) {
				mvList.add(movie);				
			}
		}
		if(mvList.size()<0) {
			return null;
		}
		else return mvList;
		
	}
	
}
