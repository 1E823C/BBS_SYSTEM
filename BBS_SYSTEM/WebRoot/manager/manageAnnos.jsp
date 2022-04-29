<%@ page language="java"
	import="java.util.*,zt.bbs.entity.*,java.io.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

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


<title>公告管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="./CSS/manage.css" />
<script type="text/javascript" src="./JS/jquery-1.11.0.js"></script>
<style type="text/css">
body,html {
	width: 1004px;
	padding: 0px;
	margin: 0px auto; /*设置居中  */
	text-align: center; /*兼容性更好  */
	font-family: "微软雅黑";
}

.pageNav {
	width: 550px;
	height: 30px;
	margin-top: 15px;
	margin-bottom: 5px;
	float: left;
}

.pageGo {
	width: 120px;
	height: 25px;
	line-height: 25px;
	margin-top: 15px;
	margin-bottom: 5px;
	float: left;
	font-size: 13px;
}

.pageNav a button {
	width: 30px;
	height: 25px;
	font-size: 14px;
	font-family: 微软雅黑;
	background-color: white;
	border: 1px solid silver;
}

.pageNav a button:HOVER {
	width: 30px;
	height: 25px;
	font-size: 14px;
	font-family: 微软雅黑;
	background-color: #A2C1DE;
	border: 1px solid silver;
}

.pageNav button {
	width: 30px;
	height: 25px;
	font-size: 14px;
	font-family: 微软雅黑;
	background-color: #EFF4FB;
	border: 1px solid silver;
}

table tr td {
	border-bottom: 1px solid silver;
	border-right: 1px solid silver;
	padding-left: 3px;
}

.annoStyle {
	width: 790px;
	margin-left: 10px;
	margin-right: 5px;
}

.annoStyle a:link,.annoStyle a:visited {
	color: blue;
	text-decoration: none;
} /* 未被访问的链接 */ /*已被访问的链接 */
.annoStyle a:hover {
	color: #2979BF;
	font-weight: bolder;
	text-decoration: none;
} /* 鼠标指针移动到链接上 */
.butt {
	background-color: #6699CC;
	width: 120px;
	height: 30px;
	margin-top: 10px;
	margin-bottom: 10px;
	border: 0;
	color: white;
	font-size: 18px;
	border: 0;
}

.butt:hover {
	background-color: #71AAE3;
	border: 0;
}
</style>

</head>

<body>

<!--使用JSTL标签获取分页显示的总页数和当前的页码  -->
	<c:set var="totalPages" value="${pageObj.totalPageCount }"/>
	<c:set var="pageIndex" value="${pageObj.currPageNo }"/>


	<div class="body">
		<div class="top">
			<img alt="" src="image/manager_top.jpg" />
		</div>
		<div class="left">
			<jsp:include page="./left.jsp"></jsp:include>
		</div>
		<div class="right">
			<div align="center" style="font-size: 24px;margin-top: 10px;">公
				告 管 理(共有${sessionScope.pageObj.totalCount}个公告)</div>
			<div style="float: left;width:100px;margin-left: 10px;">
				<input type="submit" value="发表公告" class="butt" style="width: 80px;"
					onclick="window.location.href='./manager/addAnno.jsp'" />
			</div>
			<div class="pageNav" align="right">      <!-- 分页显示功能 -->
				
				<c:if test="${pageIndex > 1}"> 
					<a href="util/anno?opr=getAnnoList&pageIndex=1" style="width: 80px;font-family: 微软雅黑" >[首页]</a>&nbsp;
          			<a href="util/anno?opr=getAnnoList&pageIndex=${pageIndex - 1}" style="width: 80px;" >[上一页]</a>
					</c:if>
					
							[${pageIndex } / ${totalPages }]
							
					<c:if test="${pageIndex < totalPages}">
          				<a href="util/anno?opr=getAnnoList&pageIndex=${pageIndex + 1}"  >[下一页]</a>
         				<a href="util/anno?opr=getAnnoList&pageIndex=${totalPages}">[末页]</a> 
      				</c:if>	
				
			</div>
			<div class="pageGo" align="right">
				
			</div>
			<table class="annoStyle" cellspacing="0">
				<tr style="background-color: #A2C1DE;height: 24px;">
					<td width="390px">标题</td>
					<td width="160px">时间</td>
					<td>操作</td>
					<td>管理</td>
				</tr>
				<c:forEach items="${sessionScope.annoList }" var="annoList">
					<tr style="height:26px">
						<td style="border-left: 1px solid silver;"><div
								style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;max-width: 400px;">
								${annoList.anno_title }
							</div></td>
						<td><fmt:formatDate value="${annoList.announce_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td style="font-size: 12px;color: blue;"><a
							href="util/anno?opr=theAnno&aid=${annoList.aid} "
							target="_top">查看详情&gt;&gt;</a></td>
						<td><a style="color: blue;"  href="util/anno?opr=toMdfAnno&aid=${annoList.aid}" >修改</a>
							<a style="color: blue;" onclick="var toDel = (confirm('确认要删除此公告?')); return toDel " href="util/anno?opr=delAnno&aid=${annoList.aid}">删除</a>
						</td>
					</tr>

				</c:forEach>
			</table>

		</div>
	</div>

</body>

</html>
