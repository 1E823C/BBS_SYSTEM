package zt.bbs.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.scene.chart.PieChart.Data;
import jdk.nashorn.internal.runtime.regexp.joni.ast.CClassNode;
import zt.bbs.util.DatabaseUtil;
import zt.bbs.dao.CommentDao;
import zt.bbs.dao.TopicDao;
import zt.bbs.dao.impl.CommentDaoImpl;
import zt.bbs.dao.impl.TopicDaoImpl;
import zt.bbs.entity.Comment;
import zt.bbs.entity.Page;
import zt.bbs.entity.Topic;
import zt.bbs.service.TopicService;

public class TopicServiceImpl implements TopicService{

	@Override
	public int addTopic(Topic topic) throws SQLException {              //添加帖子的service
		Connection conn =  null;
		int result;
		try {
			    conn = DatabaseUtil.getConnection();
	            conn.setAutoCommit(false);
	            topic.setTopic_time(new Date());
	            result= new TopicDaoImpl(conn).addTopic(topic);
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
	public List<Topic> getHotTopics() throws SQLException {                  //获取热帖版10名的service
		Connection conn = null;
			try {
				conn =  DatabaseUtil.getConnection();
				return new TopicDaoImpl(conn).getHotTopics();
			}catch(SQLException e) {
				e.printStackTrace();
				throw e;
				
			}finally {
				DatabaseUtil.closeAll(conn, null, null);
			}
		
	}

	@Override
	public List<Topic> getFreshTopics() throws SQLException {                   //获取新帖版前10名
		Connection conn = null;
		try {
			conn = DatabaseUtil.getConnection();
			return new TopicDaoImpl(conn).getFreshTopics();
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
			
		}finally {
			DatabaseUtil.closeAll(conn, null, null);
		}
		
	}

	@Override
	public void getAllPageTopicsByTId(int tid ,Page pageObj) throws SQLException {           //按指定的类型iD来获取类型下的所有帖子 并分页显示出来
		Connection conn =null;
		try {
			conn = DatabaseUtil.getConnection();
			TopicDao topicDao= new TopicDaoImpl(conn);
			int totalCount = topicDao.getTotalCountByTId(tid);
			pageObj.setTotalCount(totalCount);    //设置总数量，计算总页数
			if(totalCount > 0){
				if(pageObj.getCurrPageNo() > pageObj.getTotalPageCount())
					pageObj.setCurrPageNo(pageObj.getTotalPageCount());
				List<Topic> ttList = topicDao.getAllPageTopicsByTId(tid, pageObj.getCurrPageNo(), pageObj.getPageSize());
				pageObj.setTtList(ttList);
			} else {
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
	public List<Topic> getAllTopics() throws SQLException {   //获取数据库中所有的帖子  不能分页以弃用
		Connection conn= null;
		try {
			conn= DatabaseUtil.getConnection();
			return new TopicDaoImpl(conn).getAllTopics();
		
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
			
		}finally {
			DatabaseUtil.closeAll(conn, null, null);
		}
		
	}

	@Override
	public List<Topic> getAllHotTopics() throws SQLException {               //按评论数排列来获取所有的帖子  不能分页以弃用
		Connection conn = null;
		try {
			conn= DatabaseUtil.getConnection();
			return new TopicDaoImpl(conn).getAllHotTopics();
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			DatabaseUtil.closeAll(conn, null, null);
		}
		
	}

	@Override
	public void getAllPageTopics(Page pageObj) throws SQLException {         //分页获取所有帖子
		Connection  conn =null;
		try {
			conn = DatabaseUtil.getConnection();
			TopicDao topicDao= new TopicDaoImpl(conn);
			int totalCount = topicDao.getTotalCount();
			pageObj.setTotalCount(totalCount);    //设置总数量，计算总页数
			if(totalCount > 0){
				if(pageObj.getCurrPageNo() > pageObj.getTotalPageCount())
					pageObj.setCurrPageNo(pageObj.getTotalPageCount());
				List<Topic> ttList = topicDao.getALlPageTopicsList(pageObj.getCurrPageNo(), pageObj.getPageSize());
				pageObj.setTtList(ttList);
			} else {
				pageObj.setCurrPageNo(0);
				pageObj.setTtList(new ArrayList<Topic>());
			}
			
			
		}catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeAll(conn, null, null);
        }
		
		
	}

	@Override
	public void getAllHotPageTopics(Page pageObj) throws SQLException {                      //分页获取所有最热帖子，按评论数降序排列
		Connection  conn =null;
		try {
			conn = DatabaseUtil.getConnection();
			TopicDao topicDao= new TopicDaoImpl(conn);
			int totalCount = topicDao.getTotalCount();
			pageObj.setTotalCount(totalCount);    //设置总数量，计算总页数
			if(totalCount > 0){
				if(pageObj.getCurrPageNo() > pageObj.getTotalPageCount())
					pageObj.setCurrPageNo(pageObj.getTotalPageCount());
				List<Topic> ttList = topicDao.getAllHotPageTopics(pageObj.getCurrPageNo(), pageObj.getPageSize());
				pageObj.setTtList(ttList);
			} else {
				pageObj.setCurrPageNo(0);
				pageObj.setTtList(new ArrayList<Topic>());
			}
			
			
		}catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeAll(conn, null, null);
        }
		
		
	}

	@Override
	public Topic getTopic(int id) throws SQLException {                 //获取指定ID的帖子详细信息
		Connection conn =null;
		Topic topic= null;
			try {
			conn=DatabaseUtil.getConnection();
			 topic=  new TopicDaoImpl(conn).getTopic(id);
			}catch(Exception e) {
				e.printStackTrace();
				throw e;
			}finally{
				DatabaseUtil.closeAll(conn, null, null);
			}
		
		return topic;
	}
	
	@Override
	public int mdfHitCount(int id) throws SQLException {                    //帖子访问量统计
		Connection conn =null;
		TopicDao topicDao= null;
		int result=0;
		try {
			conn=DatabaseUtil.getConnection();
			conn.setAutoCommit(false);
			topicDao=new TopicDaoImpl(conn);
			result= topicDao.mdfHitCount(id);
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
			DatabaseUtil.closeAll(conn, null, null);
		}
		
		return result;
	}

	

	@Override
	public int modifyTopic(Topic topic) throws SQLException {             //根据帖子ID修改帖子信息
		Connection conn = null;
		int result;
		try {
			conn=DatabaseUtil.getConnection();
			conn.setAutoCommit(false);
			topic.setTopic_time(new Date());
			
			result= new TopicDaoImpl(conn).modifyTopic(topic);
			
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
        } finally {
            DatabaseUtil.closeAll(conn, null, null);
        }
		
		return result;
	}

	@Override
	public int deleteTopic(Topic topic) throws SQLException {                //根据ID删除帖子
		Connection conn = null;
		int result;
		try {
			conn=DatabaseUtil.getConnection();
			conn.setAutoCommit(false);
			//删除相关评论
			new CommentDaoImpl(conn).delCommentById(topic.getId());
			//删除帖子
			result= new TopicDaoImpl(conn).deleteTopic(topic);
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
	public void searchTopicsByName(String word, Page pageObj) throws SQLException {               //根据搜索词模糊查询帖子信息
		
		Connection conn=null;
		try {
		conn=DatabaseUtil.getConnection();
		TopicDao topicDao= new TopicDaoImpl(conn);
		int totalCount= topicDao.searchTCountByName(word);
		System.out.println(totalCount+"总数已经的出");
		pageObj.setTotalCount(totalCount);    //设置总数量，计算总页数
		if(totalCount > 0){
			if(pageObj.getCurrPageNo() > pageObj.getTotalPageCount())
				pageObj.setCurrPageNo(pageObj.getTotalPageCount());
			List<Topic> ttList= topicDao.searchTopicsByName(word, pageObj.getCurrPageNo(), pageObj.getPageSize());
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


}
