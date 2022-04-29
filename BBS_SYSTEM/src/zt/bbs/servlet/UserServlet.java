package zt.bbs.servlet;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import zt.bbs.entity.Comment;
import zt.bbs.entity.Page;
import zt.bbs.entity.Topic;
import zt.bbs.entity.User;
import zt.bbs.service.TopicService;
import zt.bbs.service.UserService;
import zt.bbs.service.impl.TopicServiceImpl;
import zt.bbs.service.impl.UserServiceImpl;

public class UserServlet extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String contextPath = request.getContextPath();
		String opr = request.getParameter("opr");
		String opr2= request.getParameter("opr2");
		UserService userService = new UserServiceImpl();
		TopicService topicService = new TopicServiceImpl();
		try {

            if ("login".equals(opr)) {  //用户登录

                String username = request.getParameter("user.username");
                String password = request.getParameter("user.password");

                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user = userService.doLogin(user);
                   
                if (user == null) {
                    out.print("<script type=\"text/javascript\">");
                    out.print("alert(\"用户名密码错误，请重新登录\");");
                    out.print("open(\"" + contextPath
                            + "/login.jsp\",\"_self\");");
                    out.print("</script>");
                } else {
                	String usable = user.getUsable();     //查看用户是否被封号
                	if(usable.equals("1")) {                  //未被封号
                		String userrole=user.getRole();       //获取登录用户的权限
                        request.getSession().setAttribute("uname", username);
                        request.getSession().setAttribute("userrole", userrole);
                        request.getRequestDispatcher("../index.jsp").forward(request, response);
                		
                	}else {                    //被封号
                		out.print("<script type=\"text/javascript\">");
                        out.print("alert(\"此账号被封停，请联系管理员解决！\");");
                        out.print("open(\"" + contextPath
                                + "/login.jsp\",\"_self\");");
                        out.print("</script>");
                		
                	}
                	
                   
                }
            }
            
            if("logout".equals(opr)) {    //用户注销
         	   request.getSession().removeAttribute("uname");        //去除用户名属性
         	  request.getSession().removeAttribute("userrole");     //去除用户角色属性
         	   response.sendRedirect("../index.jsp");
         	   
            }
            
            if("register".equals(opr)) {   //用户注册
            	  
            	String username= request.getParameter("user.username");
            	String password= request.getParameter("user.password");
            	String email= request.getParameter("user.email");
            	User user =  new User();
            	user.setUsername(username);
            	user.setPassword(password);
            	user.setEmail(email);
            	if(!userService.doRegister(user)) {
            		out.print("<script type=\"text/javascript\">");
                    out.print("alert(\"用户名已存在，请重新注册！\");");
                    out.print("open(\"" + contextPath
                            + "/register.jsp\",\"_self\");");
                    out.print("</script>");
            	}else {
            		out.print("<script type=\"text/javascript\">");
                    out.print("alert(\"注册成功，即将前往登录页面！\");");
                    out.print("open(\"" + contextPath
                            + "/login.jsp\",\"_self\");");
                    out.print("</script>");
            	}
            }
           if("getUInfo".equals(opr)) {     //获取用户信息
        	   String username= (String)request.getSession().getAttribute("uname");//获取JSP中的登录用户名
        	   User user= new User();
        	   user = userService.getUserInfo(username);
        	   request.getSession().setAttribute("userInfo", user);
        	   request.getRequestDispatcher("../user/userhome.jsp").forward(request, response);//转发到个人中心首页
           }
           if("otherUInfo".equals(opr)) {        //获取其他用户的信息      不能在IE浏览器下进行操作，因为IE浏览器的兼容性不好
        	   String username = opr2;        //获取此用户的用户名
        	   User user =new  User();
        	   user = userService.getUserInfo(username);
        	   request.getSession().setAttribute("otherUInfo", user);           //设置attribute        	   
        	   request.getRequestDispatcher("../user/otherUser.jsp").forward(request, response);        //转发到其他用户详细信息页面
        	
           }
           if("updateUInfo".equals(opr)) {    //更新用户信息
        	   String username= (String)request.getSession().getAttribute("uname");//获取JSP中的登录用户名
        	   User user= new User();
        	   user = userService.getUserInfo(username);
        	   request.getSession().setAttribute("userInfo", user);
        	   request.getRequestDispatcher("../user/updateInfo.jsp").forward(request, response);//转发到修改资料页
           }
           if("submitUpdate".equals(opr)) {   //提交用户的更新请求
        	   String username= (String)request.getSession().getAttribute("uname");//获取JSP中的登录用户名
        	   String sex = request.getParameter("userSex");
        	   String email= request.getParameter("userEmail");
        	   String age= request.getParameter("userAge");
        	   String introduction = request.getParameter("userIntro");
        	   User user= new User();
        	   user.setUsername(username);
        	   user.setSex(sex);
        	   user.setEmail(email);
        	   user.setAge(age);
        	   user.setIntroduction(introduction);
        	   int result=0;
        	   result=userService.updateUInfo(user);
        	   if(result==1) {
        		   
        		   out.print("<script type=\"text/javascript\">");
                   out.print("alert(\"修改成功，前往个人中心首页！\");");
                   out.print("open(\"" + contextPath
                           + "/util/user?opr=getUInfo\",\"_self\");");
        		   out.print("</script>");
        		   
        	   }else {
        		   out.print("<script type=\"text/javascript\">");
                   out.print("alert(\"修改失败，请检查你输入的数据！\");");
                   out.print("open(\"" + contextPath
                           + "/util/user?opr=updateUInfo\",\"_self\");");
        		   out.print("</script>");
        		   
        	   }
        	   
           } 
       
           if("uploadUPic".equals(opr)) {          //上传用户的头像
        	  
        	   String username= (String)request.getSession().getAttribute("uname");//获取JSP中的登录用户名
        	   User user= new User();
        	   user.setUsername(username);
        	   boolean isMultipart = ServletFileUpload
                       .isMultipartContent(request);
               // 上传文件的存储路径（服务器文件系统上的绝对文件路径）
        	   String uploadFilePath = "d:\\Users\\12487\\eclipse-workspace\\BBS_SYSTEM\\WebRoot\\userPicture";
        	   if(isMultipart) {
        		   FileItemFactory factory = new DiskFileItemFactory();
                   ServletFileUpload upload = new ServletFileUpload(factory);
                   upload.setSizeMax(1024 * 1024 * 5);
                // 解析form表单中所有文件
        		   try {
        			   List<FileItem> items = upload.parseRequest(request);
                       Iterator<FileItem> iter = items.iterator();
                       FileItem item = null;
                       boolean isUnallowedType = false; // 是否为不允许的类型
                       File saveFile = null; // 上传并保存的文件
                       while (iter.hasNext()) { // 依次处理每个文件
                           	   item = iter.next();
                               String fileName = item.getName();
                               if (fileName.length() > 0) {
                                   List<String> filType = Arrays.asList("png",
                                           "jpg", "jpeg","bmp");
                                   int index = fileName.lastIndexOf(".");
                                   String ext = index == -1 ? "" : fileName
                                           .substring(index + 1).toLowerCase();
                                   if (filType.contains(ext)) { // 判断文件类型是否在允许范围内
                                       File fullFile = new File(item.getName());
                                       saveFile = new File(uploadFilePath,
                                               fullFile.getName());
                                       item.write(saveFile);
                                       String temp1 = uploadFilePath+File.pathSeparator+fullFile.getName();
                                   
                                       
                                       String picture="./userPicture/"+fullFile.getName();  //改变字符串，以让数据库能读出
                
                                       user.setPicture(picture);
                                   } else {
                                       isUnallowedType = true;
                                   }
                               }
                           }
                       
        		   }catch (FileUploadBase.SizeLimitExceededException ex) {
                       out.print("<script type=\"text/javascript\">");
                       out.print("alert(\"图片上传失败，文件的最大限制是：5MB\");");
                       out.print("open(\"" + contextPath
                               + "/util/user?opr=updateUInfo\",\"_self\");");
                       out.print("</script>");
                   }
        	   }
        	   int result=0;
        	   result= userService.uploadUPic(user);
        	   if(result==1) {
        		   out.print("<script type=\"text/javascript\">");
                   out.print("alert(\"上传成功，前往个人中心首页！\");");
                   out.print("open(\"" + contextPath
                           + "/util/user?opr=getUInfo\",\"_self\");");
        		   out.print("</script>");
        		    
        	   }else {
        		   out.print("<script type=\"text/javascript\">");
                   out.print("alert(\"上传失败，请重新上传！\");");
                   out.print("open(\"" + contextPath
                           + "/util/user?opr=updateUInfo\",\"_self\");");
        		   out.print("</script>");
        		   
        	   }    
        	   
           }
           
           if("updateUPass".equals(opr)) {            //修改用户的密码
        	   String username= (String)request.getSession().getAttribute("uname");//获取JSP中的登录用户名
        	   User user = new User(); 
        	   String oldPass = request.getParameter("oldPass"); 
        	   user.setUsername(username);
        	   user.setPassword(oldPass);
        	   user = userService.doLogin(user);         //使用旧密码登录，成功即为旧密码输入正确
        	   if (user == null) {
                   out.print("<script type=\"text/javascript\">");
                   out.print("alert(\"旧密码输入错误，请仔细核对!\");");
                   out.print("open(\"" + contextPath
                           + "/user/updatePass.jsp\",\"_self\");");
                   out.print("</script>");
               } else {
            	   String newPass= request.getParameter("userPass");
            	   user.setPassword(newPass);                        //设置用户的新密码
            	   int result= 0;
            	   result = userService.updateUPass(user); 
            	   if(result==0) {
            		   out.print("<script type=\"text/javascript\">");
                       out.print("alert(\"修改密码失败，请再次尝试!\");");
                       out.print("open(\"" + contextPath
                               + "/user/updatePass.jsp\",\"_self\");");
                       out.print("</script>");
            	   }else {
            		   
            		   request.getSession().removeAttribute("uname");             //去除用户的登录状态，让用户重新登录
            		   out.print("<script type=\"text/javascript\">");
                       out.print("alert(\"修改密码成功，请重新登录!\");");
                       out.print("open(\"" + contextPath
                               + "/login.jsp\",\"_self\");");
                       out.print("</script>");

            	   }
               }
        	   
           }
           
           
           if("myTopics".equals(opr)) {                        //获取当前用户的所有帖子
        	   User user= new User();
        	   List<Topic> myTopics= null;
        	   String pageIndex= request.getParameter("pageIndex");        //获取页码信息
        	   if (pageIndex == null                                         //如果用户没有指定页码，则默认第一页
                       || (pageIndex = pageIndex.trim()).length() == 0) {
                   pageIndex = "1";
               }
        	   int currPageNo = Integer.parseInt(pageIndex);           //获取当前页码
        	   if (currPageNo < 1)
                   currPageNo = 1;
        	   Page pageObj = new Page();
               pageObj.setCurrPageNo(currPageNo); // 设置当前页码
               pageObj.setPageSize(5); // 设置每页显示条数 现在是5条
        	   user.setUsername((String)request.getSession().getAttribute("uname"));   //获取JSP中登录的登录用户名
        	   userService.getMyTopics(user, pageObj);
        	   myTopics= pageObj.getTtList();
        	   request.getSession().setAttribute("myTopics", myTopics);            //帖子列表
        	   request.getSession().setAttribute("pageObj", pageObj);            //页面列表
        	   request.getRequestDispatcher("../user/topics.jsp").forward(request, response);      //转发到用户帖子页面
        	   
           }
           
           if("myComments".equals(opr)) {                  //获取当前用户的所有评论
        	   User user = new User();
        	   List<Comment> myComments = null;
        	   String pageIndex= request.getParameter("pageIndex");        //获取页码信息
        	   if (pageIndex == null                                         //如果用户没有指定页码，则默认第一页
                       || (pageIndex = pageIndex.trim()).length() == 0) {
                   pageIndex = "1";
               }
        	   int currPageNo = Integer.parseInt(pageIndex);           //获取当前页码
        	   if (currPageNo < 1)
                   currPageNo = 1;
        	   Page pageObj = new Page();
        	   pageObj.setCurrPageNo(currPageNo); // 设置当前页码
               pageObj.setPageSize(3); // 设置每页显示条数 现在是3条
               user.setUsername((String)request.getSession().getAttribute("uname"));   //获取JSP中登录的用户的用户名
               userService.getMyComments(user, pageObj);
               myComments= pageObj.getCcList();                      //获取评论列表
               request.getSession().setAttribute("myComments", myComments);   //评论列表
               request.getSession().setAttribute("pageObj", pageObj);            //页面信息列表
               request.getRequestDispatcher("../user/comments.jsp").forward(request, response);	        //转发到用户中心我的评论页面 
           }
           
           if("manGetUList".equals(opr)) {                      //管理员：获取所有用户的信息
        	   List<User> manUserList= null;
        	   String pageIndex= request.getParameter("pageIndex");         //获取页码信息
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
               userService.manGetAllUserList(pageObj);
               manUserList = pageObj.getUuList();
               request.getSession().setAttribute("manUserList", manUserList);   //用户列表
               request.getSession().setAttribute("pageObj", pageObj);       //页面信息
               request.getRequestDispatcher("/manager/manageUsers.jsp").forward(request, response);      //转发到用户管理详情页面
           }
           
           if("manGetUInfo".equals(opr)) {                   //管理员获取用户的详细信息
        	   String username= request.getParameter("username");        //管理员获取此用户名的详细信息
        	   User user= new User();
        	   user= userService.getUserInfo(username);
        	   request.getSession().setAttribute("manUInfo", user);      //设置用户对象
        	   request.getRequestDispatcher("/manager/user.jsp").forward(request, response);     //转发到用户详情信息页面
           }
           
           if("mdfRole".equals(opr)) {                         //管理员将普通用户设置成管理员
        	   String username= request.getParameter("username");        //管理员获取此用户名的详细信息
        	   int result=0;
        	   result= userService.manMdfRole(username);
        	   if(result!=0) {                       //设置成功
        		                
        		   out.print("<script type=\"text/javascript\">");
                   out.print("alert(\"权限赋予成功!\");");
                   out.print("open(\"" + contextPath
                           + "/util/user?opr=manGetUList\",\"_self\");");              //返回用户管理界面
                   out.print("</script>"); 
        	   }else {                               //设置失败
        		   out.print("<script type=\"text/javascript\">");
                   out.print("alert(\"权限赋予失败。请重试!\");");
                   out.print("open(\"" + contextPath
                           + "/util/user?opr=manGetUInfo&username="+username+"\",\"_self\");");   //返回原页面
                   out.print("</script>"); 
      
        	   }
           }
           
           if("mdfUsable".equals(opr)) {                   //管理员将普通用户封号或者解封
        	   String username= request.getParameter("use");        //管理员获取要更改的用户名
        	   System.out.println(username+"出来了");
        	   System.out.println(opr2+"出来了");
        	   int result=0;
        	   result=userService.manMdfUsable(opr2, username);
        	   if(result!=0){               //更改成功
        		   out.print("<script type=\"text/javascript\">");
                   out.print("alert(\"操作成功!\");");
                   out.print("open(\"" + contextPath
                           + "/util/user?opr=manGetUList\",\"_self\");");              //返回用户管理界面
                   out.print("</script>"); 
        	   }else {                   //更改失败
        		   out.print("<script type=\"text/javascript\">");
                   out.print("alert(\"操作失败，请重试!\");");
                   out.print("open(\"" + contextPath
                           + "/util/user?opr=manGetUList\",\"_self\");");              //返回用户管理界面
                   out.print("</script>"); 
        	   }
           }
           
           
            
        } catch (Exception e) {
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

