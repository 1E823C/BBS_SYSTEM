package zt.bbs.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import zt.bbs.entity.Type;
import zt.bbs.service.TypeService;
import zt.bbs.service.impl.TypeServiceImpl;

public class PieServlet extends HttpServlet{
	
	public PieServlet() {
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
		List<String> listname= new ArrayList<String>();
		List<Integer> listTCount = new ArrayList<Integer>(); 
		List<Integer> listCCount = new ArrayList<Integer>(); 
		for(Type tp:echartList) {
			listTCount.add(tp.getTopics_count());
			listCCount.add(tp.getComments_count());
			listname.add(tp.getName());
		}
			JSONObject json=new JSONObject();
			json.put("listTCount", listTCount);
			json.put("listname", listname);
			json.put("listCCount", listCCount);
			System.out.println(json.toString());
			out.println(json);
			out.flush();
			out.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
