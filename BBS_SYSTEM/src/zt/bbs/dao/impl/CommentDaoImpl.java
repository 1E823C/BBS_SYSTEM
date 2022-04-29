package zt.bbs.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import zt.bbs.dao.BaseDao;
import zt.bbs.dao.CommentDao;
import zt.bbs.entity.Comment;
import zt.bbs.util.DatabaseUtil;

public class CommentDaoImpl extends BaseDao implements CommentDao{

	public CommentDaoImpl(Connection conn) {
		super(conn);
	}

	@Override
	public int addComment(Comment comment) throws SQLException {               //在帖子下添加评论
		String sql="insert into comment(c_content,comment_time,comment_username,comment_topic_id,comment_type_id) values(?,?,?,?,?)";
		int result= 0;
		try {
			
			result = executeUpdate(sql, new Object[] {comment.getC_content(),comment.getComment_time(),
													  comment.getComment_username(),comment.getComment_topic_id(),comment.getComment_type_id()});
			
			if(result!=0) {
				
				String sql2= "update type set comments_count = comments_count + 1 where tid = ?";       //添加评论成功时，评论所在版块的评论总数+1
				int r2= executeUpdate(sql2, comment.getComment_type_id());
				String sql3="update topic set comment_count = comment_count + 1 where id=?";           //添加评论成功时，评论所在帖子号的评论数+1
				int r3= executeUpdate(sql3, comment.getComment_topic_id());
				String sql4 ="update user set comment_count = comment_count + 1 where username=?";       //添加评论成功时，评论者的评论数+1
				int r4 = executeUpdate(sql4, comment.getComment_username());
				
			}
			
		}catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
		return result;
	}

	@Override
	public int getCCountById(int id) throws SQLException {                     //获取指定帖子ID下的评论总数目
		ResultSet rs =null;
		String sql="select count(comment_topic_id) from comment where comment_topic_id =?";
		int count=-1;
		try {
				rs = this.executeQuery(sql, id);
				rs.next();
				count=rs.getInt(1);
			
		}catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DatabaseUtil.closeAll(null, null, rs);
        }
		return count;
	}

	@Override
	public List<Comment> getAllCommentById(int id, int pageNo, int pageSize) throws SQLException {            //根据帖子ID获取所有的评论
		List<Comment> list= new ArrayList<Comment>();
		ResultSet rs=  null;
		String sql="select * from comment,user where comment.comment_username = user.username and comment_topic_id = ? order by comment_time asc limit ?,?";
		try {
			rs= this.executeQuery(sql, id,(pageNo - 1) * pageSize, pageSize);
			Comment comment= null;
			while(rs.next()) {
				comment= new Comment();
				comment.setCid(rs.getInt("cid"));
				comment.setC_content(rs.getString("c_content"));
				comment.setComment_time(rs.getTimestamp("comment_time"));
				comment.setComment_username(rs.getString("comment_username"));
				comment.setComment_topic_id(rs.getInt("comment_topic_id"));
				comment.setComment_type_id(rs.getInt("comment_type_id"));
				comment.setComment_userPic(rs.getString("picture"));
				list.add(comment);
			}
			
			
		}catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DatabaseUtil.closeAll(null, null, rs);
        }
		
		return list;
	}

	@Override
	public int delCommentById(int id) throws SQLException {             //根据帖子ID删除帖子下的评论
		String sql="delete from comment where comment_topic_id=?";
		int result= 0;
		try {
			result=executeUpdate(sql, id);
		}catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
		
		return result;
	}

	@Override
	public int getAllCCountByUname(String username) throws SQLException {                 //根据用户名获取用户的所有评论数
		ResultSet rs=null;
		String sql="select count(comment_username) from comment where comment_username = ?";
		int count=-1;
		try {
			rs=this.executeQuery(sql, username);
			rs.next();
			count=rs.getInt(1);
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			DatabaseUtil.closeAll(null, null, rs);
		}
		return count;
	}

	@Override
	public int delCommentByCId(Comment comment) throws SQLException {                     //根据cid来删除评论
		String sql="delete from comment where cid=?";             
		String sql2="update type set comments_count = comments_count - 1 where tid=? ";      //删除评论时，评论所在的板块号总评论数-1
		String sql3= "update user set comment_count = comment_count - 1 where username=?";      //删除评论时，此评论者的评论数-1
		String sql4="update topic set comment_count = comment_count - 1 where  id =?";      //删除评论时，该帖子的评论总数-1      
		int result= 0;
		int r2;
		int r3;
		int r4;
		try {
			
			result= executeUpdate(sql, comment.getCid());
			
			if(result !=0) {
				r2=executeUpdate(sql2, comment.getComment_type_id());
				r3=executeUpdate(sql3, comment.getComment_username());
				r4=executeUpdate(sql4, comment.getComment_topic_id());
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	
	
}
