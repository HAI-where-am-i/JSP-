<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="loginBean" class="mybean.data.Login" scope="session"/>
<%@ page import="java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="head.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询订单</title>
</head>
<!-- lookOrderForm.jsp负责显示用户的订单信息。 -->
<body>
<div align="center">
	<%
		if(loginBean==null){
			response.sendRedirect("login.jsp");//重定向到登录页面
		}else{
			boolean b=loginBean.getLogname()==null||
						loginBean.getLogname().length()==0;
			if(b)
				response.sendRedirect("login.jsp");//重定向到登录页面
		}
		Connection con;
		Statement sql;
		ResultSet rs;
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e){}
		try{
			String uri="jdbc:mysql://127.0.0.1/mobileshop";
			String user="root";
			String password="121803";
			con=DriverManager.getConnection(uri,user,password);
			sql=con.createStatement();
			String cdn="select id,mess,sum from orderform where logname='"+loginBean.getLogname()+"'";
			rs=sql.executeQuery(cdn);
			out.print("<table border=2>");
			out.print("<tr>");
				out.print("<th width=100>"+"订单号");
				out.print("<th width=100>"+"信息");
				out.print("<th width=100>"+"价格");
			out.print("</tr>");
			while(rs.next()){
				out.print("<tr>");
					out.print("<td>"+rs.getString(1)+"</td>");
					out.print("<td>"+rs.getString(2)+"</td>");
					out.print("<td>"+rs.getString(3)+"</td>");
				out.print("</tr>");
			}
			out.print("</table>");
			con.close();
		}catch(SQLException e){
			out.print(e);
		}
	%>
</div>
</body>
</html>