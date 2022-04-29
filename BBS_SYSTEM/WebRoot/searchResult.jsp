<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

<title>查询结果</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="./JS/jquery-1.11.0.js"></script>
<style type="text/css">
body,html {
	width: 1004px;
	padding: 0px;
	margin: 0px auto; /*设置居中  */
	text-align: center; /*兼容性更好  */
	background-color: #F8F8F8;
	border: none;
}

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
	width: 960px;
	font-family: 微软雅黑;
	min-height: 460px;
	float: left;
}

.smallNav {
	width: 960px;
	height: 24px;
	margin-top: 10px;
	float: left;
	font-size: 14px;
}

.editMenu {
margin-left:20px;
	width: 340px;
	height: 30px;
	margin-top: 15px;
	float: left;
}

.pageNav {
	width: 480px;
	height: 30px;
	margin-top: 15px;
	float: left;
}

.pageGo {
	width: 120px;
	height: 30px;
	line-height: 30px;
	margin-top: 15px;
	float: left;
	font-size: 13px;
	margin-top: 15px;
}

.pageNav a button {
	width: 35px;
	height: 30px;
	font-size: 14px;
	font-family: 微软雅黑;
	background-color: white;
	border: 1px solid silver;
}

.pageNav a button:HOVER {
	width: 35px;
	height: 30px;
	font-size: 14px;
	font-family: 微软雅黑;
	background-color: #A2C1DE;
	border: 1px solid silver;
}

.pageNav button {
	width: 35px;
	height: 30px;
	font-size: 14px;
	font-family: 微软雅黑;
	background-color: #EFF4FB;
	border: 1px solid silver;
}

.butt {
	background-color: #6699CC;
	width: 120px;
	height: 30px;
	margin-right: 10px;
	float: left;
	border: 0;
	color: white;
	font-size: 18px;
}

.butt:hover {
	background-color: #71AAE3;
	border: 0;
}

a:link,a:visited {
	color: #525252;
	text-decoration: none;
} /* 未被访问的链接 */ /*已被访问的链接 */
a:hover {
	color: #525252;
	text-decoration: underline;
} /* 鼠标指针移动到链接上 */
.listTopicDiv {
	float: left;
	width: 960px;
	min-height: 320px;
	margin-top: 15px;
	width: 960px;
	/* border: 1px solid #C2D5E3; */
}

.listTopicStyle {
	background-color: white;
	margin-top: 20px;
	border: 1px solid #C2D5E3;
}
</style>
</head>

<body>

	<!--使用JSTL标签获取分页显示的总页数和当前的页码  -->
	<c:set var="totalPages" value="${pageObj.totalPageCount }"/>
	<c:set var="pageIndex" value="${pageObj.currPageNo }"/>


	<div class="background" align="center">
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
					&nbsp;&gt;&nbsp;<a href="index.jsp">论坛</a>&nbsp;&gt;&nbsp;模糊查询结果
				</div>
			</div>
			<div>
				<div class="editMenu">
					<div
						style="font-size: 24px;height: 30px;line-height:30px;color: red;float: left;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;max-width: 250px;">
						${sessionScope.word}
					</div>
					<div style="font-size: 14px;height: 30px;line-height:30px;float: left;">的搜索结果</div>
				</div>
				<div class="pageNav" align="right">           <!-- 分页显示功能 -->
				
				<c:if test="${pageIndex > 1}"> 
					<a href="util/topic?opr=search&content=${sessionScope.word}&pageIndex=1" style="width: 80px;font-family: 微软雅黑" >[首页]</a>&nbsp;
          			<a href="util/topic?opr=search&content=${sessionScope.word}&pageIndex=${pageIndex - 1}" style="width: 80px;" >[上一页]</a>
					</c:if>
					
							[${pageIndex } / ${totalPages }]
							
					<c:if test="${pageIndex < totalPages}">
          				<a href="util/topic?opr=search&content=${sessionScope.word}&pageIndex=${pageIndex + 1}"  >[下一页]</a>
         				<a href="util/topic?opr=search&content=${sessionScope.word}&pageIndex=${totalPages}">[末页]</a> 
      				</c:if>	
				
					
				</div>
				
				<div class="pageGo" align="right">
					
				</div>
			</div>
			<div class="listTopicDiv" align="center">
				<c:forEach items="${sessionScope.searchTopics }" var="searchTopics">
					<table align="center" class="listTopicStyle">
						<tr>
							<td align="left"
								style="width:900px;height:30px;border-bottom: 1px solid #C2D5E3;background-color: #EFF4FB">&nbsp;
								<a
								href="util/topic?opr=getTopic&opr2=${searchTopics.id} "
								target="_top" class="topicTitle"><font
									style="font-size: 12px;color: #58586A">[${searchTopics.type_name}]--</font>${searchTopics.title}  </a>
							</td>
						</tr>
						<tr>
							<td style="font-size: 12px;height:20px;color: silver;">&nbsp;&nbsp;&nbsp;&nbsp;作者:${searchTopics.topic_username}
							&nbsp;&nbsp;发帖日期:<fmt:formatDate value="${searchTopics.topic_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
							&nbsp;&nbsp;评论量:${searchTopics.comment_count }
							</td>
						</tr>
						<tr>
							<td
								style="border-top: 1px solid #C2D5E3;text-align:left;width: 900px;max-width: 900px;height:60px;font-size:12px;color:#363131;">
								<div style="overflow:hidden;height:60px;padding:0px 5px 5px 5px">
									${searchTopics.content}
								</div>
							</td>

						</tr>
					</table>
				</c:forEach>

			</div>
		</div>
		<div class="copyRight">
			<jsp:include page="/copyRight.jsp"></jsp:include>
		</div>
	</div>

</body>
</html>
