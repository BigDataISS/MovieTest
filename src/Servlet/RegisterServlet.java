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
 * 接收用户注册的信息并添加到数据库
 * @author ��־��
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
		String password=request.getParameter("password");
		
		PrintWriter out = response.getWriter();
		UserService userService=new UserService();
		UserBean checkuser=userService.getUserByName(name);
		String regex="^[A-Za-z0-9\\-]+$";
		
		/**
		 * 如果用户名已存在，设置error=1
		 */
		if(checkuser!=null) {
			jsonObject.put("error", "1");
			response.setContentType("application/json");
			out.print(jsonObject);
			return;
		}
		/**
		 * 如果用户年龄输入不是整数则设置error=2
		 */
		else if(!StringUtils.isNumeric(age)||age.length()>3) {
			jsonObject.put("error", "2");
			response.setContentType("application/json");
			out.print(jsonObject);
			return;
		}
		/**
		 * 用户名或密码只能是数字、字母、"-"
		 */
		else if(!name.matches(regex)) {
			jsonObject.put("error", "3");
			response.setContentType("application/json");
			out.print(jsonObject);
			return;
		}
		else if(!password.matches(regex)) {
			jsonObject.put("error", "4");
			response.setContentType("application/json");
			out.print(jsonObject);
			return;
		}
		/**
		 * 若一切正常，则error为空
		 */
		else {
			UserBean user=new UserBean();
			user.setUserName(name);	
			user.setPassword(password);
			user.setSex(request.getParameter("sex"));
			//若输入的age为空字符串，则默认设置age为0
			if(age.length()==0)
				user.setAge(0);
			else
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
/**
 * End
 * @author 宁志豪
 */