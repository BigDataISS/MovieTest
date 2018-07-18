package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.MovieBean;
import Service.MovieService;
import net.sf.json.JSONObject;
/**
 * Start
 * 通过界面传来的电影名称判断该用户是否收藏过该电影，如果未收藏则收藏，已收藏会在jsp中给出提示
 * @author 李耀鹏
 */
@WebServlet(name="collectDetailServlet",urlPatterns= {"/collectDetail"})
public class collectDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public collectDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	HttpSession session = request.getSession();
		String moviename=request.getParameter("moviename");
		
		MovieService movieService=new MovieService();
		
		MovieBean movie=movieService.getTheMovieByName(moviename);
		int userID=(int) session.getAttribute("userid");
		
		List<MovieBean> MovieList = movieService.getAllMovieFromCollect(userID);
		List<String> MovieNames = new ArrayList<String>();
		
		for(int i = 0;i < MovieList.size();i++) {
			MovieNames.add(MovieList.get(i).getName());
		}
		
		JSONObject jsonObject = new JSONObject();
		PrintWriter out = response.getWriter();
		
		if(!MovieNames.contains(movie.getName())) {			//如果用户为收藏该电影则插入数据库
			movieService.addCollectMovie(userID, movie.getMovieId());			//���û�id�Լ��û��ղص�id�������ݿ���
			jsonObject.put("isCollect", "0");
			response.setContentType("application/json");
			out.print(jsonObject);
			out.flush();
			out.close();
		}
		
		else {
			
			jsonObject.put("isCollect", "1");
			response.setContentType("application/json");
			out.print(jsonObject);
			out.flush();
			out.close();	
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
