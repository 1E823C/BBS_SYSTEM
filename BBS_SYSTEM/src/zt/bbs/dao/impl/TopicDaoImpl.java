package zt.bbs.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.scene.chart.PieChart.Data;
import zt.bbs.util.DatabaseUtil;

import zt.bbs.dao.BaseDao;
import zt.bbs.dao.TopicDao;
import zt.bbs.entity.Comment;
import zt.bbs.entity.Topic;

public class TopicDaoImpl extends BaseDao implements TopicDao{
	
	public TopicDaoImpl(Connection conn) {
		super(conn);
	}

	@Override
	public int addTopic(Topic topic) throws SQLException {                  //添加新贴
		String sql= "insert into topic(title,content,topic_time,topic_username,type_id) values(?,?,?,?,?) "; 
		int result =0;
		 try {
	            result = executeUpdate(sql, new  Object[] {topic.getTitle(),topic.getContent(),topic.getTopic_time(),topic.getTopic_username(),
	            		                                   topic.getType_id()});
	            if(result!=0) {
	            	
	            	String sql2="update type set topics_count = topics_count + 1 where tid=?";       //当发帖成功时，对应板块下的帖子总数+1
	            	int r2= executeUpdate(sql2, topic.getType_id());
	            	
	            	String sql3= "update user set topic_count = topic_count + 1 where username=?";    //发帖成功时，对应用户的发帖数+1
	            	int r3= executeUpdate(sql3, topic.getTopic_username());
	            	
	            }
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw e;
	        }
		return result;
	}

	@Override
	public List<Topic> getHotTopics() throws SQLException {        //按评论数的多少获取热帖版10名
		List<Topic> list =new ArrayList<Topic>();
		ResultSet rs= null;
		String sql= "select id , tid, title, comment_count, topic_time, topic_username, name from topic, type where topic.type_id = type.tid "
					+" order by comment_count desc limit 10";          //limit设置为10 只获取前10名
		try {
			rs = this.executeQuery(sql);
			Topic topic= null;
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
			
		}catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DatabaseUtil.closeAll(null, null, rs);
        }
		
		return list;
	}

	@Override
	public List<Topic> getFreshTopics() throws SQLException {           //按发布时间的早晚来获取前10名的帖子
		List<Topic> list = new ArrayList<Topic>();
		ResultSet rs = null;
		String sql = "select id ,tid, title, comment_count, topic_time, topic_username, name from topic, type where topic.type_id = type.tid "
					  + " order by topic_time desc limit 10 ";         //limit设置为10 只获取前10名
		try {
			rs= this.executeQuery(sql);
			Topic topic = null;
			while(rs.next()) {
				topic = new Topic();
				topic.setId(rs.getInt("id"));
				topic.setType_name(rs.getString("name"));
				topic.setTitle(rs.getString("title"));
				topic.setComment_count(rs.getInt("comment_count"));
				topic.setTopic_time(rs.getTimestamp("topic_time"));      //获取时间戳
				topic.setTopic_username(rs.getString("topic_username"));
				topic.setType_id(rs.getInt("tid"));
				list.add(topic);
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
	public List<Topic> getAllPageTopicsByTId(int tid,int pageNo,int pageSize) throws SQLException {          //按类型ID获取该类型下的所有帖子并且分页显示
		List<Topic> list= new ArrayList<Topic>();
		ResultSet rs = null;
		String sql = "select id ,tid,  title, comment_count , topic_time , topic_username , name from topic, type where topic.type_id = type.tid "
				      +" and type.tid = ? order by topic_time desc limit ? , ? ";    //获取指定ID下的所有帖子，并且帖子按发布顺序倒序排列。并分页显示
		try {
			rs= this.executeQuery(sql, tid, (pageNo - 1) * pageSize, pageSize);
			Topic topic = null;
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
	public List<Topic> getAllTopics() throws SQLException {                  //获取数据库下的所有帖子   无法分页以弃用
		
		List<Topic> list = new ArrayList<Topic>();
		ResultSet rs  = null;
		String sql =" select * from topic ,type where topic.type_id = type.tid order by  topic.topic_time desc ";
			try {
				 rs= this.executeQuery(sql);
				 Topic topic=  null;
				 while(rs.next()){
					 topic =  new Topic();
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
	public List<Topic> getAllHotTopics() throws SQLException {                //获取所有的热门帖子 既获取所有帖子按评论数排列  无法分页以弃用
		List<Topic> list = new ArrayList<Topic>();
		ResultSet rs= null;
		String sql= "select title, comment_count, topic_time, topic_username, name from topic, type where topic.type_id = type.tid "
				+ " order by comment_count desc ";
		try {
				rs= this.executeQuery(sql);
				Topic topic = null;
				while(rs.next()) {
					topic= new Topic();
					topic.setType_name(rs.getString("name"));
					topic.setTitle(rs.getString("title"));
					topic.setComment_count(rs.getInt("comment_count"));
					topic.setTopic_time(rs.getTimestamp("topic_time"));      //获取时间戳
					topic.setTopic_username(rs.getString("topic_username"));
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
	public List<Topic> getALlPageTopicsList(int pageNo, int pageSize) throws SQLException {         //分页获得所有帖子
		List<Topic>  list= new ArrayList<Topic>();
		ResultSet rs= null;
		String sql= "select * from topic ,type,user where topic.type_id = type.tid and topic.topic_username = user.username order by topic.topic_time desc limit ? , ? ";
		try {
			rs= this.executeQuery(sql, (pageNo - 1) * pageSize, pageSize );
			Topic topic = null;
			while(rs.next()) {
				topic= new Topic();
				topic.setId(rs.getInt("id"));
				topic.setType_name(rs.getString("name"));
				topic.setTitle(rs.getString("title"));
				topic.setComment_count(rs.getInt("comment_count"));
				topic.setTopic_time(rs.getTimestamp("topic_time"));      //获取时间戳
				topic.setTopic_username(rs.getString("topic_username"));
				topic.setType_id(rs.getInt("tid"));
				topic.setTopic_userrole(rs.getString("role"));        //获取用户的权限
				topic.setHit_count(rs.getInt("hit_count"));         //获取帖子的点击数
				list.add(topic);
				
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
	public int getTotalCount() throws SQLException {               //获取所有类型帖子的总数
		ResultSet rs= null;
		String sql = " select count(1) from topic ";         //获取所有帖子，返回总数
		int count= -1;
		try {
            rs = this.executeQuery(sql);
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DatabaseUtil.closeAll(null, null, rs);
        }
		return count;
	}
	
	@Override
	public int getTotalCountByTId(int tid) throws SQLException {              //获取指定ID类型下的所有帖子的数目
		ResultSet rs= null;
		String sql= "select count(type_id) from topic where type_id = ? ";
		int count= -1;
		try {
            rs = this.executeQuery(sql,tid);
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DatabaseUtil.closeAll(null, null, rs);
        }
		return count;
	}

	
	

	@Override
	public List<Topic> getAllHotPageTopics(int pageNo, int pageSize) throws SQLException {          //获取所有最热帖子，以评论数降序排列
		List<Topic>  list= new ArrayList<Topic>();
		ResultSet rs= null;
		String sql= "select id , tid, title, comment_count, topic_time, topic_username, name from topic, type where topic.type_id = type.tid "
				     +"order by comment_count desc limit ?,? ";   //以评论数降序排列
		try {
			rs= this.executeQuery(sql, (pageNo - 1) * pageSize, pageSize );
			Topic topic = null;
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
		}catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DatabaseUtil.closeAll(null, null, rs);
        }
		return list;
	}

	@Override
	public Topic getTopic(int id) throws SQLException {                       //获取帖子的详情信息，并加载评论分页显示
		Topic topic= null;
		ResultSet rs = null;
		String sql=" select * from topic,type,user  where topic.type_id= type.tid  and topic.topic_username = user.username and  id = ? ";
		try {
			rs= this.executeQuery(sql, id);
			while(rs.next()) {
				topic= new Topic();
				topic.setId(rs.getInt("id"));
				topic.setTitle(rs.getString("title"));
				topic.setContent(rs.getString("content"));
				topic.setTopic_time(rs.getTimestamp("topic_time"));
				topic.setTopic_username(rs.getString("topic_username"));
				topic.setType_id(rs.getInt("type_id"));
				topic.setType_name(rs.getString("name"));
				topic.setTopic_userPic(rs.getString("picture"));    //帖子作者的头像
			}
		}catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            DatabaseUtil.closeAll(null, null, rs);
        }
		return topic;
	}
	
	@Override
	public int mdfHitCount(int id) throws SQLException {                     //点击量统计，进入一次使此帖子的访问量+1
		String sql=" update topic set hit_count = hit_count + 1 where id=?";         //将访问量+1
		int count= -1;
		try {
			count= executeUpdate(sql, id);
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return count;
	}
	
	@Override
	public int getTotalCountByUsername(String username) throws SQLException {              //获取指定名字下的帖子总数
		ResultSet rs=null;
		String sql="select count(topic_username) from topic where topic_username = ?";	
		int count= -1;
		try {
			rs= this.executeQuery(sql, username);
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
	public int modifyTopic(Topic topic) throws SQLException {                      //修改指定ID的帖子的信息
		String sql="update topic set title= ? , content=? , topic_time=? where id=?";
		int result= 0;
		try {
			result= executeUpdate(sql, new Object[] {topic.getTitle(),topic.getContent(),topic.getTopic_time(),topic.getId()});
			
		}catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }	
		return result;
	}

	@Override
	public int deleteTopic(Topic topic) throws SQLException {               //根据指定ID删除帖子
		String sql="delete from topic where id=?";
		String sql2="update user set topic_count= topic_count - 1 where username=?";        //删除成功时，用户的帖子数-1
		String sql3="update type set topics_count= topics_count - 1 where tid=?";            //删除成功时，板块下的帖子数-1
		int result=0;
		int r2=0;
		int r3=0;
		try {
			result= executeUpdate(sql, topic.getId());
			
			if(result != 0) {
				r2=executeUpdate(sql2, topic.getTopic_username());
				r3=executeUpdate(sql3, topic.getType_id());
			}
		}catch (SQLException e) {
            e.printStackTrace();
            throw e;
		}
		return result;
	}

	@Override
	public List<Topic> searchTopicsByName(String word,int pageNo,int pageSize) throws SQLException {                   //根据搜索词模糊查询数据库中的帖子
		List<Topic> list= new ArrayList<Topic>();
		ResultSet rs= null;
		String sql="select * from topic,type where topic.type_id = type.tid and title like ? limit ?,?";          //模糊查询语句
		try {
			
			rs=this.executeQuery(sql, new Object[] {"%"+word+"%",(pageNo - 1) * pageSize, pageSize });                //CSDN大佬的总结，只有这样才能让数据库识别出来
			Topic topic= null;
			while(rs.next()) {
				topic = new Topic();
				topic.setId(rs.getInt("id"));
				topic.setType_name(rs.getString("name"));
				topic.setTitle(rs.getString("title"));
				topic.setContent(rs.getString("content"));
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
	public int searchTCountByName(String word) throws SQLException {                //根据搜索词获取搜索到的结果总数，给分页使用
		ResultSet rs=null;
		String sql="select count(*) from topic where title like ? ";
		int count= -1;
		try {
			rs= this.executeQuery(sql, new Object[] {"%"+word+"%"});        //CSDN大佬的总结，只有这样才能让数据库识别出来
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

	
}
