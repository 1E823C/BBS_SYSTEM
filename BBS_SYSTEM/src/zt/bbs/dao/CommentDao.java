package zt.bbs.dao;

import java.sql.SQLException;
import java.util.List;

import zt.bbs.entity.Comment;

public interface CommentDao {
	public int addComment(Comment comment)throws SQLException;            //在一个帖子下添加评论
	public int getCCountById(int id)throws SQLException;          //获取帖子号下的评论数目
	public List<Comment> getAllCommentById(int id,int pageNo,int pageSize)throws SQLException;   //根据帖子ID获取所有的评论
	public int delCommentById(int id)throws SQLException;         //根据帖子ID号删除帖子评论
	public int getAllCCountByUname(String username)throws SQLException;          //根据用户名获取所有评论总数
	public int delCommentByCId(Comment comment)throws SQLException;          //根据cid删除评论
}
