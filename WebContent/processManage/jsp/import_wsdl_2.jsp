<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("username") == null) {
%>
<jsp:forward page="timeout.jsp"></jsp:forward>
<%
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>无标题</title>
<style>
form {
	font-family: Microsoft YaHei;
	font-size: 14px;
	margin: 60px auto;
}

#url {
	width: 360px;
}

#next {
	background: #EEEEEE;
	border: 2px outset #DDDDDD;
	font-family: Microsoft YaHei;
	font-size: 14px;
	padding: 2px 16px;
}
</style>
</head>
<body>

</body>
</html>