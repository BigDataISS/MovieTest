<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.List" import="Bean.MovieBean,Bean.UserBean" import="java.util.ArrayList" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!--
	Start
	实现信息的传输以及界面的跳转
	@author 宁志豪
-->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript">

	/**
	* START 检测是否已登录，防止用户直接输入url访问
	* @author 毛恺
	*/
	window.onload=function(){ 
		var usid=<%= session.getAttribute("userid")%>;
		if(usid==null || usid=="")
			window.location.href='signin.jsp';
	}
	/**
	* END
	* @author 毛恺
	*/

    $(document).ready(function(){
				
		$("#change-infobutton").click(function() {
			$.ajax({
			    type: "POST",
			    url: "${pageContext.request.contextPath}/UserChangeServlet",
			    /* dataType: "json", */			   
			    /* contentType: "application/x-www-form-urlencoded; charset=utf-8", */
			    success: function(data){			    	
			    	window.location.href="${pageContext.request.contextPath}/ChangeInfo.jsp";			    	
			    },
				error: function(data){
			    	
			    },
			});   
	    });
    });
	
	function switchUser(){
		$.ajax({
		    type: "POST",
		    url: "${pageContext.request.contextPath}/switchUserServlet",
		    /* dataType: "json", */			   
		    /* contentType: "application/x-www-form-urlencoded; charset=utf-8", */
		    success: function(data){			    	
		    	window.location.href="${pageContext.request.contextPath}/person.jsp";			    	
		    },
			error: function(data){
		    	
		    },
		});   
	}
</script>

</head>
<body>
	<% UserBean user=(UserBean)session.getAttribute("CurrentUser");%>
	<!--jumbotron 超大屏幕，内容居中显示，两边用底层内容补齐btn btn-primary btn-lg -->
	<!--电影推荐-->
	<div class="jumbotron" id="movie-recommend">
		<div class="album py-5 bg-light">
			<center>
				<div class="container">
				<div class="row">
				<font size=7>个人资料</font>
				<button class="btn btn-info" onclick="switchUser()">切换用户</button>
				</div>
				</div>
				<div class="container marketing">
			<div class="row">
				<!-- left column;picture,time -->
				<div class="col-lg-4">
					<img class="card-img" src="pics/client.jpeg" alt="Generic placeholder image" width="300" height="300">
					<h2><%=user.getUserName() %> </h2>
					<p>
					<a class="btn btn-success" href="#" role="button" id="change-infobutton">修改资料 &raquo;</a>
					</p>
				</div><!-- /.col-lg-4 -->
				
				<!-- right column;details -->
				<div class="col-lg-8">
					<p>
					<font size="10">用户名:<%=user.getUserName() %>&nbsp </font><br>
					<br>
					<font size="10">性别：<%=user.getSex() %>&nbsp</font><br>
					<br>
					<font size="10">年龄：<%=user.getAge() %>&nbsp </font><br>
					<br>
					<font size="10">职业：<%=user.getProfession() %>&nbsp </font><br>
					<br>
					</p>
					<p><font size="7">简介:<%if(null == user.getDescription()){%>
					这家伙很懒，什么也没有留下...<%}%>
					<%if(null != user.getDescription()){%>
					<%=user.getDescription()%>
					<%}%>
					</font></p>
				</div><!-- /.col-lg-6 -->
				
			</div><!--end row-->
		</div><!--end marketing-->
				


			</center>
		</div>
	</div>
</body>
</html>