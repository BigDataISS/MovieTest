<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.List" import="Bean.MovieBean" import="java.util.ArrayList" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<!-- 
	START
	功能描述：movie.html, 查看单个电影具体信息
	Created by —— 毛恺 
-->
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Movie</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/movie.css" rel="stylesheet" type="text/css">

<link href="css/jumbotron.css" rel="stylesheet">

<script src="js/html5shiv.min.js"></script>
<script src="js/respond.min.js"></script>

<link href="css/ie10-viewport-bug-workaround.css" rel="stylesheet">
<script src="js/ie-emulation-modes-warning.js"></script>

<!--
	Start
	传递输入栏关键字信息，实现界面跳转
	@author 宁志豪
-->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
	    $(".collapse .active").click(function() {
	    	var output=$(this).parent().next();
	    	
	    	console.log(output.html())
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
	    
	    $(".item  .btn").click(function() {
			var a=$(this).parent().siblings("h2");
	    	
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
			    	
			    },
			}); 
						
		});
	    
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

</head>

<body>

<!-- navbar 悬浮导航栏 -->
<nav class="navbar navbar-inverse navbar-fixed-top">
<div class="container">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle collapsed"
		data-toggle="collapse" data-target="#navbar"
		aria-expanded="false" aria-controls="navbar">
			<span class="sr-only">Toggle navigation</span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
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

<%MovieBean movie=(MovieBean)session.getAttribute("movie"); %>

<!--jumbotron 超大屏幕，内容居中显示，两边用底层内容补齐btn btn-primary btn-lg -->
<div class="jumbotron">
	<div class="container">
		<div class="container marketing">
			<!-- Example row of columns -->
			<div class="row">
				<!-- Three columns of text below the carousel -->
				<div class="col-lg-4">
					<img class="card-img" src="pics/<%=movie.getName() %>.jpg" alt="Generic placeholder image" width="140" height="200">
					<h2><%=movie.getName() %></h2>
					<p><span><%=movie.getCountry() %></span>
					<br><span><%=movie.getReleaseDate() %></span>
					</p>
				</div><!-- /.col-lg-4 -->
				<div class="col-lg-8">
					<h2>评分：<%=movie.getRatingNum() %></h2>
					<p>
					<font size="3">导演：<%=movie.getDirection() %>&nbsp </font>
					<font size="3">语言：<%=movie.getLanguage() %>&nbsp </font>
					<font size="3">类型：<%=movie.getType() %>&nbsp </font>
					<br>
					<font size="3">时长：<%=movie.getRuntime() %>&nbsp </font>
					<font size="3">主演：<%=movie.getActors() %>&nbsp </font>
					</p>
					<p><span id="description">简介：<%=movie.getDescription() %></span></p>
				</div><!-- /.col-lg-6 -->
			</div>
		</div>
	</div>
</div>
	
<!-- Carousel 幻灯片，轮转播放，推荐电影
    ================================================== -->
<div id="myCarousel" class="carousel slide" data-ride="carousel">
	<!-- Indicators -->
	<ol class="carousel-indicators">
		<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
		<li data-target="#myCarousel" data-slide-to="1"></li>
		<li data-target="#myCarousel" data-slide-to="2"></li>
	</ol>
	<div class="carousel-inner" role="listbox">
		<!-- 幻灯片第一页 -->
		<div class="item active">

			<div class="container marketing">
				<!-- Example row of columns -->
				<div class="row">
					<!-- Three columns of text below the carousel -->
					<!--
						Start
						展示电影信息
						@author 宁志豪
					-->
					<% List<MovieBean> movieList = (List<MovieBean>)session.getAttribute("movieList"); 
						if(movieList == null){
							movieList = new ArrayList<MovieBean>();
						}
						for(MovieBean mv : movieList){
					%>
					
					<div class="col-lg-4">
						<img class="img-circle" src="pics/<%=mv.getName() %>.jpg" alt="Generic placeholder image" width="140" height="140">
						<h2 id="h2"><%=mv.getName() %></h2>
						<p><% if(mv.getDescription().length()>60){
							out.print(mv.getDescription().substring(0,50)+"......");
							}else	out.print(mv.getDescription());
							%></p>
						<p><a class="btn btn-default" href="#" role="button" >View details &raquo;</a></p>
					</div><!-- /.col-lg-4 -->
				<% } %>
				<!--
					End
					@author 宁志豪
				-->
				</div><!-- /.row -->
			</div>
		
		</div>
		
		

	<a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
		<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
		<span class="sr-only">Previous</span>
	</a>
	<a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
		<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
		<span class="sr-only">Next</span>
	</a>
</div><!-- /.carousel -->




<!-- 底部文字 -->
<div class="container">
	<!-- FOOTER -->
	<footer>
		<p class="pull-right"><a href="#">Back to top</a></p>
		<p>&copy; 2018 Company, Inc. &middot; <a href="#">Privacy</a> &middot; <a href="#">Terms</a></p>
	</footer>
</div>


<script src="js/jquery-2.1.3.min.js"></script>
<script src="js/bootstrap.min.js"></script>

<script src="js/holder.min.js"></script>
<script src="js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>

<!-- 
	END
	Created by —— 毛恺 
-->