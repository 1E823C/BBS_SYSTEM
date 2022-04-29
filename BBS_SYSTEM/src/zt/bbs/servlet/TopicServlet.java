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

import zt.bbs.entity.*;

import zt.bbs.entity.Topic;
import zt.bbs.entity.Type;
import zt.bbs.service.AnnounceService;
import zt.bbs.service.CommentService;
import zt.bbs.service.TopicService;
import zt.bbs.service.TypeService;
import zt.bbs.service.impl.AnnounceServiceImpl;
import zt.bbs.service.impl.CommentServiceImpl;
import zt.bbs.service.impl.TopicServiceImpl;
import zt.bbs.service.impl.TypeServiceImpl;

public class TopicServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServerException, IOException{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String contextPath = request.getContextPath();
        String opr = request.getParameter("opr");
        String opr2= request.getParameter("opr2");
        String tid= request.getParameter("tid");          //帖子类型ID
        
        TypeService typeservice= new TypeServiceImpl();       //发帖时获取版块类型用
        TopicService topicservice= new TopicServiceImpl();
        CommentService commentservice = new CommentServiceImpl();
        AnnounceService announceservice = new AnnounceServiceImpl();
        try {
        	
        	if("toAddTopic".equals(opr)) {      //当用户点击发帖时，进入这里，将加载出所有的版块，进入发帖界面
        		
        		List<Type>  list = null;
        		try {
				      list = typeservice.getAllType();
			      }catch(SQLException e) {
				     e.printStackTrace();
				     list= new ArrayList<Type>();
			       }
			            request.getSession().setAttribute("typeList", list);
			            request.getRequestDispatcher("../user/newTopic.jsp").forward(request, response);
		       }
        	if("addTopic".equals(opr)) {                         //提交发帖请求
        		Topic topic = new Topic();
        		int result=0;
        		String topic_username= (String)request.getSession().getAttribute("uname");
        		String title =request.getParameter("topic.title");
        		String content= request.getParameter("topic.content");
        		int type_id= Integer.parseInt(request.getParameter("topic.type"));
        		topic.setTitle(title);
        		topic.setTopic_username(topic_username);
        		topic.setContent(content);
        		topic.setType_id(type_id);
        		result = topicservice.addTopic(topic);
        		if(result==0) {
        			out.print("<script type=\"text/javascript\">");
                    out.print("alert(\"帖子发布失败，请重试！\");");
                    out.print("location.href=\"" + contextPath
                            + "/util/topic?opr=toAddTopic\";");
                    out.print("</script>");
        		}else {
        			request.getSession().removeAttribute("hotTList");
        			request.getSession().removeAttribute("freshTList");
        			out.print("<script type=\"text/javascript\">");
                    out.print("alert(\"发帖成功，回到首页！\");");
                    out.print("location.href=\"" + contextPath
                            + "/index.jsp\";");
                    out.print("</script>");
        		}
        	}
        	if("getTList".equals(opr)) {                    //获取首页的信息
        		List<Topic> hotTList = null;
        		hotTList = topicservice.getHotTopics();           //获取前10名热帖版
        		List<Topic> freshTList = null;
        		freshTList = topicservice.getFreshTopics();            //获取前10名新帖版
        		List<Announce> indexAnno = null;
        		indexAnno = announceservice.getIndexAnnos();           //获取首页公告信息
        		request.getSession().setAttribute("hotTList", hotTList);
        		request.getSession().setAttribute("freshTList", freshTList);
                request.getSession().setAttribute("indexAnno", indexAnno);      //首页公告信息列表
        		request.getRequestDispatcher("../index.jsp").forward(request, response);
        	}
        	if("getType".equals(opr)) {                    //根据类型获取类型下的帖子  不能分页显示以弃用
        		List<Topic> tList= null;
        	//	tList= topicservice.getAllTopicsByTId(Integer.parseInt(opr2));
        		request.getSession().setAttribute("tList", tList);
        		request.getRequestDispatcher("../type_topic.jsp").forward(request, response);
        			
        	}
        	if("getAllTopic".equals(opr)) {                     //获取所有的帖子   不能分页以弃用
        		List<Topic> allTList = null;
        		allTList= topicservice.getAllTopics();
        		request.getSession().setAttribute("allTList", allTList);
        		request.getRequestDispatcher("../allTopic.jsp").forward(request, response);		
        		
        	}
        	if("getAllHotTopic".equals(opr)) {                     //按评论量获取所有的帖子     不能分页以弃用
        		List<Topic> allHotTList = null;
        		allHotTList= topicservice.getAllHotTopics();
        		request.getSession().setAttribute("allHotTList", allHotTList);
        		request.getRequestDispatcher("../hotTopic.jsp").forward(request, response);
        				
        		
        	}
        	if("getAllPageTopics".equals(opr)) {             //分页获取所有帖子
        		List<Topic> allPTopics = null;
        		List<Topic> hotPTopics = null;
        		List<Topic> idPTopics = null;
        		String pageIndex = request.getParameter("pageIndex");        //获取页码信息
        		if (pageIndex == null                                         //如果用户没有指定页码，则默认第一页
                        || (pageIndex = pageIndex.trim()).length() == 0) {
                    pageIndex = "1";
                }
        		int currPageNo = Integer.parseInt(pageIndex);           //获取当前页码
                if (currPageNo < 1)
                    currPageNo = 1;
                Page pageObj = new Page();
                pageObj.setCurrPageNo(currPageNo); // 设置当前页码
                pageObj.setPageSize(10); // 设置每页显示条数 现在是10条
                if("All".equals(opr2)) {
                	topicservice.getAllPageTopics(pageObj);    //分页查询所有帖子
                    allPTopics = pageObj.getTtList();
                    request.getSession().setAttribute("allPTopics", allPTopics);
                    request.setAttribute("pageObj", pageObj);
                    request.getRequestDispatcher("../allTopic.jsp").forward(request, response);
                }else if("Hot".equals(opr2)) {                 //分页查询最热帖子
                	topicservice.getAllHotPageTopics(pageObj);
                	hotPTopics = pageObj.getTtList();
                	request.getSession().setAttribute("hotPTopics", hotPTopics);
                    request.setAttribute("pageObj", pageObj);
                    request.getRequestDispatcher("../hotTopic.jsp").forward(request, response);
                	
                }else if("type".equals(opr2)) {                    //显示此ID类型下的所有帖子，并分页显示
                	topicservice.getAllPageTopicsByTId(Integer.parseInt(tid), pageObj);
                	System.out.println(tid);
                	idPTopics= pageObj.getTtList();
                	request.getSession().setAttribute("idPTopics", idPTopics);
                	request.setAttribute("pageObj", pageObj);
                	request.getRequestDispatcher("../type_topic.jsp").forward(request, response);
                }
                
        	}
        	
        		if("getTopic".equals(opr)) {                  //获取帖子详细页面，显示评论并分页显示
        			Topic tInfo= null;
        			
        			List<Comment> tComment= new ArrayList<Comment>();
        			String pageIndex = request.getParameter("pageIndex");     //获取页码信息
        			if (pageIndex == null                                         //如果用户没有指定页码，则默认第一页
                            || (pageIndex = pageIndex.trim()).length() == 0) {
                        pageIndex = "1";
                    }
        			int currPageNo = Integer.parseInt(pageIndex);           //获取当前页码
                    if (currPageNo < 1)
                        currPageNo = 1;
                    Page pageObj = new Page();
                    pageObj.setCurrPageNo(currPageNo); // 设置当前页码
                    pageObj.setPageSize(10); // 设置每页显示条数 现在是10条
        			tInfo = topicservice.getTopic(Integer.parseInt(opr2));            //获取浏览器传过来的帖子ID号
        			if(pageIndex.equals("1")) {
        				topicservice.mdfHitCount(Integer.parseInt(opr2));          //进入页面。使页面访问数+1
        			}
        			commentservice.getAllCommentById(Integer.parseInt(opr2), pageObj);
        		    tComment = pageObj.getCcList();
        			request.getSession().setAttribute("tInfo", tInfo);         //帖子
        			request.getSession().setAttribute("tComment", tComment);    //评论
        			request.getSession().setAttribute("pageObj", pageObj);        //页数信息
        			request.getRequestDispatcher("../user/theTopic.jsp").forward(request, response);
        	
        		}
        		
        		if("toModifyTopic".equals(opr)) {                              //前往修改帖子详细页面
        			Topic mInfo= null;
        			mInfo= topicservice.getTopic(Integer.parseInt(opr2));
        			request.getSession().setAttribute("mInfo", mInfo);          //帖子信息列表
        			request.getSession().setAttribute("id", Integer.parseInt(opr2));     //设置id记录帖子的ID号
        			request.getRequestDispatcher("../user/modifyTopic.jsp").forward(request, response);      //转发到修改帖子信息页面
        		}
        		
        	   if("modifytopic".equals(opr)) {                        //提交修改帖子表单
        		   Topic topic =new Topic();
        		   int result= 0;
        		   int id= Integer.parseInt(request.getParameter("id"));     //获取帖子ID
        		   String title =request.getParameter("topic.title");
           		   String content= request.getParameter("topic.content");
           		   topic.setTitle(title);
           		   topic.setId(id);
           		   topic.setContent(content);
           		   result = topicservice.modifyTopic(topic);
           		if(result==0) {                     //修改帖子失败
           			             
           			   out.print("<script type=\"text/javascript\">");
                       out.print("alert(\"帖子修改失败，请重试！\");");
                       out.print("location.href=\"" + contextPath
                               + "/util/topic?opr=toModifyTopic&opr2="+id+"\";");
                       out.print("</script>");
           		}else {                                                  //帖子修改成功
           			
           			   request.getSession().removeAttribute("hotTList");           //重新加载热帖10名
           			   request.getSession().removeAttribute("freshTList");       //重新加载新帖10名
           			   out.print("<script type=\"text/javascript\">");
                       out.print("alert(\"帖子修改成功！\");");
                       out.print("location.href=\"" + contextPath
                               + "/user/userhome.jsp\";");
                       out.print("</script>");
           		}
        		   
        		   
        	   }
        	   if("deleteTopic".equals(opr)) {                         //通过帖子ID删除帖子和下属评论
        		   Topic topic= new Topic();
        		   String uname=(String)request.getSession().getAttribute("uname");           //获取登录好的用户名
        		   int id=Integer.parseInt(opr2);              //获取帖子ID
        		   int type_id= Integer.parseInt(tid);           //获取帖子类型ID
        		   topic.setId(id);
        		   topic.setType_id(type_id);
        		   topic.setTopic_username(uname);
        		   int result=0;
        		   result=topicservice.deleteTopic(topic);                 //删除帖子
        		   if(result==0) {               //删除失败
        			   out.print("<script type=\"text/javascript\">");
                       out.print("alert(\"帖子删除失败，请重试！\");");
                       out.print("location.href=\"" + contextPath
                    		   + "/user/userhome.jsp\";");
                       out.print("</script>");
        			 
        		   }else {                //删除成功
        			   
        			   request.getSession().removeAttribute("hotTList");           //重新加载热帖10名
           			   request.getSession().removeAttribute("freshTList");       //重新加载新帖10名
        			   out.print("<script type=\"text/javascript\">");
                       out.print("alert(\"帖子删除成功！\");");
                       out.print("location.href=\"" + contextPath
                               + "/user/userhome.jsp\";");
                       out.print("</script>");
        			 
        		   } 
        	   }
        	   
        	if("search".equals(opr)) {                        //模糊查询帖子信息 分页显示
        		List<Topic> searchTopics = null;
        		String pageIndex= request.getParameter("pageIndex");         //获取页码信息
        		String word =new String(request.getParameter("content").getBytes("utf-8"),"utf-8");   //获取搜索框中填写的内容,并转换成utf-8格式
        		if (pageIndex == null                                         //如果用户没有指定页码，则默认第一页
                        || (pageIndex = pageIndex.trim()).length() == 0) {
                    pageIndex = "1";
                }
        		int currPageNo = Integer.parseInt(pageIndex);           //获取当前页码
                if (currPageNo < 1)
                    currPageNo = 1;
                Page pageObj = new Page();
                pageObj.setCurrPageNo(currPageNo); // 设置当前页码
                pageObj.setPageSize(4); // 设置每页显示条数 现在是4条
        		topicservice.searchTopicsByName(word, pageObj);        //获得搜索的帖子列表
        		searchTopics= pageObj.getTtList();
        		request.getSession().setAttribute("word", word);        //搜索词
        		request.getSession().setAttribute("searchTopics", searchTopics);   //帖子列表
        		request.getSession().setAttribute("pageObj", pageObj);       //页面信息
        		request.getRequestDispatcher("../searchResult.jsp").forward(request, response);      //转发到搜索详情页面
        	}
        	
        	if("manGetAllTopics".equals(opr)) {                //管理员获取所有的用户帖子
        		List<Topic> manTopics= null;
        		String pageIndex = request.getParameter("pageIndex");     //获取页码信息
    			if (pageIndex == null                                         //如果用户没有指定页码，则默认第一页
                        || (pageIndex = pageIndex.trim()).length() == 0) {
                    pageIndex = "1";
                }
    			int currPageNo = Integer.parseInt(pageIndex);           //获取当前页码
                if (currPageNo < 1)
                    currPageNo = 1;
                Page pageObj = new Page();
                pageObj.setCurrPageNo(currPageNo); // 设置当前页码
                pageObj.setPageSize(10); // 设置每页显示条数 现在是10条
                topicservice.getAllPageTopics(pageObj);    //管理员分页查询所有帖子
                manTopics= pageObj.getTtList();
                request.getSession().setAttribute("manTopics", manTopics);   //帖子列表
                request.getSession().setAttribute("pageObj", pageObj);     //页面列表
                request.getRequestDispatcher("/manager/manageTopics.jsp").forward(request, response);   //转发到帖子管理页面
           
        	}
        	
        	if("manDelTopic".equals(opr)) {                       //管理员删除普通用户的帖子和下属评论
        		Topic topic= new Topic();
        		String topic_username = request.getParameter("topic_username");       //帖子作者
        		int id=Integer.parseInt(request.getParameter("id"));            //帖子编号
        		int type_id = Integer.parseInt(request.getParameter("type_id"));     //板块编号
        		topic.setId(id);
        		topic.setType_id(type_id);
        		topic.setTopic_username(topic_username);
        		int result=0;
        		result = topicservice.deleteTopic(topic);
        		if(result==0) {               //帖子删除失败
        			
        			out.print("<script type=\"text/javascript\">");
                    out.print("alert(\"帖子删除失败，请重试！\");");
                    out.print("location.href=\"" + contextPath
                 		   + "/util/topic?opr=manGetAllTopics\";");
                    out.print("</script>");
        		}else {                      //帖子删除成功
        			out.print("<script type=\"text/javascript\">");
                    out.print("alert(\"帖子删除成功！\");");
                    out.print("location.href=\"" + contextPath
                 		   + "/util/topic?opr=manGetAllTopics\";");
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
