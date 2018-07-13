package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.UserBean;
import Service.UserService;

/**
 * Servlet implementation class UserInfoServlet
 */
@WebServlet("/UserInfoServlet")
public class UserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		int CurrentUserId = (int)session.getAttribute("userid");
		UserService userservice = new UserService();
		UserBean CurrentUser = userservice.getUser(CurrentUserId);
		if(CurrentUser == null) {
			System.out.println("UserInfoServlet ERROR!");
		}else {
			System.out.println("CurrentUser id  is "+ CurrentUser.getUserId());
		}
		session.setAttribute("CurrentUser", CurrentUser);		
	}

}
