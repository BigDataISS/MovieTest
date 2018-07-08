<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.List" import="Bean.MovieBean,Bean.UserBean" import="java.util.ArrayList" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<% UserBean user=(UserBean)session.getAttribute("person");%>	
		<center>
		<h1>个人资料</h1>
		<div class="container marketing">
			<div class="row">
				<!-- left column;picture,time -->
				<div class="col-lg-4">
					<img class="card-img" src="pics/girl.jpg" alt="Generic placeholder image" width="140" height="200">
					<h2><%=user.getUserName() %></h2>
					<p>
					<a class="btn btn-success" href="#" role="button">修改资料 &raquo;</a>
					</p>
				</div><!-- /.col-lg-4 -->
				
				<!-- right column;details -->
				<div class="col-lg-8">
					<p>
					<font size="3">用户名：<%=user.getUserName() %>&nbsp </font>
					<br>
					<font size="3">性别：<%=user.getSex() %>&nbsp </font>
					<br>
					<font size="3">年龄：<%=user.getAge() %>&nbsp </font>
					<br>
					<font size="3">邮箱：qwer777@123.com&nbsp </font>
					<br>
					<font size="3">喜欢电影类型：动作、科幻&nbsp </font>
					</p>
					<p><span id="description">简介：Donec sed odio dui. Etiam porta sem malesuada magna mollis euismod. Nullam id dolor id nibh ultricies vehicula ut id elit. Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Praesent commodo cursus magna.</span></p>
				</div><!-- /.col-lg-6 -->;
				
			</div><!--end row-->
		</div><!--end marketing-->
		</center>

</body>
</html>