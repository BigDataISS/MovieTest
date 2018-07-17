<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.List" import="Bean.MovieBean,Bean.UserBean" import="java.util.ArrayList" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<!-- 
	START
	ChangeInfo.jsp，修改个人资料
	Created by —— 马雨昂 
-->
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="icon" type="image/x-icon" href="icon/favicon.ico"/>
<title>修改资料</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/jumbotron.css" rel="stylesheet">
<link href="css/signup.css" rel="stylesheet">

<script src="js/html5shiv.min.js"></script>
<script src="js/respond.min.js"></script>

<link href="css/ie10-viewport-bug-workaround.css" rel="stylesheet">
<script src="js/ie-emulation-modes-warning.js"></script>

<!--
	Start
	用户注册功能
	@author 马雨昂
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
						
		})
	});
    function check() {
	       if($("#password").val()==""||$.trim($("#password").val())==""){
	    	   $("#passwordErrorMsg").html("密码不能为空");
	    	   $("#passwordErrorMsg").show();
	       }
	       else if($("#password").val()!=$("#passwordConfirm").val()){
	    	   $("#passwordErrorMsg").html("密码不一致");
	    	   $("#passwordErrorMsg").show();
	       }
	       else{
	    	   $("#passwordErrorMsg").hide();
	       }
	}
    
    function countDown(secs){    
		$("#sucAppointment").html("修改成功，"+secs+"秒后返回界面");
		$("#sucAppointment").show();
		if(--secs>0){       
			setTimeout("countDown("+secs+")",1000);       
		}else{         
	    	location.href='${pageContext.request.contextPath}/person.jsp';       
	    }       
	 }
    
    function ajaxAll() {
		if($("#password").val()!=$("#passwordConfirm").val()
				||$("#password").val()==""||$.trim($("#password").val())=="")
			check();					
		else{
        	$.ajax({      	 
            	type: "POST",
            	url: "${pageContext.request.contextPath}/HandleUserChangeServlet",
           		data: $('#userinfo').serialize(),
            	dataType: "json",
            	contentType: "application/x-www-form-urlencoded; charset=utf-8",
           	 	success: function (data) {
					if(data.error==="2"){
						$("#ageErrorMsg").html("年龄必须是整数");
			    	 	$("#ageErrorMsg").show();	
					}
					else{
						$("#ageErrorMsg").hide();	
						countDown(2)
					}//endelse
            	}//endsuccess
        	});//endajax
		}//endelse
    }//endfunction
    
    
</script>
<!--
	End
	@author 马雨昂
-->

</head>

<body>
<!-- navbar 顶部悬浮导航栏 -->
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
		</ul>		
	</div>
</div>
</nav>

<% 	UserBean user=(UserBean)session.getAttribute("ReadyToChangeUser");%>
<!-- 中间内容 -->
<div class="jumbotron">
<div class="container">
<div class="row">

	<div class="col-lg-6">
		<img src="image/person.jpg" width="500" height="600"></image>
	</div>
	
	<div class="col-lg-6">
	<h4 class="mb-3">修改你的账户</h4>
	<form class="needs-validation" id="userinfo" novalidate>


	<div class="mb-3">
		<label for="new_username">用户名:</label>
		<font size="6"><%=user.getUserName() %>&nbsp </font><br><br>
	</div>
	<div class="mb-3">
		<label for="password">新密码</label>
		<div class="input-group">
			<input type="password" class="form-control" id="password" name="password"
			 	onclick="check()" onblur="check()" placeholder="<%user.getPassword();%>" required>
		</div>
	</div>
	<div class="mb-3">
		<label for="passwordConfirm">确认新密码</label>
		<div class="input-group">
			<input type="password" class="form-control" id="passwordConfirm" name="passwordConfirm"
				onclick="check()" onblur="check()" placeholder="确认密码" required>
				<div class="invalid-feedback" style="display:none" id="passwordErrorMsg"></div>
		</div>
	</div>
	<div class="mb-3">
		<label for="sex">性别</label>
		<div class="custom-control custom-radio">
			<input id="male" name="sex" value="男" type="radio" class="custom-control-input" checked required>
			<label class="custom-control-label" for="credit">男</label>
			<input id="female" name="sex" value="女" type="radio" class="custom-control-input" required>
			<label class="custom-control-label" for="debit">女</label>
		</div>
	</div>
	

	<div class="mb-3">
		<label for="age">年龄 <span class="text-muted"></span></label>
		<input type="text" class="form-control" id="age" name="age" placeholder="<%user.getAge();%>">
		<div class="invalid-feedback" style="display:none" id="ageErrorMsg"></div>
	</div>

	<div class="mb-3">
		<label for="profession">职业</label>
		<input type="text" class="form-control" id="profession" name="profession" placeholder="<%user.getProfession();%>">
	</div>

	<div class="mb-3">
		<label for="description">个人说明</label>
		<input type="text" class="form-control" id="description" name="description" placeholder="<%user.getDescription();%>">
	</div>


	
	<hr class="mb-4">
	<button class="btn btn-lg btn-primary" onclick="ajaxAll()" type="button" id="signup">修改</button>
	<a class="btn btn-lg btn-primary" href="person.jsp">返回</a>
	<div class="invalid-feedback" style="color:red" id="sucAppointment"></div>
	</form>
	</div>
</div>
</div><!--end container-->
</div><!--end jumbotron-->






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