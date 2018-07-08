package Servlet;

import java.io.IOException;
import java.sql.SQLException;
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
import Service.UserService;

/**
 * Servlet implementation class MovieRecommendServlet
 */
@WebServlet(name="MovieRecommendServlet",urlPatterns= {"/movieRecommendServlet"})
public class MovieRecommendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieRecommendServlet() {
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
		String id=request.getParameter("userId");
		//String id="1";
		System.out.println("userid"+id);
		MovieService movieService=new MovieService();
		//UserService userService=new UserService();
		List<MovieBean> movieList;
		try {
			movieList = movieService.getRecommendMovie(Integer.parseInt(id));
			System.out.println("listsize"+movieList.size());
			//UserBean user=userService.getUser(id);
			
			//request.setAttribute("movieList", movieList);
			//request.setAttribute("person", user);
			
			HttpSession session = request.getSession();
			session.setAttribute("movieList", movieList);
			//session.setAttribute("person", user);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
