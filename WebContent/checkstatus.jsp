<%@ page contentType="text/html; charset=UTF-8" language="java"%> 
<%@ page import="java.sql.*"%>
<!-- 
	START
	检查登录状态
	@author 毛恺 
-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>check status</title>
</head>

<body>
<%
	//从session获取用户名信息
	String name = (String)session.getAttribute("username");
	//如果用户名为空，则此时为未登录状态，跳转至登录界面
	if(name == null){
		response.sendRedirect("signin.jsp");//进入登陆页面
	}
	//用户名不为空，跳转至个人中心
	else{%>
	<script type="text/javascript" language="javascript">
		alert(name);
	</script> 
	<%
		response.sendRedirect("person.jsp");
	}
%>
</body>
</html>
<!-- 
	END
	@author 毛恺 
-->