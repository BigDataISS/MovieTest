<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.List" import="Bean.MovieBean,Bean.UserBean" import="java.util.ArrayList" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*"%>
<!DOCTYPE html>

<!-- 
	START
	功能描述：person.jsp，按关键字、类别检索电影
	@author 毛恺 
-->
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Browse</title>
<link href="css/bootstrap.min.css" rel="stylesheet">

<link href="css/jumbotron.css" rel="stylesheet">
<link href="css/browse.css" rel="stylesheet" type="text/css">

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
			用户点击个人主页按钮，跳转个人主页页面	
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
	    
	    
	/**
	用户点击个人资料按钮，跳转到用户的个人资料信息页面
	*/
	$("#personal-info").click(function() {
		$.ajax({
		    type: "POST",
		    url: "${pageContext.request.contextPath}/UserInfoServlet",
		    /* dataType: "json", */			   
		    /* contentType: "application/x-www-form-urlencoded; charset=utf-8", */
		    success: function(data){			    	
		    	window.location.href="${pageContext.request.contextPath}/personInfo.jsp";			    	
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
				</ul>

				<form class="navbar-form navbar-right">
					<button class="btn btn-success" type="button" id="search">search<tton>
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

	

<!-- 搜索结果显示 -->
<div class="album py-5 bg-light">
	<div class="container">
		<div class="row">
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
			<div class="col-md-4">
				<div class="card mb-4 box-shadow">
					<img class="card-img-top" src="pics/<%=movie.getName() %>.jpg" alt="Card image cap" width="288" height="400">
					<div class="card-body">
						<p class="card-text">
						<% if(movie.getDescription().length()>65){
							out.print(movie.getDescription().substring(0,60)+"......");
							}else{
								out.print(movie.getDescription());
							}
							%></p>
						<div class="d-flex justify-content-between align-items-center">
							<div class="btn-group">
								<button type="button" class="btn btn-sm btn-outline-secondary">View</button>
								
							</div>
							<small class="text-muted" name="<%=movie.getName() %>">
							<%
								if(movie.getName().length()>20){
									out.print(movie.getName().substring(0,15)+"......");
								}else{
									out.print(movie.getName());
								}
							%>
							</small>
						</div>
					</div>
				</div>
			</div>
			<% } %>
			<!--
				End
				@author 宁志豪
			-->
			
		</div>
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