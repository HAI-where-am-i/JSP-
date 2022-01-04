<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="mybean.data.DataByPage" %>
<%@ page import="com.sun.rowset.*" %>
<jsp:useBean id="dataBean" class="mybean.data.DataByPage" scope="session"/>
<%@ include file="head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>显示dataBean中的数据</title>
</head>
<!-- 在lookMobile.jsp页面选择某个分类,比如iphone手机,然后提交给servlet控制器queryServlet,该控制器将查询结果放到JavaBean模型dataBean中,然后将显示dataBean中的数据任务交给byPageShow.jsp页面
byPageShow.jsp页面可以分页显示dataBean中的数据,即分页显示记录。用户在byPageShow.jsp页面看到产品后,可以选择"查看细节",到showDetail.jsp页面查看该产品的细节。 -->
<body bgcolor=#66FFAA>
<center>
	<br>当前显示的内容是:
	<table  border="2">
	<tr>
		<th>手机标识号:</th>
		<th>手机名称:</th>
		<th>手机制造商:</th>
		<th>手机价格:</th>
		<th>手机详情:</th>
		<td><font color=blue>添加到购物车</font></td>
	</tr>
	<jsp:setProperty name="dataBean" property="pageSize" param="pageSize"/>
	<jsp:setProperty name="dataBean" property="currentPage" param="currentPage"/>
	<%
		CachedRowSetImpl rowSet=dataBean.getRowSet();
		if(rowSet==null){
			out.print("没有任何查询信息,无法浏览");
			return;
		}
		rowSet.last();
		int totalRecord=rowSet.getRow();
		out.println("全部记录数"+totalRecord);//全部记录数
		int pageSize=dataBean.getPageSize();//每页显示的记录数
		int totalPages=dataBean.getTotalPages();
		if(totalRecord%pageSize==0)
			totalPages=totalRecord/pageSize;//总页数
		else
			totalPages=totalRecord/pageSize+1;
		dataBean.setPageSize(pageSize);
		dataBean.setTotalPages(totalPages);
		if(totalPages>=-1){
			if(dataBean.getCurrentPage()<1)
				dataBean.setCurrentPage(dataBean.getTotalPages());
			if(dataBean.getCurrentPage()>dataBean.getTotalPages())
				dataBean.setCurrentPage(1);
			int index=(dataBean.getCurrentPage()-1)*pageSize+1;
			rowSet.absolute(index);//查询位置移动到currentPage页起始位置
			boolean boo=true;
			for(int i=1;i<=pageSize&&boo;i++){
				String number=rowSet.getString(1);
				String name=rowSet.getString(2);
				String maker=rowSet.getString(3);
				String price=rowSet.getString(4);
				String goods="("+number+","+name+","+maker+","+price+")#"+price;//便于购物车在计算价格,尾加上#价格值
				goods=goods.replaceAll("\\p{Blank}","");
				String button="<form action='putGoodsServlet' method='post'>"+
								"<input type='hidden' name='java' value="+goods+">"+
								"<input type='submit' value='放入购物车'></form>";
				String detail="<form action='showDetail.jsp' method='post'>"+
						"<input type='hidden' name='xijie' value="+number+">"+
						"<input type='submit' value='查看细节'></form>";
				out.print("<tr>");
				out.print("<td>"+number+"</td>");
				out.print("<td>"+name+"</td>");
				out.print("<td>"+maker+"</td>");
				out.print("<td>"+price+"</td>");
				out.print("<td>"+detail+"</td>");
				out.print("<td>"+button+"</td>");
				out.print("</tr>");
				boo=rowSet.next();
			}
		}
	%>	
	</table>
	<br>每页最多显示<jsp:getProperty name="dataBean" property="pageSize"/>条消息。
	<br>当前显示第<font color=blue>
		<jsp:getProperty name="dataBean" property="currentPage"/>
	</font>页,共有
	<font color=red><jsp:getProperty property="totalPages" name="dataBean"/>
	</font>页。
		<table>
			<tr>
				<td>
					<form action="" method="post">
					<input type="hidden" name="currentPage" value="<%=dataBean.getCurrentPage()-1 %>">
					<input type="submit" name="g" value="上一页"></form>
				</td>
				<td>
					<form action="" method="post">
					<input type="hidden" name="currentPage" value="<%=dataBean.getCurrentPage()+1 %>">
					<input type="submit" name="g" value="下一页"></form>
				</td>
			</tr>
			<tr>
				<td>
					<form action="" method="post">
						每页显示<input type="text" name="pageSize" value="2" size="3">
						条记录<input type="submit" name="g" value="确定">
					</form>
				<td>
				<td>
					<form action="" method="post">
						输入页码:<input type="text" name="currentPage" size="2">
						<input type="submit" name="g" value="提交">
					</form>
				</td>
			</tr>
		</table>
</center>
</body>
</html>