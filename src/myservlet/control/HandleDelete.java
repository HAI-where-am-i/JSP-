package myservlet.control;

import mybean.data.Login;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
/*用户查看购物车后,会看到购物车中的物品,并且每个物品都后缀一个删除按钮,用户单击这个删除按钮,deleteServlet控制器从购物车中删除
 * 这件物品。*/
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
					resp.sendRedirect("login.jsp");//重定向到登陆页面
			LinkedList<String> car=loginBean.getCar();
			car.remove(delete);//从购物车中删除产品
		}catch(Exception exp){
			resp.sendRedirect("login.jsp");//重定向到登录页面
		}
		RequestDispatcher dispatcher=req.getRequestDispatcher("lookShoppingCar.jsp");
		dispatcher.forward(req, resp);//转发
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req,resp);
	}
}
