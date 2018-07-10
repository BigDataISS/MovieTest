<%@ page contentType="text/html; charset=gb2312" language="java"%> 
<%@ page import="java.sql.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>用户身份验证</title>
</head>

<body>
<%
	//从signin页面传入的用户名和密码
	String username=request.getParameter("username");
	String password=request.getParameter("password");
	if(username==null) username="";
	if(password==null) password="";
	int userExisted = 0;

	try{
		//装载驱动程序
		Class.forName("org.gjt.mm.mysql.Driver").newInstance();
		//连接字符串	  
		String url ="jdbc:mysql://192.168.154.89/movie"; 
		//建立连接
		Connection conn= DriverManager.getConnection(url,"CSuser","123456"); 
		//建立Statement
		Statement stmt=conn.createStatement();
		//执行查询建立ResultSet
		ResultSet rs=stmt.executeQuery("select username,password from user where username = '"
			+username+"'");
		//判断用户是否存在以及密码是否正确
		while(rs!=null && rs.next()){
			if(password.equals(rs.getString("password"))){
				userExisted = 1;
			}
		}
		//关闭连接、释放资源
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
%>
  <%

   if(userExisted == 1){//验证用户信息
	  session.setAttribute("username",username);
	  response.sendRedirect("person.jsp");//进入欢迎页面
   }
   else{
	  response.sendRedirect("signin.jsp");//进入登陆页面
   }
  %>
</body>
</html>