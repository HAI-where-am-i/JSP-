package myservlet.control;
import mybean.data.*;
import java.sql.*;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
//servlet��������registerServlet����10.3.3�ڸ�����web.xml�����ļ���,�����������ݿ�,���û��ύ����Ϣд�뵽user����
//�����û�ת����inputRegisterMess.jspҳ��鿴ע�ᷴ����Ϣ
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
		Register userBean = new Register();//������JavaBeanģʽ
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
			userBean.setBackNews("�������벻ͬ,ע��ʧ��!");
			RequestDispatcher dispatcher = request.getRequestDispatcher("inputRegisterMess.jsp");
			dispatcher.forward(request,response);//ת��
			return;
		}
		boolean isLD=true;
		for(int i=0;i<logname.length();i++)
		{
			char c=logname.charAt(i);//���������ʲô��˼
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
				sql.setString(1,logname);//sql.setString ��ʲô��˼
				sql.setString(2,password);
				sql.setString(3,phone);
				sql.setString(4,handleString(address));
				sql.setString(5,handleString(realname));
				int m=sql.executeUpdate();
				System.out.println("�Ա��в���"+m+"����¼�ɹ�!");
				if(m!=0){
					backNews="ע��ɹ�";
					System.out.println("�Ա��в���"+m+"����¼�ɹ�!");
					userBean.setBackNews(backNews);
					userBean.setLogname(logname);
					userBean.setPhone((phone));
					userBean.setAddress((address));//�������
					userBean.setRealname((realname));
				}
			}else{
				backNews="��Ϣ��д���������������зǷ��ַ�";
				userBean.setBackNews(backNews);
			}
			con.close();
		}catch(SQLException exp){
			backNews="�û�Ա���ѱ�ʹ��,�����������"+exp;
			userBean.setBackNews(backNews);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("inputRegisterMess.jsp");
		dispatcher.forward(request, response);//ת��
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
}
