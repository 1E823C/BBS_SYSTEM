package zt.bbs.dao;

import java.sql.SQLException;
import java.util.List;

import zt.bbs.entity.Topic;

public interface TopicDao {
	public int addTopic(Topic topic)throws SQLException;         //添加帖子
	public List<Topic> getHotTopics()throws SQLException;		//获取热帖版10名
	public List<Topic> getFreshTopics()throws SQLException;       //获取新帖版10名
	public List<Topic> getAllPageTopicsByTId(int tid ,int pageNo,int pageSize )throws SQLException;     //获取选定的类型下的所有帖子，并且分页显示
	public List<Topic> getAllTopics()throws SQLException;         //获取所有的帖子  ***不能分页显示已弃用
	public List<Topic> getAllHotTopics() throws SQLException;      //按评论量获取所有的帖子    ****不能分页显示已弃用
	public List<Topic> getALlPageTopicsList(int pageNo, int pageSize)throws SQLException;   //分页获取所有的帖子
	public int getTotalCount()throws SQLException;              //获取所有类型帖子的总数量
	public int getTotalCountByTId(int tid)throws SQLException;     //按类型的ID来获取该类型下的所有帖子
	public List<Topic> getAllHotPageTopics(int pageNo, int pageSize)throws SQLException;        //获取所有类型帖子，以评论数降序排列
	public Topic getTopic(int id)throws SQLException;               //根据帖子编号获取帖子的详情信息，并加载评论分页显示
	public int mdfHitCount(int id)throws SQLException;         //帖子访问数目统计函数 点击一次使访问量+1
	public int getTotalCountByUsername(String username)throws SQLException;         //获取指定姓名下的帖子总数
	public int modifyTopic(Topic topic)throws SQLException;             //修改指定ID的帖子的信息
	public  int deleteTopic(Topic topic)throws SQLException;             //指定ID删除一条帖子
	public List<Topic> searchTopicsByName(String word,int pageNo,int pageSize)throws SQLException;        //根据输入的单词模糊查询数据库中的帖子
	public int searchTCountByName(String word)throws SQLException;              //根据输入的单词模糊查询数据库中比对正确的帖子的总数
}
