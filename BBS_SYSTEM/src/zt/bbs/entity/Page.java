package zt.bbs.entity;

import java.util.List;

public class Page {
	
	private int totalPageCount= 0;       //总页数，默认是0
	private int pageSize = 10 ;            //每页的大小，默认是10
	private int totalCount;              //总记录数
	private int currPageNo = 1;          //当前页码，默认第一页
	private List<Topic> ttList;        //每页帖子的集合
	private List<Comment> ccList;         //每页评论的集合；
	private List<User> uuList;         //每页用户的集合
	private List<Announce> aaList;       //每页公告的集合
	
	
	
	
	public List<Announce> getAaList() {
		return aaList;
	}
	public void setAaList(List<Announce> aaList) {
		this.aaList = aaList;
	}
	public List<User> getUuList() {
		return uuList;
	}
	public void setUuList(List<User> uuList) {
		this.uuList = uuList;
	}
	public int getTotalPageCount() {
		return totalPageCount;
	}
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		if(pageSize > 0)
		     this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		if(totalCount > 0) {       //计算总的页数
			this.totalCount = totalCount;
			totalPageCount = this.totalCount % pageSize == 0 ?(this.totalCount / pageSize):
				(this.totalCount / pageSize + 1);
		}
		
	}
	public int getCurrPageNo() {
		if (totalPageCount == 0)
            return 0;
		return currPageNo;
	}
	public void setCurrPageNo(int currPageNo) {
		if (currPageNo > 0)
		    this.currPageNo = currPageNo;
	}
	public List<Topic> getTtList() {
		return ttList;
	}
	public void setTtList(List<Topic> ttList) {
		this.ttList = ttList;
	}
	public List<Comment> getCcList() {
		return ccList;
	}
	public void setCcList(List<Comment> ccList) {
		this.ccList = ccList;
	}
	
	
	
}
