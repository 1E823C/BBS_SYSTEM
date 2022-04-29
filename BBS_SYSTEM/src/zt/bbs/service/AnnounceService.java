package zt.bbs.service;

import java.sql.SQLException;
import java.util.List;

import zt.bbs.entity.Announce;
import zt.bbs.entity.Page;

public interface AnnounceService {
	public int addAnno(Announce anno)throws SQLException;        //管理员添加公告
	public void getAllAnnos(Page pageObj)throws SQLException;      //分页获取所有的公告
	public Announce theAnno(int aid)throws SQLException;          //获取公告详细信息
	public List<Announce> getIndexAnnos()throws SQLException;      //首页的5条最新的公告
	public int mdfAnno(Announce anno) throws SQLException;          //修改公告内容
	public int delAnno(int aid)throws SQLException;            //删除公告
	
}
