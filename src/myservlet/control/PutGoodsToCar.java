package myservlet.control;

import mybean.data.Login;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
//���û���byPageShow.jspҳ���showDetail.jsp������Ʒʱ,ÿ����Ʒ����׺һ��"��ӵ����ﳵ"��ť
//�û������ð�ť��,putGoodsServlet���������ò�Ʒ�����û��Ĺ��ﳵ
public class PutGoodsToCar extends HttpServlet {
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		String goods=req.getParameter("java");
		System.out.println(goods);
		Login loginBean=null;
		HttpSession session=req.getSession(true);
		try{
			loginBean=(Login)session.getAttribute("loginBean");
			boolean b=loginBean.getLogname()==null||loginBean.getLogname().length()==0;
			if(b)
				resp.sendRedirect("login.jsp");//�ض��򵽵�¼ҳ��
			LinkedList<String> car=loginBean.getCar();
			car.add(goods);//��Ʒ���빺�ﳵ
			speakSomeMess(req,resp,goods);
		}catch(Exception exp){
			resp.sendRedirect("login.jsp");
		}
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req,resp);
	}
	public void speakSomeMess(HttpServletRequest request,HttpServletResponse response,String goods){
		response.setContentType("text/html;charset=utf-8");
		try{
			PrintWriter out = response.getWriter();
			out.print("<%@ include file='head.jsp' %>");
			out.println("<html><body>");
			out.println("<h2>"+goods+"���빺�ﳵ</h2>");
			out.println("�鿴���ﳵ�򷵻�<br>");
			out.println("<a href=lookShoppingCar.jsp>�鿴���ﳵ</a>");
			out.println("<br><a href=byPageShow.jsp>��ҳ</a>");
			out.println("</body></html>");
		}catch(IOException exp){}
	}
}
