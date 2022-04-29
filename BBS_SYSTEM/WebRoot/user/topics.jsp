<%@ page language="java" import="java.util.*,java.io.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<base href="<%=basePath%>">

<%
   String user = (String)session.getAttribute("uname");
	if (user == null || user == "") {
	PrintWriter pw = response.getWriter();
	pw.println("<script type='text/javascript'>alert('未登录或登录已失效！请登录！');location.href = '../login.jsp';</script>");
              //设置过滤器，当用户未登录时将不能进入本页面
} 
%>
<script type="text/javascript">
	function iframeAutoFit() {
		try {
			if (window != parent) {
				var a = parent.document.getElementsByTagName("IFRAME");
				for ( var i = 0; i < a.length; i++) //author:meizz
				{
					if (a[i].contentWindow == window) {
						var h1 = 0, h2 = 0;
						a[i].parentNode.style.height = a[i].offsetHeight + "px";
						a[i].style.height = "10px";
						if (document.documentElement
								&& document.documentElement.scrollHeight) {
							h1 = document.documentElement.scrollHeight;
						}
						if (document.body)
							h2 = document.body.scrollHeight;
						var h = Math.max(h1, h2);
						if (document.all) {
							h += 4;
						}
						if (window.opera) {
							h += 1;
						}
						a[i].style.height = a[i].parentNode.style.height = h
								+ "px";
					}
				}
			}
		} catch (ex) {
		}
	}
	if (window.attachEvent) {
		window.attachEvent("onload", iframeAutoFit);
		//window.attachEvent("onresize",  iframeAutoFit);
	} else if (window.addEventListener) {
		window.addEventListener('load', iframeAutoFit, false);
		//window.addEventListener('resize',  iframeAutoFit,  false);
	}
</script>
<script type="text/javascript" src="./JS/jquery-1.11.0.js"></script>
<style type="text/css">
.background {
	width: 100%;
	height: 100%;
	margin: 0 auto;
	font-family: "微软雅黑";
}

.topicTitle:link,.topicTitle:visited {
	color: #525252;
	text-decoration: none;
}

.topicTitle:hover {
	color: #2C86E5;
	text-decoration: underline;
}

.pageNav {
	width: 777px;
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

.listTopicStyle {
	background-color: white;
	margin-bottom: 10px;
	border: 1px solid #C2D5E3;
}

.listTopicStyle a:link,.listTopicStyle a:visited {
	color: black;
	text-decoration: none;
} /* 未被访问的链接 */ /*已被访问的链接 */
.listTopicStyle a:hover {
	color: #2979BF;
	font-weight: bolder;
	text-decoration: none;
} /* 鼠标指针移动到链接上 */
</style>

<!--使用JSTL标签获取分页显示的总页数和当前的页码  -->
	<c:set var="totalPages" value="${pageObj.totalPageCount }"/>
	<c:set var="pageIndex" value="${pageObj.currPageNo }"/>


<div class="background">
	<div style="min-height: 350px;">
		
		<c:if test="${myTopics == null || fn:length(myTopics)==0}">   
		<h1 align="center" style="color: #6699CC">你没有发布帖子</h1>	
		</c:if> 
		 
		
		<c:forEach items="${sessionScope.myTopics }" var="myTopics">
			<table class="listTopicStyle">
				<tr>
					<td
						style="border-bottom: 1px solid #C2D5E3;background-color: #EFF4FB;width:750px;height: 20px">
						<div style="width: 400px;text-align: left;float: left;">
							  <font style="font-size: 12px;color: #817E7E;">
							  [评论量: ${myTopics.comment_count}]</font>
						</div>
						<div style="width: 300px;text-align: right;float: left;">
							<font style="font-size: 14px;color: #817E7E;">发布时间:<fmt:formatDate value="${myTopics.topic_time}" pattern="yyyy-MM-dd HH:mm:ss"/></font>
						</div>
					</td>
				</tr>
				<tr>
					<td style="width:750px;height: 30px">
						<div
							style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;max-width: 750px;float: left;">
							<a href="util/topic?opr=getAllPageTopics&opr2=type&tid=${myTopics.type_id} " target="_top" style="font-size: 13px;">[${myTopics.type_name }]-</a>&nbsp;&nbsp;
							
							<a href="util/topic?opr=getTopic&opr2=${myTopics.id} " target="_top" class="topicTitle"><font
								style="font-size: 16px;font-weight: bold;">${myTopics.title } </font> </a>
						</div>
						<div
						     style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;max-width: 750px;float: right;">
						     
						     <a href="util/topic?opr=toModifyTopic&opr2=${myTopics.id} " target="_top" class="topicTitle"><font
								style="font-size: 16px;font-weight: bold;"> 修改 </font> </a>
								
							 <a href="util/topic?opr=deleteTopic&opr2=${myTopics.id}&tid=${myTopics.type_id}" target="_top" 
							    class="topicTitle" onclick="var toDel = (confirm('删除帖子后，相关评论也将删除，是否继续?')); return toDel"><font
								style="font-size: 16px;font-weight: bold;"> 删除 </font> </a>
								
							<!--  	util/topic?opr=deleteTopic&opr2=${myTopics.id}&tid=${myTopics.type_id}  -->
								
						</div>
					
					</td>
				</tr>
			</table>
		  </c:forEach>

	</div>
	
		
		<div class="pageNav" align="center">       <!-- 分页显示功能 -->
			
			<c:if test="${pageIndex > 1}"> 
					<a href="util/user?opr=myTopics&pageIndex=1" style="width: 80px;font-family: 微软雅黑" >[首页]</a>&nbsp;
          			<a href="util/user?opr=myTopics&pageIndex=${pageIndex - 1}" style="width: 80px;" >[上一页]</a>
					</c:if>
					
							[${pageIndex } / ${totalPages }]
							
					<c:if test="${pageIndex < totalPages}">
          				<a href="util/user?opr=myTopics&pageIndex=${pageIndex + 1}"  >[下一页]</a>
         				<a href="util/user?opr=myTopics&pageIndex=${totalPages}">[末页]</a> 
      				</c:if>	
			
		</div>
	
</div>