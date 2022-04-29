package zt.bbs.dao;

import java.sql.SQLException;
import java.util.List;

import zt.bbs.entity.Announce;

public interface AnnounceDao {
	public int addAnno(Announce anno)throws SQLException;           //管理员添加公告
	public List<Announce> getAllAnnos(int pageNo, int pageSize)throws SQLException;          //获得所有公告，显示在后台界面
	public int getAnnoCount()throws SQLException;                 //获取公告个数，给分页显示所用
	public Announce theAnno(int aid)throws SQLException;           //获取公告的详情
	public List<Announce> getIndexAnnos()throws SQLException;          //首页的公告列表，获取最新的5条公告
	public int mdfAnno(Announce anno)throws SQLException;    //修改公告
	public int delAnno(int aid)throws SQLException;            //删除指定公告

	
}
