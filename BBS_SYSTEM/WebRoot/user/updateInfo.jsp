<%@ page language="java" import="java.util.*,java.io.*" pageEncoding="UTF-8"%>
<%@page import="zt.bbs.entity.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%
   String user = (String)session.getAttribute("uname");
	if (user == null || user == "") {
	PrintWriter pw = response.getWriter();
	pw.println("<script type='text/javascript'>alert('未登录或登录已失效！请登录！');location.href = '../login.jsp';</script>");
              //设置过滤器，当用户未登录时将不能进入本页面
} 
%>
<base href="<%=basePath%>">

<style type="text/css">
#home {
	background-color: white;
	width: 778px;
	height: 360px;;
	font-size: 14px;
	font-family: 微软雅黑;
	font-size: 14px;
}

.inputText {
	width: 180px;
	height: 30px;
	font-size: 16px;
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

.file {
	border: 1px solid #C2D5E3;
	height: 24px
}
</style>
<script type="text/javascript" src="./JS/jquery-1.11.0.js"></script>
<script type="text/javascript" src="./JS/uploadPic.js"></script>
<script type="text/javascript">
	function iFrameHeight(n) {
		var ifm = document.getElementById("test" + n);
		var subWeb = document.frames ? document.frames["test" + n].document
				: ifm.contentDocument;
		if (ifm != null && subWeb != null) {
			ifm.height = subWeb.body.scrollHeight;
		}
	}
	
	function isEmail(str) {
		var reg = /[a-z0-9-]{1,30}@[a-z0-9-]{1,65}.[a-z]{3}/;
		return reg.test(str);
	}
	
	function checkInfo(){
		var email = $("#uEmail").val().replace(/(&nbsp;)|\s|\u00a0/g, '');
		var age = $("#age").val().replace(/(&nbsp;)|\s|\u00a0/g, '');
		var intro = $("#intro").val().replace(/(&nbsp;)|\s|\u00a0/g, '');
		if (email == "" || email == null) {
			alert("邮箱不能为空!");
			return false;
		}else if(!isEmail(email)){
			alert("请注意邮箱格式!");
			return false;
		}
		else if(age == "" || age ==null){
			alert("年龄不能为空!");
			return false;
		}else if(isNaN(age)){
			alert("年龄只能为数字!");
			return false;
		}
		else if(intro == "" || intro == null){
			alert("个人介绍不能为空!");
			return false;
		}
		return true;
	}
	
	
 	var msg=""+'${request.tipMessage}'; 
 	if(msg!=""){ 
   		alert(msg); 
 	} 
 	
</script>

<%
	User user1= new User();
	user1= (User)request.getSession().getAttribute("userInfo");   //获取登录的用户的信息
%>



<div id="home">

	<div
		style="float: left;background-color: white;width: 50px;height: 100%"></div>
	<div style="float: left;width: 460px;height: 100%">
		<form action="util/user" method="post" onsubmit="return checkInfo()">
			<div align="left"
				style="float: left;background-color: white;width: 100%;height: 90%;line-height: 28px;">
				<table>
				
					<tr height="30px;">
						<td width="60px;">用户名:</td>
						<td>${sessionScope.userInfo.username}</td>
					</tr>
		
					<tr>
					  
						<td>性&nbsp;&nbsp;&nbsp;别:</td>        <!-- 从数据库中加载出之前的性别,并使用EL表达式来输出 -->
						<td><input name="userSex" type="radio" ${sessionScope.userInfo.sex eq "男" ? "checked" : "" } value="男"> 男 
						    <input name="userSex" type="radio" ${sessionScope.userInfo.sex eq "女" ? "checked" : "" } value="女"> 女 
						    <input name="userSex" type="radio" ${sessionScope.userInfo.sex eq "保密" ? "checked" : "" } value="保密"> 保密
						    <input type="hidden" name="opr" value="submitUpdate"/>  <!-- 设置隐藏属性，提交修改表单到userServlet -->
					    </td>
					</tr>
					<tr>
						<td>邮&nbsp;&nbsp;&nbsp;箱:</td>
						<td><input type="text" name="userEmail" class="inputText"
							id="uEmail" value="${sessionScope.userInfo.email}"
							onfocus="warnEmail()" onblur="return checkemail();" />
							
						</td>
						<td id="emailts"
							style="height:20px;line-height:20px;text-align: left;font-size: 12px;"></td>
					</tr>
					<tr>
						<td>年&nbsp;&nbsp;&nbsp;龄:</td>
						<td><input type="text" class="inputText" name="userAge" id="age"
							value="${sessionScope.userInfo.age}">
						</td>
					</tr>

					
				</table>
				个人简介:<br />
				<textarea style="width: 400px;height: 100px;resize: none;" name="userIntro" id="intro">${sessionScope.userInfo.introduction}</textarea>

			</div>
			<div align="center"
				style="float: left;background-color: white;width: 80%;height:10%;margin-top: -30px;">
				<input type="submit" value="保存资料" class="butt" />
			</div>
		</form>
	</div>
	<div align="center"
		style="float: left;background-color: white;width: 240px;height: 100%;border-left: 1px solid #C2D5E3;">
		<p>
			<img alt="我的头像" style="width: 160px;height: 220px;"  onerror="this.src='./image/upicture.png'" 
				src="${sessionScope.userInfo.picture}" >
		<form action="util/user?opr=uploadUPic" enctype="multipart/form-data" method="post" onsubmit="return uploadF()">
			<div align="right">
				<input type="text"  class="inputText" style="width: 160px;"  id="f_file" value="${sessionScope.userInfo.picture}"> 
				
				<input type="button" value="选择" class="butt" style="width: 50px;" onClick="fu.click()"> 
					
				<input name="fileUpload" type="file" id="fu" onchange="f_file.value=this.value" style="display:none">
				
			</div>
			<p>
				<input type="submit" value="上传" class="butt"
					style="height: 30px;width: 80px;font-size: 16px;" />
			<p align="left" style="margin-left: 10px;">&nbsp;&nbsp;&nbsp;&nbsp;允许上传的格式：bmp,png,jpg,jpeg!</p>
		</form>
	</div>

</div>




