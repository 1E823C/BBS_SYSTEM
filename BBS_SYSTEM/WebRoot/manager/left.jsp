<%@ page language="java"
	import="java.util.*,zt.bbs.entity.*,java.io.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%
   String user = (String)session.getAttribute("uname");
   String userrole = (String)session.getAttribute("userrole");
	if (user == null || user == "") {
	PrintWriter pw = response.getWriter();
	pw.println("<script type='text/javascript'>alert('未登录或登录已失效！请登录！');location.href = '../login.jsp';</script>");
              //设置过滤器，当用户未登录时将不能进入本页面
}else if(!userrole.equals("1")){
	PrintWriter pw = response.getWriter();
	pw.println("<script type='text/javascript'>alert('本页面不允许非管理员进入！');location.href = '../index.jsp';</script>");
			//设置过滤器，禁止普通用户进入管理页面
}
%>

<head><link rel="SHORTCUT ICON" href="./image/logo.jpg"></head>
<link rel="stylesheet" type="text/css" href="./CSS/manager_left.css">

<div class="links">
	<a href="util/user?opr=manGetUList"><span
		style=" line-height:50px;"><font color="white">用 户 管 理</font> </span>
	</a>
</div>

<div class="links">
	<a href="util/topic?opr=manGetAllTopics"><span
		style=" line-height:50px;"><font color="red">帖 子 管 理</font> </span>
	</a>
</div>
<div class="links">
	<a href="util/type?opr=manGetType"><span style=" line-height:50px;"><font
			color="white">版 块 管 理</font> </span> </a>
</div>
<div class="links">          
		<a href="util/anno?opr=getAnnoList"><span
		style=" line-height:50px;"><font color="white">公 告 管 理</font> </span>
		</a>
</div>
<div class="links">
	<a href="./Chart.jsp"><span style="line-height:50px;"><font
			color="white">图 表 统 计</font> </span> </a>
</div>
<div class="links">
	<a href="./index.jsp"><span style=" line-height:50px;"><font
			color="white">返 回 首 页</font> </span> </a>
</div>
<div style="padding: 6px;margin-left: 5px;margin-right: 5px;margin-top:40px;font-size: 14px;line-height: 20px;color: white;float: left;font-family: 微软雅黑;	border: 1px solid #C2D5E3;">
	<font color="red">注意：</font>本页面是校园论坛的后台管理页面，只允许管理员进入和操作。另外，为保证论坛的安全运转，管理员人数应控制在<font color="red">3</font>人以内。
</div>