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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body bgcolor="white">
	<%
		if ( session.getAttribute("PPusername") == null&&session.getAttribute("Adminusername")==null&&session.getAttribute("ORGusername")==null) {
			out.print("<script>alert('您没有以Process Provider或Administrator身份登录，不能进入该界面!');window.location.href='/BPEP/login/login2.jsp'</script>");
		} else{
	%>
	<center>
			<font size="4"><%=request.getSession().getAttribute("processdelete")%></font>
	</center>
	<%
	if(session.getAttribute("PPusername")!=null)
	     out.print("<script>alert('删除process文件失败!');window.location.href='/BPEP/userManage/userPP.jsp'</script>");
	else if(session.getAttribute("Adminusername")!=null)
		out.print("<script>alert('删除process文件失败!');window.location.href='/BPEP/userManage/userAdmin.jsp'</script>");
	else if(session.getAttribute("ORGusername")!=null)
		out.print("<script>alert('删除extendedprocess文件失败!');window.location.href='/BPEP/userManage/userORG.jsp'</script>");
		}
	%>

</body>
</html>
