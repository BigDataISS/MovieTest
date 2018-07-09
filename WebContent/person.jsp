<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.List" import="Bean.MovieBean,Bean.UserBean" import="java.util.ArrayList" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<!-- 
	START
	功能描述：person.jsp，按关键字、类别检索电影
	@author 毛恺 
-->
<html lang="en">

<head>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Personal</title>
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
			用户点击view按钮，传输电影名，并跳转到电影的具体信息页面 	
		*/
	    $(".col-md-4 .btn").click(function() {
	    	var a=$(this).parent().next();
	    	
	    	console.log(a.html())
			$.ajax({
			    type: "POST",
			    url: "${pageContext.request.contextPath}/movieDetailServlet",
			    data: {"name":a.html()},
			    /* dataType: "json", */			   
			    /* contentType: "application/x-www-form-urlencoded; charset=utf-8", */
			    success: function(data){
			    	window.location.href="${pageContext.request.contextPath}/movie.jsp";
			    },
				error: function(data){
			    	alert("失败");
			    },
			}); 
						
		});
	    
	    /**
			用户点击个人主页按钮，刷新个人主页页面	
		*/
	    $("#signinbtn .btn").click(function() {
	    	var id="1"
	    	console.log(id)
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
});
</script>
<!--
	End
	@author 宁志豪
-->


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
						<li class="nav-item"><a class="nav-link" href="#info-edit">
								<span data-feather="shopping-cart"></span> 编辑资料
						</a></li>
						<li class="nav-item"><a class="nav-link"
							href="#browse-record"> <span data-feather="users"></span>
								浏览记录
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

	<!--jumbotron 超大屏幕，内容居中显示，两边用底层内容补齐btn btn-primary btn-lg -->
	<!--电影推荐-->
	<div class="jumbotron" id="movie-recommend">
		<div class="album py-5 bg-light">
			<center>
				<h1>电影推荐</h1>
				<!-- 所推荐电影，最多6个，每个col-md-4为一个电影 -->
				<!--
					Start
					展示电影信息
					@author 宁志豪
				-->
				<%
					List<MovieBean> movieList = (List<MovieBean>) session.getAttribute("movieList");

					if (movieList == null) {
						movieList = new ArrayList<MovieBean>();
					}
					for (MovieBean movie : movieList) {
						//System.out.println(movie.getMovieId());
				%>
				<div class="col-md-4">
					<div class="card mb-4 box-shadow">
						<img class="card-img-top" src="pics/<%=movie.getName()%>.jpg"
							alt="Card image cap" width="288" height="140">
						<div class="card-body">
							<p class="card-text">
							<% if(movie.getDescription().length()>60){
									out.print(movie.getDescription().substring(0,50)+"......");
								}
								else	out.print(movie.getDescription());
							%></p>
							<div class="d-flex justify-content-between align-items-center">
								<div class="btn-group">
									<button type="button" class="btn btn-sm btn-outline-secondary">View</button>
								</div>
								<small class="text-muted"><%=movie.getName()%></small>
							</div>
						</div>
					</div>
				</div>
				<%
					}
				%>
				<!--
					End
					@author 宁志豪
				-->


			</center>
		</div>
	</div>



	<script src="js/jquery-2.1.3.min.js"></script>
	<script src="js/bootstrap.min.js"></script>

	<script src="js/holder.min.js"></script>
	<script src="js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>

<!-- 
	END
	@author 毛恺 
-->