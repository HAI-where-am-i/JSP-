package myservlet.control;

import mybean.data.Login;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
//当用户在byPageShow.jsp页面或showDetail.jsp看到产品时,每个产品都后缀一个"添加到购物车"按钮
//用户单击该按钮后,putGoodsServlet控制器将该产品放入用户的购物车
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
				resp.sendRedirect("login.jsp");//重定向到登录页面
			LinkedList<String> car=loginBean.getCar();
			car.add(goods);//产品放入购物车
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
			out.println("<h2>"+goods+"放入购物车</h2>");
			out.println("查看购物车或返回<br>");
			out.println("<a href=lookShoppingCar.jsp>查看购物车</a>");
			out.println("<br><a href=byPageShow.jsp>主页</a>");
			out.println("</body></html>");
		}catch(IOException exp){}
	}
}
