package Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import Bean.UserBean;
import Service.UserService;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class HandleUserChangeServlet
 */
@WebServlet("/HandleUserChangeServlet")
public class HandleUserChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jsonObject = new JSONObject();
		String name=request.getParameter("username");
		String age=request.getParameter("age");
		PrintWriter out = response.getWriter();
		UserService userService=new UserService();
		UserBean checkuser=userService.getUserByName(name);
		
		HttpSession session = request.getSession();
		UserBean ReadyToChangeUser = (UserBean)session.getAttribute("ReadyToChangeUser");
				
		if(checkuser!=null && (checkuser.getUserId() == ReadyToChangeUser.getUserId())) {
			//即使未修改用户名依旧可以更新用户信息
			userService.UpdateUserWitholdname(ReadyToChangeUser.getUserId(), request.getParameter("password"), request.getParameter("sex"), Integer.parseInt(age), request.getParameter("profession"), request.getParameter("description"));
			jsonObject.put("error", "");
			response.setContentType("application/json");
			out.print(jsonObject);
			return;		
		}
		else if(checkuser!=null && (checkuser.getUserId() != ReadyToChangeUser.getUserId())) {
			jsonObject.put("error", "1");
			response.setContentType("application/json");
			out.print(jsonObject);
			return;
			
		}
		else if(!StringUtils.isNumeric(age)||age.length()>3) {
			jsonObject.put("error", "2");
			response.setContentType("application/json");
			out.print(jsonObject);
			return;
		}
		else {
			userService.UpdateUserWithnewname(ReadyToChangeUser.getUserId(), name, request.getParameter("password"), request.getParameter("sex"), Integer.parseInt(age), request.getParameter("profession"), request.getParameter("description"));
			jsonObject.put("error", "");
			response.setContentType("application/json");
			out.print(jsonObject);
			return;
		}	
	}

}
