<%@ page language="java" import="java.util.*,zt.bbs.entity.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<style type="text/css">
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
</style>

<div style="font-family: 微软雅黑;">
	<table>
		<%
			int i = 1;
		%>
	
		<c:forEach items="${sessionScope.hotTList }" var="hotTList">
			<tr>
				<td style="width: 35px;font-style: italic;" align="center"><%=i%></td>
				<td><div style="width:600px;">
						<div
							style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;max-width: 400px;float: left;">
							<a href="util/topic?opr=getAllPageTopics&opr2=type&tid=${hotTList.type_id} "	target="_top" style="font-size: 14px;">${hotTList.type_name}</a>&nbsp;||
							
							<a href="util/topic?opr=getTopic&opr2=${hotTList.id} " target="_top" class="topicTitle">${hotTList.title} </a>
						</div>
						<div style="float: left;font-size: 12px;color: #4C9ED9">
							&nbsp;&nbsp;&nbsp;[评论:
							${ hotTList.comment_count}
							]&nbsp;[
								发帖时间:<fmt:formatDate value="${ hotTList.topic_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
							]
						</div>
					</div></td>
				<td align="right" width="80px;"><div
						style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;max-width: 80px;">
						<a class="topicTitle" href="util/user?opr=otherUInfo&opr2=${hotTList.topic_username} " target="_top"style="font-size: 14px;">${hotTList.topic_username } </a>&nbsp;&nbsp;  <!-- 发帖作者 -->
					</div></td>
			</tr>
			<%
				i++;
			%>
		   </c:forEach>
	</table>
</div>
