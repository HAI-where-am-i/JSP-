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
					//���õ�¼�ɹ��ķ���
					success(req,resp,logname,password);
					RequestDispatcher dispatcher = req.getRequestDispatcher("login.jsp");  //ת��
					dispatcher.forward(req, resp);
				}else{
					String backNews="��������û���������,�����벻ƥ��";
					//���õ�¼ʧ�ܵķ���
					fail(req,resp,logname,backNews);
				}
			}else{
				String backNews="�������û���������";
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
				loginBean=new Login();//�����µ�����ģ��
				session.setAttribute("loginBean", loginBean);
				loginBean=(Login)session.getAttribute("loginBean");
			}
			String name=loginBean.getLogname();
			if(name.equals(logname)){
				loginBean.setBackNews(logname+"�Ѿ���¼��");
				loginBean.setLogname(logname);
			}else{
				//����ģ�ʹ洢�µĵ�¼�û�
				loginBean.setBackNews(logname+"��¼�ɹ�");
				loginBean.setLogname(logname);
			}
		}catch(Exception ee){
			loginBean = new Login();
			session.setAttribute("loginBean", loginBean);
			loginBean.setBackNews(logname+"��¼�ɹ�");
			loginBean.setLogname(logname);
		}
	}
	public void fail(HttpServletRequest request,HttpServletResponse response,
														String logname,String backNews){
		response.setContentType("text/html;charset=utf-8");
		try{
			PrintWriter out = response.getWriter();
			out.println("<html><body>");
			out.println("<h2>"+logname+"��¼�������<br>"+backNews+"</h2>");
			out.println("���ص�¼ҳ�����ҳ<br>");
			out.println("<a href=login.jsp>��¼ҳ��</a>");
			out.println("<br><a href=index.jsp>��ҳ</a>");
			out.println("</body></html>");
		}catch(IOException exp){}
	}
}