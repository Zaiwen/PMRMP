<%@ page language="java" import="java.util.*" pageEncoding="utf-8"
	import="java.util.*,java.sql.*,databaseaccess.Access"%>
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
<title>Delete Web Service</title>
</head>

<body>
	<%
		String id = request.getParameter("id");
		if (id.length() < 1) {
	         out.print("<script>alert('删除失败!');window.location.href='/BPEP/userManage/userAdmin.jsp'</script>");
	         return;
		} else {
			try {
				DA.connDB("bpep");
				ResultSet res;
				String sql_del = "DELETE FROM serviceinfo WHERE id=" + id;
				DA.executeUpdateSql(sql_del);
				sql_del = "DELETE FROM inputinfo WHERE wsid=" + id;
				DA.executeUpdateSql(sql_del);
				sql_del = "DELETE FROM outputinfo WHERE wsid=" + id;
				DA.executeUpdateSql(sql_del);
				sql_del = "DELETE FROM operationinfo WHERE wsid=" + id;
				DA.executeUpdateSql(sql_del);
				DA.closeDB();
				out.print("<script>window.location.href='/BPEP/userManage/userAdmin.jsp'</script>");
			} catch (SQLException e) {
				out.print("<script>alert('删除时出现异常!');window.location.href='/BPEP/userManage/userAdmin.jsp'</script>");
			}catch(Exception e){
				out.print("<script>alert('删除时出现异常!');window.location.href='/BPEP/userManage/userAdmin.jsp'</script>");
			}
		}
	%>
</body>
</html>
