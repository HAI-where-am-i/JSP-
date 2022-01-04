package myservlet.control;
import mybean.data.*;
import java.sql.*;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
//servlet控制器是registerServlet（见10.3.3节给出的web.xml配置文件）,负责链接数据库,将用户提交的信息写入到user表中
//并将用户转发到inputRegisterMess.jsp页面查看注册反馈信息
public class HandleRegister extends HttpServlet {
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public String handleString(String s)
	{
		try{
			byte bb[]=s.getBytes("utf-8");
			s=new String(bb);
		}catch(Exception e){
			
		}
		return s;
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		 request.setCharacterEncoding("utf-8"); 
//	    response.setContentType("text/html;charset=ISO-8859-1");
		String uri="jdbc:mysql://localhost/mobileshop?"+
									"user=root&password=121803&useUnicode=true&characterEncoding=utf-8";
		Connection con;
		PreparedStatement sql;
		Register userBean = new Register();//创建的JavaBean模式
		request.setAttribute("userBean",userBean);
		String logname=request.getParameter("logname").trim();
		String password=request.getParameter("password").trim();
		String again_password=request.getParameter("again_password").trim();
		String phone=request.getParameter("phone").trim();
		String address=request.getParameter("address").trim();
		String realname=request.getParameter("realname").trim();
		System.out.println(realname);
		if(logname==null)
			logname="";
		if(password==null)
			password="";
		if(!password.equals(again_password)){
			userBean.setBackNews("两次密码不同,注册失败!");
			RequestDispatcher dispatcher = request.getRequestDispatcher("inputRegisterMess.jsp");
			dispatcher.forward(request,response);//转发
			return;
		}
		boolean isLD=true;
		for(int i=0;i<logname.length();i++)
		{
			char c=logname.charAt(i);//这条语句是什么意思
			if(!((c<='z'&&c>='a')||(c<='Z'&&c>='A')||(c<='9'&&c>='0')))
				isLD=false;
		}
		boolean boo=logname.length()>0&&password.length()>0&&isLD;
		String backNews="";
		try{
			con=DriverManager.getConnection(uri);
			String insertCondition="INSERT INTO user VALUES(?,?,?,?,?)";
			sql=con.prepareStatement(insertCondition);
			if(boo)
			{
				sql.setString(1,logname);//sql.setString 是什么意思
				sql.setString(2,password);
				sql.setString(3,phone);
				sql.setString(4,handleString(address));
				sql.setString(5,handleString(realname));
				int m=sql.executeUpdate();
				System.out.println("对表中插入"+m+"条记录成功!");
				if(m!=0){
					backNews="注册成功";
					System.out.println("对表中插入"+m+"条记录成功!");
					userBean.setBackNews(backNews);
					userBean.setLogname(logname);
					userBean.setPhone((phone));
					userBean.setAddress((address));//罪魁祸首
					userBean.setRealname((realname));
				}
			}else{
				backNews="信息填写不完整或名字中有非法字符";
				userBean.setBackNews(backNews);
			}
			con.close();
		}catch(SQLException exp){
			backNews="该会员名已被使用,请你更换名字"+exp;
			userBean.setBackNews(backNews);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("inputRegisterMess.jsp");
		dispatcher.forward(request, response);//转发
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
}
