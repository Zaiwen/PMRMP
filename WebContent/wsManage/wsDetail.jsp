<%@ page language="java"
	import="java.util.*,java.sql.*,databaseaccess.Access"
	pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<jsp:useBean id="DA" class="databaseaccess.Access" scope="page" />
<%@ taglib prefix="s" uri="/struts-tags" %>
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
<title>Web服务详细信息</title>
<link rel="stylesheet" type="text/css" href="wsstyle/wsinfo.css" />
<link rel="stylesheet" href="js/jquery-ui.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/rightbutton.js"></script>
<script>
	$(function() {
		$("#accordion").accordion();
	});
</script>
</head>
<body>
	<%
		String id = request.getParameter("id");
		if (id.length() < 1) {
			out.print("<script>alert('查看失败!');window.location.href='/BPEP/userManage/userAdmin.jsp'</script>");
		} else {
			try {
				DA.connDB("bpep");
				ResultSet res1, res2, res3, res4;
				String sql_sel1 = "SELECT * FROM serviceinfo WHERE id="
						+ id;
				String sql_sel2 = "SELECT * FROM operationinfo WHERE wsid="
						+ id;
				res1 = DA.executeSelectSql(sql_sel1);
				res2 = DA.executeSelectSql(sql_sel2);
				res1.first();
	%>
	<br />
	<br />
	<br />
	 <a style= "color:black" href="I18NAction.action?local=zh_CN&url=wsdetail&id=<%=id %>">中文</a> 
			 <a style= "color:black" href="I18NAction.action?local=en_US&url=wsdetail&id=<%=id %>">English</a> 
	<br />
	<br />
	<div id="accordion" class="accordion">
		<h3><s:i18n name="bpep"><s:text name="wsdl_detail"></s:text></s:i18n>:</h3>
		
			
		
		<div id="showdetails" class="accordion">
			<br /> <br />
			<table align="center" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="4" class="basicinformation"><s:i18n name="bpep"><s:text name="basic_info"></s:text></s:i18n>：</td>
				</tr>
				<tr>
					<td class="lefttext"><s:i18n name="bpep"><s:text name="service_id"></s:text></s:i18n></td>
					<td><%=res1.getString("id")%></td>
				</tr>
				<tr>
					<td class="lefttext"><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n></td>
					<td><%=res1.getString("name")%></td>

				</tr>
				<tr>
					<td class="lefttext"><s:i18n name="bpep"><s:text name="domain"></s:text></s:i18n></td>
					<td><%=res1.getString("domain")%></td>
				</tr>
				<tr>
					<td class="lefttext">WSDL URL</td>
					<td colspan="3"><%=res1.getString("wsdllocation")%></td>
				</tr>
				<tr>
					<td colspan="4" class="basicinformation"><s:i18n name="bpep"><s:text name="function"></s:text></s:i18n>：</td>
				</tr>
				<%
					while (res2.next()) {
				%>
				<tr>
					<td class="lefttext"><s:i18n name="bpep"><s:text name="option"></s:text></s:i18n></td>
					<td colspan="3">
						<table class="operationinfo">
							<tr>
								<td colspan="3"><s:i18n name="bpep"><s:text name="namespace"></s:text></s:i18n>:<%=res2.getString("NamespaceURI")%><br />
									<s:i18n name="bpep"><s:text name="input_sign"></s:text></s:i18n>:<%=res2.getString("InputMessageName")%><br /> <s:i18n name="bpep"><s:text name="output_sign"></s:text></s:i18n>:<%=res2.getString("OutputMessageName")%>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="lefttext"><s:i18n name="bpep"><s:text name="input"></s:text></s:i18n></td>
					<td colspan="3">
						<table class="operationinfo">
							<tr>
								<td>
									<%
										int opid = res2.getInt("opid");
													String sql_sel3 = "SELECT * FROM inputinfo WHERE wsid="
															+ id + " AND opid=" + opid;
													res3 = DA.executeSelectSql(sql_sel3);
													while (res3.next()) {
									%> <%
 	out.println(res3.getString("name") + ":");
 %> <%=res3.getString("kind")%><br /> <%
 	}
 %>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="lefttext"><s:i18n name="bpep"><s:text name="output"></s:text></s:i18n></td>
					<td colspan="3">
						<table class="operationinfo">
							<tr>
								<td>
									<%
										String sql_sel4 = "SELECT * FROM outputinfo WHERE wsid="
															+ id + " AND opid=" + opid;
													res4 = DA.executeSelectSql(sql_sel4);
													while (res4.next()) {
									%> <%
 	out.println(res4.getString("name") + ":");
 %> <%=res4.getString("kind")%> <br /> <%
 	}
 %>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<%
					}
				%>
			</table>
		</div>
	</div>
	<%
		DA.closeDB();
			} catch (Exception e) {
				out.print("<script>alert('出现异常!');window.location.href='/BPEP/index.html'</script>");
			}
		}
	%>
</body>
</html>
