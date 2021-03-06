package mybean.data;
import com.sun.rowset.*;
//本模块的模型是 dataBean,声明周期是session,用于存储数据库中的记录。
//servlet控制器queryServlet把从数据库查询到的记录存储到dataBean中。
public class DataByPage {
	CachedRowSetImpl rowSet=null;//存储表中全部记录的行集对象
	int pageSize=1;//每页显示的记录数
	int totalPages=1;//分页后的总页数
	int currentPage=1;//当前显示页
	public void setRowSet(CachedRowSetImpl set){
		rowSet=set;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public CachedRowSetImpl getRowSet() {
		return rowSet;
	}
	
	
}
