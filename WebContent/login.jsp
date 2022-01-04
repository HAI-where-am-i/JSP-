<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="loginBean" class="mybean.data.Login" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="head.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录页面</title>
</head>
<!-- 页面负责提供输入登录信息界面,并负责像是登录反馈信息,比如登录是否成功,是否已经登录等 -->
<body bgcolor=pink>
<font size="2">
	<div align="center">
		<table border="2">
			<tr><th>登录</th></tr>
			<form action="loginServlet" method="post">
				<tr><td>登录名称:<input type="text" name="logname"></td></tr>
				<tr><td>输入密码:<input type="password" name="password"></td></tr>
				<tr><th><input type="submit" name="g" value="提交"></th></tr>
			</form>
		</table>
	</div>
	<div align="center">
		登录反馈信息:<br>
		<jsp:getProperty name="loginBean" property="backNews"/><br>
		登录名称:<br><jsp:getProperty name="loginBean" property="logname"/>
	</div>
</font>
</body>
</html>