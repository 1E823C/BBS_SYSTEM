<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<style type="text/css">
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

<div style="font-family: 微软雅黑;">
	<table class="table-striped">
			<c:forEach items="${sessionScope.indexAnno}" var="indexAnno">
			<tr>
				<td width="120px" height="30px" style="font-size: 14px;"><div
						style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;max-width: 110px;float: left;">
						<a
							href=" "
							target="_top">${indexAnno.anno_title }</a>
					</div>
				</td>
				<td width="80px" style="font-size: 9px;">
				${indexAnno.announce_time}
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
