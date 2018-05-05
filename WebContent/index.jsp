<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>  
<%@ taglib prefix="s" uri="/struts-tags" %>  
<title>BusinessProcessExtension</title>
<link rel="stylesheet" type="text/css" href="style/basic.css" />
<script type="text/javascript" src="js/rightbutton.js"></script>
<style>
A {text-decoration: NONE;} 
</style>
</head>
<body >
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
		 <li class = "noneline"><a href="I18NAction.action?local=en_US&url=index">English</a>
		 <ul class="user">
		    <li><a href="I18NAction.action?local=zh_CN&url=index">中文</a></li>
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

<div id="content">
	<dl id="pd">
		<dt id="dt1"><s:i18n name="bpep"><s:text name="introduce_label"></s:text></s:i18n></dt>
		<dd><s:i18n name="bpep"><s:text name="introduction"></s:text></s:i18n> </dd>
		<dd class="line"></dd>
		<dd><s:i18n name="bpep"><s:text name="introduce_leader"></s:text></s:i18n></dd>
		<dd class="line"></dd>
		<dt id="dt2"><s:i18n name="bpep"><s:text name="project_introduce"></s:text></s:i18n></dt>
		<dd></dd>
		<dd><img src="images/ProjectDescription1.jpg" alt="pic1" height="450" width="538"/></dd>
		<dd></dd>
		<dd class="line"></dd>
		<dd><img src="images/ProjectDescription2.jpg" alt="pic2" height="310" width="492"/></dd>
	</dl>
	
</div>

<div id="footer">
		<ul>
			<li><a href="mailto:zwfeng@whu.edu.cn"><s:i18n name="bpep"><s:text name="contact"></s:text></s:i18n></a></li>
			<li><s:i18n name="bpep"><s:text name="links"></s:text></s:i18n></li>
			<li><s:i18n name="bpep"><s:text name="comments"></s:text></s:i18n></li>
			
		</ul>
		<strong>Copyright  &amp;copy: 2013 <s:i18n name="bpep"><s:text name="bottom_place"></s:text></s:i18n>.&nbsp;&nbsp;<s:i18n name="bpep"><s:text name="copyright"></s:text></s:i18n>&nbsp;&nbsp;<s:i18n name="bpep"><s:text name="zip_code"></s:text></s:i18n>430072</strong>
</div>


</body>
</html>