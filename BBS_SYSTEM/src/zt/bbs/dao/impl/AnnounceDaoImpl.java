package zt.bbs.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import zt.bbs.dao.AnnounceDao;
import zt.bbs.dao.BaseDao;
import zt.bbs.entity.Announce;
import zt.bbs.util.DatabaseUtil;

public class AnnounceDaoImpl extends BaseDao implements AnnounceDao{
	
	public AnnounceDaoImpl(Connection conn) {
		super(conn);
	}

	
	@Override
	public int addAnno(Announce anno) throws SQLException {               //管理员添加公告
		String sql="insert into announce(announcement, anno_title,announce_time) values(?,?,?)";
		int result=0;
		try {
				result= executeUpdate(sql, new Object[] {anno.getAnnouncement(),anno.getAnno_title(),anno.getAnnounce_time()});
				
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}


	@Override
	public List<Announce> getAllAnnos(int pageNo, int pageSize) throws SQLException {               //获得所有的公告，显示在后台主界面上
		
		List<Announce> list = new ArrayList<Announce>();
		ResultSet rs=null;
		String sql ="select * from announce order by announce_time desc limit ?,?";
		try {
			rs= this.executeQuery(sql, (pageNo - 1) * pageSize,pageSize);
			Announce anno =null;
			while(rs.next()) {
				anno =new Announce();
				anno.setAid(rs.getInt("aid"));
				anno.setAnno_title(rs.getString("anno_title"));
				anno.setAnnouncement(rs.getString("announcement"));
				anno.setAnnounce_time(rs.getTimestamp("announce_time"));
				list.add(anno);
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			DatabaseUtil.closeAll(null, null, rs);
			
		}
		
		
		return list;
	}


	@Override
	public int getAnnoCount() throws SQLException {                      //获得所有公告个数
		
		ResultSet rs= null;
		String sql= "select count(1) from announce";
		int count= -1;
		try {
			
			rs= this.executeQuery(sql);
			rs.next();
			count= rs.getInt(1);
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			DatabaseUtil.closeAll(null, null, rs);
		}
		
		
		
		return count;
	}


	@Override
	public Announce theAnno(int aid) throws SQLException {                       //获取公告的详情信息
		Announce anno= null;
		ResultSet rs= null;
		String sql="select * from announce where aid=?";
		try {
			rs=this.executeQuery(sql, aid);
			while(rs.next()) {
				anno =new Announce();
				anno.setAid(rs.getInt("aid"));
				anno.setAnno_title(rs.getString("anno_title"));
				anno.setAnnouncement(rs.getString("announcement"));
				anno.setAnnounce_time(rs.getTimestamp("announce_time"));
				
			}
		
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			DatabaseUtil.closeAll(null, null, rs);
		}
		
		
		return anno;
	}


	@Override
	public List<Announce> getIndexAnnos() throws SQLException {               //首页获取5条最新的公告
		
		List<Announce> list = new ArrayList<Announce>();
		ResultSet rs=null;
		String sql ="select * from announce order by announce_time desc limit 5";
		try {
			rs= this.executeQuery(sql);
			Announce anno =null;
			while(rs.next()) {
				anno =new Announce();
				anno.setAid(rs.getInt("aid"));
				anno.setAnno_title(rs.getString("anno_title"));
				anno.setAnnouncement(rs.getString("announcement"));
				anno.setAnnounce_time(rs.getTimestamp("announce_time"));
				list.add(anno);
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			DatabaseUtil.closeAll(null, null, rs);
			
		}
		
		
		return list;
	
	}


	@Override
	public int mdfAnno(Announce anno) throws SQLException {                        //管理员修改公告
		String sql="update announce set anno_title=?, announcement=?, announce_time=? where aid=?";
		int result= 0;
		try {
				result = executeUpdate(sql, new Object[] {anno.getAnno_title(),anno.getAnnouncement(),anno.getAnnounce_time(),anno.getAid()});
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return result;
		
		
		
		
	}


	@Override
	public int delAnno(int aid) throws SQLException {                        //删除指定ID公告
		
		String sql= "delete from announce where aid=?";
		int result= 0;
		try {
			
			result= executeUpdate(sql, aid);
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		
		return result;
	}

}
