<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="head.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择分类页面</title>
</head>
<!-- 在lookMobile.jsp页面中选择某个分类,比如iPhone手机,然后提交给servlet控制器queryServlet,该控制器将查询结果放到JavaBean
模型 dataBean 中,然后将显示dataBean中的数据的任务交给byPageShow。jsp页面 -->
<body bgcolor="cyan">
	<font size="2">
		<div align="center">
			<%
				try{
					Class.forName("com.mysql.jdbc.Driver");
				}catch(Exception e){}
			String uri="jdbc:mysql://127.0.0.1/mobileshop?"+
										"user=root&password=121803&characterEncoding=utf-8";
			Connection con;
			Statement sql;
			ResultSet rs;
			try{
				con=DriverManager.getConnection(uri);
				sql=con.createStatement();
				//读取mobileClassify表,获得分类
				rs=sql.executeQuery("select * from mobileClassify");
				out.print("<form action='queryServlet' method='post'>");
				out.print("<select name='fenleiNumber'>");
				while(rs.next()){
					int id=rs.getInt(1);
					String mobileCategory = rs.getString(2);
					out.print("<option value="+id+">"+mobileCategory+"</option>");
				}
				out.print("</select>");
				out.print("<input type='submit' value='提交'>");
				out.print("</form>");
				con.close();
			}catch(SQLException e){
				out.print(e);
			}
			%>
		</div>
	</font>
</body>
</html>