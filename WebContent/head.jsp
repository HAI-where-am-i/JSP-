<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div align="center">
	<h2>"智多星"智能手机销售网</h2>
	<table cellSpacing="1" cellPadding="1" width="660" align="center" border="0">
		<tr valign="bottom"><!-- valign=bottom是什么意思 -->
			<td><a href="inputRegisterMess.jsp"><font size=2>注册</font></a></td>
			<td><a href="login.jsp"><font size=2>登录</font></a></td>
			<td><a href="lookMobile.jsp"><font size=2>浏览手机</font></a></td>
			<td><a href="searchMobile.jsp"><font size=2>查询手机</font></a></td>
			<td><a href="lookShoppingCar.jsp"><font size=2>查看购物车</font></a></td>
			<td><a href="lookOrderForm.jsp"><font size=2>查看订单</font></a></td>
			<!--<td><a href="exitOrderForm.jsp"><font size=2>退出</font></a></td>  -->
			<td><form action="exitServlet" method="post"><input type="submit" value="退出登录"></form></td>
			<td><a href="index.jsp"><font size=2>主页</font></a></td>			
		</tr>
	</table>
</div>
</body>
</html>