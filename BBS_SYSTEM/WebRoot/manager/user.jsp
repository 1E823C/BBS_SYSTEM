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


<title>用户资料卡</title>

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

table tr td {
	padding-left: 10px;
	border: 1px solid silver;
}

table tr th {
	border: 1px solid silver;
	text-align: center;
	color: #254472;
}

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
<script type="text/javascript" src="./JS/jquery-1.11.0.js"></script>
<script type="text/javascript">
	function warnUpdate(userId){
		if (confirm("确认将此用户升级为管理员？请注意此操作不可逆！")){
		 	window.location.href="util/user?opr=mdfRole&username="+userId;
		 }else{
		}
	}
</script>
</head>


<body>
	<div class="body">
		<div class="top">
			<img alt="" src="image/manager_top.jpg" />
		</div>
		<div class="left">
			<jsp:include page="./left.jsp"></jsp:include>
		</div>
		<div class="right">
			<div align="center" style="font-size: 28px;margin-top: 20px;">用
				户 信 息</div>
			<div style="float: left;width: 300px;height: 400px;margin-top: 40px;"
				align="center">
				<img alt="我的头像" style="width: 140px;height: 200px;"  onerror="this.src='./image/upicture.png'" 
					src="${sessionScope.manUInfo.picture} ">
			</div>
			<div style="float: left;width: 400px;height: 400px;margin-top: 30px;">
				<table align="left" style="width: 400px;text-align: left;"
					cellspacing="0">
					<tr>
						<th width="100px;">用&nbsp;户&nbsp;名</th>
						<td width="300px;">${sessionScope.manUInfo.username}
						</td>
					</tr>
					<tr>
						<th>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别</th>
						<td>${sessionScope.manUInfo.age}&nbsp;</td>
					</tr>
					<tr>
						<th>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱</th>
						<td>${sessionScope.manUInfo.email}&nbsp;</td>
					</tr>
					<tr>
						<th>帖&nbsp;子&nbsp;数</th>
						<td>${sessionScope.manUInfo.topic_count}&nbsp;&nbsp;</td>
					</tr>
					<tr>
						<th>评&nbsp;论&nbsp;数</th>
						<td>${sessionScope.manUInfo.comment_count}&nbsp;</td>
					</tr>
					<tr height="80px;">
						<th>个人简介</th>
						<td>&nbsp;${sessionScope.manUInfo.introduction}
						</td>
					</tr>  
					  <c:if test="${sessionScope.manUInfo.role eq 0}">
						<tr height="40px;">
							<th>赋予权限</th>
							<td>
								<form action="util/user?opr=mdfRole" id="mdfrole" style="margin:0px;" method="post">
									<input type="hidden" name="username" value="${sessionScope.manUInfo.username}"> 
									<input type="submit" value="管理员" class="butt" 
									onclick="var toMdf = (confirm('此操作将不可逆，是否确认将用户提权？')); return toMdf" />
								</form>
							</td>
						</tr>
					</c:if>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
