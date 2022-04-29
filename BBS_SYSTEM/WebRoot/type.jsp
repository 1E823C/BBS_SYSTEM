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

<title>版块</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<style type="text/css">
.background {
	width: 1004px;
	overflow: auto;
	background-color: #F5F5F5;
	margin: 0 auto;
	min-height: 700px;
}

.leftBodyBlank {
	width: 22px;
	min-height: 300px;
	float: left;
}

.tBody {
	width: 982px;
	float: left;
	font-family: 微软雅黑;
}

.smallNav {
	width: 982px;
	height: 24px;
	margin-top: 10px;
	float: left;
	font-size: 14px;
}

.cateTitle:HOVER {
	color: red;
}
</style>
</head>

<body>
	<div class="background" align="left">
		<div class="top_nav">
			<jsp:include page="/top.jsp"></jsp:include>
		</div>
		<div class="leftBodyBlank"></div>
		<div class="tbody">
			<div class="smallNav" align="left">
				<div style="float: left;">
					<a href="index.jsp" title="论坛首页"><img
						src="image/homepage_24.png"></img> </a>
				</div>
				<div style="float: left;line-height:24px;">
					&nbsp;&gt;&nbsp;<a href="index.jsp">论坛</a>&nbsp;&gt;&nbsp;版块
				</div>
			</div>
			<div style="float: left;margin-top: 15px;">
			
				<c:forEach items="${sessionScope.typeList }" var="typeList" >       <!-- 获取typeList然后用JSTL标签进行输出 -->
				
					<div style="width: 960px;margin:0px 0px 20px 0px;float: left;">
						<div
							style="width: 960px;height: 35px;background-color: #A2C1DE;line-height: 35px;color:white">
							&nbsp;&nbsp; <a class="cateTitle" href="util/topic?opr=getAllPageTopics&opr2=type&tid=${typeList.tid }">${typeList.name } </a>
							<font style="font-size: 12px;">(共<font style="font-weight: bolder;">${typeList.topics_count}</font>主题/
							<font style="font-weight: bolder;">${typeList.comments_count} </font>回复)</font>
						</div>
					
						
					</div>
				
				</c:forEach>
				
			</div>
		</div>
		<div class="copyRight">
			<jsp:include page="/copyRight.jsp"></jsp:include>
		</div>
	</div>


</body>
</html>
