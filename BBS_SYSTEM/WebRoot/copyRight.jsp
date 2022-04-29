<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<base href="<%=basePath%>">
<style>
.copyRight {
	margin-top: 30px;
	width: 1004px;
	height: 100px;
	float: left;
	border-top: 1px dashed silver;
	/* 	background-color: yellow; */
}
</style>
<script type="text/javascript">

	function about(){
		
		alert("本大学生校园生活论坛系统由东北电力大学计算机学院曾泰所编写，旨在"+
			"为广大校友提供一个方便知识传播、经验交流和发展兴趣社交的校园平台。目前论坛处于初步建设阶段,更多功能正在开发中···");
		return false;
		
	}
	
	function help(){
		alert("①如何发帖：发帖需要注册我们的论坛账号，可在首页右上方点击“注册”按钮进行注册，注册成功后，回到首页点击“我要发帖”即可发帖。"
				+"②如何评论：已注册用户，在帖子详细页面最下方可以进行回复。"+
				"③个人中心：个人中心可以查看你的帖子，评论，并且可以修改、删除你的帖子和删除评论，在个人资料选项卡可以进行个人资料的修改，最后一项修改密码选项卡可以进行密码修改。");
		return false;
		
	}


</script>


<div style="text-align: center;margin-top: 20px;" align="center">
	<font face="微软雅黑" size="2px" style="line-height: 30px;"><a href=" " onclick="return about()">关于论坛</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=" " onclick="return help()">论坛帮助</a>&nbsp;&nbsp;&nbsp;&nbsp;
	<c:choose>
		<c:when test="${sessionScope.userrole eq 1}">
			<a href="./manager/manage.jsp"><font style="color:red;">后台管理</font></a>
		</c:when>
		<c:otherwise>
			<font style="color: silver;">后台管理</font>
		</c:otherwise>	
	</c:choose>
	</font><br /> <font face="微软雅黑" size="2px">Copyright@zengtai |
		东北电力大学<br /> 电话：15216112034
		邮箱：1248740449@qq.com </font>
</div>
