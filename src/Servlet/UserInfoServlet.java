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
 * Start
 * 获得从界面传来的userid并显示个人信息
 * @author 马雨昂
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
/**
 * END
 * 
 * @author 马雨昂
 */
