<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>输入查询信息</title>
</head>
<!-- 用户在searchMobile.jsp页面输入查询信息,提交给searchByConditionServlet控制器,该控制器将查询结果放到dataBean中。 -->
<body bgcolor="#55BBDD">
<font size="2">
	<div align="center">
		<br>查询时可以输入手机的版本号或手机名称及价格.<br>
		手机名称支持模糊查询。
		<br>输入价格是在两个值之间的价格,格式是:价格1-价格2<br>
		例如3987-8976
		<form action="searchByConditionServlet" Method="post">
			<br>输入查询信息:<input type="text" name="searchMess"><br>
			<input type="radio" name="radio" value="mobile_version">手机版本号
			<input type="radio" name="radio" value="mobile_name" checked="ok">手机名称
			<input type="radio" name="radio" value="mobile-price">手机价格
			<br><input type="submit" name="g" value="提交">
		</form>		
	</div>
</font>
</body>
</html>