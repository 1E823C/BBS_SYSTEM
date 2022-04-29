package zt.bbs.service;

import java.sql.SQLException;

import zt.bbs.entity.Comment;
import zt.bbs.entity.Page;

public interface CommentService {
	public int addComment(Comment comment)throws SQLException;      //添加评论
	public void getAllCommentById(int id, Page pageObj)throws SQLException;   //根据ID获取所有评论
	public int delCommentByCId(Comment comment)throws SQLException;        //根据cid删除评论
	
}
