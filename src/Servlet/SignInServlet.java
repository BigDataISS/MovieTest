package Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.UserBean;
import Service.UserService;
import net.sf.json.JSONObject;

/**
 * Start
 * 	登录
 * @author 毛恺
 */
@WebServlet(name="SignInServlet",urlPatterns= {"/signInServlet"})
public class SignInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignInServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject();
		PrintWriter out = response.getWriter();
		
		//String username=request.getParameter("username");
		int uesrId = Integer.parseInt(request.getParameter("userid"));
		String password=request.getParameter("password");

		//System.out.println("username: " + username + " password: "+password);
		
		UserService userService = new UserService();
		UserBean userBean = userService.getUser(uesrId);
		
		System.out.println(userBean.getUserId());
		
		if(userBean!=null && password.equals(userBean.getPassword())) {
			jsonObject.put("error", 0);
			
			//System.out.println("username: " + username + " password: "+password);
			HttpSession session = request.getSession();
			UserBean user=userService.getUser(uesrId);
			//session.setAttribute("username", username);
			session.setAttribute("userid", user.getUserId());
		}
		else {
			jsonObject.put("error", 1);			
		}
		response.setContentType("application/json");
		out.print(jsonObject);
		return;
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
 * @author 毛恺
 */
