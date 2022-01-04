package myservlet.control;

import mybean.data.Login;
import java.sql.*;
import java.util.*;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

//�û�����ͼ���ֵ���"���ɶ���",buyServlet������������û����ﳵ�е���Ʒ��ŵ����ݿ��orderform����
//�����ɶ���,Ȼ������û��Ĺ��ﳵ
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
			fail(req,resp,"���ﳵû����Ʒ,�޷����ɶ���");
			return;
		}
		String price=req.getParameter("price");
		if(price==null||price.length()==0){
			fail(req,resp,"û�м���۸��,�޷����ɶ���");
			return;
		}
		float sum=Float.parseFloat(price);
		Login loginBean=null;
		HttpSession session = req.getSession(true);
		try{
			loginBean=(Login)session.getAttribute("loginBean");
			boolean b=loginBean.getLogname()==null||loginBean.getLogname().length()==0;
			if(b)
				resp.sendRedirect("login.jsp");//�ض��򵽵�¼ҳ��
		}catch(Exception exp){
			resp.sendRedirect("login.jsp");//�ض��򵽵�¼ҳ��
		}
		String uri="jdbc:mysql://127.0.0.1/mobileshop?"+
					"user=root&password=121803&characterEncoding=utf-8";
		Connection con;
		PreparedStatement sql;
		try{
			con=DriverManager.getConnection(uri);
			String insertCondition ="insert into orderform values(?,?,?,?)";
			sql=con.prepareStatement(insertCondition);
			sql.setInt(1,0);//������Ż��Զ�����
			sql.setString(2, loginBean.getLogname());
			sql.setString(3,buyGoodsMess);
			sql.setFloat(4, sum);
			sql.executeUpdate();
			LinkedList car=loginBean.getCar();
			car.clear();//��չ��ﳵ
			success(req,resp,"���ɶ����ɹ�");
		}catch(SQLException exp){
			fail(req,resp,"���ɶ���ʧ��"+exp);
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
			out.println("������ҳ");
			out.println("<a href=index.jsp>��ҳ</a>");
			out.println("<br>�鿴����:");
			out.println("a href=lookOrderForm.jsp>�鿴����</a>");
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
			out.println("������ҳ");
			out.println("<a href=index.jsp>��ҳ</a>");
			out.println("</body></html>");
		}catch(IOException exp){}
			
	}
}
