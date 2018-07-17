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
import Service.UserService;

/**
 * Start
 * 返回该用户收藏的电影表单的信息
 * @author 李耀鹏
 */
@WebServlet(name="collectionServlet",urlPatterns= {"/collect"})
public class collectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public collectionServlet() {
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
		List<MovieBean> movieList=movieService.getAllMovieFromCollect(user.getUserId());
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
