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
 * Start
 * �����û��Ƽ���Ӱ�������Ϣ
 * @author ��־��
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
		HttpSession session = request.getSession();
		String name=(String) session.getAttribute("username");
		System.out.println(name);
		MovieService movieService=new MovieService();
		UserService userService=new UserService();
		UserBean user=userService.getUserByName(name);
		List<MovieBean> movieList;
		try {
			movieList = movieService.getRecommendMovie(user.getUserId());
			System.out.println("listsize"+movieList.size());
			session.setAttribute("movieList", movieList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
/**
 * END
 * @author ��־��
 */
