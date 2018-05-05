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
<title>退出返回信息</title>
<script type="text/javascript" src="js/rightbutton.js"></script>
</head>
<body>

	<div id="content" align="center">
		<%
			if (session.getAttribute("Adminusername") != null
					&& session.getAttribute("ISVusername") == null
					&& session.getAttribute("PPusername") == null
					&& session.getAttribute("ORGusername") == null) {
				session.removeAttribute("Adminusername");
				session.invalidate();
				out.print("<script>alert('Admin管理员即将退出!');window.location.href='index.jsp'</script>");
				return;
			} else if (session.getAttribute("ISVusername") != null
					&& session.getAttribute("PPusername") == null
					&& session.getAttribute("ORGusername") == null) {
				session.removeAttribute("ISVusername");
				session.invalidate();
				out.print("<script>alert('ISV用户即将退出!');window.location.href='index.jsp'</script>");
				return;
			} else if (session.getAttribute("ISVusername") == null
					&& session.getAttribute("PPusername") != null
					&& session.getAttribute("ORGusername") == null) {
				session.removeAttribute("PPusername");
				session.invalidate();
				out.print("<script>alert('Process Provider用户即将退出!');window.location.href='index.jsp'</script>");
				return;
			} else if (session.getAttribute("ISVusername") == null
					&& session.getAttribute("PPusername") == null
					&& session.getAttribute("ORGusername") != null) {
				session.removeAttribute("ORGusername");
				session.invalidate();
				out.print("<script>alert('Organizations用户即将退出!');window.location.href='index.jsp'</script>");
				return;
			} else {
				out.print("<script>alert('您没有登录，不需要退出!');window.location.href='index.jsp'</script>");
			}
		%>
	</div>

</body>
</html>
