package myservlet.control;

import mybean.data.DataByPage;
import com.sun.rowset.*;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
//searchMobile.jsp�����ѯ������servlet������searchByConditionServlet,SearchByConditionServlet�����������ѯ���ݿ�,����
//��ѯ����ŵ�����ģ��dataBean��,Ȼ���û��ض���byPageShow.jspҳ��鿴dataBean�е�����.
public class SearchByCondition extends HttpServlet{
	CachedRowSetImpl rowSet=null;
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e){}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		String searchMess=req.getParameter("searchMess");
		String radioMess=req.getParameter("radio");
		if(searchMess==null||searchMess.length()==0){
			fail(req,resp,"û�в�ѯ��Ϣ,�޷���ѯ");
			return;
		}
		String condition="";
		if(radioMess.equals("mobile_version")){
			condition="select * from mobileForm where mobile_version='"+searchMess+"'";
		}else if(radioMess.equals("mobile_name")){
			condition="select * from mobileForm where mobile_name like '%"+searchMess+"%'";
		}else if(radioMess.equals("mobile_price")){
			double max=0,min=0;
			String regex="[^0123456789.]";
			String[] priceMess=searchMess.split(regex);
			if(priceMess.length==1){
				max=min=Double.parseDouble(priceMess[0]);
			}else if(priceMess.length==2){
				min=Double.parseDouble(priceMess[0]);
				max=Double.parseDouble(priceMess[1]);
				if(max<min){
					double t=max;
					max=min;
					min=t;
				}
			}else{
				fail(req,resp,"����ļ۸��ʽ�д���");
				return;
			}
			condition ="select * from mobileForm where"+"moble_price<="+max+"and mobile_price>="+min;
		}
		HttpSession session=req.getSession(true);
		Connection con=null;
		DataByPage dataBean=null;
		try{
			dataBean=(DataByPage)session.getAttribute("dataBean");
			if(dataBean==null){
				dataBean=new DataByPage();//����JavaBean����
				session.setAttribute("dataBean", dataBean);
			}
		}catch(Exception exp){
			dataBean=new DataByPage();//����JavaBean����
			session.setAttribute("dataBean", dataBean);
		}
		String uri="jdbc:mysql://127.0.0.1/mobileshop?"+
				"user=root&password=121803&characterEncoding=utf-8";
		try{
			con=DriverManager.getConnection(uri);
			Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);//?
			ResultSet rs=sql.executeQuery(condition);
			rowSet=new CachedRowSetImpl();//�����м�����
			rowSet.populate(rs);
			dataBean.setRowSet(rowSet);//�м����ݴ洢��dataBean��
			con.close();//�ر�����
		}catch(SQLException exp){}
		resp.sendRedirect("byPageShow.jsp");//�ض���byPageShow.jsp
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req,resp);
	}
	
	public void fail(HttpServletRequest request,HttpServletResponse response,String backNews){
		response.setContentType("text/html;charset=utf-8");
		try{
			PrintWriter out=response.getWriter();
			out.println("<html><body>");
			out.println("<h2>"+backNews+"</h2>");
			out.println("����:");
			out.println("<a href=searchMobile.jsp>��ѯ�ֻ�</a>");
			out.println("</body></html>");
		}catch(IOException exp){}
	}
}
