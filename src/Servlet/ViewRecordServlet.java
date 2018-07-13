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
import Bean.UserBean;
import Service.MovieService;
import Service.RateService;
import Service.UserService;

/**
 * Servlet implementation class ViewRecord
 */
@WebServlet(name="ViewRecordServlet",urlPatterns= {"/viewRecord"})
public class ViewRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewRecordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		MovieService movieService=new MovieService();
		UserService userService=new UserService();
		
		int userID=(int) session.getAttribute("userid");
		

			UserBean user = userService.getUser(userID);
			System.out.println(userID);
			List<MovieBean> movieList=movieService.getAllMovieFromViewRecord(user.getUserId());			
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
