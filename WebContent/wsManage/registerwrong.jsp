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
		if (session.getAttribute("Adminusername") == null) {
			out.print("<script>alert('您没有以Admin身份登录，不能进入该界面!');window.location.href='/BPEP/login/adminlogin.jsp'</script>");
			return;
		} else {
	%>
	<center>
		<font size="2"><%=request.getSession().getAttribute("wserror")%></font>
	</center>
	<%
		out.print("<script>alert('web服务注册失败了!');window.location.href='/BPEP/userManage/userAdmin.jsp'</script>");
		}
	%>

</body>
</html>
