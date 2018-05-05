<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>  
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%
	request.setCharacterEncoding("UTF-8");
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业IT人员登陆</title>
<link rel="stylesheet" type="text/css" href="style/login.css" />
<link rel="stylesheet" href="js/jquery-ui.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/rightbutton.js"></script>
<script type="text/javascript" src="js/imagecode.js"></script>
<script type="text/javascript" src="js/specialstringcheck.js"></script>
<script>
	$(function() {
		$("#accordion").accordion();
	});
</script>
<script language="javascript">
	function resultcheckorg() {
		if (formORG.check.value.length != 4) {
			alert("请输入4位验证码!");
			formORG.check.focus();
			return (false);
		} else if (formORG.username.value.length < 1) {
			alert("请输入用户名!");
			formORG.username.focus();
			return (false);
		} else if (!checkSpecial(formORG.username.value)) {
			alert("用户名不能包含特殊字符!");
			formORG.username.focus();
			return false;
		} else if (formORG.userpassword.value.length < 1) {
			alert("请输入密码!");
			formORG.userpassword.focus();
			return (false);
		} else if (!checkSpecial(formORG.userpassword.value)) {
			alert("密码不能包含特殊字符!");
			formORG.userpassword.focus();
			return false;
		} else {
			return (true);
		}
	}
</script>
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
		<li class="noneline"><a href="index.jsp"><s:i18n name="bpep"><s:text name="index"></s:text></s:i18n></a></li>
		<li class="noneline"><s:i18n name="bpep"><s:text name="login"></s:text></s:i18n>
		  <ul class="login">
		    <li><a href="login/login1.jsp">ISV login</a></li>
		    <li><a href="login/login2.jsp" >PP login</a></li>
		    <li><a href="login/login3.jsp" >ORG login</a></li>
		    <li><a href="login/adminlogin.jsp">Admin login</a></li>
		  </ul>
        </li>
        <li class="noneline"><s:i18n name="bpep"><s:text name="users"></s:text></s:i18n>
		  <ul class="user">
		    <li><a href="userManage/information.jsp" ><s:i18n name="bpep"><s:text name="info"></s:text></s:i18n></a></li>
			<li><a href="userManage/register.jsp" ><s:i18n name="bpep"><s:text name="register"></s:text></s:i18n></a></li>
			<li><a href="logout.jsp" ><s:i18n name="bpep"><s:text name="logout"></s:text></s:i18n></a></li>
		  </ul>
		</li>
		<li class="noneline"><s:i18n name="bpep"><s:text name="tools"></s:text></s:i18n>
		   <ul class="tools">
		   <li><a href="###" target="_blank"><s:i18n name="bpep"><s:text name="process_edit"></s:text></s:i18n></a></li>
		   <li><a href="###" target="_blank"><s:i18n name="bpep"><s:text name="plugin_design"></s:text></s:i18n></a></li>
		   <li><a href="###" target="_blank"><s:i18n name="bpep"><s:text name="process_plugin"></s:text></s:i18n></a></li>
		   </ul>
		</li>
		<li class="noneline"><a href="help/help.jsp" ><s:i18n name="bpep"><s:text name="help"></s:text></s:i18n></a></li>
		<li class="noneline"><a href="help/about.jsp" ><s:i18n name="bpep"><s:text name="about"></s:text></s:i18n></a></li>
		 <li class = "noneline"><a href="I18NAction.action?local=en_US&url=login3">English</a>
		 <ul class="user">
		    <li><a href="I18NAction.action?local=zh_CN&url=login3">中文</a></li>
		  </ul>
		 </li>
		 
    
		
	</ul>	
</div>


	<div id="sidebar">
	<div id="sidetop">
		<h1>Contact us</h1>
		<ul id="conus">
			<li>QQ: 1694844516 </li>
			<li>Email: zwfeng@whu.edu.cn </li>
			<li>Telephone: 155-2781-4852 </li>
			<li><s:i18n name="bpep"><s:text name="left_place"></s:text></s:i18n> </li>
			<li> 
i18n:<s:i18n name="bpep">
<s:text name="hello"></s:text>
</s:i18n></li>
			<li>
			
			</li>
		</ul>
		<h1>Links</h1>
		<ul id="links">
			<li><a href="http://www.whu.edu.cn/" target="_balnk">WHU</a> </li>
			<li><a href="http://www.sklse.org/" target="_blank"><s:i18n name="bpep"><s:text name="link_labname"></s:text></s:i18n></a> </li>
			<li><a href="http://cs.whu.edu.cn/cs2011/" target="_blank">WHU CS</a> </li>
			<li><a href="http://www.lib.whu.edu.cn/" target="_blank">WHU Library</a> </li>
			<li><a href="http://iss.whu.edu.cn/" target="_blank">WHU ISS</a> </li>
			
		</ul>
		
	</div>
	</div>

	<div id="content" align="center">
	<br/><br/>
	<br/>
		<div id="accordion" class="accordion">
			<h3>Organizations</h3>
			<div id="showdetails" class="accordion">
				<br />
				<form name="formORG" method="post"
					action="logincheck/checkORGuser.jsp"
					onsubmit="return resultcheckorg();">
					<table align="center">
						<tr>
							<td><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n></td>
							<td><input type="text" name="username" class="text username"
								maxlength="50" /></td>
						</tr>
						<tr>
							<td><s:i18n name="bpep"><s:text name="password"></s:text></s:i18n></td>
							<td><input type="password" name="userpassword"
								class="text password" maxlength="50" /></td>
						</tr>
						<tr>
							<td><s:i18n name="bpep"><s:text name="verify_code"></s:text></s:i18n></td>
							<td><input type="text" name="check" id="check"
								class="text check" maxlength="4" /></td>
						</tr>
						<tr>
							<td><img src="imgcode" id="picture" /></td>
							<td><a
								style="display: block; line-height: 23px; margin-top: 10px; font-size: 13px; font: 宋体; text-align: center;"
								href="javascript:void(0);" onclick="change()"><s:i18n name="bpep"><s:text name="tips"></s:text></s:i18n></a></td>
						</tr>
						<tr>
							<td><input type="submit" name="submit"
								value="Submit" class="submit" /></td>
							<td><input type="reset" name="reset"
								value="Cancel" class="reset" /></td>
						</tr>
					</table>
				</form>
				</div>
				</div>
			</div>

			<jsp:include page="/style/footer.jsp" />
</body>
</html>