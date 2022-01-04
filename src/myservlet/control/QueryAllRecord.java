package myservlet.control;

import mybean.data.DataByPage;
import com.sun.rowset.*;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
//queryServlet 把从数据库mobileForm表中查询到的记录存到dataBean中,然后将用户重定向到byPageShow.jsp页面.
public class QueryAllRecord extends HttpServlet{
	CachedRowSetImpl rowSet = null;//???
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
		String idNumber=req.getParameter("fenleiNumber");
		if(idNumber==null)
			idNumber="0";
		int id=Integer.parseInt(idNumber);
		HttpSession session=req.getSession(true);
		Connection con=null;
		DataByPage dataBean=null;
		try{
			dataBean=(DataByPage)session.getAttribute("dataBean");
			if(dataBean==null){
				dataBean=new DataByPage();//创建JavaBean对象
				session.setAttribute("dataBean", dataBean);
			}
		}catch(Exception exp){
			dataBean=new DataByPage();
			session.setAttribute("dataBean", dataBean);
		}
		String uri="jdbc:mysql://127.0.0.1/mobileshop";
		try{
			con=DriverManager.getConnection(uri,"root","121803");
			Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=sql.executeQuery("select * from mobileForm where id = "+id);
			rowSet=new CachedRowSetImpl();//创建行集对象
			rowSet.populate(rs);
			dataBean.setRowSet(rowSet);//行集数据存储在dataBean中
			con.close();//关闭连接
		}catch(Exception exp){}
		resp.sendRedirect("byPageShow.jsp");//重定向到byPageShow.jsp
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req,resp);
	}
}
