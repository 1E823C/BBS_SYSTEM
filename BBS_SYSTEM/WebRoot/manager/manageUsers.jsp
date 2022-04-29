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

<title>用户管理</title>

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

.userStyle {
	width: 790px;
	margin-left: 10px;
	margin-right: 5px;
}

.userStyle a:link,.userStyle a:visited {
	color: blue;
	text-decoration: none;
} /* 未被访问的链接 */ /*已被访问的链接 */
.userStyle a:hover {
	color: #2979BF;
	font-weight: bolder;
	text-decoration: none;
} /* 鼠标指针移动到链接上 */
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
			<div align="center" style="font-size: 24px;margin-top: 10px;">
				用 户 管 理<font style="font-size: 14px;">(共有${sessionScope.pageObj.totalCount}位用户)</font>
			</div>
			<div align="left" style="font-size: 16px;margin-top: 10px;color: red;padding-left: 20px;">
				
			</div>
			<div class="pageNav" align="right">
			
			<c:if test="${pageIndex > 1}"> 
					<a href="util/user?opr=manGetUList&pageIndex=1" style="width: 80px;font-family: 微软雅黑" >[首页]</a>&nbsp;
          			<a href="util/user?opr=manGetUList&pageIndex=${pageIndex - 1}" style="width: 80px;" >[上一页]</a>
					</c:if>
					
							[${pageIndex } / ${totalPages }]
							
					<c:if test="${pageIndex < totalPages}">
          				<a href="util/user?opr=manGetUList&pageIndex=${pageIndex + 1}"  >[下一页]</a>
         				<a href="util/user?opr=manGetUList&pageIndex=${totalPages}">[末页]</a> 
      				</c:if>	
			
			</div>
			<div class="pageGo" align="right">
				
			</div>
			<table class="userStyle" cellspacing="0">
				<tr style="background-color: #A2C1DE;height: 24px;">
					<td width="160px">用户名</td>
					<td width="80px">权限</td>
					<td width="60px">状态</td>
					<td width="160px">操作</td>
					<td width="50px">管理</td>
				</tr>
				<c:forEach items="${sessionScope.manUserList }" var="manUserList">
					<tr style="height: 26px;">
						<td style="border-left: 1px solid silver;">${manUserList.username }
						</td>
				
						<td>
							<c:choose>       
									<c:when test="${manUserList.role eq 1 }">
									    <font color="red">管理员</font>
									</c:when>
									
									<c:otherwise>
									      普通用户
									</c:otherwise>
							</c:choose>
						</td>
						
						<td>
						<c:choose>
							<c:when test="${manUserList.usable eq 1 }">
									    可用状态
							</c:when>
									
							<c:otherwise>
									   <font color="red">封停状态</font>
							</c:otherwise>
						</c:choose>
						               
						</td>
						
						<td style="font-size: 12px;color: blue;"><a
							href="util/user?opr=manGetUInfo&username=${manUserList.username} ">查看详情&gt;&gt;</a>
						</td>
						<td style="color: blue;">
							<c:choose>
								<c:when test="${manUserList.role eq 1 }">
									<font style="color:silver;">无权限</font>
								</c:when>
								<c:otherwise>
									<c:if test="${manUserList.usable eq 1 }">
										<a
										href="util/user?opr=mdfUsable&opr2=0&use=${manUserList.username}"
										onclick="var toUnuse = (confirm('此操作将暂时禁用该用户，是否继续?')); return toUnuse ">禁用</a>
									</c:if>
								
									<c:if test="${manUserList.usable ne 1 }">
										<a style="color: red"
										href="util/user?opr=mdfUsable&opr2=1&use=${manUserList.username} "
										onclick="var toUse = (confirm('此操作将解禁该用户，是否继续?')); return toUse ">解禁</a>
									</c:if>
								</c:otherwise>
							</c:choose>
						
						</td>
					</tr>

				</c:forEach>
			</table>

		</div>
	</div>

</body>

</html>
