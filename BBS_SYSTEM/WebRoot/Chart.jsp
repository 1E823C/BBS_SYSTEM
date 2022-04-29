<%@ page language="java"
	import="java.util.*,zt.bbs.entity.*,java.io.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%
   String user = (String)session.getAttribute("uname");
   String userrole = (String)session.getAttribute("userrole");
	if (user == null || user == "") {
	PrintWriter pw = response.getWriter();
	pw.println("<script type='text/javascript'>alert('未登录或登录已失效！请登录！');location.href = 'login.jsp';</script>");
              //设置过滤器，当用户未登录时将不能进入本页面
}else if(!userrole.equals("1")){
	PrintWriter pw = response.getWriter();
	pw.println("<script type='text/javascript'>alert('本页面不允许非管理员进入！');location.href = 'index.jsp';</script>");
			//设置过滤器，禁止普通用户进入管理页面
}
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<script type="text/javascript"
	src="http://echarts.baidu.com/build/dist/echarts.js"></script>
<script type="text/javascript" src="./JS/jquery.min.js"></script>
<title>图表统计</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="./CSS/manage.css" />
<script type="text/javascript" src="./JS/jquery-1.11.0.js"></script>
<style type="text/css">
body,html {
	width: 1004px;
	padding: 0px;
	margin: 0px auto; /*设置居中  */
	text-align: center; /*兼容性更好  */
	font-family: "微软雅黑";
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

.pageNav a button {
	width: 30px;
	height: 25px;
	font-size: 14px;
	font-family: 微软雅黑;
	background-color: white;
	border: 1px solid silver;
}

.pageNav a button:HOVER {
	width: 30px;
	height: 25px;
	font-size: 14px;
	font-family: 微软雅黑;
	background-color: #A2C1DE;
	border: 1px solid silver;
}

.pageNav button {
	width: 30px;
	height: 25px;
	font-size: 14px;
	font-family: 微软雅黑;
	background-color: #EFF4FB;
	border: 1px solid silver;
}

table tr td {
	border-bottom: 1px solid silver;
	border-right: 1px solid silver;
	padding-left: 3px;
}

.annoStyle {
	width: 790px;
	margin-left: 10px;
	margin-right: 5px;
}

.annoStyle a:link,.annoStyle a:visited {
	color: blue;
	text-decoration: none;
} /* 未被访问的链接 */ /*已被访问的链接 */
.annoStyle a:hover {
	color: #2979BF;
	font-weight: bolder;
	text-decoration: none;
} /* 鼠标指针移动到链接上 */
.butt {
	background-color: #6699CC;
	width: 120px;
	height: 30px;
	margin-top: 10px;
	margin-bottom: 10px;
	border: 0;
	color: white;
	font-size: 18px;
	border: 0;
}

.butt:hover {
	background-color: #71AAE3;
	border: 0;
}
</style>

</head>

<body>

<!--使用JSTL标签获取分页显示的总页数和当前的页码  -->
	<c:set var="totalPages" value="${pageObj.totalPageCount }"/>
	<c:set var="pageIndex" value="${pageObj.currPageNo }"/>


<div class="body">
	<div class="top">
			<img alt="" src="image/manager_top.jpg" />
	</div>
	
	<div class="left">
			<jsp:include page="./manager/left.jsp"></jsp:include>
	</div>
	
	<div class="right">
			<div id="echart1" style="height: 270px; width: 540Px"></div>  
			<div id="echart2" style="height: 270px; width: 700Px"></div>
<script type="text/javascript">
	// 路径配置
	require.config({
		paths : {
			echarts : 'http://echarts.baidu.com/build/dist'
		}
	});
	// 使用
	require([ 'echarts', 'echarts/chart/bar' ,'echarts/chart/line'// 使用柱状图就加载bar模块，按需加载
	], 
	drewEcharts
);
	function drewEcharts(ec) {
		// 基于准备好的dom，初始化echarts图表
		var myChart = ec.init(document.getElementById('echart1'));
       
		var option = {
			    title : {
			        text: '版块信息统计柱状图',
			        
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['帖子数','评论数']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            dataView : {show: true, readOnly: false},
			            magicType : {show: true, type: ['line', 'bar']},
			            
			            
			        }
			    },
			    calculable : true,
			    xAxis : [
			        {
			             type: 'category',
			             data : (function(){
			            var arr=[];
			            $.ajax({
			                 type : "post",
			                 async : false, //同步执行
			                 url : "echart",
			                 data : {},
			                dataType : "json", //返回数据形式为json
			                 success : function(result) {
			                   if (result) {
			                        for(var i=0;i<result.length;i++){
			                        console.log(result[i].name);
			                       arr.push(result[i].name);
			                     }    
			                   }
			                                        
			                },
			                    error : function(errorMsg) {
			                        alert("不好意思，图表请求数据失败啦!");
			                         myChart.hideLoading();
			                       }
			               })
			                 return arr;
			              })() ,
			               axisLabel:{  
			                         interval:0,//横轴信息全部显示  
			                           
			                    }   
			        }
			    ],
			yAxis : [ {
				type : 'value'
			} ],
			series : [ 
			     {
				"name" : "帖子数",
				"type" : "bar",
				"data" : (function(){
                                        var arr=[];
                                        $.ajax({
                                        type : "post",
                                        async : false, //同步执行
                                        url : "echart",
                                        data : {},
                                        dataType : "json", //返回数据形式为json
                                        success : function(result) {
                                        if (result) {
                                               for(var i=0;i<result.length;i++){
                                                  console.log(result[i].topics_count);
                                                  arr.push(result[i].topics_count);
                                                }  
                                        }
                                    },
                                    error : function(errorMsg) {
                                        alert("图表请求数据失败啦!");
                                        myChart.hideLoading();
                                    }
                                   })
                                  return arr;
				 })(),
				 
				  markPoint : {
                data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            
				 },
			
				{
				name : '评论数',
				type : 'bar',
				"data" : (function() {
					var arr = [];
					$.ajax({
						type : "post",
						async : false, //同步执行
						url : "echart",
						data : {},
						dataType : "json", //返回数据形式为json
						success : function(result) {
							if (result) {
								for (var i = 0; i < result.length; i++) {
									console.log(result[i].comments_count);
									arr.push(result[i].comments_count);
								}
							}
						},
						error : function(errorMsg) {
							alert("图表请求数据失败啦!");
							myChart.hideLoading();
						}
					})
					return arr;
				})(),
				markPoint : {
	                data : [
	                        {type : 'max', name: '最大值'},
	                        {type : 'min', name: '最小值'}
	                    ]
	                },
	                
	    	}

			]
		};

		// 为echarts对象加载数据 
		myChart.setOption(option);
	}
</script>    
			
<script type="text/javascript">

		// 路径配置  
	require.config({
		paths : {
			echarts : 'http://echarts.baidu.com/build/dist'
		}
	});  
  
	// 使用  
     require(['echarts','echarts/chart/pie',  'echarts/chart/funnel'// 使用柱状图就加载bar模块，按需加载 
   ],  
   function (ec) {  
       // 基于准备好的dom，初始化echarts图表  
       var myChart = ec.init(document.getElementById('echart2'));  
       option = {
 				title : {
    			 text: '版块信息统计饼状图',
    			
     			x:'center'
		 },
 				tooltip : {
    			 trigger: 'item',
     			formatter: "{a} <br/>{b} : {c} ({d}%)"
 		},
 		legend: {
 	          orient : 'vertical',
 	          x : 'left',
 	          data:["帖子数","评论数"]
 	      },
 	     toolbox: {
 	         show : true,
 	          feature : {
 	              //mark : {show: true},
 	              dataView : {show: true, readOnly: false},
 	             magicType : {
 	                 show: true, 
 	                  type: ['pie'],
 	                  option: {
 	                     
 	                  }
 	              },
 	              
 	          }
 	      },
 	     calculable : true,
 	    series : [
 	          {
 	              name:'帖子数',
 	              type:'pie',
 	              radius : '45%',
 	              center: ['28%', '50%'],
 	              data : (function(){
 	              var arr=[];
 	              $.ajax({
 	                  type : "post",
 	                   async : false, //同步执行
 	                   url : "pie",
 	                   data : {},
 	                  dataType : "json", //返回数据形式为json
 	                  success : function(result) {
 	                     if (result) {
 	                         for(var i=0;i<result.listTCount.length;i++){
 	                     
 	                         //alert(result.listCont[i]+" "+result.listName[i]);
 	                          arr.push({
 	                            name : result.listname[i],
 	                            value : result.listTCount[i]
 	                          }); 
 	                      
 	                       }    
 	                    }
 	                                          
 	                  },
 	                      error : function(errorMsg) {
 	                          alert("不好意思,图表请求数据失败啦!");
 	                          myChart.hideLoading();
 	                         }
 	                 })
 	                   return arr;
 	                })() 
 	             
 	          },
 	          
 	         {
	              name:'评论数',
	              type:'pie',
	              radius : '45%',
	              center: ['70%', '50%'],
	              data : (function(){
	              var arr=[];
	              $.ajax({
	                  type : "post",
	                   async : false, //同步执行
	                   url : "pie",
	                   data : {},
	                  dataType : "json", //返回数据形式为json
	                  success : function(result) {
	                     if (result) {
	                         for(var i=0;i<result.listCCount.length;i++){
	                     
	                         //alert(result.listCont[i]+" "+result.listName[i]);
	                          arr.push({
	                            name : result.listname[i],
	                            value : result.listCCount[i]
	                          }); 
	                      
	                       }    
	                    }
	                                          
	                  },
	                      error : function(errorMsg) {
	                          alert("不好意思,图表请求数据失败啦!");
	                          myChart.hideLoading();
	                         }
	                 })
	                   return arr;
	                })() 
	             
	          }   
 	          
 	          
 	      ]
       };
       
       
       // 为echarts对象加载数据   
       myChart.setOption(option);   
   }  
);    
 	  
</script>

		</div>
	</div>

</body>

</html>
