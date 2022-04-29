package zt.bbs.dao;

import java.sql.SQLException;
import java.util.List;

import zt.bbs.entity.*;

public interface UserDao {
	public User findUser(String uname, String password)  throws SQLException;         //查询是否存在用户
	public boolean registerUser(User user) throws SQLException;                   //注册用户
	public User getUInfo(String uname) throws SQLException;               //获取用户信息
	public int updateUInfo(User user) throws SQLException;            //更新用户信息
	public int uploadUPic(User user) throws SQLException;          //更新用户头像
	public int updateUPass(User user) throws SQLException;           //更新密码
	public List<Topic> getMyTopics(User user,int pageNo,int pageSize) throws SQLException;         //获取用户的所有帖子  分页显示
	public List<Comment> getMyComments(User user,int pageNo,int pageSize)throws SQLException;    //获取用户做的所有的评论 分页显示
	public List<User> manGetAllUserList(int pageNo,int pageSize)throws SQLException;        //管理员获取所有用户的信息
	public int getAllUCount()throws SQLException;            //管理员获取所有用户的个数
	public int manMdfRole(String  username)throws SQLException;        //管理员升级普通用户为管理员 
	public int manMdfUsable(String usable,String username)throws SQLException;      //管理员将普通用户进行封号和解封
	
}
