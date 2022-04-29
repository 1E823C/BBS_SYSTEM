package zt.bbs.entity;
import java.util.Date;
public class Comment {
	private int cid;      //评论ID
	private String c_content;    //评论正文
	private int floor;    //评论楼层
	private Date comment_time;     //评论时间
	private String comment_username;      // 评论者
	private int comment_topic_id;     //评论所属帖子的ID
	private int status;        //评论是否被删除，0否1是
	private int comment_type_id;        //评论所在版块号
	private String comment_topic_title;         //评论所在的帖子标题
	private String comment_topic_username;       //评论所在的帖子的作者
	private int tcCount;          //评论所在帖子的总评论数
	private String comment_userPic;       //评论作者的头像，帖子详细页面使用
	
	
	
	public String getComment_userPic() {
		return comment_userPic;
	}
	public void setComment_userPic(String comment_userPic) {
		this.comment_userPic = comment_userPic;
	}
	public int getTcCount() {
		return tcCount;
	}
	public void setTcCount(int tcCount) {
		this.tcCount = tcCount;
	}
	public String getComment_topic_title() {
		return comment_topic_title;
	}
	public void setComment_topic_title(String comment_topic_title) {
		this.comment_topic_title = comment_topic_title;
	}
	public String getComment_topic_username() {
		return comment_topic_username;
	}
	public void setComment_topic_username(String comment_topic_username) {
		this.comment_topic_username = comment_topic_username;
	}
	public int getComment_type_id() {
		return comment_type_id;
	}
	public void setComment_type_id(int comment_type_id) {
		this.comment_type_id = comment_type_id;
	}
	public String getC_content() {
		return c_content;
	}
	public void setC_content(String c_content) {
		this.c_content = c_content;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public Date getComment_time() {
		return comment_time;
	}
	public void setComment_time(Date comment_time) {
		this.comment_time = comment_time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getComment_username() {
		return comment_username;
	}
	public void setComment_username(String comment_username) {
		this.comment_username = comment_username;
	}
	public int getComment_topic_id() {
		return comment_topic_id;
	}
	public void setComment_topic_id(int comment_topic_id) {
		this.comment_topic_id = comment_topic_id;
	}
	
	
}
