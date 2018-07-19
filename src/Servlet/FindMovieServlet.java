package Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.MovieBean;
import Service.MovieService;

/**
 * Start
 * 获得从界面传来的关键字信息获得相关电影信息并返回
 * @author 宁志豪
 */
@WebServlet(name="FindMovieServlet",urlPatterns= {"/findMovie"})
public class FindMovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindMovieServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name=request.getParameter("name");
		MovieService movieService = new MovieService();
		List<MovieBean> movieList=movieService.getMovieByName(name);
		HttpSession session = request.getSession();
		
		System.out.println(name);
		if(movieList.size()==0) {
			String str="很抱歉，未找到与 '"+name+"' 相关的电影";
			session.setAttribute("notfound", str);
		}
		else
			session.setAttribute("notfound","1");
		session.setAttribute("movieList", movieList);
	}

}
/**
 * END
 * 
 * @author 宁志豪
 */

