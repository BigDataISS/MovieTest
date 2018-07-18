package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Bean.EchartMovie;
import Service.DbConnectionService;
import net.sf.json.JSONArray;

/**
 * Start
 * 读取数据库中的推荐信息，生成json数据
 * @author 张子健，王鑫科
 */

@WebServlet(name="ChartServlet",urlPatterns= {"/ChartServlet"})
@SuppressWarnings("serial")
public class ChartServlet extends HttpServlet{
	
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
		try {
			HttpSession session = request.getSession();
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = response.getWriter();
			ArrayList<EchartMovie> echartDatas = new ArrayList<EchartMovie>();
			Integer userid = (Integer) session.getAttribute("userid");
			Connection conn = DbConnectionService.getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select movie.Type from recommend join movie on recommend.MovieId = movie.MovieId where recommend.UserId = "
					+userid);
			Map<String,Integer> typeMap = new HashMap<String,Integer>();
			while(rs!=null && rs.next()) {
				String[] types = rs.getString("Type").split(" ");
				for(int j = 0; j < types.length; j++){
					String type = types[j];
					if(typeMap.containsKey(type))
						typeMap.put(type, (Integer)typeMap.get(type) + 1);
					else
						typeMap.put(type, 1);
				}
				System.out.print(rs.getString("Type")+" ");
			}
			System.out.println();
			
			@SuppressWarnings("rawtypes")
			Iterator iterator = typeMap.keySet().iterator();
			
			while (iterator.hasNext()) {    
	            Object key = iterator.next();    
	            echartDatas.add(new EchartMovie((String)key,(Integer)typeMap.get(key)));
	        }    
			if(echartDatas.isEmpty())
				echartDatas.add(new EchartMovie("还未评分电影，喜好不明..", 1));
			
			JSONArray json = JSONArray.fromObject(echartDatas);
			System.out.println(json.toString());
			//返回到JSP
			writer.println(json);
			writer.flush();
			//关闭输出流
			writer.close();
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

/**
 * End
 * 读取数据库中的推荐信息，生成json数据
 * @author 张子健，王鑫科
 */