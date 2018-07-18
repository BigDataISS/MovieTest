<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.List" import="Bean.MovieBean,Bean.UserBean" import="java.util.ArrayList" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<!-- 
	START
	功能描述：viewRecord.jsp,展示用户浏览的电影表单的信息
	@author 毛恺 
-->
<html lang="en">

<head>

<link rel="icon" type="image/x-icon" href="icon/favicon.ico"/>
<title>浏览记录</title>
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
    
    
 
    
});
</script>

<!--
	End
	@author 李耀鹏
-->


</head>

<body>
<!-- 搜索结果显示 -->
<div class="jumbotron" id="movie-recommend">
		<div class="album py-5 bg-light">
		<center>
				<h1>浏览记录</h1>
			<!--
				Start
				展示电影信息
				@author 宁志豪
			-->		
			<% List<MovieBean> movieList = (List<MovieBean>)session.getAttribute("movieList"); 
			
			if(movieList.size() == 0){%>
			
			<div class="container">
					<div class="row">
					<font color="darkgray" size="5">你还没有收藏任何电影
					<br/>去浏览一些自己感兴趣的电影吧</font>
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
						<% if(movie.getDescription().length()>60){
							out.print(movie.getDescription().substring(0,50)+"......");
							}else{
								out.print(movie.getDescription());
							}
							%></p>
						<div class="d-flex justify-content-between align-items-center">
							<div class="btn-group">
								<button type="button" class="btn btn-sm btn-default">View</button>
								
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
						
						<div>
								<small class="text-muted">
								<%
								if(movie.getViewTime() != null){
									out.print(movie.getViewTime());
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
			</center>
		</div>
	</div>
</div>

</body>
</html>

<!-- 
	END
	@author 毛恺 
-->