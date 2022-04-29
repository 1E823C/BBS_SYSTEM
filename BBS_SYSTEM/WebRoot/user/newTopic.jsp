<%@ page language="java" import="java.util.*,java.io.*" pageEncoding="UTF-8"%>
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
              //设置过滤器，当用户未登录时将不能进入本页面
} 
%>

   
<title>发表帖子</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css"
	href="./ueditor/third-party/SyntaxHighlighter/shCoreDefault.css">
<script type="text/javascript"
	src="./ueditor/third-party/SyntaxHighlighter/shCore.js"></script>
<!-- 配置文件 -->
<script type="text/javascript" src="./ueditor/Topic-ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="./ueditor/ueditor.all.js"></script>
<!-- 语言包文件(建议手动加载语言包，避免在ie下，因为加载语言失败导致编辑器加载失败) -->
<script type="text/javascript" src="./ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="./JS/jquery-1.11.0.js"></script>
<script type="text/javascript">
	var UEDITOR_HOME_URL = "/ueditor/";//从项目的根目录开始
	SyntaxHighlighter.all();
	function validToipc() {
		var topTitle = $("#tTitle").val().replace(/(&nbsp;)|\s|\u00a0/g, '');
		var topCate = $("#selectCate").val();
		if (topTitle == "" || topTitle == null) {
			alert("请填写文章标题!");
			return false;
		}else if(editor.getContent().replace(/(&nbsp;)|\s|\u00a0/g, '')==""||editor.getContent().replace(/(&nbsp;)|\s|\u00a0/g, '')==null){
			alert("文章内容不能为空！");
			return false;
		} else if (editor.getContentLength(true) < 10) {
			alert("文章内容不得少于10个字符！");
			return false;
		} else if (topCate == 0) {
			alert("请选择版块！");
			return false;
		} 
		$("#content").val(editor.getContent());
		return true;
	};
	
	function showLen(obj){
   		document.getElementById('span').innerHTML = ' 您还可以输入'+ (50 - obj.value.length) +'个字符';
   		document.getElementById('span').style.color='red';
	}
	
</script>
<style type="text/css">
.background {
	width: 1004px;
	overflow: auto;
	background-color: #F5F5F5;
	margin: 0px auto; /*设置居中  */
}

.smallNav {
	width: 982px;
	height: 24px;
	margin-left: 22px;
	margin-top: 15px;
	float: left;
	font-size: 14px;
	font-family: 微软雅黑;
}

.titleStyle {
	width: 940px;
	height: 40px;
	line-height: 40px;
	margin-left: 22px;
	margin-top: 15px;
	padding-left: 20px;
	background-color: #A2C1DE;
	font-family: 微软雅黑;
	float: left;
	text-align: left;
}

.newStyle {
	width: 960px;
	margin-left: 22px;
	margin-top: 20px;
	font-family: 微软雅黑;
	float: left;
	/* border: 1px solid silver; */
}

.newStyle table {
	border-right: 1px solid #A9CBEE;
	border-bottom: 1px solid #A9CBEE
}

.newStyle table td,.newStyle table th {
	height: 30px;
	line-height: 30px;
	border-left: 1px solid #A9CBEE;
	border-top: 1px solid #A9CBEE;
}

.newStyle select {
	font-family: 微软雅黑;
	border: 1px solid #A9CBEE;
}

.butt {
	background-color: #6699CC;
	width: 120px;
	height: 30px;
	margin-left: 10px;
	margin-top: 15px;
	border: 0;
	color: white;
	font-size: 18px;
	border: 0;
}

.butt:hover {
	background-color: #71AAE3;
	border: 0;
}

.notice {
	margin: 10px 10px 10px 10px;
	padding: 10px 10px 10px 25px;
	border: solid 1px #ccc;
}
</style>
</head>

<body>
	<div class="background">
		<div class="top_nav">
			<jsp:include page="../top.jsp"></jsp:include>
		</div>
		<div class="newToic" align="center">
			<div class="smallNav" align="left">
				<div style="float: left;">
					<a href="index.jsp" title="论坛首页"><img
						src="image/homepage_24.png"></img> </a>
				</div>
				<div style="float: left;line-height:24px;">
					&nbsp;&gt;&nbsp;<a>论坛</a>&nbsp;&gt;&nbsp;发帖
				</div>
			</div>
			<form id="newT" method="post" onsubmit="return validToipc()"
				action="util/topic?opr=addTopic">
				<div class="titleStyle" align="left">发布帖子</div>
				<div class="newStyle">
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<th width="150px" align="center">文章标题</th>
							<td><input type="text" name="topic.title" id="tTitle" onkeyup="showLen(this);"
								style="width: 600px;height: 26px;line-height: 24px;font-size: 14px;padding-left: 6px;"
								maxlength="50" /><span id="span"
								style="color:red;font-size:14px"> 您还可以输入50个字符</span></td>
						</tr>
						<tr>
							<th align="center">文章内容</th>
							<td width="700px"><textarea id="content"
									style="display: none;" name="topic.content"></textarea> <script
									id="container" type="text/plain"></script>
							</td>
							<script type="text/javascript">        <!--加载UEditor 富文本编辑器 -->
							var editor = UE.getEditor('container');
						    </script>
						</tr>
						<tr>
							<th align="center" style="height: 50px">选择版块</th>
							<td>
								<table>
									<tr>
										<td><select 
										
											style="height:26px;width: 200px;font-size:14px"
											id="selectCate" " name="topic.type" >
												<option value="0">--请选择版块--</option>	          <!-- 获取所有版块的信息 -->
												<c:forEach items="${sessionScope.typeList}" var="typeList"> 						
													<option value="${typeList.tid}">${typeList.name}</option>
												</c:forEach>		
					
										</select>
										</td>
									</tr>
								</table></td>
						</tr>
						<tr>
							<th style="height: 180px;">&nbsp;</th>
							<td align="left" style="height: 180px;"><input type="submit"
								value="发表帖子" class="butt" onclick="return validToipc()" /><input
								type="button" value="取消" class="butt"
								onclick="if(window.confirm('你确定要取消吗？')){history.go(-1);};" />
								<ol class="notice">
									<li>请文明发言，不要发布与黄色暴力、政治、宗教相关内容。</a></li>
									<li>请对您的言行负责，并遵守有关法律法规,尊重网上道德。</li>
								</ol></td>
						</tr>
					</table>
				</div>
			</form>
		</div>
		
		<div class="copyRight">
			<jsp:include page="../copyRight.jsp"></jsp:include>
		</div>
	</div>
</body>
</html>
