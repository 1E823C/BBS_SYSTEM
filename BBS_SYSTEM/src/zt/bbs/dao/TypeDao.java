package zt.bbs.dao;

import java.sql.SQLException;
import java.util.List;

import zt.bbs.entity.Type;

public interface TypeDao {
	public List<Type> getAllType()throws SQLException;              //获取所有版块
	public int manMdfTypeName(int tid,String name)throws SQLException;          //管理员更改板块名称
	public int manAddType(String type_name)throws SQLException;             //管理员增加板块
	public int manDelType(int tid)throws SQLException;                //管理员删除板块
	
	

}
