package myservlet.control;

import mybean.data.Login;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
/*�û��鿴���ﳵ��,�ῴ�����ﳵ�е���Ʒ,����ÿ����Ʒ����׺һ��ɾ����ť,�û��������ɾ����ť,deleteServlet�������ӹ��ﳵ��ɾ��
 * �����Ʒ��*/
public class HandleDelete extends HttpServlet{
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		String delete=req.getParameter("delete");
		Login loginBean=null;
		HttpSession session=req.getSession(true);
		try{
			loginBean=(Login)session.getAttribute("loginBean");
			boolean b = loginBean.getLogname()==null||loginBean.getLogname().length()==0;
			if(b)
					resp.sendRedirect("login.jsp");//�ض��򵽵�½ҳ��
			LinkedList<String> car=loginBean.getCar();
			car.remove(delete);//�ӹ��ﳵ��ɾ����Ʒ
		}catch(Exception exp){
			resp.sendRedirect("login.jsp");//�ض��򵽵�¼ҳ��
		}
		RequestDispatcher dispatcher=req.getRequestDispatcher("lookShoppingCar.jsp");
		dispatcher.forward(req, resp);//ת��
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req,resp);
	}
}
