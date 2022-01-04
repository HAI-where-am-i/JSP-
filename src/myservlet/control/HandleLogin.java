package myservlet.control;

import mybean.data.*;
import java.sql.*;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;

public class HandleLogin extends HttpServlet{
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e){}
	}
	public String handleString(String s){
		try{
			byte bb[]=s.getBytes("utf-8");
			s=new String(bb);
		}catch(Exception ee){}
			return s;
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		Connection con;
		Statement sql;
		String logname=req.getParameter("logname").trim();
		String password=req.getParameter("password").trim();
		logname=handleString(logname);
		password=handleString(password);
		String uri="jdbc:mysql://127.0.0.1/mobileshop?"+
									"user=root&password=121803&characterEncoding=utf-8";
		boolean boo=(logname.length()>0)&&(password.length()>0);
		try{
			con = DriverManager.getConnection(uri);
			String condition="select * from user where logname='"+logname+
					"'and password = '"+password+"'";
			sql=con.createStatement();//????
			if(boo){
				ResultSet rs=sql.executeQuery(condition);
				boolean m=rs.next();
				if(m==true){
					//调用登录成功的方法
					success(req,resp,logname,password);
					RequestDispatcher dispatcher = req.getRequestDispatcher("login.jsp");  //转发
					dispatcher.forward(req, resp);
				}else{
					String backNews="你输入的用户名不存在,或密码不匹配";
					//调用登录失败的方法
					fail(req,resp,logname,backNews);
				}
			}else{
				String backNews="请输入用户名和密码";
				fail(req,resp,logname,backNews);
			}
			con.close();
		}catch(SQLException exp){
			String backNews=""+exp;
			fail(req,resp,logname,backNews);
		}
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req,resp);
	}
	public void success(HttpServletRequest request,HttpServletResponse response,
												String logname,String password){
		Login loginBean=null;
		HttpSession session=request.getSession(true);
		try{
			loginBean=(Login)session.getAttribute("loginBean");
			if(loginBean==null){
				loginBean=new Login();//创建新的数据模型
				session.setAttribute("loginBean", loginBean);
				loginBean=(Login)session.getAttribute("loginBean");
			}
			String name=loginBean.getLogname();
			if(name.equals(logname)){
				loginBean.setBackNews(logname+"已经登录了");
				loginBean.setLogname(logname);
			}else{
				//数据模型存储新的登录用户
				loginBean.setBackNews(logname+"登录成功");
				loginBean.setLogname(logname);
			}
		}catch(Exception ee){
			loginBean = new Login();
			session.setAttribute("loginBean", loginBean);
			loginBean.setBackNews(logname+"登录成功");
			loginBean.setLogname(logname);
		}
	}
	public void fail(HttpServletRequest request,HttpServletResponse response,
														String logname,String backNews){
		response.setContentType("text/html;charset=utf-8");
		try{
			PrintWriter out = response.getWriter();
			out.println("<html><body>");
			out.println("<h2>"+logname+"登录反馈结果<br>"+backNews+"</h2>");
			out.println("返回登录页面或主页<br>");
			out.println("<a href=login.jsp>登录页面</a>");
			out.println("<br><a href=index.jsp>主页</a>");
			out.println("</body></html>");
		}catch(IOException exp){}
	}
}