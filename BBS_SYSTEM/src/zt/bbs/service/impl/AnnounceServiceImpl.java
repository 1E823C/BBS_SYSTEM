package zt.bbs.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zt.bbs.dao.AnnounceDao;
import zt.bbs.dao.impl.AnnounceDaoImpl;
import zt.bbs.entity.Announce;
import zt.bbs.entity.Page;
import zt.bbs.service.AnnounceService;
import zt.bbs.util.DatabaseUtil;

public class AnnounceServiceImpl  implements AnnounceService{

	@Override
	public int addAnno(Announce anno) throws SQLException {                  //管理员添加公告
		Connection conn = null;
		int result;
		try {
			
			conn = DatabaseUtil.getConnection();
			conn.setAutoCommit(false);
			anno.setAnnounce_time(new Date());
			result= new AnnounceDaoImpl(conn).addAnno(anno);
			conn.commit();		
			
		}catch(SQLException e) {
			e.printStackTrace();
			if(conn !=null)
				try {
					conn.rollback();
				}catch(SQLException e1) {
					e1.printStackTrace();
					
				}
			throw e;
		}finally {
			
			DatabaseUtil.closeAll(conn, null, null);
		}	
		return result;
	}

	@Override
	public void getAllAnnos(Page pageObj) throws SQLException {                //分页获取所有公告，显示在后台界面
		Connection conn = null;
		try {
			conn = DatabaseUtil.getConnection();
			AnnounceDao announceDao = new AnnounceDaoImpl(conn);
			int totalCount = announceDao.getAnnoCount();
			pageObj.setTotalCount(totalCount);           //设置总数量，计算总页数
			if(totalCount > 0){
				if(pageObj.getCurrPageNo() > pageObj.getTotalPageCount())
					pageObj.setCurrPageNo(pageObj.getTotalPageCount());
				List<Announce> aaList = announceDao.getAllAnnos(pageObj.getCurrPageNo(), pageObj.getPageSize());
				pageObj.setAaList(aaList);
			}else {
				pageObj.setCurrPageNo(0);
				pageObj.setAaList(new ArrayList<Announce>());
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DatabaseUtil.closeAll(conn, null, null);
		}
		
	}

	@Override
	public Announce theAnno(int aid) throws SQLException {                      //获取公告的详细信息
		Connection conn = null;
		Announce anno = null;
		try {
			conn =DatabaseUtil.getConnection();
			anno = new AnnounceDaoImpl(conn).theAnno(aid);	
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DatabaseUtil.closeAll(conn, null, null);
		}
		
		return anno;
	}

	@Override
	public List<Announce> getIndexAnnos() throws SQLException {                    //获取最新的5条公告
		Connection conn =null;
		try {
			conn =DatabaseUtil.getConnection();
			return new AnnounceDaoImpl(conn).getIndexAnnos();
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			DatabaseUtil.closeAll(conn, null, null);
		}
		
	}

	@Override
	public int mdfAnno(Announce anno) throws SQLException {      //修改公告内容
		
		Connection conn =null;
		int result;
		try {
			conn = DatabaseUtil.getConnection();
			conn.setAutoCommit(false);
			anno.setAnnounce_time(new Date());
			
			result = new AnnounceDaoImpl(conn).mdfAnno(anno);
			conn.commit();
		}catch(SQLException e) {
			e.printStackTrace();
			if(conn != null)
				try {
					conn.rollback();
				}catch(SQLException e1) {
					e1.printStackTrace();
				}
			throw e;
		}finally {
			DatabaseUtil.closeAll(conn, null, null);
		}
		return result;
	}

	@Override
	public int delAnno(int aid) throws SQLException {                    //删除公告
		
		Connection conn = null;
		int result;
		try {
			conn= DatabaseUtil.getConnection();
			conn.setAutoCommit(false);
			result = new AnnounceDaoImpl(conn).delAnno(aid);
			conn.commit();
		}catch(SQLException e) {
			e.printStackTrace();
			if(conn != null)
				try {
					conn.rollback();
				}catch(SQLException e1) {
					e1.printStackTrace();
				}
			throw e;
		}finally {
			DatabaseUtil.closeAll(conn, null, null);
		}
		
		
		return result;
	}

	

}
