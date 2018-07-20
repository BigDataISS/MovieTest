<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.List" import="Bean.MovieBean,Bean.UserBean" import="java.util.ArrayList" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*"%>
<%@ page import="Servlet.RateServlet"%>
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

<link rel="icon" type="image/x-icon" href="icon/favicon.ico"/>
<title>个人中心</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/person.css" rel="stylesheet" type="text/css">


<link href="css/jumbotron.css" rel="stylesheet">



<script src="js/html5shiv.min.js"></script>
<script src="js/respond.min.js"></script>

<link href="css/ie10-viewport-bug-workaround.css" rel="stylesheet">
<script src="js/ie-emulation-modes-warning.js"></script>

<!--
	Start
	个人主页，实现各个按钮的功能，并展示相应信息
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
		用户点击Chart按钮,显示报表
	*/
    $("#echart").click(function() {
    	output=$(this).parent().next();
    	
		$.ajax({
		    type: "POST",
		    url: "${pageContext.request.contextPath}/ChartServlet",			    
		    /* dataType: "json", */			   
		    /* contentType: "application/x-www-form-urlencoded; charset=utf-8", */
		    success: function(data){
		    	$("#mainbody").load("${pageContext.request.contextPath}/echart.jsp");		    	
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
			用户点击电影推荐按钮,跳转到搜索界面  	
		*/
		$("#movierecommend").click(function() {
			window.location.href="${pageContext.request.contextPath}/person.jsp";
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
    			$("#mainbody").load("${pageContext.request.contextPath}/viewRecord.jsp");
    		},
			error: function(data){
    	
    		},
		}); 
			
	});
	    
	    /**
			用户点击个人主页按钮，刷新个人主页页面	
		*/
	    $("#signinbtn").click(function() {
	    	
			$.ajax({
			    type: "POST",
			    url: "${pageContext.request.contextPath}/movieRecommendServlet",
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
    			$("#mainbody").load("${pageContext.request.contextPath}/collection.jsp");
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
		    	$("#mainbody").load("${pageContext.request.contextPath}/personInfo.jsp");	    	
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
					<button class="btn btn-success" type="button" id="search">返回查询</button>
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
						<li class="nav-item"><a class="nav-link" id="movierecommend"
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
						<li class="nav-item"><a class="nav-link"
							href="#browse-record" id="echart"> <span data-feather="users"></span>
								兴趣报表
						</a></li>
					</ul>

				</div>


			</nav>
		</div>
		<!--end row-->
	</div>
	<!--end container-->

	
<div id="mainbody">	
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
					@author 毛恺 
					@author 宁志豪
				-->
				<%
					try{
						//连接到mysql数据库
						Class.forName("org.gjt.mm.mysql.Driver").newInstance();  
						String url ="jdbc:mysql://localhost/movie"; 
						Connection conn= DriverManager.getConnection(url,"root","Nimakengdie1"); 
						Statement stmt=conn.createStatement();
						
						//获取当前用户用户名
						String user=(String)session.getAttribute("username");
						//等待推荐线程写入数据库结束
						if(RateServlet.ct != null){
							long start = System.currentTimeMillis();
							while(RateServlet.ct.isAlive())
								;
							long end = System.currentTimeMillis();
							System.out.println(end-start+"ms");
						}
						//执行查询建立ResultSet,获取推荐电影movieid
						ResultSet rs=stmt.executeQuery("select movieid from recommend join user on recommend.userid = user.userid where username = '"
							+user+"'");
						if(!rs.next()||rs==null){
							System.out.print("dsgfuysdil");
							%>
							<div class="container">
							<div class="row">
							<font color="darkgray" size="5">对不起，我们还没有获得关于您的信息，
							<br/>无法为您提供推荐。
							<br/>请先对一些电影进行评分！</font>
							</div>
							</div>
							<%
						}
						else{
							%>
							<script>
							$("#main-title").append("<button id=	\"recommend\" class=\"btn btn-link\"><img src=\"image/reflesh.png\"></img></button>");
							</script>
							<%
						}				
						//获取推荐电影具体信息
						rs.previous();
						while(rs!=null && rs.next()){
							String mvid = rs.getString("movieid");
							Statement stmt2=conn.createStatement();
							ResultSet movieInfo = stmt2.executeQuery("select * from movie where movieid = "
							+mvid+"");
							while(movieInfo!=null && movieInfo.next()){
							%>
							<div class="col-md-4">
								<div class="card mb-4 box-shadow">
									<img class="card-img-top" src="pics/<%=movieInfo.getString("Name")%>.jpg"
										alt="Card image cap" width="288" height="400">
									<div class="card-body">
										<p class="card-text">
										<% if(movieInfo.getString("Description").length()>60){
												out.print(movieInfo.getString("Description").substring(0,50)+"......");
											}
											else	out.print(movieInfo.getString("Description"));
										%></p>
										<div class="d-flex justify-content-between align-items-center">
											<div class="btn-group">
												<button type="button" class="btn btn-sm btn-default">View</button>
											</div>
											<small class="text-muted"><%=movieInfo.getString("Name")%></small>
										</div>
									</div>
								</div>
							</div>
						<%
							}
							movieInfo.close();
							stmt2.close();
						}
						
						//关闭连接、释放资源
						rs.close();
						stmt.close();
						conn.close();
					}catch(ClassNotFoundException cnfe){
						out.print(cnfe);
					}catch(SQLException sqle){
						out.print(sqle);
					}catch(Exception e){
						out.print(e);
					}
				%>
				
				<!--
					End
					@author 毛恺 
					@author 宁志豪
				-->


			</center>
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
