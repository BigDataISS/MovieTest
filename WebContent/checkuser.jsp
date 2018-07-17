<%@ page contentType="text/html; charset=gb2312" import="Bean.UserBean" import="Service.UserService" language="java"%> 
<%@ page import="java.sql.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>��濮�锟斤拷����锟斤拷锟解��锟芥�锟介���ゆ�凤拷锟介��锟�</title>
</head>

<body>
<%
	//濞达拷��锟�signin濡���锟介�╂�烽���ュ�奸���ゆ�凤拷��烽���ゆ�凤拷锟介�����查���ゆ�烽���ゆ�凤拷纰��烽���ゆ��
	String username=request.getParameter("username");
	String password=request.getParameter("password");
	int userid= 0;
	if(username==null) username="";
	if(password==null) password="";
	int userExisted = 0;

	try{
		//锟斤拷���ワ拷锟芥�锟斤拷锟介��濮�锟窖��凤拷杈炬��
		Class.forName("org.gjt.mm.mysql.Driver").newInstance();
		//锟解���烽��濮�锟姐���烽����锟介���ワ拷��锟�	  
		String url ="jdbc:mysql://localhost/movie"; 
		//�点��锟界�告�烽���ワ拷���烽���ゆ��
		Connection conn= DriverManager.getConnection(url,"root","Nimakengdie1"); 
		//�点��锟界�告�烽��锟�Statement
		Statement stmt=conn.createStatement();
		//��绐�锟介���ゆ�凤拷锟介��濮�锟斤拷缂�锟界�锟介��锟�ResultSet
		ResultSet rs=stmt.executeQuery("select userid,username,password from user where username = '"
			+username+"'");
		//��濮�锟斤拷���ゆ�烽��濮�锟斤拷��浠�锟斤拷���ゆ�烽���ゆ�凤拷锟介���ゆ�凤拷��锟介���ゆ�凤拷纰��烽���ゆ�烽���ゆ�烽���ゆ�锋慨锟介��锟斤拷锟�
		while(rs!=null && rs.next()){
			if(password.equals(rs.getString("password"))){
				userExisted = 1;
				userid=rs.getInt("userid");
			}
		}
		//���ワ拷�ゆ�烽���ワ拷���烽��濮�锟斤��烽���ゆ�烽���ゆ�凤拷锟介��������锟�
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
  <%
	
   if(userExisted == 1){//濡わ拷����锟斤拷���ゆ�凤拷锟介����锟斤拷娣�锟介���ゆ��
	  session.setAttribute("username",username);
	  session.setAttribute("userid",userid);
	  response.sendRedirect("index.jsp");//锟解���烽��濮�锟斤拷��濮�锟斤��峰���锟介�╂�烽��锟�
   }
   else{ 
	  response.sendRedirect("signin.jsp");//锟解���烽��濮�锟窖��锋�锟介���ゆ�峰���锟介�╂�烽��锟�
   }
  %>
</body>
</html>