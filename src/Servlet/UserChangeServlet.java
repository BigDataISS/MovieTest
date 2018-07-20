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
 * 获得从界面传来的userid并在修改资料界面显示用户名
 * @author 马雨昂
 */
@WebServlet("/UserChangeServlet")
public class UserChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int CurrentID = (int) session.getAttribute("userid");
		UserService userservice = new UserService();
		UserBean ReadyToChangeUser = userservice.getUser(CurrentID);
		if(ReadyToChangeUser == null) {
			System.out.println("UserChangeServlet ERROR!");
		}else {
			System.out.println("ReadyToChangeUser id  is "+ ReadyToChangeUser.getUserId());
		}
		session.setAttribute("ReadyToChangeUser", ReadyToChangeUser);
	}

}
/**
 * END
 * 
 * @author 马雨昂
 */
