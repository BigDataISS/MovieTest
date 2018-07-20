<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.List" import="Bean.MovieBean" import="java.util.ArrayList" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<!DOCTYPE html>

<!-- 
	START
	功能描述：movie.jsp, 查看单个电影具体信息
	@author 毛恺
-->
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="icon" type="image/x-icon" href="icon/favicon.ico"/>
<title>Movie</title>
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	

<link href="css/movie.css" rel="stylesheet" type="text/css">

<link href="css/jumbotron.css" rel="stylesheet">
<link href="css/star.css" rel="stylesheet">

<script src="js/html5shiv.min.js"></script>
<script src="js/respond.min.js"></script>

<link href="css/ie10-viewport-bug-workaround.css" rel="stylesheet">
<script src="js/ie-emulation-modes-warning.js"></script>

<%
	MovieBean movie=(MovieBean)session.getAttribute("movie");
	if(movie==null){
		response.sendRedirect("MovieServlet");		
	}
	else{
%>

<!--
	Start
	实现信息的传输以及界面的跳转
	@author 宁志豪
-->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
    	/**
			用户点击home按钮，返回主页
		*/
	    $(".collapse .active").click(function() {
	    	var output=$(this).parent().next();	    	
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
	    $(".item  .btn").click(function() {
			var a=$(this).parent().siblings("h2");
	    	
	    	console.log(a.attr("name"))
			$.ajax({
			    type: "POST",
			    url: "${pageContext.request.contextPath}/movieDetailServlet",
			    data: {"name":a.attr("name")},
			    /* dataType: "json", */			   
			    /* contentType: "application/x-www-form-urlencoded; charset=utf-8", */
			    success: function(data){
			    	window.location.href="${pageContext.request.contextPath}/movie.jsp";
			    	
			    },
				error: function(data){
			    	
			    },
			}); 
						
		});
	    
	    /**
			用户点击search按钮，跳转到搜索结果界面  	
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
			传输用户的评分数据
		*/
  	  	$(".stars").click(function() {
			var movie=$("#movieName").html()
  	  		
    		console.log(movie)
    		if(confirm("确定要给这电影打分吗？")){
				$.ajax({
		 		   	type: "POST",
		   	 		url: "${pageContext.request.contextPath}/rateServlet",
		    		data: {"rate":$(this).html(),"moviename":movie},
		    		/* dataType: "json", */			   
		    		/* contentType: "application/x-www-form-urlencoded; charset=utf-8", */
		    		success: function(data){
		    			window.location.href="${pageContext.request.contextPath}/movie.jsp";		    		
		   			},
					error: function(data){
		    	
		    		},
				}); 
    		}	
			});
	    
  	  
	    
});
    /**
    	collect按钮绑定的方法
    */
    
    
    function ajaxAll() {
    	var movie=$("#movieName").html()
    	
    	$.ajax({      	 
        	type: "POST",
        	url: "${pageContext.request.contextPath}/collectDetail",
        	 data: {"moviename":movie},
        	dataType: "json",
        	contentType: "application/x-www-form-urlencoded; charset=utf-8",
       	 	success: function (data) {
				if(data.isCollect==="1"){
				 	alert("该电影已收藏") 
				}//endif
				else{
					alert("收藏成功");
				}
        	}//endsuccess
    	});//endajax
}//endfunction
    
</script>
<!--
	End
	@author 宁志豪
-->

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
		<div class="navbar-brand">MovieBar</div>
	</div>
	<div id="navbar" class="collapse navbar-collapse">
		<ul class="nav navbar-nav">
			<li class="active"><a href="#">Home</a></li>
		</ul>
		
		
		<form class="navbar-form navbar-right" action="person.jsp" method="post">
			<button class="btn btn-success" type="button" id="search">search</button>
			<button class="btn btn-success" type="submit">个人中心</button>
		</form>
	</div>
</div>
</nav>



<!--jumbotron 超大屏幕，内容居中显示，两边用底层内容补齐btn btn-primary btn-lg -->
<div class="jumbotron">
	<div class="container">
		<div class="container marketing">
			<!-- Example row of columns -->
			<div class="row">
				<!-- Three columns of text below the carousel -->
				<!--
					Start
					展示电影信息
					@author 宁志豪
				-->	
				<div class="col-lg-4">
					<img class="card-img" src="pics/<%=movie.getName() %>.jpg" alt="Generic placeholder image" width="140" height="200">
					<h2 id="movieName"><%=movie.getName() %></h2>
					<p><span><%=movie.getCountry() %></span>
					<br><span><%=movie.getReleaseDate() %></span>
					</p>
				</div><!-- /.col-lg-4 -->
				<div class="col-lg-8">
						<font size=6>评分：<%=movie.getRatingNum() %></font>
						<%
							int isLogin = (int)session.getAttribute("israte");
							if(isLogin!=6){ %>
						
						<button class="btn btn-warning" id ="collect" onclick="ajaxAll()">收藏</button>
						<% 
							}
							%>
					<!--
						Start
						星星评分
						@author 毛恺
					-->	
					<div id="scoremark" class="scoremark">
						<%
							int israte=(int)session.getAttribute("israte");
							if(israte==0)
								out.print("对它评分:");
							else if(israte==6)
								out.print("评分请先登录:");
							else
								out.print("你的评分: </span><ul>");							
						%>
						
						<%	
							if(israte==0){								
						%>
						<span class="star">
							<span class="ystar" style="width:80%"></span>
							<ul>
								<li><a href="#" data-name="很差" class="one-star stars">1</a></li>
								<li><a href="#" data-name="较差" class="two-stars stars">2</a></li>
								<li><a href="#" data-name="一般" class="three-stars stars">3</a></li>
								<li><a href="#" data-name="较好" class="four-stars stars">4</a></li>
								<li><a href="#" data-name="很好" class="five-stars stars">5</a></li>
							</ul>
						</span>
						<div style="left: 0px; display: none;" class="tips"></div>
						
						<%	}
							else if(israte==6){
								out.println("<img src=\"image/star2.png\"></img>");
							}
							else{
								for(int i=0;i<israte&&i<5;i++)
									out.println("<img src=\"pics/star.png\"></img>");
							}
						%>
					</div>
					
					<!--
						End
						@author 毛恺
					-->
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
				<!--
					End
					@author 宁志豪
				-->
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
						for(int i=0;i<3;i++){
							MovieBean mv=movieList.get(i);
					%>
					
					<div class="col-lg-4">
						<img class="img-circle" src="pics/<%=mv.getName() %>.jpg" alt="Generic placeholder image" width="140" height="140">
						<h2 id="h2" name="<%= mv.getName()%>">
						<%
						if(mv.getName().length()>15)
							out.print(mv.getName().substring(0,13)+"......");
						else
							out.print(mv.getName());
						%>
						</h2>
						<p><% if(mv.getDescription().length()>60){
									out.print(mv.getDescription().substring(0,50)+"......");
								}else	out.print(mv.getDescription());
							%></p>
						<p><a class="btn btn-default" href="#" role="button" >View details &raquo;</a></p>
					</div><!-- /.col-lg-4 -->
				<% } %>

				</div><!-- /.row -->
			</div>
		</div>
		
		<!-- 幻灯片第二页 -->
		<div class="item">
		<div class="container marketing">
		<div class="row">
				<%
					for (int i = 3; i < 6; i++) {
						MovieBean mv = movieList.get(i);
				%>
				<div class="col-lg-4">
					<img class="img-circle" src="pics/<%=mv.getName()%>.jpg"
						alt="Generic placeholder image" width="140" height="140">
					<h2 id="h2" name="<%= mv.getName()%>">
						<%
						if(mv.getName().length()>15)
							out.print(mv.getName().substring(0,13)+"......");
						else
							out.print(mv.getName());
						%>
					</h2>
					<p>
						<%
							if (mv.getDescription().length() > 60) {
									out.print(mv.getDescription().substring(0, 50) + "......");
								} else
									out.print(mv.getDescription());
						%>
					</p>
					<p>
						<a class="btn btn-default" href="#" role="button">View
							details &raquo;</a>
					</p>
				</div>
				<!-- /.col-lg-4 -->
				<% } %>
			</div>
			</div>
			</div>
		
		<!-- 幻灯片第三页 -->
		<div class="item">
		<div class="container marketing">
		<div class="row">
			<%
				for (int i = 6; i < 9; i++) {
					MovieBean mv = movieList.get(i);
			%>
			<div class="col-lg-4">
				<img class="img-circle" src="pics/<%=mv.getName()%>.jpg"
					alt="Generic placeholder image" width="140" height="140">
				<h2 id="h2" name="<%= mv.getName()%>">
					<%
					if(mv.getName().length()>15)
						out.print(mv.getName().substring(0,13)+"......");
					else
						out.print(mv.getName());
					%>
				</h2>
				<p>
					<%
						if (mv.getDescription().length() > 60) {
								out.print(mv.getDescription().substring(0, 50) + "......");
							} else
								out.print(mv.getDescription());
					%>
				</p>
				<p>
					<a class="btn btn-default" href="#" role="button">View
						details &raquo;</a>
				</p>
			</div>
			<!-- /.col-lg-4 -->
			<% }}%>
		</div>
		</div>
		</div>
		<!--
			End
			@author 宁志豪
		-->
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





<script src="js/holder.min.js"></script>
<script src="js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>

<!-- 
	END
	Created by —— 毛恺 
-->