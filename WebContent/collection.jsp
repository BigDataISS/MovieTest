<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.List" import="Bean.MovieBean,Bean.UserBean" import="java.util.ArrayList" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*"%>
<!DOCTYPE html>

<!-- 
	START
	功能描述：collection.jsp，显示用户收藏的电影表单的信息
	@author 毛恺
-->
<html lang="en">

<head>

<link rel="icon" type="image/x-icon" href="icon/favicon.ico"/>
<title>我的收藏</title>
<!--
	Start
	实现信息的传输以及界面的跳转
	@author 李耀鹏
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
		用户点击view按钮，传输电影名，并跳转到电影的具体信息页面 	
	*/
    $(".view").click(function() {
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
	用户点击Cancel Collection按钮，传输电影名，用户取消对该电影的收藏	
	*/
$(".cancel").click(function() {
	var a=$(this).parent().next();
	
	console.log(a.attr("name"))
	$.ajax({
	    type: "POST",
	    url: "${pageContext.request.contextPath}/cancelCollectiong",
	    data: {"name":a.attr("name")},
	    /* dataType: "json", */			   
	    /* contentType: "application/x-www-form-urlencoded; charset=utf-8", */
	    success: function(data){
	    	alert("取消成功");
	    	
	    	$.ajax({
	    		type: "POST",
	   	 		url: "${pageContext.request.contextPath}/collect",
	    		/* dataType: "json", */			   
	    		/* contentType: "application/x-www-form-urlencoded; charset=utf-8", */
	    		success: function(data){
	    			$("#mainbody").load("${pageContext.request.contextPath}/collection.jsp");
	    		},
				error: function(data){
	    	
	    		},
			}); 
	    },
		error: function(data){
	    	alert("失败");
	    },
	}); 
				
});
	 
	});
    
    
</script>

<!--
	End
	@author 李耀鹏
-->


</head>

<body>
<div class="jumbotron" id="movie-recommend">
		<div class="album py-5 bg-light">
		<center>
				<h1>我的收藏</h1>
			<!--
				Start
				展示电影信息
				@author 宁志豪
			-->		
			<% List<MovieBean> movieList = (List<MovieBean>)session.getAttribute("movieList"); 
			
			//System.out.println(movieList.size());
				if(movieList.size() == 0){%>
					
					<div class="container">
							<div class="row">
							<font color="darkgray" size="5">你还没有收藏任何电影
							<br/>去收藏一些自己喜欢的电影吧</font>
							</div>
							</div>
					
					<%movieList = new ArrayList<MovieBean>();
				}
				for(MovieBean movie : movieList){
			%>
			<div class="col-md-4">
				<div class="card mb-4 box-shadow">
					<img class="card-img-top" src="pics/<%=movie.getName() %>.jpg" alt="Card image cap" width="288" height="440">
					<div class="card-body">
						<p class="card-text">
						<% if(movie.getDescription().length()>50){
							out.print(movie.getDescription().substring(0,50)+"......");
							}else{
								out.print(movie.getDescription());
							}
							%></p>
						<div class="d-flex justify-content-between align-items-center">
							<div class="btn-group">
								<button type="button" class="btn btn-sm btn-default view">View</button>
								<button type="button" class="btn btn-sm btn-default cancel">Cancel Collection</button>
							</div>
							<small class="text-muted" name="<%=movie.getName() %>">
							<%
								if(movie.getName().length()>10){
									out.print(movie.getName().substring(0,10)+"......");
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


</body>
</html>

<!-- 
	END
	@author 毛恺 
-->