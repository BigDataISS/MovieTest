<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.List" import="Bean.MovieBean" import="java.util.ArrayList" pageEncoding="UTF-8"%>

<!--
	Start
	movielist.jsp 展示电影列表的信息
	@author 宁志豪
-->
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
    	/**
			用户点击view按钮，传输电影名，并跳转到电影的具体信息页面 	
		*/
	    $(".col-md-4 .btn").click(function() {
	    	var output=$(this).parent().next();
	    	
	    	console.log(output.attr("name"))
			$.ajax({
			    type: "POST",
			    url: "${pageContext.request.contextPath}/movieDetailServlet",
			    data: {"name":output.attr("name")},
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
		展示电影信息		
	-->
	<%
		String notfound = (String) session.getAttribute("notfound");
		System.out.println(notfound);
		if (notfound == "1") {
			List<MovieBean> movieList = (List<MovieBean>) session.getAttribute("movieList");
			if (movieList == null) {
				movieList = new ArrayList<MovieBean>();
			}
			for (MovieBean movie : movieList) {
	%>
	<div class="col-md-4">
		<div class="card mb-4 box-shadow">
			<img class="card-img-top" src="pics/<%=movie.getName()%>.jpg"
				alt="Card image cap" width="288" height="140">
			<div class="card-body">
				<p class="card-text">
					<%
						if (movie.getDescription().length() > 65) {
									out.print(movie.getDescription().substring(0, 60) + "......");
								} else {
									out.print(movie.getDescription());
								}
					%>
				</p>
				<div class="d-flex justify-content-between align-items-center">
					<div class="btn-group">
						<button type="button" class="btn btn-sm btn-outline-secondary">View</button>

					</div>
					<small class="text-muted" name="<%=movie.getName()%>"> 
					<%
 						if (movie.getName().length() > 20) {
 							out.print(movie.getName().substring(0, 15) + "......");
 						} else {
 							out.print(movie.getName());
 						}
 					%>
					</small>
				</div>
			</div>
		</div>
	</div>
	<%
		} //endfor
		} //endif
		else {
	%>
	<font color="darkgray" size="5"><%=notfound%></font>
	<%
		}
	%>

</body>
</html>
<!--
	End
	@author 宁志豪
-->