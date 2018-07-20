package Servlet;

import java.io.IOException;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

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
		System.out.println(name);
		
		MovieService movieService=new MovieService();
		UserService userService=new UserService();
		RateService rateService=new RateService();
		
		MovieBean movie=movieService.getTheMovieByName(name);
		
		System.out.println(movie.getCountry());
		List<MovieBean> movieList=movieService.getListMovieByType(movie.getType(),movie.getMovieId());
		int userid;
		try {
			userid=(int) session.getAttribute("userid");
			System.out.println("zhengquy:" + userid);
		}catch(Exception e) {
			userid = 0;
		}
		
		if (userid != 0) {
			//UserBean user = userService.getUserByName(userName);
			UserBean user = userService.getUser(userid);
			RateBean rateBean = rateService.getRate(user.getUserId(), movie.getMovieId());
			
			//获取响应view按钮的当前时间并格式化(有用户登入的情况下)
			Date date=new Date();
			//创建一个格式化日期对象
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//格式化后的时间
			String time = simpleDateFormat.format(date);
			//将用户id、浏览的电影id以及浏览的时间存入数据库
			movieService.addViewRecordMovie(user.getUserId(), movie.getMovieId(),time);			
			
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

