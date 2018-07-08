<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.List" import="Bean.MovieBean" import="java.util.ArrayList" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>推荐结果</title>
  </head>
 
  <body>
    <center>
    <table width="90%" border="1" cellpadding="0" cellspacing="0"> 
    <tr>  
    <!-- 推荐的电影 -->
    
    <tr><td align="left" colspan="5"><font color="black">推荐的电影</font></td></tr>
     <tr><td>电影名称</td><td>上映年份</td><td>电影类型</td><td>评分</td><td>电影海报</td></tr>

	<% List<MovieBean> movieList = (List<MovieBean>)session.getAttribute("movieList"); 
		if(movieList == null){
			movieList = new ArrayList<MovieBean>();
		}
		for(MovieBean movie : movieList){
			
	%>
	<tr><td><%=movie.getName() %></td><td><%=movie.getReleaseDate() %></td>
	<td><%=movie.getType() %></td><td><%=movie.getRatingNum() %></td><td>假装这里有个海报</td></tr>
	<% } %>
    </table>
    </center>
  </body>
</html>
