<%@ page language="java"
	import="java.util.*,zt.bbs.entity.*,java.io.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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


<title>帖子管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="./CSS/manage.css" />
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

.topicStyle {
	width: 790px;
	margin-left: 10px;
	margin-right: 5px;
}

.topicStyle a:link,.topicStyle a:visited {
	color: blue;
	text-decoration: none;
} /* 未被访问的链接 */ /*已被访问的链接 */
.topicStyle a:hover {
	color: #2979BF;
	font-weight: bolder;
	text-decoration: none;
} /* 鼠标指针移动到链接上 */
</style>
<script type="text/javascript" src="./JS/jquery-1.11.0.js"></script>
<script type="text/javascript">
	
	
</script>
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
			<div align="center" style="font-size: 24px;margin-top: 10px;">
				帖 子 管 理<font style="font-size: 14px;">(共有${sessionScope.pageObj.totalCount}个帖子)</font>
			</div>
			<div class="pageNav" align="right">          <!-- 分页显示功能 -->
				
				<c:if test="${pageIndex > 1}"> 
					<a href="util/topic?opr=manGetAllTopics&pageIndex=1" style="width: 80px;font-family: 微软雅黑" >[首页]</a>&nbsp;
          			<a href="util/topic?opr=manGetAllTopics&pageIndex=${pageIndex - 1}" style="width: 80px;" >[上一页]</a>
					</c:if>
					
							[${pageIndex } / ${totalPages }]
							
					<c:if test="${pageIndex < totalPages}">
          				<a href="util/topic?opr=manGetAllTopics&pageIndex=${pageIndex + 1}"  >[下一页]</a>
         				<a href="util/topic?opr=manGetAllTopics&pageIndex=${totalPages}">[末页]</a> 
      				</c:if>	
			</div>
			<div class="pageGo" align="right">
				
			</div>
			<table class="topicStyle" cellspacing="0">
				<tr style="background-color: #A2C1DE;height: 26px;">
					<td width="300px">标题</td>
					<td width="100px">类型</td>
					<td width="100px">作者</td>
					<td width="50px">评论数</td>
					<td width="50px">访问数</td>
					<td width="70px">操作</td>
					<td width="60px">管理</td>
				</tr>
				<c:forEach items="${sessionScope.manTopics}" var="manTopics">
					<tr style="height: 26px;">
						<td style="text-align: left;border-left: 1px solid silver;"><div
								style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;max-width: 220px;">
								${manTopics.title}
							</div></td>
						<td style="font-size: 12px;">${manTopics.type_name}</td>
						<td>            <!-- 管理员红色字体，普通用户黑色字体 -->
							<c:choose>
								<c:when test="${manTopics.topic_userrole eq 1}">
									<font style="color:red;">${manTopics.topic_username}</font>
								</c:when>
								<c:otherwise>
									${manTopics.topic_username}
								</c:otherwise>
							</c:choose>
						</td>
						<td>${manTopics.comment_count}
						</td>
						<td>${manTopics.hit_count}
						</td>
						
						<td style="font-size: 12px;color: blue;"><a
							href="util/topic?opr=getTopic&opr2=${manTopics.id}">查看详细</a>
						</td>
						<td >        <!-- 管理员可以删除普通用户发帖，无法删除管理员发帖 -->
							<c:choose>
								<c:when test="${manTopics.topic_userrole eq 1}">
									<font style="color:silver">无权限</font>
								</c:when>
								<c:otherwise>
									<a  href="util/topic?opr=manDelTopic&topic_username=${manTopics.topic_username}&id=${manTopics.id}&type_id=${manTopics.type_id} " style="color:blue;"
							onclick="var checkDel = (confirm('此操作将删除帖子及其所有下属评论，是否继续?')); return checkDel">删除</a>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>

				</c:forEach>
			</table>
			
			<div style="padding: 6px;margin-left: 5px;margin-right: 5px;margin-top:40px;font-size: 14px;line-height: 20px;color: white;float: left;font-family: 微软雅黑;	border: 1px solid #C2D5E3;">
					<font color="red">注意：由于评论数量众多，评论管理设置在相应的帖子页面，主要就是评论的删除功能。</font>
			</div>
 
		
		</div>
	</div>

</body>
</html>
