<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.List" import="Bean.MovieBean,Bean.UserBean" import="java.util.ArrayList" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>PersonInfo</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/person.css" rel="stylesheet" type="text/css">


<link href="css/jumbotron.css" rel="stylesheet">



<script src="js/html5shiv.min.js"></script>
<script src="js/respond.min.js"></script>

<link href="css/ie10-viewport-bug-workaround.css" rel="stylesheet">
<script src="js/ie-emulation-modes-warning.js"></script>


<!--
	Start
	实现信息的传输以及界面的跳转
	@author 宁志豪
-->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript">

    $(document).ready(function(){
    	/**
			用户点击home按钮，返回主页
		*/
	    $(".collapse .active").click(function() {
	    	output=$(this).parent().next();
	    	
			$.ajax({
			    type: "POST",
			    url: "${pageContext.request.contextPath}/MovieServlet",			    
			    /* dataType: "json", */			   
			    /* contentType: "application/x-www-form-urlencoded; charset=utf-8", */
			    success: function(data){
			    	window.location.href="${pageContext.request.contextPath}/index.jsp";		    	
			    },
				error: function(data){
			    	alert("失败");
			    },
			});
						
		});
	    
	    
	    /**
			用户点击search按钮,跳转到搜索界面  	
		*/
		$("#search").click(function() {
			$.ajax({
	    		type: "POST",
	   	 		url: "${pageContext.request.contextPath}/findMovie",
	    		data: {"name":""},
	    		/* dataType: "json", */			   
	    		/* contentType: "application/x-www-form-urlencoded; charset=utf-8", */
	    		success: function(data){
	    			window.location.href="${pageContext.request.contextPath}/browse.jsp";
	    		},
				error: function(data){
	    	
	    		},
			}); 
				
		});
	    
	    /**
			用户点击个人主页按钮，刷新个人主页页面	
		*/
	    $("#signinbtn .btn").click(function() {
	    	
			$.ajax({
			    type: "POST",
			    url: "${pageContext.request.contextPath}/movieRecommendServlet",
			    data: {"userId":id},
			    /* dataType: "json", */			   
			    /* contentType: "application/x-www-form-urlencoded; charset=utf-8", */
			    success: function(data){			    	
			    	window.location.href="${pageContext.request.contextPath}/person.jsp";			    	
			    },
				error: function(data){
			    	
			    },
			}); 
						
		});
	    
		$("#change-info").click(function() {
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
		
		/**
		用户点击浏览记录，	跳转到显示用户浏览记录界面
	*/
    
	$("#view-record").click(function() {
		$.ajax({
    		type: "POST",
   	 		url: "${pageContext.request.contextPath}/viewRecord",
    		data: {"name":""},
    		/* dataType: "json", */			   
    		/* contentType: "application/x-www-form-urlencoded; charset=utf-8", */
    		success: function(data){
    			window.location.href="${pageContext.request.contextPath}/viewRecord.jsp";
    		},
			error: function(data){
    	
    		},
		}); 
			
	});
		
		
		
	/**
	用户点击我的收藏，跳转到显示用户收藏电电影界面
*/

$("#collect").click(function() {
	$.ajax({
		type: "POST",
	 		url: "${pageContext.request.contextPath}/collect",
		data: {"name":""},
		/* dataType: "json", */			   
		/* contentType: "application/x-www-form-urlencoded; charset=utf-8", */
		success: function(data){
			window.location.href="${pageContext.request.contextPath}/collection.jsp";
		},
		error: function(data){
	
		},
	}); 
		
});
		
    });
</script>

</head>
<body>

<!-- navbar 顶部悬浮导航栏 -->
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">MovieBar</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Home</a></li>
					<li><a href="#about">About</a></li>
					<li><a href="#contact">Contact</a></li>
				</ul>

				<form class="navbar-form navbar-right" id="signinbtn">
					<button type="button" class="btn btn-success" id="sign-in">个人中心</button>
				</form>
			</div>
		</div>
	</nav>


<!-- 左侧导航栏 -->
	<div class="container-fluid">
		<div class="row">
			<nav class="col-md-2 d-none d-md-block bg-light sidebar">
				<div class="sidebar-sticky">
					<ul class="nav flex-column">
						<li class="nav-item"><a class="nav-link"
							href="#movie-recommend"> <span data-feather="home"></span>
								电影推荐 <span class="sr-only">(current)</span>
						</a></li>
						<li class="nav-item"><a class="nav-link" href="#"
							id="personal-info"> <span data-feather="file"></span> 个人资料
						</a></li>
						<li class="nav-item"><a class="nav-link" href="#info-edit" id="change-info">
								<span data-feather="shopping-cart"></span> 编辑资料
						</a></li>
						<li class="nav-item"><a class="nav-link"
							href="#browse-record" id="view-record"> <span data-feather="users"></span>
								浏览记录
						</a></li>
						<li class="nav-item"><a class="nav-link"
							href="#browse-record" id="collect"> <span data-feather="users"></span>
								我的收藏
						</a></li> 
					</ul>

					<h6
						class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
						<span>Saved reports</span> <a
							class="d-flex align-items-center text-muted" href="#"> <span
							data-feather="plus-circle"></span>
						</a>
					</h6>
					<ul class="nav flex-column mb-2">
						<li class="nav-item"><a class="nav-link"
							href="#contact-service"> <span data-feather="file-text"></span>
								联系客服
						</a></li>
					</ul>
				</div>


			</nav>
		</div>
		<!--end row-->
	</div>
	<!--end container-->
	
	<% UserBean user=(UserBean)session.getAttribute("CurrentUser");%>
	<!--jumbotron 超大屏幕，内容居中显示，两边用底层内容补齐btn btn-primary btn-lg -->
	<!--电影推荐-->
	<div class="jumbotron" id="movie-recommend">
		<div class="album py-5 bg-light">
			<center>
				<h1>个人资料</h1>
				<div class="container marketing">
			<div class="row">
				<!-- left column;picture,time -->
				<div class="col-lg-4">
					<img class="card-img" src="pics/girl.jpg" alt="Generic placeholder image" width="140" height="200">
					<h2><%=user.getUserName() %> </h2>
					<p>
					<a class="btn btn-success" href="#" role="button" id="change-infobutton">修改资料 &raquo;</a>
					</p>
				</div><!-- /.col-lg-4 -->
				
				<!-- right column;details -->
				<div class="col-lg-8">
					<p>
					<font size="3">用户名:<%=user.getUserName() %>&nbsp </font>
					<br>
					<font size="3">性别：<%=user.getSex() %>&nbsp</font>
					<br>
					<font size="3">年龄：<%=user.getAge() %>&nbsp </font>
					<br>
					<font size="3">职业：<%=user.getProfession() %>&nbsp </font>
					<br>
					</p>
					<p><span id="description">简介:<%=user.getDescription() %></span></p>
				</div><!-- /.col-lg-6 -->
				
			</div><!--end row-->
		</div><!--end marketing-->
				


			</center>
		</div>
	</div>

	<script src="js/jquery-2.1.3.min.js"></script>
	<script src="js/bootstrap.min.js"></script>

	<script src="js/holder.min.js"></script>
	<script src="js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>