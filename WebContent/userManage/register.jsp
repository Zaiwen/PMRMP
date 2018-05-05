<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<% 
request.setCharacterEncoding("UTF-8");
String path=request.getContextPath();
   String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册</title>
<link rel="stylesheet" type="text/css" href="style/register.css" />
<link rel="stylesheet" href="js/jquery-ui.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/rightbutton.js"></script>
<script type="text/javascript" src="js/imagecode.js"></script>
<script type="text/javascript" src="js/email.js"></script>
<script type="text/javascript" src="js/specialstringcheck.js"></script>
<script>
$(function(){
	$("#accordion").accordion();
});
</script>
<script language="JavaScript">
function resultcheckReg()
{
	if(formRegister.check.value.length != 4)
	{
		alert("请输入4位验证码!");
		formRegister.check.focus();
		return (false);
	}
	else  if(formRegister.username.value.length<1)
   {  alert("请输入用户名!");
      formRegister.username.focus();
      return (false);
    }
	else if(!checkSpecial(formRegister.username.value))
	{
	    alert("用户名不能包含特殊字符!");
		formRegister.username.focus();
		return false;
	}
	else  if(formRegister.userpassword.value.length<1)
    { alert("请输入密码!");
      formRegister.userpassword.focus();
      return (false);
     }
	 else if(!checkSpecial(formRegister.userpassword.value))
	{
	    alert("密码不能包含特殊字符!");
		formRegister.userpassword.focus();
		return false;
	}
	else if(formRegister.reasons.value.length<1)
	{ alert("请输入注册理由,以方便管理员认证!");
	  formRegister.reasons.focus();
	  return (false);
	}
	else if(!checkSpecial(formRegister.reasons.value))
	{
	    alert("注册理由不能包含特殊字符!");
		formRegister.reasons.focus();
		return false;
	}
	else{
		return (true);
	}
 }
 </script>
<style>
A {text-decoration: NONE} 
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
		 <li class = "noneline"><a href="I18NAction.action?local=en_US&url=register">English</a>
		 <ul class="user">
		    <li><a href="I18NAction.action?local=zh_CN&url=register">中文</a></li>
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
			<li>湖北・武汉 　WHU </li>
			<li><br/></li>
			
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

<div id="content"class="content">
	<br/><br/>
	<br/>
	<div id="accordion" class="accordion">
	<h3><s:i18n name="bpep"><s:text name="role"></s:text></s:i18n><s:i18n name="bpep"><s:text name="register"></s:text></s:i18n></h3>
	<div id="showdetails" class="accordion">
	<br/>
	<form  name="formRegister"  method="post" action="userManage/registerresult.jsp" onsubmit="return resultcheckReg();">
		<table>
		<tr>
		<td><s:i18n name="bpep"><s:text name="role"></s:text></s:i18n>:</td>
		<td><select name="usertype" >
	             <option value="ISV"selected="selected">ISV register</option>
	             <option value="PP">P P register</option>
	             <option value="ORG">ORG register</option>
	             </select></td>
		</tr>
		<tr>
		<td><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n>:</td>
		<td><input type="text" name="username"class="text username" maxlength="60"/></td>
		</tr>
		<tr>
		<td><s:i18n name="bpep"><s:text name="password"></s:text></s:i18n>:</td>
		<td><input type="password" name="userpassword" class="text password" maxlength="60"/></td>
		</tr>
		<tr>
		<td><s:i18n name="bpep"><s:text name="register_reason"></s:text></s:i18n>:</td>
		<td><textarea name="reasons" class="text reasons"></textarea></td>
		</tr>
		<tr>
		<td><s:i18n name="bpep"><s:text name="verify_code"></s:text></s:i18n>:</td>
		<td><input type="text" name="check" id="check"  class="text check" maxlength="4"/></td>
		</tr>
		<tr>
		<td><img src="imgcode" id="picture"/></td>
		<td ><a style="display:block;line-height:23px;margin-top:10px;font-size:13px;font:宋体;text-align:center;" href="javascript:void(0);" onclick="change()" ><s:i18n name="bpep"><s:text name="tips"></s:text></s:i18n></a></td>
		</tr>
		<tr>
		<td ><input type="submit" name="submit" value="&nbsp;注&nbsp;册&nbsp;" class="submit" /></td>
		<td><input type="reset"name="reset"  value="&nbsp;取&nbsp;消&nbsp;" class="reset" /></td>
		</tr>
		 </table>
	</form>
	</div>
	</div>
</div>

<jsp:include page = "/style/footer.jsp"/>


</body>
</html>