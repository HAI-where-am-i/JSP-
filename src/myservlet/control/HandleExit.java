package myservlet.control;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
/*				�˳���¼ģ��
 * ֻ��һ��exitServlet��servlet������,exitServlet ���������û���session����,���µ�¼ʧЧ */
public class HandleExit extends HttpServlet {
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=req.getSession(true);
		session.invalidate();//�����û���session����
		resp.sendRedirect("index.jsp");//������ҳ
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req,resp);
	}
}
