package zt.bbs.entity;

import java.io.Serializable;

public class User implements Serializable{
	private String username ;     //用户名
	private String password;      //密码
	private String sex;         //性别
	private String picture;        //头像
	private String email;           //邮箱
	private String age;            //年龄
	private int topic_count;          //用户帖子总数
	private int comment_count;     //用户评论总数
	private String introduction;      //用户个人简介
	private String role;           //用户的角色 0普通用户 1管理员
	private String usable;          //账号是否可用，是的话是1，否则是0
	
	
	public String getUsable() {
		return usable;
	}
	public void setUsable(String usable) {
		this.usable = usable;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getTopic_count() {
		return topic_count;
	}
	public void setTopic_count(int topic_count) {
		this.topic_count = topic_count;
	}
	public int getComment_count() {
		return comment_count;
	}
	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	

}
