package myservlet.control;

import mybean.data.Login;
import java.sql.*;
import java.util.*;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

//用户在视图部分单机"生成订单",buyServlet控制器负责把用户购物车中的物品存放到数据库的orderform表中
//即生成订单,然后清空用户的购物车
public class HandleBuyGoods extends HttpServlet {
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e){}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		String buyGoodsMess=req.getParameter("buy");
		if(buyGoodsMess==null||buyGoodsMess.length()==0){
			fail(req,resp,"购物车没有物品,无法生成订单");
			return;
		}
		String price=req.getParameter("price");
		if(price==null||price.length()==0){
			fail(req,resp,"没有计算价格和,无法生成订单");
			return;
		}
		float sum=Float.parseFloat(price);
		Login loginBean=null;
		HttpSession session = req.getSession(true);
		try{
			loginBean=(Login)session.getAttribute("loginBean");
			boolean b=loginBean.getLogname()==null||loginBean.getLogname().length()==0;
			if(b)
				resp.sendRedirect("login.jsp");//重定向到登录页面
		}catch(Exception exp){
			resp.sendRedirect("login.jsp");//重定向到登录页面
		}
		String uri="jdbc:mysql://127.0.0.1/mobileshop?"+
					"user=root&password=121803&characterEncoding=utf-8";
		Connection con;
		PreparedStatement sql;
		try{
			con=DriverManager.getConnection(uri);
			String insertCondition ="insert into orderform values(?,?,?,?)";
			sql=con.prepareStatement(insertCondition);
			sql.setInt(1,0);//订单序号会自定增加
			sql.setString(2, loginBean.getLogname());
			sql.setString(3,buyGoodsMess);
			sql.setFloat(4, sum);
			sql.executeUpdate();
			LinkedList car=loginBean.getCar();
			car.clear();//清空购物车
			success(req,resp,"生成订单成功");
		}catch(SQLException exp){
			fail(req,resp,"生成订单失败"+exp);
		}
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req,resp);
	}
	public void success(HttpServletRequest request,HttpServletResponse response,
						String backNews){
		response.setContentType("text;charset=utf-8");
		try{
			PrintWriter out = response.getWriter();
			out.println("<html><body>");
			out.println("<h2>"+backNews+"</h2>");
			out.println("返回主页");
			out.println("<a href=index.jsp>主页</a>");
			out.println("<br>查看订单:");
			out.println("a href=lookOrderForm.jsp>查看订单</a>");
			out.println("</body></html>");
		}catch(IOException exp){}
	}
	public void fail(HttpServletRequest request,HttpServletResponse response,
			String backNews){
		response.setContentType("text/html;charset=utf-8");
		try{
			PrintWriter out=response.getWriter();
			out.println("<html><body>");
			out.println("<h2>"+backNews+"</h2>");
			out.println("返回主页");
			out.println("<a href=index.jsp>主页</a>");
			out.println("</body></html>");
		}catch(IOException exp){}
			
	}
}
