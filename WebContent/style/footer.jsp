<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>  
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <link rel="stylesheet" type="text/css" href="style/basic.css" />
<script type="text/javascript" src="js/rightbutton.js"></script>
<style>
A {text-decoration: NONE;} 
</style>
   
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
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
