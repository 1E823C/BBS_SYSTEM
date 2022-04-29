<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	    
	
%>       
 <!-- 加载主页时获取热帖版和新帖版  hotTList和freshTList前不能加任何东西，否则无法获取 -->

<c:choose>           
<c:when test="${hotTList == null || freshTList == null || indexAnno == null}">
    <jsp:forward page="util/topic?opr=getTList"></jsp:forward>
    <jsp:forward page="util/anno?opr=indexAnno"></jsp:forward>
</c:when>
<c:otherwise>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">

<title>大学生校园生活论坛</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" type="text/css" href="./CSS/index.css">
<script type="text/javascript" src="./JS/jquery-1.11.0.js"></script>
  
<style type="text/css">
body,html {
	width: 1004px;
	padding: 0px;
	margin: 0px auto; /*设置居中  */
	text-align: center; /*兼容性更好  */
	border: none;
}
.btn {
	border: none;
	border-radius: 0;
	min-width: 80px;
	height: 28px;
	line-height: 16px;
	color: #fff;
}

.btn-top {
	width: 40px;
	height: 40px;
	background-color: #ccc;
}

.btn-top:hover,.btn-top:focus {
	background-color: #676767;
}

.btn-top .glyphicon-chevron-up .glyphicon-share-alt {
	font-size: 18px;
}

.glyphicon {
	position: relative;
	top: 1px;
	display: inline-block;
	font-style: normal;
	font-weight: normal;
	line-height: 1;
}

.glyphicon-share-alt:before {
	content: "回复";
}

.glyphicon-chevron-up:before {
	content: "顶部";
}

.table-striped td {
	border-bottom: 1px dashed silver;
}

a:link {
	color: #525252;
	text-decoration: none;
} /* 未被访问的链接 */
a:visited {
	color: #525252;
} /*已被访问的链接 */
a:hover {
	color: red;
	text-decoration: underline;
} /* 鼠标指针移动到链接上 */

</style>
</head>
<%

	



%>


<body>
	<div class="background">
		<div class="topNav">
			<jsp:include page="/top.jsp"></jsp:include>
		</div>
		<div class="leftBodyBlank"></div>
		<div class="tBody">
			<div class="topBody" align="left">
				<div style="float: left;">
					<a href="index.jsp" title="论坛首页"><img
						src="image/homepage_24.png" /> </a>
				</div>
				<div style="float: left;line-height:24px;">&nbsp;&gt;&nbsp;论坛
				</div>
			</div>
			<div class="leftBody">
				<div class="hot">
					<div class="ltitle">
						<div style="float: left;width: 660px;">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;热帖榜&nbsp;&nbsp;&nbsp;&nbsp;<font
								style="font-style: italic;">TOP 10 </font>
						</div>
						<div align="center" style="float: left;width: 80px;color: #6699CC">
							<a href="util/topic?opr=getAllPageTopics&opr2=Hot">More&gt;&gt;</a>
						</div>
					</div>
					<div class="hotContent">
					  	<iframe width="740px" height="335px" frameborder="0"
							scrolling="no" src="indexHotTopic.jsp"></iframe>    
					</div>
				</div>
				<div class="fresh">
					<div class="ltitle">
						<div style="float: left;width: 660px;">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;新帖榜&nbsp;&nbsp;&nbsp;&nbsp;<font
								style="font-style: italic;">TOP 10 </font>
						</div>
						<div align="center" style="float: left;width: 80px;">
							<a href="util/topic?opr=getAllPageTopics&opr2=All">More&gt;&gt;</a>
						</div>
					</div>
					<div class="freshContent">  	 
						
						<iframe width="740px" height="335px" frameborder="0"
							scrolling="no" src="indexFreshTopic.jsp"></iframe> 
					</div>
				</div>
				
			</div>
			<div class="rightBody">
				<div class="announces">
					<div class="rtitle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最新公告
					</div>
					<div class="announcesContent">
						
						<table class="table-striped">
							<c:forEach items="${indexAnno}" var="indexAnno">
						<tr>
							<td width="120px" height="30px" style="font-size: 14px;"><div
							style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;max-width: 110px;float: left;">
						<a
							href="util/anno?opr=theAnno&aid=${indexAnno.aid} " style="color:red;font-weight:bold;"
							target="_top">${indexAnno.anno_title }</a>
					</div>
					</td>
								<td width="80px" style="font-size: 9px;">       <!-- 输出时间 修改时间格式为月-日-时-分 -->
								<fmt:formatDate value="${indexAnno.announce_time}" pattern="MM-dd HH:mm"/>
					</td>
					</tr>
					</c:forEach>
						</table>
						
						
					</div>
				</div>
				<div class="newButton">
				<c:set var="isLogin" value="${empty sessionScope.uname }" />     <!-- 设置变量 判断是否已经登录-->
				<c:choose>
					<c:when test="${isLogin }">
					    <input type="button" value="未登录不能发帖">      <!-- 未检测到登录状态就不能发帖 -->
					</c:when>
					
					<c:otherwise>
					   <form action="util/topic?opr=toAddTopic" method="post" >
						<input type="submit" value="我要发帖">
					    </form>
					</c:otherwise>
				</c:choose>	
				</div>
				
				<div class="friendLink">
					<div class="rtitle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;友情链接
					</div>
					<div class="announcesContent">
						<iframe width="200px" height="170px" frameborder=0 scrolling="no"
							src="indexFriend.jsp"> </iframe>
					</div>
				</div>
			</div>
		</div>
		<div class="copyRight">
			<jsp:include page="/copyRight.jsp"></jsp:include>
		</div>
	</div>
</body>
</html>
</c:otherwise>
</c:choose>