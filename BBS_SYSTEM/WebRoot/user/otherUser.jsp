<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

<title>${sessionScope.otherUInfo.username }的资料</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="./JS/jquery-1.11.0.js"></script>
<style type="text/css">
.background {
	width: 1004px;
	overflow: auto;
	background-color: #F5F5F5;
	margin: 0 auto;
	min-height: 700px;
}

.tBody {
	margin-left: 22px;
	margin-right: 22px;
	font-family: 微软雅黑;
}

.smallNav {
	width: 982px;
	height: 24px;
	margin-top: 10px;
	float: left;
	font-size: 14px;
}
</style>
</head>

<body>
	<div class="background">
		<div class="topNav">
			<jsp:include page="../top.jsp"></jsp:include>
		</div>
		<div class="tBody">
			<div class="smallNav" align="left">
				<div style="float: left;">
					<a href="index.jsp" title="论坛首页"><img
						src="image/homepage_24.png"></img> </a>
				</div>
				<div style="float: left;line-height:24px;">
					&nbsp;&gt;&nbsp;<a href="index.jsp">论坛</a>&nbsp;&gt;&nbsp;${sessionScope.otherUInfo.username }的资料
				</div>
			</div>
			<div class="userStyle" style="float: left;margin-top: 50px;min-height: 380px;">
				<div align="right" style="width:300px;float: left;margin-right: 30px;">
					<img alt="我的头像" style="width: 140px;height: 200px;"  onerror="this.src='./image/upicture.png'" 
						src="${sessionScope.otherUInfo.picture }">
				</div>
				<div  align="left" style="width:400px;float: left;margin-left: 30px;">
					<table>
						<tr>
							<td>用户名：</td>
							<td>${sessionScope.otherUInfo.username }
							</td>
						</tr>
						<tr>
							<td>性别：</td>
							<td>${sessionScope.otherUInfo.sex }
							</td>
						</tr>
						<tr>
							<td>年龄：</td>
							<td>${sessionScope.otherUInfo.age }</td>
						</tr>
						<tr>
							<td>注册邮箱：</td>
							<td>${sessionScope.otherUInfo.email}</td>
						</tr>
						<tr>
							<td>用户权限：</td>
							<td>
								<c:choose>
									<c:when test="${sessionScope.otherUInfo.role eq 1 }">
										<font color="red">管理员</font>
									</c:when>
									<c:otherwise>
									      普通用户
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td>他的帖子数：</td>
							<td>${sessionScope.otherUInfo.topic_count }</td>
						</tr>
						<tr>
							<td>他的评论数：</td>
							<td>${sessionScope.otherUInfo.comment_count }
							</td>
						</tr>
						<tr>
							<td>他的简介：</td>
							<td>${sessionScope.otherUInfo.introduction }
							</td>
						
					</table>
				</div>

			</div>
		</div>
		<div class="copyRight">
			<jsp:include page="../copyRight.jsp"></jsp:include>
		</div>
	</div>
</body>
</html>
