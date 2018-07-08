<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.List" import="Bean.MovieBean" import="java.util.ArrayList" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>title</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/index.css" rel="stylesheet" type="text/css">

<link href="css/jumbotron.css" rel="stylesheet">

<script src="js/html5shiv.min.js"></script>
<script src="js/respond.min.js"></script>

<link href="css/ie10-viewport-bug-workaround.css" rel="stylesheet">
<script src="js/ie-emulation-modes-warning.js"></script>


</head>

<body>
<!--
	Start
	传递输入栏关键字信息，实现界面跳转
	@author 宁志豪
-->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
	    $(".jumbotron .navbar-form .btn").click(function() {
	    	var input=$(this).prev(".form-control");
	    	
	    	console.log(input.val())
			$.ajax({
			    type: "POST",
			    url: "${pageContext.request.contextPath}/findMovie",
			    data: {"name":input.val()},
			    /* dataType: "json", */			   
			    /* contentType: "application/x-www-form-urlencoded; charset=utf-8", */
			    success: function(data){
			    	window.location.href="${pageContext.request.contextPath}/browse.jsp";
			    },
				error: function(data){
			    	
			    },
			}); 
						
		});
	    
	    $(".marketing .col-lg-4 .btn").click(function() {
	    	var output=$(this).parent().siblings("h2");
	    	
	    	console.log(output.html())
			$.ajax({
			    type: "POST",
			    url: "${pageContext.request.contextPath}/movieDetailServlet",
			    data: {"name":output.html()},
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
	    	var id="2"
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
			<div class="form-group">
				<input type="text" placeholder="Email" class="form-control">
			</div>
			<div class="form-group">
				<input type="password" placeholder="Password" class="form-control">
			</div>
			<button type="button" class="btn btn-success" id="sign-in">Sign in</button>
		</form>
	</div>
</div>
</nav>

	


<!--jumbotron 超大屏幕，内容居中显示，两边用底层内容补齐 -->
<div class="jumbotron">
	<div class="container">
		<center>
		<h1>Hello!</h1>
		<p>
		<form class="navbar-form">
			<input type="text" placeholder="Search" class="form-control">
			<a class="btn btn-success" href="#" role="button" id="#search">Search &raquo;</a>
		</form>
		</p>
		<a href="http://www.baidu.com" class="btn btn-secondary my-2">百度</a>
		<a href="https://cn.bing.com/" class="btn btn-secondary my-2">必应</a>
		<a href="http://www.whu.edu.cn/" class="btn btn-secondary my-2">武大</a>
		</center>
	</div>
</div>
	
<!-- Carousel 幻灯片，轮转播放
    ================================================== -->
<div id="myCarousel" class="carousel slide" data-ride="carousel">
	<!-- Indicators -->
	<ol class="carousel-indicators">
		<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
		<li data-target="#myCarousel" data-slide-to="1"></li>
		<li data-target="#myCarousel" data-slide-to="2"></li>
	</ol>
	<div class="carousel-inner" role="listbox">
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
						for(MovieBean movie : movieList){
			%>
					
					<div class="col-lg-4">
						<img class="img-circle" src="pics/<%=movie.getName() %>.jpg" alt="Generic placeholder image" width="140" height="140">
						<h2 id="h2"><%=movie.getName() %></h2>
						<p><%=movie.getDescription() %></p>
						<p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
					</div><!-- /.col-lg-4 -->
				<% } %>
				<!--
					End
					@author 宁志豪
				-->
				</div><!-- /.row -->
			</div>
			
		</div>
		<div class="item">

			
		
		</div>
		<div class="item">
			<img class="third-slide" src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" alt="Third slide">
			<div class="container">
				<div class="carousel-caption">
					<h1>One more for good measure.</h1>
					<p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
					<p><a class="btn btn-lg btn-primary" href="#" role="button">Browse gallery</a></p>
				</div>
			</div>
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
