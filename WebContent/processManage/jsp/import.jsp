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
<style type="text/css">
li {
	line-height: 200%;
	font-size: 14px;
}

a {
	color: blue;
}

a:visited {
	color: blue;
}
</style>
</head>
<body>

	<ol>
		<li><a href="import_wsdl_1.jsp">通过WSDL导入</a></li>
		<li><a href="#">引用共享的流程</a></li>
	</ol>
</body>
</html>