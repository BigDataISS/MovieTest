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
 * Servlet implementation class MovieDetailServlet
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
		String name=request.getParameter("name");
		//System.out.print(name);
		MovieService movieService=new MovieService();
		MovieBean movie=movieService.getTheMovieByName(name);
		List<MovieBean> movieList=movieService.getThreeMovieByType(movie.getType(),movie.getMovieId());
		
		HttpSession session = request.getSession();
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
