<%@ page language="java"
	import="java.util.*,zt.bbs.entity.*,java.io.*" pageEncoding="UTF-8"%>
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
   String userrole = (String)session.getAttribute("userrole");
	if (user == null || user == "") {
	PrintWriter pw = response.getWriter();
	pw.println("<script type='text/javascript'>alert('未登录或登录已失效！请登录！');location.href = '../login.jsp';</script>");
              //设置过滤器，当用户未登录时将不能进入本页面
}else if(!userrole.equals("1")){
	PrintWriter pw = response.getWriter();
	pw.println("<script type='text/javascript'>alert('本页面不允许非管理员进入！');location.href = '../index.jsp';</script>");
			//设置过滤器，禁止普通用户进入管理页面
}
%>

<title>版块管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="./CSS/manage.css" />
<style type="text/css">
body,html {
	width: 1004px;
	padding: 0px;
	margin: 0px auto; /*设置居中  */
	text-align: center; /*兼容性更好  */
	font-family: "微软雅黑";
}

table {
	word-wrap: break-word;
	word-break: break-all;
	table-layout: fixed;
}

.butt {
	background-color: #6699CC;
	width: 100px;
	height: 30px;
	margin-right: 10px;
	border: 0;
	color: white;
	font-size: 18px;
}

.butt:hover {
	background-color: #71AAE3;
	border: 0;
}

.buaa{

background-color: #6699CC;
width: 45px;
height: 22px;
margin-right: 10px;
border: 0;
color: white;
font-size: 14px;

}

.buaa:hover {
	background-color: #71AAE3;
	border: 0;
}


.pageNav {
	width: 550px;
	height: 30px;
	margin-top: 15px;
	margin-bottom: 5px;
	float: left;
}

.pageGo {
	width: 120px;
	height: 25px;
	line-height: 25px;
	margin-top: 15px;
	margin-bottom: 5px;
	float: left;
	font-size: 13px;
}

table tr td {
	border-bottom: 1px solid silver;
	border-right: 1px solid silver;
	padding-left: 3px;
}

.userStyle {
	width: 790px;
	margin-left: 10px;
	margin-right: 5px;
}

.userStyle a:link,.userStyle a:visited {
	color: blue;
	text-decoration: none;
} /* 未被访问的链接 */ /*已被访问的链接 */
.userStyle a:hover {
	color: #2979BF;
	font-weight: bolder;
	text-decoration: none;
} /* 鼠标指针移动到链接上 */

</style>
<script type="text/javascript" src="./JS/jquery-1.11.0.js"></script>
<script type="text/javascript">
	
	function checkAddType() {
		var cateName = $("#aType").val();
		var updateCateName = cateName.replace(/(&nbsp;)|\s|\u00a0/g, '');
		if (updateCateName == null || updateCateName == "") {
			alert("版块名称不能为空！");
			return false;
		}
		return true;
	};
	
</script>
</head>

<body>
	<div class="body">
		<div class="top">
			<img alt="" src="image/manager_top.jpg" />
		</div>
		<div class="left">
			<jsp:include page="./left.jsp"></jsp:include>
		</div>
		<div class="right">
			<div align="center" style="font-size: 24px;margin-top: 10px;">
				版 块 管 理<br />
			</div>
			
			<table class="userStyle" cellspacing="0">
				<tr style="background-color: #A2C1DE;height: 24px;">
					<td width="80px">版块名</td>
					<td width="80px">帖子总数</td>
					<td width="60px">评论总数</td>
					<td width="250px">修改名称</td>
					<td width="50px">删除版块 </td>
				</tr>
				
				<c:forEach items="${sessionScope.manTypeList}" var="manTypeList">
					<tr style="height: 26px;">
						<td style="border-left: 1px solid silver;">${manTypeList.name}
						</td>
				
						<td>
							${manTypeList.topics_count}
						</td>
						
						<td>
						    ${manTypeList.comments_count}
						</td>
						
						<td style="color: blue;">
							           <form action="util/type?opr=manMdfTypeName" name="form${manTypeList.tid}" method="post" style="margin:0" onsubmit="return checkUpdateType${manTypeList.tid}()">
									   <input type="text" name= "name" id="tName${manTypeList.tid}"/>  
									   <input type="hidden" name="tid" value="${manTypeList.tid}"/>
							           <input type="submit" value="修改" class="buaa" onclick="var toMdf = (confirm('确认修改吗?')); return toMdf"/> 
									   </form>
									<script type="text/javascript">
										function checkUpdateType${manTypeList.tid}() {
											var cateName = $("#tName${manTypeList.tid}").val();
											var updateCateName = cateName.replace(/(&nbsp;)|\s|\u00a0/g, '');
											if (updateCateName == null || updateCateName == "") {
												alert("版块名称不能为空！");
												return false;
											}
											return true;
										};
									</script>
									
									
						</td>
						
						<td style="color: blue;">
							<c:choose>
								<c:when test="${manTypeList.topics_count eq 0}">
									<form action="util/type?opr=manDelType" name="form2" method="post" style="margin:0">
									<input type="hidden" name="type_id" value="${manTypeList.tid}"/>
									<input type="submit" value="删除" class="buaa" onclick="var toDel = (confirm('确认删除该版块吗?')); return toDel" />
									</form>
								</c:when>
								<c:otherwise>
								<font style="color:silver">无法删除</font>	
								</c:otherwise>
							</c:choose>
						</td>
						
					</tr>
				</c:forEach>
			</table>
			
			
			<div align="center"
				style="float: left;width:100%;height:40px;border-top: 1px solid #2B4A78;">
				<form action="util/type?opr=manAddType" method="post" onsubmit="return checkAddType()">
					<table style='width:550px;'>
						<tr style='background-color: #A2C1DE;height:30px;'>
							<td width="200px"
								style="font-size: 24px;margin-top: 10px;color: #4D9EF0;background-color: white;text-align: left;">添
								加  版  块</td>
							<td style='width:150px;'>版块名称</td>
							<td style="background-color: white;width: 200px;"><input
								type="text" style="	width: 200px;height: 30px;font-size: 18px;"
								name="type_name" id="aType">
							</td>
							<td align="center"><input type="submit" value="添加"
								class="butt" />
							</td>
						</tr>
					</table>
				</form>
			</div>
			
			<div style="padding: 6px;margin-left: 5px;margin-right: 5px;margin-top:40px;font-size: 14px;line-height: 20px;color: white;float: left;font-family: 微软雅黑;	border: 1px solid #C2D5E3;">
					<font color="red">注意：由于版块下帖子和评论数众多，所以删除版块只能在帖子数为0时进行。</font>
			</div>
			

		</div>
	</div>
</body>
</html>
