package Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import Bean.UserBean;
import Service.UserService;
import net.sf.json.JSONObject;

/**
 * Start
 * 注册用户信息
 * @author 宁志豪
 */
@WebServlet(name="RegisterServlet",urlPatterns= {"/registerServlet"})
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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
		JSONObject jsonObject = new JSONObject();
		String name=request.getParameter("username");
		String age=request.getParameter("age");
		PrintWriter out = response.getWriter();
		UserService userService=new UserService();
		UserBean checkuser=userService.getUserByName(name);
		if(checkuser!=null) {
			jsonObject.put("error", "1");
			response.setContentType("application/json");
			out.print(jsonObject);
			return;
		}
		else if(!StringUtils.isNumeric(age)) {
			jsonObject.put("error", "2");
			response.setContentType("application/json");
			out.print(jsonObject);
			return;
		}
		else {
			UserBean user=new UserBean();
			user.setUserName(name);	
			user.setPassword(request.getParameter("password"));
			user.setSex(request.getParameter("sex"));
			user.setAge(Integer.parseInt(age));
			user.setProfession(request.getParameter("profession"));
			user.setDescription(request.getParameter("description"));
			userService.addUser(user);
			jsonObject.put("error", "");
			response.setContentType("application/json");
			out.print(jsonObject);
			return;
		}
	}

}
