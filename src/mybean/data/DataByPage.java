package mybean.data;
import com.sun.rowset.*;
//��ģ���ģ���� dataBean,����������session,���ڴ洢���ݿ��еļ�¼��
//servlet������queryServlet�Ѵ����ݿ��ѯ���ļ�¼�洢��dataBean�С�
public class DataByPage {
	CachedRowSetImpl rowSet=null;//�洢����ȫ����¼���м�����
	int pageSize=1;//ÿҳ��ʾ�ļ�¼��
	int totalPages=1;//��ҳ�����ҳ��
	int currentPage=1;//��ǰ��ʾҳ
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
