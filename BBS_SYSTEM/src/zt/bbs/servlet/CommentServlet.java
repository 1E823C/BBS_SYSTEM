package zt.bbs.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zt.bbs.entity.Comment;
import zt.bbs.service.CommentService;
import zt.bbs.service.impl.CommentServiceImpl;


public class CommentServlet extends HttpServlet{
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServerException,IOException{
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out= response.getWriter();
		String contextPath = request.getContextPath();
		String opr = request.getParameter("opr");
		
		CommentService commentservice= new CommentServiceImpl();
		
		try {
				if("addComment".equals(opr)) {               //在帖子下添加评论
					Comment comment = new Comment();
					int result = 0;
					String c_content = request.getParameter("comment.content");
					String comment_topic_id = request.getParameter("topic_id");
					String comment_username= request.getParameter("c_username");
					String comment_type_id= request.getParameter("type_id");
					comment.setC_content(c_content);
					comment.setComment_topic_id(Integer.parseInt(comment_topic_id));
					comment.setComment_username(comment_username);
					comment.setComment_type_id(Integer.parseInt(comment_type_id));
					result =commentservice.addComment(comment);
					if(result == 0) {
						out.print("<script type=\"text/javascript\">");
	                    out.print("alert(\"评论失败，请重试！\");");
	                    out.print("location.href=\"" + contextPath
	                            + "/util/topic?opr=getTopic&opr2="+Integer.parseInt(comment_topic_id)+"\";");         //返回评论处
	                    out.print("</script>");
					
					}else {
						request.getSession().removeAttribute("hotTList");
	        			request.getSession().removeAttribute("freshTList");
						out.print("<script type=\"text/javascript\">");
	                    out.print("alert(\"评论成功！\");");
	                    out.print("location.href=\"" + contextPath
	                            + "/util/topic?opr=getTopic&opr2="+Integer.parseInt(comment_topic_id)+"\";");   //返回原帖
	                    out.print("</script>");

					}
					
					
				}
				
				if("delComment".equals(opr)) {                              //通过cid删除评论
					Comment comment=new Comment();
					String uname=(String)request.getSession().getAttribute("uname");           //获取登录好的用户名
					int  id= Integer.parseInt(request.getParameter("id"));                     //获取帖子id
					int type_id =Integer.parseInt(request.getParameter("type_id"));          //获取评论下的类型ID
					int cid=Integer.parseInt(request.getParameter("cid"));                  //获取评论的cid
					comment.setCid(cid);
					comment.setComment_type_id(type_id);
					comment.setComment_username(uname);
					comment.setComment_topic_id(id);
					int result=0;
					result=commentservice.delCommentByCId(comment);
					if(result==0) {                 //删除失败
						 out.print("<script type=\"text/javascript\">");
	                     out.print("alert(\"评论删除失败，请重试！\");");
	                     out.print("location.href=\"" + contextPath
	                    		   + "/user/userhome.jsp\";");
	                     out.print("</script>");
					}else {                 //删除成功
						out.print("<script type=\"text/javascript\">");
	                    out.print("alert(\"评论删除成功！\");");
	                    out.print("location.href=\"" + contextPath
	                               + "/user/userhome.jsp\";");
	                    out.print("</script>");
						
					}
				}
				
				
				
	//*********************************************以下是管理员的操作****************************************************************************************
				if("manDelComment".equals(opr)) {                              //管理员通过cid来删除评论
						Comment comment= new Comment();
						String uname=request.getParameter("uname");           //获取评论者名字
						int id = Integer.parseInt(request.getParameter("id"));      //获取当前帖子的ID
						int type_id = Integer.parseInt(request.getParameter("type_id"));      //获取当前评论的板块类型ID
						int cid=Integer.parseInt(request.getParameter("cid"));                  //获取评论的cid
						comment.setCid(cid);
						comment.setComment_type_id(type_id);
						comment.setComment_topic_id(id);
					    comment.setComment_username(uname);
					    int result=0;
					    result=commentservice.delCommentByCId(comment);
					    request.getSession().removeAttribute("hotTList");           //重新加载热帖10名
	        			request.getSession().removeAttribute("freshTList");         //重新加载新帖10名
					    out.print("<script type=\"text/javascript\">");
	                    out.print("alert(\"评论删除成功！\");");
	                    out.print("location.href=\"" + contextPath
	                               + "/util/topic?opr=getTopic&opr2="+id+"\";");           //返回原帖
	                    out.print("</script>");
					    
				}
				
				
			
			
			
		}catch(Exception e) {
        	e.printStackTrace();
        }
		
		out.flush();
        out.close();
		
	}
	
	public void doGET(HttpServletRequest request, HttpServletResponse response)throws ServerException,IOException{
		
		request.setCharacterEncoding("UTF-8");
        this.doPost(request, response);
	}
	
	
	
	

}
