<%@ page language="java" import="java.util.*,java.sql.*"
	pageEncoding="utf-8"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
</head>
<body bgcolor="white">
	<%
		if (session.getAttribute("ISVusername") != null) {
	%>
	<script language="javascript">
		alert("创建plugin文件失败!");
		window.location.href = "/BPEP/userManage/userISV.jsp";
	</script>
	<%
		} else if (session.getAttribute("PPusername") != null) {
	%>
	<script language="javascript">
		alert("创建process文件失败了!");
		window.location.href = "/BPEP/userManage/userPP.jsp";
	</script>
	<%
		} else if (session.getAttribute("ORGusername") != null) {
	%>
	<script language="javascript">
		alert("创建extendedprocess文件失败了! ");
		window.location.href = "index.html";
	</script>
	<%
		}
	%>
</body>
</html>
