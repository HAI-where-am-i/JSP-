package myservlet.control;

import mybean.data.DataByPage;
import com.sun.rowset.*;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
//searchMobile.jsp输入查询条件给servlet控制器searchByConditionServlet,SearchByConditionServlet控制器负责查询数据库,并将
//查询结果放到数据模型dataBean中,然后将用户重定向到byPageShow.jsp页面查看dataBean中的数据.
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
			fail(req,resp,"没有查询信息,无法查询");
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
				fail(req,resp,"输入的价格格式有错误");
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
				dataBean=new DataByPage();//创建JavaBean对象
				session.setAttribute("dataBean", dataBean);
			}
		}catch(Exception exp){
			dataBean=new DataByPage();//创建JavaBean对象
			session.setAttribute("dataBean", dataBean);
		}
		String uri="jdbc:mysql://127.0.0.1/mobileshop?"+
				"user=root&password=121803&characterEncoding=utf-8";
		try{
			con=DriverManager.getConnection(uri);
			Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);//?
			ResultSet rs=sql.executeQuery(condition);
			rowSet=new CachedRowSetImpl();//创建行集对象
			rowSet.populate(rs);
			dataBean.setRowSet(rowSet);//行集数据存储在dataBean中
			con.close();//关闭连接
		}catch(SQLException exp){}
		resp.sendRedirect("byPageShow.jsp");//重定向到byPageShow.jsp
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
			out.println("返回:");
			out.println("<a href=searchMobile.jsp>查询手机</a>");
			out.println("</body></html>");
		}catch(IOException exp){}
	}
}
