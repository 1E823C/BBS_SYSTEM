package zt.bbs.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.chart.PieChart.Data;
import zt.bbs.util.*;

import zt.bbs.dao.BaseDao;
import zt.bbs.dao.UserDao;
import zt.bbs.entity.Comment;
import zt.bbs.entity.Topic;
import zt.bbs.entity.User;

public class UserDaoImpl extends BaseDao implements UserDao{
	public UserDaoImpl(Connection conn) {
		super(conn);
	}
	//用于登录时验证用户名是否存在
	public User findUser(String username,String password) throws SQLException{
		ResultSet rs = null;
		User user = null;
		String sql = "select * from user where username=? and password=?";
		try {
		rs= this.executeQuery(sql, username,password);
		if(rs.next()) {
			user = new User();
			user.setUsername(username);
			user.setPassword(password);
			user.setRole(rs.getString("role"));       //获得角色的权限
			user.setUsable(rs.getString("usable"));      //是否可登陆
		}
		} catch (SQLException e) {
			e.printStackTrace();
            throw e;
		} finally {
			DatabaseUtil.closeAll(null, null, rs);
		}
		return user;
	}
	//注册用户
	public boolean registerUser(User user)throws SQLException{
		ResultSet rs = null;
		
		String sql = "select * from user where username=? and password=?";
		
		try {
		rs= this.executeQuery(sql, new Object[] {user.getUsername(),user.getPassword()});
		}catch ( SQLException ee) {
			 ee.printStackTrace();
	            throw ee;
		}
		
		if(rs.next()) {
			return false;
		}
		else {
			
			String sqll="insert into user(username,password,email) values(?,?,?)";
			int result=0;
			try {
	            result = executeUpdate(sqll,new Object[]{user.getUsername(),user.getPassword(),user.getEmail()});
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw e;
	        }
				return true;
		}
	}
	//获取用户的个人信息
	@Override
	public User getUInfo(String uname) throws SQLException {
		
		ResultSet rs= null;
		String sql="select * from user where username=?"; 
		User user= null;
		try {
			rs=this.executeQuery(sql, uname);	
			if(rs.next()) {
			user= new User();
			user.setUsername(rs.getString("username"));
			user.setAge(rs.getString("age"));
			user.setComment_count(rs.getInt("comment_count"));
			user.setEmail(rs.getString("email"));
			user.setIntroduction(rs.getString("introduction"));
			user.setPicture(rs.getString("picture"));
			user.setSex(rs.getString("sex"));
			user.setTopic_count(rs.getInt("topic_count"));
			user.setRole(rs.getString("role"));	
			user.setUsable(rs.getString("usable"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			DatabaseUtil.closeAll(null, null, rs);
		}
		return user;
	}
	//更新用户的个人信息
	@Override
	public int updateUInfo(User user ) throws SQLException {
		String sql="update user set sex=?,email=?,age=?,introduction=? where username=? ";
		int result=0;
		try{
			result= executeUpdate(sql, new Object[] {user.getSex(),user.getEmail(),user.getAge(),user.getIntroduction(),user.getUsername()});
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	@Override
	//上传用户的个人头像
	public int uploadUPic(User user) throws SQLException {
		String sql= "update user set picture=? where username=? ";
		int result =0;
		try {
			
			result= executeUpdate(sql, new Object[] {user.getPicture(),user.getUsername()});
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		
		return result;
	}
	@Override
	public int updateUPass(User user) throws SQLException {
		String sql= "update user set password=? where username=?";
		int result= 0;
		try {
			result = executeUpdate(sql, new Object[] {user.getPassword(),user.getUsername()});
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	@Override
	public List<Topic> getMyTopics(User user,int pageNo,int pageSize) throws SQLException {                //获取用户的所有帖子   分页显示
		ResultSet  rs =null;
		Topic topic= null;
		List<Topic> list = new ArrayList<Topic>();
		String sql="select * from topic,type where topic.type_id = type.tid and topic.topic_username = ? order by topic_time asc limit ?,?";   //获取帖子。分页显示
		try {
		rs=this.executeQuery(sql, user.getUsername(),(pageNo - 1) * pageSize, pageSize);
		while(rs.next()) {
			topic= new Topic();
			topic.setId(rs.getInt("id"));
			topic.setType_name(rs.getString("name"));
			topic.setTitle(rs.getString("title"));
			topic.setComment_count(rs.getInt("comment_count"));
			topic.setTopic_time(rs.getTimestamp("topic_time"));      //获取时间戳
			topic.setTopic_username(rs.getString("topic_username"));
			topic.setType_id(rs.getInt("tid"));
			list.add(topic);
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
	public List<Comment> getMyComments(User user, int pageNo, int pageSize) throws SQLException {            //获取当前用户的所有评论 分页显示
		ResultSet rs =null;
		Comment comment= null;
		List<Comment> list= new ArrayList<Comment>();
		String sql="select * from comment,topic,type  where comment.comment_topic_id = topic.id and topic.type_id = type.tid and "+
		            "comment.comment_username=? order by comment_time asc limit ?,?";       //三表连接查询语句
		     //获取评论，分页显示
		try {
			rs= this.executeQuery(sql, user.getUsername(),(pageNo - 1) * pageSize, pageSize);
			while(rs.next()){
				comment= new Comment();
				comment.setCid(rs.getInt("cid"));
				comment.setComment_time(rs.getTimestamp("comment_time"));          //获取时间戳
				comment.setComment_username(rs.getString("comment_username"));
				comment.setComment_topic_id(rs.getInt("comment_topic_id"));
				comment.setC_content(rs.getString("c_content"));
				comment.setComment_topic_title(rs.getString("title"));
				comment.setComment_topic_username(rs.getString("topic_username"));
				comment.setTcCount(rs.getInt("comment_count"));
				comment.setComment_type_id(rs.getInt("tid"));
				list.add(comment);
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
	public List<User> manGetAllUserList(int pageNo, int pageSize) throws SQLException {                  //管理员：获取所有用户的信息
		ResultSet rs= null;
		User user= null;
		List<User> list= new ArrayList<User>();
		String sql="select * from user order by role desc limit ?,?";              //获取所有用户的信息，分页显示
		try {
			rs= this.executeQuery(sql, (pageNo - 1) * pageSize, pageSize);
			while(rs.next()) {
				user= new User();
				user.setUsername(rs.getString("username"));
				user.setAge(rs.getString("age"));
				user.setComment_count(rs.getInt("comment_count"));
				user.setEmail(rs.getString("email"));
				user.setIntroduction(rs.getString("introduction"));
				user.setPicture(rs.getString("picture"));
				user.setSex(rs.getString("sex"));
				user.setTopic_count(rs.getInt("topic_count"));
				user.setRole(rs.getString("role"));
				user.setUsable(rs.getString("usable"));
				list.add(user);
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
	public int getAllUCount() throws SQLException {                              //管理员：获取所有用户的个数
		ResultSet rs= null;
		int count= -1;
		String sql= "select count(1) from user";
		try {
			rs= this.executeQuery(sql);
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
	public int manMdfRole(String username) throws SQLException {                  //管理员：升级普通用户为管理员
		
		String sql="update user set role=1 where username=?";
		int result= 0;
		try {
			
			result= executeUpdate(sql, username);
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
			
		}
		return result;
	}
	@Override
	public int manMdfUsable(String usable, String username) throws SQLException {             //管理员：将普通用户封号和解封
		String sql="update user set usable=? where username=?";
		int result= 0;
		try {
			
			
			result= executeUpdate(sql, new Object[] {usable,username});
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
			
		}
		
		
		return result;
	}
		
		
}
