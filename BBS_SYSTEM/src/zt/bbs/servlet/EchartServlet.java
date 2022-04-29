package zt.bbs.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.bcel.internal.generic.NEW;

import net.sf.json.JSONArray;
import zt.bbs.dao.TypeDao;
import zt.bbs.dao.impl.TypeDaoImpl;
import zt.bbs.entity.Type;
import zt.bbs.service.TypeService;
import zt.bbs.service.impl.TypeServiceImpl;
import zt.bbs.util.DatabaseUtil;

public class EchartServlet extends HttpServlet{
	
	public EchartServlet() {
		super();
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServerException,IOException{
		
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out= response.getWriter();
		String contextPath = request.getContextPath();
		try {
		TypeService typeservice= new TypeServiceImpl();
		ArrayList<Type> echartList = (ArrayList<Type>)typeservice.getAllType();
		JSONArray json = JSONArray.fromObject(echartList);
		out.println(json);
        out.flush();  
        out.close();  
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
