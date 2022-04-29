package zt.bbs.entity;
import java.io.Serializable;
public class Type {
	private int tid;        //类型ID
	private String name;   //类型名称
	private int topics_count;      //类型下的帖子总数
	private int comments_count;       //类型下的评论总数
	

	
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTopics_count() {
		return topics_count;
	}
	public void setTopics_count(int topics_count) {
		this.topics_count = topics_count;
	}
	public int getComments_count() {
		return comments_count;
	}
	public void setComments_count(int comments_count) {
		this.comments_count = comments_count;
	}
	
	
	
}
