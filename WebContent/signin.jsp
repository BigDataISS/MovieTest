<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- 
	START
	功能描述：signin.jsp，主页，按关键字、类别检索电影
	@author 毛恺 
-->
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	<link rel="icon" type="image/x-icon" href="icon/favicon.ico"/>
	<title>登录</title>

	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/signin.css" rel="stylesheet">
</head>

<body class="text-center">
	<form class="form-signin" action="checkuser.jsp" method="post">
		<label>
			<% 
				if(!session.isNew()){
					String name=(String)session.getAttribute("username");
					
					if(name==null) name="";
				}
				out.println("<span>Session ID:"+session.getId()+"</span>");//输出会话编号
			%>
		</label>
		<img class="mb-4" src="https://getbootstrap.com/docs/4.1/assets/brand/bootstrap-solid.svg" alt="" width="72" height="72"/>
		<h1 class="h3 mb-3 font-weight-normal">请登录</h1>
		<label for="inputName" class="sr-only">User Name</label>
		<input type="text" name="username" class="form-control" placeholder="用户名" required autofocus></input>
		<br>
		<label for="inputPassword" class="sr-only">Password</label>
		<input type="password" name="password"id="inputPassword" class="form-control" placeholder="密码" required></input>
		<div class="checkbox mb-3">
			<a href="signup.jsp" class="btn btn-secondary my-2">注册</a>
		</div>
		<button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
		<p class="mt-5 mb-3 text-muted">&copy; 2017-2018</p>
	</form>
</body>
</html>
<!-- 
	END
	@author 毛恺 
-->