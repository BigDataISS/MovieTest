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
 * Start
 * 通过界面传来的电影名称从该用户收藏表单删除该电影
 * @author 李耀鹏
 */
@WebServlet(name="cancelCollectiongServlet",urlPatterns= {"/cancelCollectiong"})
public class cancelCollectiongServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public cancelCollectiongServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String moviename=request.getParameter("name");
		
		//System.out.print(moviename);
		
		MovieService movieService=new MovieService();
		UserService userService=new UserService();
		
		MovieBean movie=movieService.getTheMovieByName(moviename);
		
		movieService.cancelCollectMovie(movie.getMovieId());			//取消对movie的收藏
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
