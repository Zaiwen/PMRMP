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
	font-size: 14px;
	margin: 60px auto;
}

#url {
	width: 360px;
}

#next {
	font-size: 12px;
	padding: 2px 16px;
}
</style>
</head>
<body>

	<center>
		<form action="import_wsdl_2.jsp" method="post">
			<span>WSDL URL:</span> <input id='url' type='text' name='url' /><br />
			<br /> <input id='next' type='submit' value='下一步' />
		</form>
	</center>
</body>
</html>