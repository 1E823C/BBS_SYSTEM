package zt.bbs.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zt.bbs.entity.Type;
import zt.bbs.service.TypeService;
import zt.bbs.service.impl.TypeServiceImpl;

public class TypeServlet extends HttpServlet{
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServerException, IOException{
			request.setCharacterEncoding("utf-8");
		    response.setContentType("text/html;charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        String contextPath = request.getContextPath();
	        String opr = request.getParameter("opr");
	        String opr2 = request.getParameter("opr2");
	        TypeService typeservice= new TypeServiceImpl();
	   try {  
		   
		       if("getAllType".equals(opr)) {              //获取所有的版块类型,显示在首页板块信息下
			      List<Type> list = null;
			      try {
				     list = typeservice.getAllType();
			      }catch(SQLException e) {
				     e.printStackTrace();
				     list= new ArrayList<Type>();
			       }
			            request.getSession().setAttribute("typeList", list);
			            request.getRequestDispatcher("../type.jsp").forward(request, response);             //转发到首页界面
		       }
		       
		       if("manGetType".equals(opr)) {             //管理员获取所有板块类型
		    	   List<Type> manTypeList= null;
		    	   try {
		    		     manTypeList = typeservice.getAllType();
				      }catch(SQLException e) {
					     e.printStackTrace();
					     manTypeList= new ArrayList<Type>();
				       }
		    	   		  request.getSession().setAttribute("manTypeList", manTypeList);
		    	   		  request.getRequestDispatcher("/manager/manageTypes.jsp").forward(request, response);     //转发到管理员界面
		    	   
		    	   
		       }
		       
		       if("manMdfTypeName".equals(opr)) {                //管理员修改板块名称
		    	   String name=request.getParameter("name");          //获取要修改成的板块名
		    	   int tid= Integer.parseInt(request.getParameter("tid"));            //获取要修改的板块ID
		    	   int result= 0;
		    	   result = typeservice.manMdfTypeName(tid, name);
		    	   if(result==0 ) {            //修改失败
		    		    out.print("<script type=\"text/javascript\">");
	                    out.print("alert(\"板块名称修改失败，请重试！\");");
	                    out.print("location.href=\"" + contextPath
	                            + "/util/type?opr=manGetType\";");
	                    out.print("</script>");
		    		
		    	   }else{
		    		    out.print("<script type=\"text/javascript\">");
	                    out.print("alert(\"板块名称修改成功！\");");
	                    out.print("location.href=\"" + contextPath
	                            + "/util/type?opr=manGetType\";");
	                    out.print("</script>");
		    		   
		    	   }
		       }
		       
		       if("manAddType".equals(opr)) {                 //管理员增加板块
		    	   
		    	   String type_name= request.getParameter("type_name");    //新增的板块名称
		    	   int result=0;
		    	   result= typeservice.manAddType(type_name);
		    	   if(result==0) {                    //添加板块失败
		    		    out.print("<script type=\"text/javascript\">");
	                    out.print("alert(\"板块添加失败，请重试！\");");
	                    out.print("location.href=\"" + contextPath
	                            + "/util/type?opr=manGetType\";");
	                    out.print("</script>");
		    		   
		    	   }else {                      //添加成功
		    		    out.print("<script type=\"text/javascript\">");
	                    out.print("alert(\"板块添加成功！\");");
	                    out.print("location.href=\"" + contextPath
	                            + "/util/type?opr=manGetType\";");
	                    out.print("</script>");
		    		   
		    		   
		    	   }
		    	   
		       }
		       
		      if("manDelType".equals(opr)) {                     //管理员删除板块
		    	  int type_id= Integer.parseInt(request.getParameter("type_id"));
		    	  int result = 0;
		    	  result = typeservice.manDelType(type_id);
		    	   if(result==0) {                    //删除板块失败
		    		    out.print("<script type=\"text/javascript\">");
	                    out.print("alert(\"板块删除失败，请重试！\");");
	                    out.print("location.href=\"" + contextPath
	                            + "/util/type?opr=manGetType\";");
	                    out.print("</script>");
		    		   
		    	   }else {                      //删除板块成功
		    		    out.print("<script type=\"text/javascript\">");
	                    out.print("alert(\"板块删除成功！\");");
	                    out.print("location.href=\"" + contextPath
	                            + "/util/type?opr=manGetType\";");
	                    out.print("</script>");
		    		 
		    	   }
		  	  
		      }
	   
	   
		       
		        
		       
		       
		       
		       
		       
	       }catch(Exception e) {
		   e.printStackTrace();
	   }
	      
	        out.flush();
	        out.close();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        this.doPost(request, response);
    }

}
