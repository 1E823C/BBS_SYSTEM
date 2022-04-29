<%@ page language="java" import="java.util.*"
	pageEncoding="UTF-8"%>
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

<title>${sessionScope.tInfo.title }</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css"
	href="./ueditor/third-party/SyntaxHighlighter/shCoreDefault.css">
<script type="text/javascript"
	src="./ueditor/third-party/SyntaxHighlighter/shCore.js"></script>
<script type="text/javascript" src="./ueditor/Comment-ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="./ueditor/ueditor.all.js"></script>
<!-- 语言包文件(建议手动加载语言包，避免在ie下，因为加载语言失败导致编辑器加载失败) -->
<script type="text/javascript" src="./ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="./JS/jquery-1.11.0.js"></script>
<script type="text/javascript" language="javascript">
	SyntaxHighlighter.all();
	function iFrameHeight() {
		var ifm = document.getElementById("iframepage");
		var subWeb = document.frames ? document.frames["iframepage"].document
				: ifm.contentDocument;
		if (ifm != null && subWeb != null) {
			ifm.height = subWeb.body.scrollHeight;
		}
	};
	function goDiv(div) {
		var a = $("#" + div).offset().top;
		$("html,body").animate({
			scrollTop : a
		}, 'slow');
	}
	function goTop() {
		$('html, body').animate({
			scrollTop : 0
		}, 'slow');
	};
	function validComment() {
		  if (editor.hasContents()) {
			if (editor.getContentLength(true) >= 5) {
				$("#content").val(editor.getContent());
				return true;
			}
			alert("评论字数不得少于5个字符！");
			return false;
		}
		alert("评论内容不能为空!");
		return false;
	}


</script>
<style type="text/css">
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

.background {
	width: 1030px;
	overflow: auto;
	background-color: #F5F5F5;
	margin: 0 auto;
	min-height: 700px;
}

.tBody {
	margin-left: 22px;
	font-family: 微软雅黑;
}

.smallNav {
	width: 982px;
	height: 24px;
	margin-top: 10px;
	float: left;
	font-size: 14px;
}

.editMenu {
	width: 520px;
	height: 30px;
	margin-top: 15px;
	float: left;
}

.pageNav {
	width: 440px;
	height: 30px;
	margin-top: 15px;
	float: left;
}

.pageNav a button {
	width: 35px;
	height: 30px;
	font-size: 14px;
	font-family: 微软雅黑;
	background-color: white;
	border: 1px solid #C2D5E3;
}

.pageNav a button:HOVER {
	width: 35px;
	height: 30px;
	font-size: 14px;
	font-family: 微软雅黑;
	background-color: #A2C1DE;
	border: 1px solid #C2D5E3;
}

.pageNav button {
	width: 35px;
	height: 30px;
	font-size: 14px;
	font-family: 微软雅黑;
	background-color: #EFF4FB;
	border: 1px solid #C2D5E3;
}

.butt {
	background-color: #6699CC;
	width: 100px;
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

.titleStyle {
	width: 960px;
	height: 40px;
	line-height: 40px;
	margin-top: 15px;
	background-color: #A2C1DE;
	float: left;
	text-align: left;
}

.topicStyle {
	float: left;
	width: 958px;
	min-height: 300px;
	background-color: #EFF4FB;
	border: 1px solid #C2D5E3;
}

.newComment {
	margin-top: 10px;
	float: left;
	width: 960px;
	height: 300px;
	background-color: white;
	border: 1px solid #C2D5E3;
	border-right: none;
	float: left;
}

.userInfo {
	width: 190px;
	height: 300px;
	float: left;
	background-color: #EFF4FB;
}

.commentInfo {
	float: left;
	width: 958px;
	/* 	min-height: 50px; */
	background-color: #EFF4FB;
	border: 1px solid #C2D5E3;
	font-family: 微软雅黑;
}

.commentUserInfo {
	width: 190px;
	height: 280px;
	float: left;
	background-color: #EFF4FB;
}

.theComment {
	text-align: left;
	width: 728px;
	min-height: 260px;
	float: left;
	padding: 10px 20px 20px 20px;
	word-break: break-all;
	float: left;
}

.commentEditLeftBlank {
	border-top: 1px solid #C2D5E3;
	width: 650px;
	float: left;
	height: 30px;
	line-height: 30px;
	word-break: break-all;
}

.commentEdit {
	border-top: 1px solid #C2D5E3;
	width: 100px;
	float: left;
	height: 30px;
	line-height: 30px;
	word-break: break-all;
}

.commentEdit input {
	background-color: silver;
	width: 40px;
	height: 20px;
	margin: 5px;
	float: left;
	border: 0;
	color: white;
	font-size: 14px;
}

.commentEdit input:hover {
	background-color: #71AAE3;
	border: 0;
}
</style>
</head>

<body>

	<!--使用JSTL标签获取分页显示的总页数和当前的页码  -->
	<c:set var="totalPages" value="${pageObj.totalPageCount }"/>
	<c:set var="pageIndex" value="${pageObj.currPageNo }"/>


	<div class="background">
		<div class="topNav">
			<jsp:include page="../top.jsp"></jsp:include>
		</div>
		<div class="tBody">
			<div class="smallNav" align="left">
				<div style="float: left;">
					<a href="index.jsp" title="论坛首页"><img
						src="image/homepage_24.png"></img> </a>
				</div>
				<div style="float: left;line-height:24px;">
					&nbsp;&gt;&nbsp;<a href="./index.jsp">论坛</a>&nbsp;&gt;&nbsp;
					${sessionScope.tInfo.title }
				</div>
			</div>
			<div class="editMenu">
				<input type="button" value="刷新"
						onclick="window.location.href = '/BBS_SYSTEM/util/topic?opr=getTopic&opr2=${sessionScope.tInfo.id}' "
						class="butt" style="width: 80px;" />
			</div>
			<div class="pageNav" align="right">     <!-- 分页显示，页码部分 -->
				<c:if test="${pageIndex > 1}"> 
					<a href="util/topic?opr=getTopic&opr2=${sessionScope.tInfo.id}&pageIndex=1" style="width: 80px;font-family: 微软雅黑" >[首页]</a>&nbsp;
          			<a href="util/topic?opr=getTopic&opr2=${sessionScope.tInfo.id}&pageIndex=${pageIndex - 1}" style="width: 80px;" >[上一页]</a>
					</c:if>
					
							[${pageIndex } / ${totalPages }]
							
					<c:if test="${pageIndex < totalPages}">
          				<a href="util/topic?opr=getTopic&opr2=${sessionScope.tInfo.id}&pageIndex=${pageIndex + 1} "  >[下一页]</a>
         				<a href="util/topic?opr=getTopic&opr2=${sessionScope.tInfo.id}&pageIndex=${totalPages} ">[末页]</a> 
      				</c:if>	
			</div>
						<!-- 第一页时或者没有评论时，显示楼主信息 -->
		<c:if test="${pageIndex == 1 || pageIndex == 0}">  
			<div class="titleStyle" align="left">
				<div style="width:800px;float: left;font-size: 18px; font-weight:bold">
					&nbsp; 
					${sessionScope.tInfo.title }
					
				</div>
				<div style="width:140px;float: left;height: 40px;line-height: 40px;"
					align="center">
					
				</div>
			</div>
			<div class="topicStyle">
				<input id="topicId" value=" "
					style="display: none;"> 
					<input id="topicUserId"
					value=""
					style="display: none;"> 
					<input id="currentUserId"
					value=""
					style="display: none;">
				<div class="userInfo">
					<div style="height:180px;" align="center">
						<img width="120px;" height="160px;" style="padding-top: 20px;" onerror="this.src='./image/upicture.png'" 
							src="${sessionScope.tInfo.topic_userPic}">
					</div>
					<div style="height:80px;padding-top: 20px" align="center">
						<a
							href="util/user?opr=otherUInfo&opr2=${sessionScope.tInfo.topic_username}">${sessionScope.tInfo.topic_username }  </a> <br />
						
					</div>
				</div>
				<div
					style="border-left:1px solid  #C2D5E3;min-height: 300px;float: left;width: 760px;">
					<div class="theTop">
						<div
							style="height: 28px;line-height:28px;font-size:13px;width:440px;text-align: left;float: left;border-bottom:1px solid  #C2D5E3;"
							align="left">
							&nbsp;&nbsp;最后更新于：<fmt:formatDate value="${sessionScope.tInfo.topic_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
							
						</div>
						<div
							style="height: 28px;line-height:28px;font-size:14px;width:320px;text-align: right;float: left;border-bottom:1px solid  #C2D5E3;"
							align="left">楼主&nbsp;&nbsp;&nbsp;&nbsp;</div>
					</div>
					<div
						style="width: 766px;min-height:270px;background-color: #F8F8F8;float: left;">
						<div
							style="text-align: left;width: 728px;float: left;padding:10px 20px 20px 20px;word-break: break-all;">
							${sessionScope.tInfo.content }
						</div>
					</div>
				</div>
			</div>
		</c:if>
				<!-- 楼层功能，设置变量，默认是1，循环一次自动+1 -->
			 <c:set value="1" var="floor"></c:set>
			
			<div>
							<!-- 分页显示评论--正文部分 -->
				<c:if test="${!empty sessionScope.tComment}">			
				<c:forEach items="${sessionScope.tComment}" var="tComment">
					<div class="commentInfo">
						
							<div class="commentUserInfo">
								<div style="width:200px;height:180px;" align="center">
									<img alt="上传图片" width="120px;" height="160px;" onerror="this.src='./image/upicture.png'" 
										style="padding-top: 20px"
										src="${tComment.comment_userPic}">
								</div>
								<div style="width:200px;height:80px;padding-top: 20px"
									align="center">
									<a
										href="util/user?opr=otherUInfo&opr2=${tComment.comment_username} ">${tComment.comment_username}  </a> <br />
									
								</div>
								<br>
							</div>
							<div
								style="border-left:1px solid  #C2D5E3;min-height: 280px;float: left;width: 758px">
								<div
									style="height: 28px;line-height:28px;font-size:13px;width:440px;text-align: left;float: left;border-bottom:1px solid  #C2D5E3;"
									align="left">
									&nbsp;&nbsp;评论于：<fmt:formatDate value="${tComment.comment_time }" pattern="yyyy-MM-dd HH:mm:ss"/>
									
								</div>
								<div
									style="height: 28px;line-height:28px;font-size:14px;width:318px;text-align: right;float: left;border-bottom:1px solid  #C2D5E3;"
									align="left">
									 &nbsp;&nbsp;# ${(pageIndex-1)*10+floor}楼
									<c:set value="${floor + 1 }" var="floor"></c:set>
									&nbsp;&nbsp;
									
									
								</div>
								
								<div
									style="width: 766px;min-height:260px;background-color: #F8F8F8;float: left;">
									<div class="theComment"
										id="comment1 />">
										${tComment.c_content }
									</div>
								</div>
								<div class="commentEditLeftBlank"></div>
								
								<div class="commentEdit" align="right">
									<c:if test="${sessionScope.userrole eq 1 }">
									
									<form action="util/comment?opr=manDelComment" method="post" >    <!-- 删除表单 -->
										<input type="hidden" name="uname" value="${tComment.comment_username }">
										<input type="hidden" name="id" value="${tComment.comment_topic_id }">
										<input type="hidden" name="type_id" value="${tComment.comment_type_id }">
										<input type="hidden" name="cid" value="${tComment.cid}">
										<input type="submit" value="删除" onclick="var toDel = (confirm('删除此评论?')); return toDel" />
									</form>	
				
									</c:if>
									
								</div>
								
							
							</div>
							
							
					</div>
				</c:forEach>
				</c:if>
			</div>
			<div class="editMenu">
				
				
			
			</div>
			<div class="pageNav" align="right">
				<c:if test="${pageIndex > 1}"> 
					<a href="util/topic?opr=getTopic&opr2=${sessionScope.tInfo.id}&pageIndex=1" style="width: 80px;font-family: 微软雅黑" >[首页]</a>&nbsp;
          			<a href="util/topic?opr=getTopic&opr2=${sessionScope.tInfo.id}&pageIndex=${pageIndex - 1}" style="width: 80px;" >[上一页]</a>
					</c:if>
					
							[${pageIndex } / ${totalPages }]
							
					<c:if test="${pageIndex < totalPages}">
          				<a href="util/topic?opr=getTopic&opr2=${sessionScope.tInfo.id}&pageIndex=${pageIndex + 1} "  >[下一页]</a>
         				<a href="util/topic?opr=getTopic&opr2=${sessionScope.tInfo.id}&pageIndex=${totalPages} ">[末页]</a> 
      				</c:if>	
				
			</div>
			<div class="newComment" id="newComment">

				<form action="util/comment?opr=addComment " method="post"
					style="float:left;">
					<input name="topic_id" value="${sessionScope.tInfo.id}"
						style="display: none;"> 
					<input name="c_username"
						value="${sessionScope.uname}"
						style="display: none;"> 
					<input name="type_id" value="${sessionScope.tInfo.type_id}"
					     style="display:none" >
					<div class="userInfo">
						
						
					</div>
					<div style="float:left;width:740px;">
						<textarea id="content" style="display: none;"
							name="comment.content"></textarea>
						<script id="container" type="text/plain"></script>
					</div>
					<script type="text/javascript">
						var editor = UE.getEditor('container');
					</script>
					<div
						style="width: 768px;height: 52px;line-height: 50px;float: left;border-left:1px solid  #C2D5E3;border-right:1px solid  silver;background-color: #F8F8F8"
						align="left">
						<c:set var="isLogin" value="${empty sessionScope.uname }" />     <!-- 设置变量 判断是否已经登录-->
						<c:choose>
							<c:when test="${isLogin }">                                    <!-- 未检测到登录 -->
								<input type="button" value="无法评论" class="butt"
								onclick=" "
								style="margin-top: 10px;margin-left: 20" />
								<font size="2px;">提示：要评论需要登录账户！</font>
							</c:when>
							<c:otherwise>
								<input type="submit" value="发表评论" class="butt"
								onclick="return validComment();"
								style="margin-top: 10px;margin-left: 20" />
								<font size="2px;">提示：请文明发言！</font>
							</c:otherwise>
						</c:choose>
			
						
					</div>
				</form>
			</div>
		</div>
		<div class="copyRight">
			<jsp:include page="../copyRight.jsp"></jsp:include>
		</div>
		<script type="text/javascript">
		function goDiv(div) {
		var a = $("#" + div).offset().top;
		$("html,body").animate({
			scrollTop : a
		}, 'slow');
	}
	function goTop() {
		$('html, body').animate({
			scrollTop : 0
		}, 'slow');
	};
	</script>
		<button onclick="goDiv('newComment')" id="quick-reply"
			class="btn btn-top" title="快速回复"
			style="cursor: pointer; border: 0px none; bottom: 35px; min-width: 40px; height: 40px; margin: 0px; padding: 0px; position: fixed; right: 40px; display: block;">
			<span class="glyphicon glyphicon-share-alt"></span>
		</button>
		<button onclick="goTop()" id="scroll-top" class="btn btn-top"
			title="返回顶部"
			style="cursor: pointer; border: 0px none; bottom: 80px; min-width: 40px; height: 40px; margin: 0px; padding: 0px; position: fixed; right: 40px; display: block;">
			<span class="glyphicon glyphicon-chevron-up"></span>
		</button>
	</div>
</body>

</html>
