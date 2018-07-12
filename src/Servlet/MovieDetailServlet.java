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
import Bean.RateBean;
import Bean.UserBean;
import Service.MovieService;
import Service.RateService;
import Service.UserService;

/**
 * Start
 * 通过界面传来的电影名称返回电影具体信息
 * @author 宁志豪
 */
@WebServlet(name="MovieDetailServlet",urlPatterns= {"/movieDetailServlet"})
public class MovieDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String name=request.getParameter("name");
		
		MovieService movieService=new MovieService();
		UserService userService=new UserService();
		RateService rateService=new RateService();
		
		MovieBean movie=movieService.getTheMovieByName(name);
		List<MovieBean> movieList=movieService.getThreeMovieByType(movie.getType(),movie.getMovieId());
		
		String userName=(String) session.getAttribute("username");
		if (userName != null) {
			UserBean user = userService.getUserByName(userName);
			RateBean rateBean = rateService.getRate(user.getUserId(), movie.getMovieId());
			
			if (rateBean != null) {
				session.setAttribute("israte", new Double(rateBean.getRatingNum()).intValue());
			} else {
				session.setAttribute("israte", 0);
			}
		}
		else {
			session.setAttribute("israte", 6);
		}
		session.setAttribute("movie", movie);
		session.setAttribute("movieList",movieList);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
/**
 * END
 * @author 宁志豪
 */

