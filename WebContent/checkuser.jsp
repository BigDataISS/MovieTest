<%@ page contentType="text/html; charset=gb2312" import="Bean.UserBean" import="Service.UserService" language="java"%> 
<%@ page import="java.sql.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�ㄦ�疯韩浠介��璇�</title>
</head>

<body>
<%
	//浠�signin椤甸��浼��ョ���ㄦ�峰����瀵���
	String username=request.getParameter("username");
	String password=request.getParameter("password");
	int userid= 0;
	if(username==null) username="";
	if(password==null) password="";
	int userExisted = 0;

	try{
		//瑁�杞介┍�ㄧ�搴�
		Class.forName("org.gjt.mm.mysql.Driver").newInstance();
		//杩��ュ��绗�涓�	  
		String url ="jdbc:mysql://192.168.154.89/movie"; 
		//寤虹��杩���
		Connection conn= DriverManager.getConnection(url,"CSuser","123456"); 
		//寤虹��Statement
		Statement stmt=conn.createStatement();
		//�ц��ヨ�㈠缓绔�ResultSet
		ResultSet rs=stmt.executeQuery("select userid,username,password from user where username = '"
			+username+"'");
		//�ゆ���ㄦ�锋����瀛��ㄤ互��瀵�������姝ｇ‘
		while(rs!=null && rs.next()){
			if(password.equals(rs.getString("password"))){
				userExisted = 1;
				userid=rs.getInt("userid");
			}
		}
		//�抽��杩��ャ�����捐�婧�
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
	
   if(userExisted == 1){//楠�璇��ㄦ�蜂俊��
	  session.setAttribute("username",username);
	  session.setAttribute("userid",userid);
	  response.sendRedirect("index.jsp");//杩��ユ�㈣�椤甸��
   }
   else{ 
	  response.sendRedirect("signin.jsp");//杩��ョ�婚��椤甸��
   }
  %>
</body>
</html>