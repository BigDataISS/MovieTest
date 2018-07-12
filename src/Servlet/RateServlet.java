package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.MovieBean;
import Bean.UserBean;
import Service.MovieService;
import Service.RateService;
import Service.UserService;

/**
 * Start
 * 接受用户传来的评分信息
 * 从数据库查询评分并返回
 * @author 宁志豪
 */
@WebServlet(name="rateServlet",urlPatterns= {"/rateServlet"})
public class RateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String userName=(String) session.getAttribute("username");
		String moviename=request.getParameter("moviename");
		double rate=Double.parseDouble(request.getParameter("rate"));
		
		UserService userService=new UserService();
		MovieService movieService=new MovieService();
		RateService rateService=new RateService();
		
		UserBean user=userService.getUserByName(userName);
		MovieBean movie=movieService.getTheMovieByName(moviename);
		
		System.out.println(user.getUserName()+":  "+movie.getRatingNum());
		rateService.addRatingByUser(user.getUserId(), movie.getMovieId(), rate);
		session.setAttribute("israte", (new Double(rate)).intValue());
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
 * End
 * @author 宁志豪
 */