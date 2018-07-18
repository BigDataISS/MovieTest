
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName()
			+ ":" + request.getServerPort()	+ path + "/";
%>
<!DOCTYPE>
<!-- 
	START
	功能描述：echart显示
	@author 张子健
-->
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>兴趣报表</title>
	<!-- 使用单文件引入的方式使用ECharts.JS -->
	<script src="echarts-min.js"></script>
	<script src="jquery-3.3.1-min.js"></script>
</head>

<body>
<!-- 搜索结果显示 -->
<div class="jumbotron" id="movie-recommend">
		<div class="album py-5 bg-light">
		<center>
				<h1>兴趣报表</h1>	
				<div id="myDiv" style="height: 800px"></div>
	<script type="text/javascript">
	function loadData(option) {
		$.ajax({
			type : 'post',	//传输类型
			async : false,	//同步执行
			url : '${pageContext.request.contextPath}/ChartServlet',	//web.xml中注册的Servlet的url-pattern
			data : {},
			dataType : 'json', //返回数据形式为json
			success : function(result) {
				if (result) {
					option.series[0].data = [];
					for (var i=0; i<result.length; i++) {
						option.series[0].data.push(result[i]);
						console.log(result[i]);
					}
				}
			},
			error : function(errorMsg) {
				alert("加载数据失败");
			}
		});//AJAX
	}//loadData()
	var myChart = echarts.init(document.getElementById('myDiv'));
	var option = {
		    series : [
		        {
		            name: '访问来源',
		            type: 'pie',
		            radius: '55%',
		            label : {
		            	normal : {
		            	textStyle : {
		            	fontWeight : 'normal',
		            	fontSize : 30
		            	}
		            	}
		            	},
		        }
		    ]
		};
	//加载数据到option
	loadData(option);
	//设置option
	myChart.setOption(option);
	</script>
			</center>
		</div>
	</div>
</div>

</body>
</html>

<!-- 
	END
	功能描述：echart显示
	@author 张子健
-->