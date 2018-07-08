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
 * 根据用户输入的关键字返回相应信息
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
		//System.out.print(name);
		List<MovieBean> movieList=movieService.getMovieByName(name);
		System.out.print(movieList.size());
		HttpSession session = request.getSession();
		session.setAttribute("movieList", movieList);
	}

}
/**
 * END
 * @author 宁志豪
 */

