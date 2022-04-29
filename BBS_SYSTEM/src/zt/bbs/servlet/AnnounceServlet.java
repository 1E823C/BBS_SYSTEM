package zt.bbs.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zt.bbs.entity.Announce;
import zt.bbs.entity.Page;
import zt.bbs.service.AnnounceService;
import zt.bbs.service.impl.AnnounceServiceImpl;

public class AnnounceServlet extends HttpServlet{
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServerException,IOException{
		
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out= response.getWriter();
		String contextPath = request.getContextPath();
		String opr = request.getParameter("opr");
		
		AnnounceService announceService = new AnnounceServiceImpl();
		
		try {
			
			if("addAnno".equals(opr)) {             //添加公告
				Announce anno = null;
				String anno_title= request.getParameter("announce_title");    //公告标题
				String announcement =  request.getParameter("announcement");       //公告内容
				anno = new Announce();
				anno.setAnno_title(anno_title);
				anno.setAnnouncement(announcement);
				int result = 0;
                result= announceService.addAnno(anno);
                if(result==0) {           //添加失败
                	out.print("<script type=\"text/javascript\">");
                    out.print("alert(\"公告发布失败！\");");
                    out.print("location.href=\"" + contextPath
                            + "/util/anno?opr=getAnnoList\";");
                    out.print("</script>");
                	
                }else {                //添加成功
                	request.getSession().removeAttribute("indexAnno");
                	out.print("<script type=\"text/javascript\">");
                    out.print("alert(\"公告发布成功！\");");
                    out.print("location.href=\"" + contextPath
                            + "/util/anno?opr=getAnnoList\";");
                    out.print("</script>");
                }
				
			}
			
		   if("getAnnoList".equals(opr)) {                      //获取所有公告，显示在后台
			   List<Announce> annoList = null;
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
			   announceService.getAllAnnos(pageObj);          //分页显示所有公告
			   annoList = pageObj.getAaList();
			   request.getSession().setAttribute("annoList", annoList);          //公告列表
			   request.getSession().setAttribute("pageObj", pageObj);           //页码列表
			   request.getRequestDispatcher("/manager/manageAnnos.jsp").forward(request, response);       //转发到后台页面
		
		   }
		   
		   if("theAnno".equals(opr)) {                  //获取公告的详细信息
			   Announce anno =null;
			   int aid= Integer.parseInt(request.getParameter("aid"));              //获取公告ID
			   anno = announceService.theAnno(aid);
			   List<Announce> leftAnno = null;                //左侧公告栏
			   leftAnno = announceService.getIndexAnnos();
			   request.getSession().setAttribute("leftAnno", leftAnno);          //左侧的公告栏
			   request.getSession().setAttribute("anno", anno);             //公告信息
			   request.getRequestDispatcher("../announce.jsp").forward(request, response);//转发到详细页面
		
		   }
		   
		  if("indexAnno".equals(opr)) {                 //首页公告列表
			   List<Announce> indexAnno = null;
			   indexAnno = announceService.getIndexAnnos();
			   request.getSession().setAttribute("indexAnno",indexAnno);
			   request.getRequestDispatcher("../index.jsp").forward(request, response);  
		  }
		  
		 if("toMdfAnno".equals(opr)) {         //前往修改公告的页面
			 Announce mdfAnno = null;
			 int aid= Integer.parseInt(request.getParameter("aid"));             //获取要修改的公告的ID
			 mdfAnno = announceService.theAnno(aid);
			 request.getSession().setAttribute("mdfAnno",mdfAnno );         //帖子信息
			 request.getRequestDispatcher("/manager/mdfAnno.jsp").forward(request, response);        //转发到修改页面
			 
			 
		 }
		 
		 if("mdfAnno".equals(opr)) {               //提交修改公告的内容
			 Announce anno = new Announce();
			 int result = 0;
			 int aid = Integer.parseInt(request.getParameter("aid"));     //获取公告ID
			 String anno_title = request.getParameter("anno_title");
			 String announcement = request.getParameter("announcement");
			 anno.setAid(aid);
			 anno.setAnno_title(anno_title);
			 anno.setAnnouncement(announcement);
			 result = announceService.mdfAnno(anno);
			 
			 if(result == 0) {                  //修改公告失败
				 out.print("<script type=\"text/javascript\">");
                 out.print("alert(\"公告修改失败，请重试！\");");
                 out.print("location.href=\"" + contextPath
                         + "/util/anno?opr=getAnnoList\";");
                 out.print("</script>");
				 
			 }else {                        //修改公告成功
				 request.getSession().removeAttribute("indexAnno");             //重新加载首页公告内容
				 out.print("<script type=\"text/javascript\">");
                 out.print("alert(\"公告修改成功！\");");
                 out.print("location.href=\"" + contextPath
                         + "/util/anno?opr=getAnnoList\";");
                 out.print("</script>");
				 
			 }
			 
		 }
		 
		 if("delAnno".equals(opr)) {                //删除公告
			 int result = 0;
			 int aid = Integer.parseInt(request.getParameter("aid"));
			 result = announceService.delAnno(aid);
			 if(result==0) {            //删除失败
		     out.print("<script type=\"text/javascript\">");
             out.print("alert(\"公告删除失败，请重试！\");");
             out.print("location.href=\"" + contextPath
                     + "/util/anno?opr=getAnnoList\";");
             out.print("</script>");
			 
			 }else {                 //删除成功
				 request.getSession().removeAttribute("indexAnno");         //重新加载首页公告内容
				 out.print("<script type=\"text/javascript\">");
                 out.print("alert(\"公告删除成功！\");");
                 out.print("location.href=\"" + contextPath
                         + "/util/anno?opr=getAnnoList\";");
                 out.print("</script>");
			 }
			 
		 }
		 
		 if("allAnno".equals(opr)) {                     //所有公告
			 
			 List<Announce> allAnno = null;
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
	               announceService.getAllAnnos(pageObj);           //分页显示所有公告
			 	   allAnno = pageObj.getAaList();
			 	   request.getSession().setAttribute("allAnno", allAnno);          //公告列表
				   request.getSession().setAttribute("pageObj", pageObj);           //页码列表
				   request.getRequestDispatcher("/allAnno.jsp").forward(request, response);       //转发到所有公告页面
			 
			 
			 
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
