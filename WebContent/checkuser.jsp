<%@ page contentType="text/html; charset=utf-8" import="Bean.UserBean" import="Service.UserService" language="java"%> 
<%@ page import="java.sql.*"%>
<!-- 
	START
	检查用户名和密码
	@author 毛恺 
-->
<html>
<head>
<<<<<<< Updated upstream
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link rel="icon" type="image/x-icon" href="icon/favicon.ico"/>
=======
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
>>>>>>> Stashed changes
<title>验证用户</title>
</head>

<body>
<%
<<<<<<< Updated upstream
	

	//从request获取用户输入的用户名和密码

=======
	//从request获取用户输入的用户名和密码
>>>>>>> Stashed changes
	String username=request.getParameter("username");
	String password=request.getParameter("password");
	int userid= 0;
	if(username==null) username="";
	if(password==null) password="";
	int userExisted = 0;
	
	System.out.println(username);

	try{
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
		//连接到数据库
		Class.forName("org.gjt.mm.mysql.Driver").newInstance();  
		String url ="jdbc:mysql://localhost/movie"; 
		Connection conn= DriverManager.getConnection(url,"root","Nimakengdie1"); 
		Statement stmt=conn.createStatement();
		ResultSet rs=stmt.executeQuery("select userid,username,password from user where username = '"
			+username+"'");
		//判断密码是否正确
<<<<<<< Updated upstream
		
=======
>>>>>>> Stashed changes
		while(rs!=null && rs.next()){
			if(password.equals(rs.getString("password"))){
				userExisted = 1;
				userid=rs.getInt("userid");
			}
		}
<<<<<<< Updated upstream

=======
		
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream

=======
%>
  <%
	
>>>>>>> Stashed changes
   if(userExisted == 1){
	  session.setAttribute("username",username);
	  session.setAttribute("userid",userid);
	  response.sendRedirect("index.jsp");
<<<<<<< Updated upstream
   }
   else if(session.getAttribute("username")!=null && (String)session.getAttribute("username")!=""){
	   %>
	   <script type="text/javascript" language="javascript">
	   alert("请不要直接输入url访问页面！");
	   window.document.location.href="person.jsp";
	   </script> 
	  <%
   }
   else{ 
	   %>
	   <script type="text/javascript" language="javascript">
	   alert("用户名或密码错误，请重试！");
	   window.document.location.href="signin.jsp";
	   </script> 
	  <%
=======
   }
   else{ 
		  %>
		  <script>
		  alert("用户名或密码错误！");
		  </script>
		  <%
	  response.sendRedirect("signin.jsp");
>>>>>>> Stashed changes
   }
  %>
</body>
</html>
<!-- 
	END
	@author 毛恺 
-->