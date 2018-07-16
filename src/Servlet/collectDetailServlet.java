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
import Service.UserService;

/**
 * Servlet implementation class collectDetailServlet
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
		
		System.out.print(moviename);
		
		MovieService movieService=new MovieService();
		UserService userService=new UserService();
		
		MovieBean movie=movieService.getTheMovieByName(moviename);
		int userID=(int) session.getAttribute("userid");
		System.out.println((String) session.getAttribute("username"));
		
		UserBean user = userService.getUser(userID);
		System.out.println("duiying :" + user.getUserId());
		movieService.addCollectMovie(user.getUserId(), movie.getMovieId());			//���û�id�Լ��û��ղص�id�������ݿ���
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
