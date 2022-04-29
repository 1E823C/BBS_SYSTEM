package zt.bbs.service;

import java.sql.SQLException;
import java.util.List;

import zt.bbs.entity.Page;
import zt.bbs.entity.Topic;

public interface TopicService {
		public int addTopic(Topic topic)throws SQLException;
		public List<Topic> getHotTopics() throws SQLException;      
		public List<Topic> getFreshTopics()throws SQLException;
		public void getAllPageTopicsByTId(int tid , Page pageObj) throws SQLException;   //以类型ID获取所有类型下的帖子，分页显示
		public List<Topic> getAllTopics()throws SQLException;     //不能分页已弃用
		public List<Topic> getAllHotTopics()throws SQLException;    //不能分页已弃用
		public void  getAllPageTopics(Page pageObj) throws SQLException;          //获取所有帖子，分页显示
		public void getAllHotPageTopics(Page pageObj)throws SQLException;         //获取最热所有帖子，分页显示      
		public Topic getTopic(int id)throws SQLException;                    //获取帖子详细信息，并附带评论分页显示
		public int mdfHitCount(int id)throws SQLException;     //访问量统计，点击一次加一
		public int modifyTopic(Topic topic)throws SQLException;           //修改帖子信息
		public int deleteTopic(Topic topic)throws SQLException;          ////删除帖子信息
		public void searchTopicsByName(String word,Page pageObj)throws SQLException;       //根据搜索词模糊查询帖子
}
