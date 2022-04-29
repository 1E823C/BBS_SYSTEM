package zt.bbs.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import zt.bbs.dao.impl.TypeDaoImpl;
import zt.bbs.entity.Type;
import zt.bbs.service.TypeService;
import zt.bbs.util.DatabaseUtil;

public class TypeServiceImpl implements TypeService{

	@Override
	public List<Type> getAllType() throws SQLException {            //获取所有帖子类型
		Connection conn = null;
		try {
			conn = DatabaseUtil.getConnection();
			return new TypeDaoImpl(conn).getAllType();
			
		}catch(SQLException e){
			e.printStackTrace();
			throw e;
		}finally {
			DatabaseUtil.closeAll(conn, null, null);
		}
	}

	@Override
	public int manMdfTypeName(int tid, String name) throws SQLException {                //管理员修改板块名称
		Connection conn=null;
		int result;
		try {
			conn= DatabaseUtil.getConnection();
			conn.setAutoCommit(false);
			result= new TypeDaoImpl(conn).manMdfTypeName(tid, name);
			conn.commit();
		}catch(SQLException e) {
			e.printStackTrace();
			if(conn !=null)
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
	public int manAddType(String type_name) throws SQLException {                    //管理员增加板块
		Connection conn= null;
		int result;
		try {
				conn = DatabaseUtil.getConnection();
				conn.setAutoCommit(false);
				result = new TypeDaoImpl(conn).manAddType(type_name);
			    conn.commit();
		}catch(SQLException e) {
			e.printStackTrace();
			if(conn !=null)
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
	public int manDelType(int tid) throws SQLException {                        //管理员删除板块
		Connection conn= null;
		int result;
		try {
			conn = DatabaseUtil.getConnection();
			conn.setAutoCommit(false);
			result = new TypeDaoImpl(conn).manDelType(tid);
		    conn.commit();
	}catch(SQLException e) {
		e.printStackTrace();
		if(conn !=null)
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
