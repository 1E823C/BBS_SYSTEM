<%@page import="java.net.URLEncoder"%>
<%@ page language="java" import="java.util.*,java.io.*" pageEncoding="UTF-8"%>
<%@page import="zt.bbs.entity.*" %>
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
	if (user == null || user == "") {
	PrintWriter pw = response.getWriter();
	pw.println("<script type='text/javascript'>alert('未登录或登录已失效！请登录！');location.href = '../login.jsp';</script>");
              //设置过滤器，当用户未登录时将不能进入页面
} 
%>


<title>个人中心</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<style type="text/css">
.Homebackground {
	width: 1004px;
	margin: 0 auto;
	min-height: 800px;
	background-color: #F5F5F5;
}

.topNav {
	float: left;
}

.leftBodyBlank {
	width: 22px;
	min-height: 100px;
	float: left;
}

.tBody {
	width: 960px;
	font-family: 微软雅黑;
	float: left;
}

.topBody {
	width: 960px;
	height: 24px;
	margin-top: 10px;
	float: left;
	font-size: 14px;
	font-family: 微软雅黑;
}

.userStyle {
	width: 960px;
	margin-top: 15px;
	float: left;
	font-size: 14px;
	/* border: 1px solid #C2D5E3; */
	min-height: 400px;
}

.leftBodyNav {
	width: 140px;
	max-height: 450px;
	float: left;
	padding: 0px 25px 0px 15px;
}

.leftBodyNavStyle {
	width: 100%;
	height: 40px;
	line-height: 40px;
	font-size: 16px;
	float: left;
	border-bottom: 1px solid white;
	background-color: #A2C1DE;
}

.leftBodyNavStyle:hover {
	background-color: #89ADCE;
	color: white;
}

.leftBodyNavDown {
	width: 100%;
	height: 40px;
	line-height: 40px;
	font-size: 16px;
	float: left;
	border-bottom: 1px solid white;
	background-color: #A2C1DE;
}

.rightBody {
	width: 778px;
	max-height: 450px;
	float: left;
	/* border-left: 1px solid #C2D5E3; */
}

.content {
	width: 778px;
	float: left;
	background-color: white;
}

.butt {
	background-color: #6699CC;
	width: 100px;
	height: 30px;
	border: 0;
	color: white;
	font-size: 18px;
}

.butt:hover {
	background-color: #71AAE3;
	border: 0;
}

.leftBodyNav a:LINK,.leftBodyNav a:HOVER,.leftBodyNav a:ACTIVE,.leftBodyNav a:VISITED,.leftBodyNav a:FOCUS
	{
	text-decoration: none;
}
</style>
<script type="text/javascript">
if (self.location != top.location) {
	top.location.href = self.location.href;
}
function iFrameHeight(n) {
	var ifm = document.getElementById("test" + n);
	var subWeb = document.frames ? document.frames["test" + n].document
			: ifm.contentDocument;
	if (ifm != null && subWeb != null) {
		ifm.height = subWeb.body.scrollHeight;
	}
}
function test_item(n) {
	var menu = document.getElementById("leftBodyNav");
	var menuli = menu.getElementsByTagName("div");
	for ( var i = 1; i <= menuli.length; i++) {
		menuli[n - 1].className = "leftBodyNavDown";
		menuli[i - 1].className = "leftBodyNavStyle";
		document.getElementById("home").style.display = 'none';//隐藏
		document.getElementById("content").style.display = 'block';//隐藏  
	}
};



	
</script>
</head>

<body>
<%
	User user1= new User();
	user1= (User)request.getSession().getAttribute("userInfo");     //获取登录的用户的信息
	
%>

	<div class="Homebackground">
		<div class="topNav">
			<jsp:include page="/top.jsp"></jsp:include>
		</div>
		<div class="leftBodyBlank"></div>
		<div class="tBody">
			<div class="topBody" align="left">
				<div style="float: left;">
					<a href="index.jsp" title="论坛首页"><img
						src="image/homepage_24.png"></img> </a>
				</div>
				<div style="float: left;line-height:24px;">
					&nbsp;&gt;&nbsp;<a href="./index.jsp">论坛</a>&nbsp;&gt;&nbsp;个人中心
				</div>
			</div>
			<div class="userStyle">
				<div class="leftBodyNav" id="leftBodyNav">
					
					<a href="util/user?opr=myTopics" target="test1"
						onclick="return checkULogin()"><div class="leftBodyNavStyle"
							id="leftBodyNavStyle_2" onclick="javascript:test_item(2);">我的帖子</div>
					</a>
					<a href="util/user?opr=myComments" target="test1"
						onclick="return checkULogin()"><div class="leftBodyNavStyle"
							id="leftBodyNavStyle_2" onclick="javascript:test_item(2);">我的评论</div>
					</a>
					 <a href="util/user?opr=updateUInfo" target="test1"
						onclick="return checkULogin()"><div class="leftBodyNavStyle"
							id="leftBodyNavStyle_3" onclick="javascript:test_item(4);">修改资料</div>
					</a> <a href="./user/updatePass.jsp" target="test1"
						onclick="return checkULogin()"><div class="leftBodyNavStyle"
							id="leftBodyNavStyle_4" onclick="javascript:test_item(5);">修改密码</div>
					</a> 
				</div>
				<div class="rightBody">
					<div id="home" style="background-color: white;">
						<div
							style="float: left;background-color: white;width: 50px;max-height:450px;min-height: 420px;"></div>
						<div align="left"
							style="float: left;background-color: white;width: 360px;max-height:450px;min-height: 420px;line-height: 28px;">
							<p>
								用户名： <font
									style="font-size: 20px;line-height: 30px;color: #6699CC;font-weight: bolder;">
									 ${sessionScope.userInfo.username}</font>
								<br />性别：${sessionScope.userInfo.sex}
								
								<br />年龄：${sessionScope.userInfo.age}
								
								<br />邮箱：${sessionScope.userInfo.email}
								
								<br />用户权限：
			
								<c:choose>       
									<c:when test="${sessionScope.userInfo.role eq 1 }">
									    <font color="red">管理员</font>
									</c:when>
									
									<c:otherwise>
									      普通用户
									</c:otherwise>
								</c:choose>
								    
								<br />我的帖子数：${sessionScope.userInfo.topic_count}
								
								&nbsp;&nbsp;&nbsp;&nbsp;<br />我的评论数：${sessionScope.userInfo.comment_count}
								
								
						</div>
						<div align="left"
							style="float: left;background-color: white;width: 360px;min-height: 420px;max-height:450px">
							<p>
								&nbsp;&nbsp;&nbsp;&nbsp;<img alt="我的头像"
									style="width: 140px;height: 200px;"  onerror="this.src='./image/upicture.png'" 
									src="${sessionScope.userInfo.picture}">
							<p>
								个人简介：<br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								${sessionScope.userInfo.introduction}
						</div>

					</div>
					<div class="content" id="content" style="display: none">
						<iframe id="test1" class="no" name="test1" frameBorder="0"
							scrolling="no" width="100%" height="100%"
							onLoad="iFrameHeight(1)"></iframe>
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
