<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<style type="text/css">
.friendLink {
	font-family: 微软雅黑;
	font-size: 13px;
	margin-left: 10px;
}

a:link {
	color: #525252;
	background-color: white;
	text-decoration: none;
} /* 未被访问的链接 */
a:visited {
	color: #525252;
	background-color: white;
} /*已被访问的链接 */
a:hover {
	color: white;
	background-color: #2C86E5;
} /* 鼠标指针移动到链接上 */
</style>

<div class="friendLink">
	<div style="height: 24px;font-size: 15px;font-weight: bolder">
		<a href="https://www.baidu.com" target="_blank">百度一下</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
			href="http://www.neepu.edu.cn" target="_blank">东北电力大学</a>
	</div>
	<div style="height: 24px;">
		<a href="http://ie.neepu.edu.cn/"
			target="_blank">东北电力大学计算机学院</a>
	</div>
	<div style="height: 24px;">
		<a href="http://lib.neepu.edu.cn/" target="_blank"
			style="font-size: 15px;font-weight: bolder">东北电力大学图书馆</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
	<div style="height: 24px;">
	</div>
	<div style="height: 24px;">
	</div>
	<div style="height: 24px;">
	</div>

</div>
