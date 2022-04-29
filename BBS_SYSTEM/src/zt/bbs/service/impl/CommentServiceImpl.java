package zt.bbs.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.scene.chart.PieChart.Data;
import zt.bbs.dao.CommentDao;
import zt.bbs.dao.impl.CommentDaoImpl;
import zt.bbs.entity.Comment;
import zt.bbs.entity.Page;
import zt.bbs.service.CommentService;
import zt.bbs.util.DatabaseUtil;

public class CommentServiceImpl implements CommentService{

	@Override
	public int addComment(Comment comment) throws SQLException {                   //添加评论服务
		Connection conn =null;
		int result;
		try {
			conn=DatabaseUtil.getConnection();
			conn.setAutoCommit(false);
			comment.setComment_time(new Date());
			result= new CommentDaoImpl(conn).addComment(comment);
			conn.commit();
		}catch (SQLException e) {
            e.printStackTrace();
            if (conn != null)
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            throw e;
        } finally {
            DatabaseUtil.closeAll(conn, null, null);
        }
		return result;
	}

	@Override
	public void getAllCommentById(int id, Page pageObj) throws SQLException {             //根据ID获取所有评论，分页显示
		Connection conn= null;
		try {
			conn=DatabaseUtil.getConnection();
			CommentDao commentDao= new CommentDaoImpl(conn);
			
			int totalCount= commentDao.getCCountById(id);
			pageObj.setTotalCount(totalCount);    //设置总数量，计算总页数
			if(totalCount > 0) {
				if(pageObj.getCurrPageNo() > pageObj.getTotalPageCount())
					pageObj.setCurrPageNo(pageObj.getTotalPageCount());
				List<Comment> ccList= commentDao.getAllCommentById(id, pageObj.getCurrPageNo(), pageObj.getPageSize());
				pageObj.setCcList(ccList);
				
			}else {
				pageObj.setCurrPageNo(0);
				pageObj.setCcList(new ArrayList<Comment>());
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
		DatabaseUtil.closeAll(conn, null, null);
			}
	
	}

	@Override 
	public int delCommentByCId(Comment comment) throws SQLException {               //根据cid删除指定评论
		Connection conn=null;
		int result;
		try {
			conn=DatabaseUtil.getConnection();
			conn.setAutoCommit(false);
			result= new CommentDaoImpl(conn).delCommentByCId(comment);
			conn.commit();
			
		}catch(SQLException e) {
			e.printStackTrace();
			if(conn!=null)
			    try {
			    	conn.rollback();
			    }catch(SQLException e1) {
			    	e1.printStackTrace();
			    }
			throw e;
		}finally {
			DatabaseUtil
			.closeAll(conn, null, null);
			
		}
		
		return result;
	}
	
	
	
	
	
}
