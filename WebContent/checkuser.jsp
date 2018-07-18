<%@ page contentType="text/html; charset=utf-8" import="Bean.UserBean" import="Service.UserService" language="java"%> 
<%@ page import="java.sql.*"%>
<!-- 
	START
	检查用户名和密码
	@author 毛恺 
-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">

<link rel="icon" type="image/x-icon" href="icon/favicon.ico"/>
<title>验证用户</title>
</head>

<body>
<%
	//验证是否已经登录，防止直接输入url访问登陆页面
	String name = (String)session.getAttribute("username");
	if(name!=null && name!="")
		%>
	   <script type="text/javascript" language="javascript">
	   alert("请不要直接输入url访问页面！");
	   window.document.location.href="person.jsp";
	   </script> 
	  <%

	//从request获取用户输入的用户名和密码

	String username=request.getParameter("username");
	String password=request.getParameter("password");
	int userid= 0;
	if(username==null) username="";
	if(password==null) password="";
	int userExisted = 0;

	try{

		//连接到数据库
		Class.forName("org.gjt.mm.mysql.Driver").newInstance();  
		String url ="jdbc:mysql://localhost/movie"; 
		Connection conn= DriverManager.getConnection(url,"root","Nimakengdie1"); 
		Statement stmt=conn.createStatement();
		ResultSet rs=stmt.executeQuery("select userid,username,password from user where username = '"
			+username+"'");
		//判断密码是否正确
		
		while(rs!=null && rs.next()){
			if(password.equals(rs.getString("password"))){
				userExisted = 1;
				userid=rs.getInt("userid");
			}
		}

		rs.close();
		stmt.close();
		conn.close();
	}catch(ClassNotFoundException cnfe){
		out.print(cnfe);
	}catch(SQLException sqle){
		out.print(sqle);
	}catch(Exception e){
		out.print(e);
	}

   if(userExisted == 1){
	  session.setAttribute("username",username);
	  session.setAttribute("userid",userid);
	  response.sendRedirect("index.jsp");
   }
   else{ 
	   %>
	   <script type="text/javascript" language="javascript">
	   alert("用户名或密码错误，请重试！");
	   window.document.location.href="signin.jsp";
	   </script> 
	  <%
   }
  %>
</body>
</html>
<!-- 
	END
	@author 毛恺 
-->