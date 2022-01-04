package myservlet.control;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
/*				退出登录模块
 * 只有一个exitServlet的servlet控制器,exitServlet 负责销毁用户的session对象,导致登录失效 */
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
		session.invalidate();//销毁用户的session对象
		resp.sendRedirect("index.jsp");//返回主页
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req,resp);
	}
}
