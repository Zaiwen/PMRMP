<%@ page language="java" import="java.util.*,java.sql.*"
	pageEncoding="utf-8"%>
<jsp:useBean id="DA" class="databaseaccess.Access" scope="page" />
<%
	request.setCharacterEncoding("UTF-8");
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册返回信息</title>
<link rel="stylesheet" type="text/css" href="style/check.css" />
<script type="text/javascript" src="js/rightbutton.js"></script>
<style>
A {
	text-decoration: NONE;
}
</style>
</head>
<body>

	<div id="header"></div>

	<div id="top">
		<ul>
			<li class="noneline"><a href="index.html">首页</a></li>
			<li class="noneline">登录
				<ul class="login">
					<li><a href="login/login1.jsp">ISV login</a></li>
					<li><a href="login/login2.jsp">PP login</a></li>
					<li><a href="login/login3.jsp">ORG login</a></li>
					<li><a href="login/adminlogin.jsp">Admin login</a></li>
				</ul>
			</li>
			<li class="noneline">用户
				<ul class="user">
					<li><a href="userManage/information.jsp">信息</a></li>
					<li><a href="userManage/register.jsp">注册</a></li>
					<li><a href="logout.jsp">退出</a></li>
				</ul>
			</li>
			<li class="noneline">工具
				<ul class="tools">
					<li><a href="###" target="_blank">编辑流程</a></li>
					<li><a href="###" target="_blank">插件设计</a></li>
					<li><a href="###" target="_blank">流程插件</a></li>
				</ul>
			</li>
			<li class="noneline"><a href="help/help.jsp">帮助</a></li>
			<li class="noneline"><a href="help/about.jsp">关于</a></li>
			<li class="end"><a href="###">留言</a></li>
		</ul>
	</div>


	<div id="sidebar">
		<div id="sidetop">
			<h1>Contact us</h1>
			<ul id="conus">
				<li>QQ: 1694844516</li>
				<li>Email: zwfeng@whu.edu.cn</li>
				<li>Telephone: 155-2781-4852</li>
				<li>湖北・武汉 WHU</li>
				<li><br /></li>
				<li><script language=JavaScript>
					today = new Date();
					function initArray() {
						this.length = initArray.arguments.length;
						for ( var i = 0; i < this.length; i++)
							this[i + 1] = initArray.arguments[i];
					}
					var d = new initArray("星期日", "星期一", "星期二", "星期三", "星期四",
							"星期五", "星期六");
					document.write(today.getFullYear(), "年",
							today.getMonth() + 1, "月", today.getDate(), "日",
							d[today.getDay() + 1], "</font>");
				</script></li>
			</ul>
			<h1>Links</h1>
			<ul id="links">
				<li><a href="http://www.whu.edu.cn/" target="_balnk">WHU</a></li>
				<li><a href="http://www.sklse.org/" target="_blank">WHU国家重点实验室</a>
				</li>
				<li><a href="http://cs.whu.edu.cn/cs2011/" target="_blank">WHU
						CS</a></li>
				<li><a href="http://www.lib.whu.edu.cn/" target="_blank">WHU
						Library</a></li>
				<li><a href="http://iss.whu.edu.cn/" target="_blank">WHU
						ISS</a></li>

			</ul>

		</div>
	</div>

	<div id="content" align="center">
		<%
			out.println("<br/><br/><br/><h2>注册成功!<br/><br/>请记住: 角色类型(ISV/PP/ORG)、用户名与密码!<br/><br/>管理员审核通过后，欢迎登录使用!</h2>");
		    out.print("<script>alert('注册成功!(管理员审核通过后，欢迎登录使用!)');</script>");
		%>
	</div>

	<div id="footer">
		<ul>
			<li><a href="mailto:zwfeng@whu.edu.cn">联系我们</a> |</li>
			<li>友情链接 |</li>
			<li>留言 |</li>
		</ul>
		<strong>Copyright &amp;copy: 2013
			WHU-版权所有.&nbsp;&nbsp;地址：武汉市武昌区珞珈山 &nbsp;&nbsp;邮编：430072</strong>
	</div>


</body>
</html>
