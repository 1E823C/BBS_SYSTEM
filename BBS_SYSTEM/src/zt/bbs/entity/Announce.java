package zt.bbs.entity;

import java.util.Date;

public class Announce {
	private int aid;        //公告编号
	private String announcement;    //公告内容
	private String anno_title;     //公告标题
	private Date announce_time;    //公告发布时间
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public String getAnnouncement() {
		return announcement;
	}
	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}
	public String getAnno_title() {
		return anno_title;
	}
	public void setAnno_title(String anno_title) {
		this.anno_title = anno_title;
	}
	public Date getAnnounce_time() {
		return announce_time;
	}
	public void setAnnounce_time(Date announce_time) {
		this.announce_time = announce_time;
	}
	
	
	

}
