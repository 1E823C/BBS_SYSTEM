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

<title>所有公告</title>

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
	width: 360px;
	height: 30px;
	margin-top: 15px;
	float: left;
}

.pageNav {
	
	width: 580px;
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

table td {
	height: 30px;
	font-size: 16px;
}

a:link,a:visited {
	color: #525252;
	text-decoration: none;
} /* 未被访问的链接 */ /*已被访问的链接 */
a:hover {
	color: #525252;
	text-decoration: underline;
} /* 鼠标指针移动到链接上 */
.topicTitle:link,.topicTitle:visited {
	color: #525252;
	text-decoration: none;
}

.topicTitle:hover {
	color: #2C86E5;
	text-decoration: underline;
}

.titleStyle {
	width: 960px;
	height: 35px;
	line-height: 30px;
	margin-top: 15px;
	background-color: #A2C1DE;
	float: left;
	text-align: left;
}

.listTopicDiv {
	float: left;
	width: 960px;
	height: 375px;
	background-color: white;
	width: 960px;
	/* border: 1px solid #C2D5E3; */
}

.listTopicStyle td {
	border-bottom: 1px dashed silver;
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
					&nbsp;&gt;&nbsp;<a href="index.jsp">论坛</a>&nbsp;&gt;&nbsp;所有公告
				</div>
			</div>
			<div>
				<div class="editMenu">
					 <input type="button" value="刷新"
						onclick="window.location.href = '/BBS_SYSTEM/util/anno?opr=allAnno' "
						class="butt" style="width: 80px;" />
				</div>
				
			 	<div class="pageNav" align="right">          <!-- 分页显示功能 -->
					<c:if test="${pageIndex > 1}"> 
					<a href="util/anno?opr=allAnno&pageIndex=1" style="width: 80px;font-family: 微软雅黑" >[首页]</a>&nbsp;
          			<a href="util/anno?opr=allAnno&pageIndex=${pageIndex - 1}" style="width: 80px;" >[上一页]</a>
					</c:if>
					
							[${pageIndex } / ${totalPages }]
							
					<c:if test="${pageIndex < totalPages}">
          				<a href="util/anno?opr=allAnno&pageIndex=${pageIndex + 1}"  >[下一页]</a>
         				<a href="util/anno?opr=allAnno&pageIndex=${totalPages}">[末页]</a> 
      				</c:if>	

				</div>   
				
			</div>
			<div class="titleStyle" align="left">
				<table border="0" style="margin: 0;		font-size: 12px;">
					<tr style="height: 30px;line-height: 30px;">
						<td style="width:800px" align="left">&nbsp;&nbsp;&nbsp;公告标题</td>
						<td style="width:130px" align="center">发布时间</td>
					</tr>
				</table>
			</div>
			<div class="listTopicDiv">
				<table align="left" class="listTopicStyle">
				<c:forEach items="${sessionScope.allAnno}" var="allAnno">
					
						<tr>
							<td style="width:750px;" align="left">&nbsp;<a style="color:red;font-weight:bold;"
								href="util/anno?opr=theAnno&aid=${allAnno.aid}" target="_top" style="font-size: 14px;"> ${allAnno.anno_title} </a>
								</td>
						
							<td align="left" width="5px;">
							
							</td>
							
							<td align="center" width="5px;">
							</td>
							
							<td align="right" width="40px" style="font-size: 13px;">
								
							</td>
							<td style="font-size: 12px;" width="120px;" align="right">
								<fmt:formatDate value="${allAnno.announce_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="copyRight">
			<jsp:include page="/copyRight.jsp"></jsp:include>
		</div>
	</div>

</body>
</html>
