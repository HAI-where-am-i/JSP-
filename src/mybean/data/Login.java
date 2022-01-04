package mybean.data;
import java.util.*;
public class Login {
	String logname="",
			backNews="未登录";
	LinkedList<String> car; //用户的购物车
	public Login(){
		car=new LinkedList<String>();//创建了个car集合对象?
	}
	public String getLogname() {
		return logname;
	}
	public void setLogname(String logname) {
		this.logname = logname;
	}
	public String getBackNews() {
		return backNews;
	}
	public void setBackNews(String backNews) {
		this.backNews = backNews;
	}
	public LinkedList<String> getCar() {
		return car;
	}
	public void setCar(LinkedList<String> car) {
		this.car = car;
	}
	
}
