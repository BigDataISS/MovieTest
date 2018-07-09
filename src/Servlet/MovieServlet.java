package Servlet;

import java.io.IOException;
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

/**
 * Start
 * 将电影信息发送到前端
 * @author 宁志豪
 */
@WebServlet(name="MovieServlet",urlPatterns= {"MovieServlet"})
public class MovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//MovieService movieService = new MovieService();
		MovieService movieService = new MovieService();
		List<MovieBean> movieList = movieService.getThreeMovie();
		HttpSession session = request.getSession();
		session.setAttribute("movieList", movieList);
		request.getRequestDispatcher("index.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public void init() throws ServletException {
        super.init();
       
    }

}
/**
 * End
 * @author 宁志豪
 */