package zt.bbs.entity;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
public class Topic {
	
	private int id;            //帖子的ID
	private String title;        //帖子标题
	private String content;       //帖子正文
	private int comment_count;      //评论数
	private Date topic_time;         //帖子创建时间
	private String topic_username;          //帖子作者
	private int type_id;            //帖子所属类型
	private String type_name;        //帖子所属类型名
	private String topic_userrole;    //帖子作者是否是管理员
	private int hit_count;             //帖子点击数   
	private String topic_userPic;     //帖子作者的头像，显示帖子信息时用

	
	
	public String getTopic_userPic() {
		return topic_userPic;
	}
	public void setTopic_userPic(String topic_userPic) {
		this.topic_userPic = topic_userPic;
	}
	public int getHit_count() {
		return hit_count;
	}
	public void setHit_count(int hit_count) {
		this.hit_count = hit_count;
	}
	public String getTopic_userrole() {
		return topic_userrole;
	}
	public void setTopic_userrole(String topic_userrole) {
		this.topic_userrole = topic_userrole;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getComment_count() {
		return comment_count;
	}
	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}
	public Date getTopic_time() {
		return topic_time;
	}
	public void setTopic_time(Date topic_time) {
		this.topic_time = topic_time;
	}
	public String getTopic_username() {
		return topic_username;
	}
	public void setTopic_username(String topic_username) {
		this.topic_username = topic_username;
	}
	public int getType_id() {
		return type_id;
	}
	public void setType_id(int type_id) {
		this.type_id = type_id;
	}
	
	
}
