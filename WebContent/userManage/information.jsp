<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
<title>Information</title>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/rightbutton.js"></script>
</head>
<body>

	<div id="content" align="center">
		<%
			if (session.getAttribute("Adminusername") == null
					&& session.getAttribute("ISVusername") == null
					&& session.getAttribute("PPusername") == null
					&& session.getAttribute("ORGusername") == null) {
				out.print("<script>alert('您没有登录，不能进入该界面!');window.location.href='/BPEP/index.jsp'</script>");
				return;
			} else if (session.getAttribute("Adminusername") != null
					&& session.getAttribute("ISVusername") == null
					&& session.getAttribute("PPusername") == null
					&& session.getAttribute("ORGusername") == null) {
				out.print("<script>window.location.href='/BPEP/userManage/userAdmin.jsp'</script>");
				return;
			} else if (session.getAttribute("Adminusername") == null
					&& session.getAttribute("ISVusername") != null
					&& session.getAttribute("PPusername") == null
					&& session.getAttribute("ORGusername") == null) {
				out.print("<script>window.location.href='/BPEP/userManage/userISV.jsp'</script>");
				return;
			} else if (session.getAttribute("Adminusername") == null
					&& session.getAttribute("ISVusername") == null
					&& session.getAttribute("PPusername") != null
					&& session.getAttribute("ORGusername") == null) {
				out.print("<script>window.location.href='/BPEP/userManage/userPP.jsp'</script>");
				return;
			} else if (session.getAttribute("Adminusername") == null
					&& session.getAttribute("ISVusername") == null
					&& session.getAttribute("PPusername") == null
					&& session.getAttribute("ORGusername") != null) {
				out.print("<script>window.location.href='/BPEP/userManage/userORG.jsp'</script>");
				return;
			}
		%>
	</div>

</body>
</html>