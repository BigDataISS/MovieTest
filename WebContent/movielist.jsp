<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.List" import="Bean.MovieBean" import="java.util.ArrayList" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
    	
	    $(".col-md-4 .btn").click(function() {
	    	var output=$(this).parent().next();
	    	
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
			    	alert("失败");
			    },
			}); 
						
		});
	    	    		
});
</script>

</head>
<body>
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
					<img class="card-img-top" src="pics/<%=movie.getName() %>.jpg" alt="Card image cap" width="288" height="140">
					<div class="card-body">
						<p class="card-text"><% if(movie.getDescription().length()>60){
							out.print(movie.getDescription().substring(0,50)+"......");
							}else	out.print(movie.getDescription());
							%></p>
						<div class="d-flex justify-content-between align-items-center">
							<div class="btn-group">
								<button type="button" class="btn btn-sm btn-outline-secondary">View</button>
								
							</div>
							<small class="text-muted"><%=movie.getName() %></small>
						</div>
					</div>
				</div>
			</div>
			<% } %>
			<!--
				End
				@author 宁志豪
			-->
</body>
</html>