package zt.bbs.service;

import java.sql.SQLException;
import java.util.List;

import zt.bbs.*;
import zt.bbs.entity.Page;
import zt.bbs.entity.Topic;
import zt.bbs.entity.User;

public interface UserService {
	public User doLogin(User user) throws SQLException;
	public boolean doRegister(User user) throws SQLException;
	public User getUserInfo(String uname)throws SQLException;
	public int updateUInfo(User user)throws SQLException;
	public int uploadUPic(User user)throws SQLException;
	public int updateUPass(User user)throws SQLException;
	public void getMyTopics(User user,Page pageObj)throws SQLException; //获取用户下的所有帖子并分页显示
	public void getMyComments(User user,Page pageObj)throws SQLException; //获取用户下的所有评论并分页显示
	public void manGetAllUserList(Page pageObj)throws SQLException;         //管理员，获取所有用户信息
	public int manMdfRole(String username)throws SQLException;        //管理员：权限赋予普通用户为管理员
	public int manMdfUsable(String usable, String username)throws SQLException;      //管理员：将普通用户封号和解封
	
}
