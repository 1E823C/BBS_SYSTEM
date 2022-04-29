package zt.bbs.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.chart.PieChart.Data;
import zt.bbs.dao.CommentDao;
import zt.bbs.dao.TopicDao;
import zt.bbs.dao.UserDao;
import zt.bbs.dao.impl.*;
import zt.bbs.entity.Comment;
import zt.bbs.entity.Page;
import zt.bbs.entity.Topic;
import zt.bbs.entity.User;
import zt.bbs.service.UserService;
import zt.bbs.util.DatabaseUtil;

public class UserServiceImpl implements UserService{

	@Override
	public User doLogin(User user) throws SQLException {              //登录
		Connection conn = null;
		try {
			conn = DatabaseUtil.getConnection();
			return new UserDaoImpl(conn).findUser(user.getUsername(), user.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			DatabaseUtil.closeAll(conn, null, null);
		}
		
		
	}

	@Override
	public boolean doRegister(User user) throws SQLException {                //注册
		Connection conn = null;
		boolean result = false;;
		
		try {
			conn = DatabaseUtil.getConnection();
			conn.setAutoCommit(false);
			result = new UserDaoImpl(conn).registerUser(user);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null)
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
				throw e;
		}finally {
			DatabaseUtil.closeAll(conn, null, null);
		}
		
		return result;
	}

	@Override
	public User getUserInfo(String uname) throws SQLException {                 //获取用户信息
		Connection conn=  null;
		 try {
	            conn = DatabaseUtil.getConnection();

	            return new UserDaoImpl(conn).getUInfo(uname);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw e;
	        } finally {
	            DatabaseUtil.closeAll(conn, null, null);
	        }
		
	}

	@Override
	public int updateUInfo(User user) throws SQLException {            //更新用户信息
		Connection conn = null;
        int result;
		try {
			conn=DatabaseUtil.getConnection();
			conn.setAutoCommit(false);
			result= new UserDaoImpl(conn).updateUInfo(user); 
			conn.commit();
		}catch(SQLException e) {
			e.printStackTrace();
			if (conn != null)
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
			throw e;
		}finally {
			DatabaseUtil.closeAll(conn, null, null);
		}
		
		return result;
	}

	@Override
	public int uploadUPic(User user) throws SQLException {                   //上传用户头像
		Connection  conn =null;
		int result;
		try {
			conn= DatabaseUtil.getConnection();
			conn.setAutoCommit(false);
			result= new UserDaoImpl(conn).uploadUPic(user);
			conn.commit();

		}catch(SQLException e) {
			e.printStackTrace();
			if(conn != null)
				try {
					conn.rollback();
				}catch(SQLException e1){
					e1.printStackTrace();
				}
			throw e;
			}finally {
				DatabaseUtil.closeAll(conn, null, null);
			}
		return result;
	}

	@Override
	public int updateUPass(User user) throws SQLException {                  //修改用户密码
		Connection conn = null;
		int result;
		try{
			conn= DatabaseUtil.getConnection();
			conn.setAutoCommit(false);
			result= new UserDaoImpl(conn).updateUPass(user);
			conn.commit();
		}catch(SQLException e) {
			e.printStackTrace();
			if(conn != null)
				try {
					conn.rollback();
				}catch(SQLException e1){
					e1.printStackTrace();
				}
			throw e;
			}finally {
				DatabaseUtil.closeAll(conn, null, null);
			}
		return result;
	}

	@Override
	public void getMyTopics(User user, Page pageObj) throws SQLException {               //获取用户下的帖子总数，分页显示
		Connection conn= null;
		TopicDao topicDao= null;
		UserDao userDao= null;
		try {
			conn=DatabaseUtil.getConnection();
			topicDao = new TopicDaoImpl(conn);
			userDao= new UserDaoImpl(conn);
			int totalCount =topicDao.getTotalCountByUsername(user.getUsername());
			pageObj.setTotalCount(totalCount);      //设置总数量，计算总页数
			if(totalCount > 0) {
				if(pageObj.getCurrPageNo() > pageObj.getTotalPageCount())
					pageObj.setCurrPageNo(pageObj.getTotalPageCount());
				List<Topic> ttList= userDao.getMyTopics(user, pageObj.getCurrPageNo(), pageObj.getPageSize());
				pageObj.setTtList(ttList);
			}else {
				pageObj.setCurrPageNo(0);
				pageObj.setTtList(new ArrayList<Topic>());
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
			
		}finally {
			DatabaseUtil.closeAll(conn, null, null);
		}
		
	}

	@Override
	public void getMyComments(User user, Page pageObj) throws SQLException {                 //获取用户名下的评论总数，分页显示
		Connection conn= null;
		CommentDao commentDao= null;
		UserDao userDao =null;
		try {
			
			conn=DatabaseUtil.getConnection();
			commentDao= new CommentDaoImpl(conn);
			userDao= new  UserDaoImpl(conn);
			int totalCount= commentDao.getAllCCountByUname(user.getUsername());
			pageObj.setTotalCount(totalCount);      //设置总数量，计算总页数
			if(totalCount > 0) {
				if(pageObj.getCurrPageNo() > pageObj.getTotalPageCount())
					pageObj.setCurrPageNo(pageObj.getTotalPageCount());
				List<Comment> ccList= userDao.getMyComments(user, pageObj.getCurrPageNo(), pageObj.getPageSize());
				pageObj.setCcList(ccList);
			}else {
				pageObj.setCurrPageNo(0);
				pageObj.setTtList(new ArrayList<Topic>());
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
			
		}finally {
			DatabaseUtil.closeAll(conn, null, null);
		}	
	}

	@Override
	public void manGetAllUserList(Page pageObj) throws SQLException {                      //管理员获取所有用户的信息
		Connection conn= null;
		
		try {
			conn= DatabaseUtil.getConnection();
			UserDao userDao=new UserDaoImpl(conn);
			int totalCount=userDao.getAllUCount();
			pageObj.setTotalCount(totalCount);        //设置总数量，计算总页数
			if(totalCount > 0){
				if(pageObj.getCurrPageNo() > pageObj.getTotalPageCount())
					pageObj.setCurrPageNo(pageObj.getTotalPageCount());
					List<User> uuList= userDao.manGetAllUserList(pageObj.getCurrPageNo(), pageObj.getPageSize());
					pageObj.setUuList(uuList);
			}else {
				
				pageObj.setCurrPageNo(0);
				pageObj.setUuList(new ArrayList<User>());
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			DatabaseUtil.closeAll(conn, null, null);
		}
		
	}

	@Override
	public int manMdfRole(String username) throws SQLException {                   //管理员。将用户设置为管理员
		Connection conn= null;
		int result;
		try {
			conn= DatabaseUtil.getConnection();
			conn.setAutoCommit(false);
			result = new UserDaoImpl(conn).manMdfRole(username);
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
	public int manMdfUsable(String usable, String username) throws SQLException {             //管理员：将用户封号和解封
		Connection conn = null;
		int result;
		try {
			conn=DatabaseUtil.getConnection();
			conn.setAutoCommit(false);
			result= new UserDaoImpl(conn).manMdfUsable(usable, username);
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
