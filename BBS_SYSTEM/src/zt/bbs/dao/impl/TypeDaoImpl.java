package zt.bbs.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import zt.bbs.dao.BaseDao;
import zt.bbs.dao.TypeDao;
import zt.bbs.entity.Type;
import zt.bbs.util.DatabaseUtil;

public class TypeDaoImpl extends BaseDao implements TypeDao{
	public TypeDaoImpl(Connection conn) {
		super(conn);
	}

	@Override
	public List<Type> getAllType() throws SQLException {             //获取所有的帖子类型
		List<Type> list = new ArrayList<Type>();
		ResultSet rs = null;
		String sql= "select * from type";
		try {
			rs= executeQuery(sql);
			Type type= null;
			while(rs.next()) {
				type= new Type();
				type.setTid(rs.getInt("tid"));
				type.setName(rs.getString("name"));
				type.setTopics_count(rs.getInt("topics_count"));
				type.setComments_count(rs.getInt("comments_count"));
				list.add(type);
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
	public int manMdfTypeName(int tid,String name) throws SQLException {                      //管理员更新板块名称
		String sql="update type set name=? where tid=?";
		int  result=0;
		try {
			result= executeUpdate(sql, new Object[] {name,tid});
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int manAddType(String type_name) throws SQLException {                        //管理员增加板块
		String sql="insert into type(name) values(?)";
		int result= 0;
		try {
			result= executeUpdate(sql, type_name);
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int manDelType(int tid) throws SQLException {                          //管理员删除板块
		String sql="delete from type where tid=?";
		int result= 0;
		try {
			result= executeUpdate(sql, tid);
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
	
		return result;
	}

	

}
