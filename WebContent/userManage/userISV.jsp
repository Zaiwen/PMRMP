<%@ page language="java"
	import="java.util.*,java.sql.*,java.io.*,java.util.Date,ontology.*,databaseaccess.*,finalvariable.*"
	pageEncoding="utf-8" contentType="text/html; charset=UTF-8"  %>
<%@ taglib prefix="s" uri="/struts-tags" %> 
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

<title>ISV</title>
<link rel="stylesheet" type="text/css" href="style/userISV.css" />
<link rel="stylesheet" href="js/jquery-ui.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/email.js"></script>
<script type="text/javascript" src="js/rightbutton.js"></script>
<script type="text/javascript" src="js/imagecode.js"></script>
<script type="text/javascript" src="js/specialstringcheck.js"></script>
<script type="text/javascript" src="js/wrongkey.js"></script>
<script type="text/javascript" src="js/createPluginname.js"></script>
<script type="text/javascript" src="js/deletePluginname.js"></script>
<script type="text/javascript" src="js/hideandshow.js"></script>
<script type="text/javascript" src="js/alluserupdate.js"></script>
<script>
	$(function() {
		$("#accordion").accordion();
		$("#accordion2").accordion();
		$("#accordion3").accordion();
		$("#accordion4").accordion();
		$("#accordion5").accordion();
	});
</script>

<style type="text/css">
td a.ontodetails {
	color: blue;
	cursor: pointer;
	text-decoration: underline;
}

td a.wsdetails {
	color: blue;
	cursor: pointer;
	text-decoration: underline;
}

td a.processdetails {
	color: blue;
	cursor: pointer;
	text-decoration: underline;
}
td.plugindetails {
	color: blue;
	cursor: pointer;
	text-decoration: underline;
}td.plugindelete {
	color: blue;
	cursor: pointer;
	text-decoration: underline;
}
</style>

<style>
A {
	text-decoration: NONE;
}
</style>
</head>
<body>
	<%!String username;
	int permission;
	String email;
	String address;
	String details;
	String reasons;
	Boolean admitpermission;%>
	<div id="header"></div>

<div id="top">
	<ul>
		<li class="noneline"><a href="index.jsp"><s:i18n name="bpep"><s:text name="index"></s:text></s:i18n></a></li>
		<li class="noneline"><s:i18n name="bpep"><s:text name="login"></s:text></s:i18n>
		  <ul class="login">
		    <li><a href="login/login1.jsp">ISV login</a></li>
		    <li><a href="login/login2.jsp" >PP login</a></li>
		    <li><a href="login/login3.jsp" >ORG login</a></li>
		    <li><a href="login/adminlogin.jsp">Admin login</a></li>
		  </ul>
        </li>
        <li class="noneline"><s:i18n name="bpep"><s:text name="users"></s:text></s:i18n>
		  <ul class="user">
		    <li><a href="userManage/information.jsp" ><s:i18n name="bpep"><s:text name="info"></s:text></s:i18n></a></li>
			<li><a href="userManage/register.jsp" ><s:i18n name="bpep"><s:text name="register"></s:text></s:i18n></a></li>
			<li><a href="logout.jsp" ><s:i18n name="bpep"><s:text name="logout"></s:text></s:i18n></a></li>
		  </ul>
		</li>
		<li class="noneline"><s:i18n name="bpep"><s:text name="tools"></s:text></s:i18n>
		   <ul class="tools">
		   <li><a href="###" target="_blank"><s:i18n name="bpep"><s:text name="process_edit"></s:text></s:i18n></a></li>
		   <li><a href="###" target="_blank"><s:i18n name="bpep"><s:text name="plugin_design"></s:text></s:i18n></a></li>
		   <li><a href="###" target="_blank"><s:i18n name="bpep"><s:text name="process_plugin"></s:text></s:i18n></a></li>
		   </ul>
		</li>
		<li class="noneline"><a href="help/help.jsp" ><s:i18n name="bpep"><s:text name="help"></s:text></s:i18n></a></li>
		<li class="noneline"><a href="help/about.jsp" ><s:i18n name="bpep"><s:text name="about"></s:text></s:i18n></a></li>
		 <li class = "noneline"><a href="I18NAction.action?local=en_US&url=userISV">English</a>
		 <ul class="user">
		    <li><a href="I18NAction.action?local=zh_CN&url=userISV">中文</a></li>
		  </ul>
		 </li>
		 
    
		
	</ul>	
</div>


	<div id="sidebar">
		<div id="sidetop">
			<h1><s:i18n name="bpep"><s:text name="user_info"></s:text></s:i18n></h1>
			<ul id="conus">
				<li id="welcomeuser"><s:i18n name="bpep"><s:text name="welcome"></s:text></s:i18n>,<%=(String) session.getAttribute("ISVusername")%></li>
				<li id="conus1"><s:i18n name="bpep"><s:text name="basic_info"></s:text></s:i18n></li>
				<li id="conus2"><s:i18n name="bpep"><s:text name="info_edit"></s:text></s:i18n></li>
				<li id="conus3"><s:i18n name="bpep"><s:text name="plugin_design"></s:text></s:i18n></li>
				<li id="conus4"><s:i18n name="bpep"><s:text name="ontology_view"></s:text></s:i18n></li>
				<li id="conus5"><s:i18n name="bpep"><s:text name="service_view"></s:text></s:i18n></li>
				<li id="conus6"><s:i18n name="bpep"><s:text name="feedback"></s:text></s:i18n></li>
				<li id="userlogout"><a href="logout.jsp"><s:i18n name="bpep"><s:text name="logout"></s:text></s:i18n></a></li>
				<li id="showtime"><script language=JavaScript>
					$(function() {
						$("#conus>li").click(function() {
							$("#conus>li").css("background", "#FFFFF0");
							$(this).css("background", "#CAFF70");
						});
					});
					today = new Date();
					function initArray() {
						this.length = initArray.arguments.length;
						for ( var i = 0; i < this.length; i++)
							this[i + 1] = initArray.arguments[i];
					}
					var d = new initArray("星期日", "星期一", "星期二", "星期三", "星期四",
							"星期五", "星期六");
					document.write(today.getFullYear(), "年",
							today.getMonth() + 1, "月", today.getDate(), "日",
							d[today.getDay() + 1], "</font>");
				</script></li>
			</ul><br />
			<h1>Links</h1>
			<ul id="links">
				<li><a href="http://www.whu.edu.cn/" target="_balnk">WHU</a></li>
				<li><a href="http://www.sklse.org/" target="_blank"><s:i18n name="bpep"><s:text name="link_labname"></s:text></s:i18n></a>
				</li>
				<li><a href="http://cs.whu.edu.cn/cs2011/" target="_blank">WHU
						CS</a></li>
				<li><a href="http://www.lib.whu.edu.cn/" target="_blank">WHU
						Library</a></li>
				<li><a href="http://iss.whu.edu.cn/" target="_blank">WHU
						ISS</a></li>

			</ul>

		</div>
	</div>

	<div id="content" align="center">
		<%
			if (session.getAttribute("ISVusername") == null) {
				out.print("<script>alert('您没有以ISV身份登录，不能进入该界面!');window.location.href='/BPEP/login/login1.jsp'</script>");
				return;
			} else {
				try {
					ResultSet rs;
					String usertype = "bpep";
					String name = (String) session.getAttribute("ISVusername");
					String sql = "select * From ISVuser where name='" + name
							+ "'";
					DA.connDB(usertype);
					rs = DA.executeSelectSql(sql);
					if (rs.next()) {
						username = rs.getString(2);
						permission = Integer.parseInt(rs.getString(4));
						email = rs.getString(5);
						address = rs.getString(6);
						details = rs.getString(7);
						reasons = rs.getString(8);
					}
					DA.closeDB();
					if (permission == 1) {
						admitpermission = true;
					} else if (permission == 0) {
						admitpermission = false;
					}
					if (email == null || email.equals("")) {
						email = "null";
					}
					if (address == null || address.equals("")) {
						address = "null";
					}
		%>
		<div id="content1" align="center" class="content">
			<input type="button" class="inputbutton"
				onclick="javascript:window.location.reload()" value="刷&nbsp;&nbsp;新"></input><br />
			<br />
			<div id="accordion4" class="accordion">
				<h3><s:i18n name="bpep"><s:text name="user_info"></s:text></s:i18n></h3>
				<div id="showdetails6" class="accordion">
					<br /> <br />
					<table class="userinfo" align="center" width="300px"
						cellpadding="2" border="3">
						<tr>
							<th align="center" width="100%" colspan="2" height="30px">Independent
								Software Vender</th>
						</tr>
						<tr>
							<td align="center" width="50%" height="30px"><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n>：</td>
							<td align="center" width="50%" height="30px"><%=username%></td>
						</tr>
						<tr>
							<td align="center" width="50%" height="30px"><s:i18n name="bpep"><s:text name="verify"></s:text></s:i18n>：</td>
							<td align="center" width="50%" height="30px"><%=admitpermission%></td>
						</tr>
						<tr>
							<td align="center" width="50%" height="30px"><s:i18n name="bpep"><s:text name="mail"></s:text></s:i18n>：</td>
							<td align="center" width="50%" height="30px"><%=email%></td>
						</tr>
						<tr>
							<td align="center" width="50%" height="30px"><s:i18n name="bpep"><s:text name="address"></s:text></s:i18n>:</td>
							<td align="center" width="50%" height="30px"><%=address%></td>
						</tr>
						<tr>
							<td align="center" width="50%" height="30px"><s:i18n name="bpep"><s:text name="role"></s:text></s:i18n>:</td>
							<td align="center" width="50%" height="30px"><%=details%></td>
						</tr>
						<tr>
							<td align="center" width="50%" height="30px"><s:i18n name="bpep"><s:text name="register_reason"></s:text></s:i18n>:</td>
							<td align="center" width="50%" height="30px"><%=reasons%></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div id="content2" class="content">
			<input type="button" class="inputbutton"
				onclick="javascript:window.location.reload()" value="刷&nbsp;&nbsp;新"></input>
			<br /> <br />
			<div id="accordion3" class="accordion">
				<h3><s:i18n name="bpep"><s:text name="info_edit"></s:text></s:i18n></h3>
				<div id="showdetails5" class="accordion" align="center">
					<h5>
						<s:i18n name="bpep"><s:text name="name"></s:text></s:i18n>:&nbsp;<%=session.getAttribute("ISVusername")%></h5>
					<br />
					<h5><s:i18n name="bpep"><s:text name="role"></s:text></s:i18n>&nbsp;Independent Software Vender</h5>
					<br />
					<form name="formUpdate" method="post"
						action="userManage/ISVupdateresult.jsp"
						onsubmit="return resultcheck();">
						<table class="userchange" align="center">
							<tr>
								<td><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n>:</td>
								<td><input type="text" name="username"
									value="<%=session.getAttribute("ISVusername")%>"
									class="text username" maxlength="60" /></td>
							</tr>
							<tr>
								<td><s:i18n name="bpep"><s:text name="old_pwd"></s:text></s:i18n>:</td>
								<td><input type="password" name="userpassword"
									class="text password" maxlength="60" /></td>
							</tr>
							<tr>
								<td><s:i18n name="bpep"><s:text name="new_pwd"></s:text></s:i18n>:</td>
								<td><input type="password" name="password1"
									class="text password" maxlength="60" /></td>
							</tr>
							<tr>
								<td><s:i18n name="bpep"><s:text name="mail"></s:text></s:i18n>:</td>
								<td><input type="text" id="me" name="useremail"
									value="<%=email%>" class="text useremail" maxlength="60" /></td>
							</tr>
							<tr>
								<td><s:i18n name="bpep"><s:text name="address"></s:text></s:i18n>:</td>
								<td><input type="text" name="useraddress"
									value="<%=address%>" class="text useraddress" maxlength="500" /></td>
							</tr>
							<tr>
								<td><s:i18n name="bpep"><s:text name="verify_code"></s:text></s:i18n>:</td>
								<td><input type="text" name="check" id="check"
									class="text check" maxlength="4" /></td>
							</tr>
							<tr>
								<td><img src="imgcode" id="picture" /></td>
								<td><a
									style="display: block; line-height: 23px; margin-top: 10px; font-size: 13px; font: 宋体; text-align: center;"
									href="javascript:void(0);" onclick="change()"><s:i18n name="bpep"><s:text name="tips"></s:text></s:i18n></a></td>
							</tr>
							<tr>
								<td><input type="submit" name="submit"
									value="&nbsp;修&nbsp;改&nbsp;" class="submit" /></td>
								<td><input type="reset" name="reset"
									value="&nbsp;取&nbsp;消&nbsp;" class="reset" /></td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
		<%
			} catch (Exception e) {
					out.print("<script>alert('数据库连接失败0!');window.location.href('/BPEP/login/login1.jsp')</script>");
				}
			}
		%>
		<div id="content3" class="content">
			<input type="button" class="inputbutton"
				onclick="javascript:window.location.reload()" value="刷&nbsp;&nbsp;新"></input>
			<br /> <br />
			<div id="accordion5" class="accordion">
				<h3><s:i18n name="bpep"><s:text name="plugin_extention_design"></s:text></s:i18n></h3>
				<div id="showdetails7" class="accordion">
					<form name="newplugin" method="post" action="/BPEP/createPlugin"
						target="_blank" onsubmit="return newPluginname();">
						<table class="templatecreate">
							<tr>
								<td><s:i18n name="bpep"><s:text name="plugin_name"></s:text></s:i18n></td>
								<td><input type="text" name="templatename" maxlength="50"
									class="templatename" /></td>
							</tr>
							<tr>
								<td><s:i18n name="bpep"><s:text name="domain"></s:text></s:i18n></td>
								<td><select name="templatedomain" class="templateselect"
									id="actionSelect" >
										<option value="logistics" selected="selected"><s:i18n name="bpep"><s:text name="logistics"></s:text></s:i18n></option>
										<option value="traffic"><s:i18n name="bpep"><s:text name="traffic"></s:text></s:i18n></option>
										<option value="weather"><s:i18n name="bpep"><s:text name="weather"></s:text></s:i18n></option>
										<option value="hotel"><s:i18n name="bpep"><s:text name="hotel"></s:text></s:i18n></option>
										<option value="dailylife"><s:i18n name="bpep"><s:text name="dailylife"></s:text></s:i18n></option>
								</select></td>
							</tr>
							<tr>
								<td>插件类型:</td>
								<td><select name="templatetype" class="templateselect"onchange="SelectProcessType($(this).val());"
									id="typeSelect" >
										<option value="OWL-S" selected="selected">OWL-S</option>
										<option value="EPC" >EPC</option>
										<option value="BPEL">BPEL</option>
								</select></td>
							</tr>
							<%
							try{
								DA.connDB("bpep");
								ResultSet res1,res2;
								String sqlpro1 = "select name,user from original_process where process_type='owls'";
								String sqlpro2 = "select name,user from original_process where process_type='epc'";
								res1 = DA.executeSelectSql(sqlpro1);
								res2 = DA.executeSelectSql(sqlpro2);
								String processname1 = "null";
								String processuser1 = "null";
								String processname2 = "null";
								String processuser2 = "null";
							%>
							<tr>
								<td><s:i18n name="bpep"><s:text name="process_select"></s:text></s:i18n>:</td>
								<td><select class="processselect"onchange="SelectProcess();"id="processSelect"
									name="processname">
									<option value="" processuser="">--请选择--</option>
										<%
											while (res1.next()) {
												processname1 = (String) res1.getString(1);
												processuser1=(String)res1.getString(2);
										%>
										<option value="<%=processname1%>"processuser="<%=processuser1 %>"><%=processname1%></option>
										<%
											}
										%>
								</select>
								<select class="processselect"onchange="SelectProcess2();"id="processSelect2"
									name="processname2"style="display:none;">
									<option value=""  processuser="">--请选择--</option>
										<%
											while (res2.next()) {
												processname2 = (String) res2.getString(1);
												processuser2=(String)res2.getString(2);
										%>
										<option value="<%=processname2%>"processuser="<%=processuser2 %>"><%=processname2%></option>
										<%
											}
										%>
								</select>
								</td>
							</tr>
							<tr>
								<td><s:i18n name="bpep"><s:text name="process_creater"></s:text></s:i18n>:</td>
								<td><select class="processselect"id="userSelect"
									name="processuser">
									<option value=""selected="selected">--请选择--</option>
									</select></td>
							</tr>
							<%
							DA.closeDB();
							}catch(Exception e){
								out.print("<script>alert('数据库连接失败1!');window.location.href('/BPEP/login/login1.jsp')</script>");
							}
							%>
							<tr>
								<td><input type="submit" value="&nbsp;设&nbsp;计&nbsp;"
									name="submit" class="submit" /></td>
								<td><input type="reset" value="&nbsp;取&nbsp;消&nbsp;"
									name="reset" class="reset" /></td>
							</tr>
						</table>
					</form>
				</div>
				<h3><s:i18n name="bpep"><s:text name="process_view"></s:text></s:i18n></h3>
				<div id="showdetails8" class="accordion">
					<table cellpadding="2" border="2" width="100%"
						class="processbrowsers">
						<tr>
							<th align="center" width="100%" height="30px" colspan="4"><s:i18n name="bpep"><s:text name="allprocess"></s:text></s:i18n></th>
						</tr>
						<tr>
							<td align="center" width="35%" height="30px"><s:i18n name="bpep"><s:text name="process_name"></s:text></s:i18n></td>
							<td align="center" width="30%" height="30px"><s:i18n name="bpep"><s:text name="process_creater"></s:text></s:i18n></td>
							<td align="center" width="15%" height="30px"><s:i18n name="bpep"><s:text name="domain"></s:text></s:i18n></td>
							<td align="center" width="20%" height="30px"><s:i18n name="bpep"><s:text name="last_modify_time"></s:text></s:i18n></td>
						</tr>
						<%
							try {
								String processfilePath = BasicPathVariable.processPath;
								File dir = new File(processfilePath);
								if (dir.isDirectory()) {
									File[] files1 = dir.listFiles();
									for (int i = 0; i < files1.length; i++) {
										if (files1[i].isDirectory()) {
											String fileuser = (String) files1[i].getName();
											File[] files2 = files1[i].listFiles();
											for (int j = 0; j < files2.length; j++) {
												String filename = (String) files2[j].getName();
												long changetime = (long) files2[j]
														.lastModified();
												Date pp = new Date(changetime);
												String thelasttime = (pp.getYear() + 1900)
														+ "-" + (pp.getMonth() + 1) + "-"
														+ (pp.getDay() + 6);
						%>
						<%
							DA.connDB("bpep");
												ResultSet res;
												String sqlpro = "select * from processinfo where name='"
														+ filename
														+ "' AND user='"
														+ fileuser
														+ "'";
												res = DA.executeSelectSql(sqlpro);
												String processdomain = "null";
												if (res.next()) {
													processdomain = (String) res.getString(3);
												}
						%>
						<tr>
							<td align="center" width="35%" height="30px"><a
								href="/BPEP/processManage/index_forISV.jsp?process=<%=filename%>"
								class="processdetails" target="_blank"
							
							><%=filename%></a></td>
							<td align="center" width="30%" height="30px"><%=fileuser%></td>
							<td align="center" width="15%" height="30px"><%=processdomain%></td>
							<td align="center" width="20%" height="30px"><%=thelasttime%></td>
						</tr>
						<%
							}
										}
									}
								}
								DA.closeDB();
							} catch (Exception e) {
								out.print("<script>alert('数据库连接失败2!');window.location.href('/BPEP/login/login1.jsp')</script>");
							}
							
						%>
					</table>
				</div>
				<h3><s:i18n name="bpep"><s:text name="plugin_view"></s:text></s:i18n></h3>
				<div id="showdetails9" class="accordion">
					<table cellpadding="2" border="2" width="100%"
						class="templatebrowsers">
						<tr>
							<th align="center" width="100%" height="30px" colspan="6"><s:i18n name="bpep"><s:text name="myplugin"></s:text></s:i18n></th>
						</tr>
						<tr>
							<td align="center" width="20%" height="30px"><s:i18n name="bpep"><s:text name="plugin_name"></s:text></s:i18n></td>
							<td align="center" width="18%" height="30px"><s:i18n name="bpep"><s:text name="plugin_creater"></s:text></s:i18n></td>
							<td align="center" width="12%" height="30px"><s:i18n name="bpep"><s:text name="domain"></s:text></s:i18n></td>
							<td align="center" width="20%" height="30px"><s:i18n name="bpep"><s:text name="base_process"></s:text></s:i18n></td>
							<td align="center" width="18%" height="30px"><s:i18n name="bpep"><s:text name="process_creater"></s:text></s:i18n></td>
							<td align="center" width="12%" height="30px"><s:i18n name="bpep"><s:text name="option"></s:text></s:i18n></td>
						</tr>
						<%
							try {
								DA.connDB("bpep");
								ResultSet plures;
								String sql_plu = "SELECT name,user,domain,processname,processuser FROM plugininfo where user='"+username+"'";
								plures = DA.executeSelectSql(sql_plu);
								while (plures.next()) {
						%>
						<tr>
							<td align="center" width="20%" height="30px"class="plugindetails"><%=plures.getString("name")%></td>
							<td align="center" width="18%" height="30px"><%=plures.getString("user")%></td>
							<td align="center" width="12%" height="30px"><%=plures.getString("domain")%></td>
							<td align="center" width="20%" height="30px"><%=plures.getString("processname")%></td>
							<td align="center" width="18%" height="30px"><%=plures.getString("processuser")%></td>
							<td align="center" width="12%" height="30px"class="plugindelete"><s:i18n name="bpep"><s:text name="delete"></s:text></s:i18n></td>
						</tr>
						<%
							}
								DA.closeDB();
							} catch (Exception e) {
								out.print("<script>alert('数据库连接失败3!');window.location.href('/BPEP/login/login1.jsp')</script>");
							}
						%>
					</table>
				</div>
			</div>
		</div>
		<div id="content4" class="content">
			<input type="button" class="inputbutton"
				onclick="javascript:window.location.reload()" value="刷&nbsp;&nbsp;新"></input>
			<br /> <br />
			<div id="accordion" class="accordion">
				<h3><s:i18n name="bpep"><s:text name="theme_ontology_query"></s:text></s:i18n></h3>
				<div id="showdetails1" class="accordion">
					<br /> <br />
					<form name="ontologysearch" method="post" action=""
						onsubmit="return wrongkeyOnto();">
						<table class="ontologymanage">
							<tr>
								<td><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n>:</td>
								<td><input type="text" name="ontologyname"
									class="ontologyname" maxlength="50" /></td>
							</tr>
							<tr>
								<td><s:i18n name="bpep"><s:text name="domain"></s:text></s:i18n>:</td>
								<td><select name="ontologydomain" class="ontologyselect"
									id="ontologydomain">
										<option value="traffic" selected="selected"><s:i18n name="bpep"><s:text name="traffic"></s:text></s:i18n></option>
										<option value="logistics"><s:i18n name="bpep"><s:text name="logistics"></s:text></s:i18n></option>
										<option value="weather"><s:i18n name="bpep"><s:text name="weather"></s:text></s:i18n></option>
										<option value="hotel"><s:i18n name="bpep"><s:text name="hotel"></s:text></s:i18n></option>
										<option value="dailylife"><s:i18n name="bpep"><s:text name="dailylife"></s:text></s:i18n></option>
								</select></td>
							</tr>
							<tr>
								<td><input type="submit" name="submit"
									value="&nbsp;查&nbsp;询&nbsp;" class="submit" /></td>
								<td><input type="reset" name="reset"
									value="&nbsp;取&nbsp;消&nbsp;" class="reset" /></td>
							</tr>
						</table>
					</form>
				</div>
				<h3><s:i18n name="bpep"><s:text name="theme_ontology_view"></s:text></s:i18n></h3>
				<div id="showdetails2" class="accordion">
					<table align="center" cellpadding="2" border="2" width="100%"
						class="ontologybrowsers">
						<tr>
							<th align="center" width="100%" colspan="5" height="30px"><s:i18n name="bpep"><s:text name="theme_ontology"></s:text></s:i18n></th>
						</tr>
						<tr>
							<td align="center" width="20%" height="30px"><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n></td>
							<td align="center" width="30%" height="30px">URI</td>
							<td align="center" width="18%" height="30px"><s:i18n name="bpep"><s:text name="domain"></s:text></s:i18n></td>
							<td align="center" width="20%" height="30px"><s:i18n name="bpep"><s:text name="creater"></s:text></s:i18n></td>
							<td align="center" width="12%" height="30px"><s:i18n name="bpep"><s:text name="view"></s:text></s:i18n></td>
						</tr>
						<%
							try {
								Connection conn = Access.getConnection();
								OntologyDAO odao = new OntologyDAO(conn);
								List<OntologyDTO> dtos = odao.queryAll();
								OntologyDTO dto = null;
								Iterator<OntologyDTO> iterator = dtos.iterator();
								while (iterator.hasNext()) {
									dto = iterator.next();
						%>
						<tr>
							<td align="center" width="20%" height="30px"><%=dto.getOntologyName()%></td>
							<td align="center" width="30%" height="30px"><%=dto.getUri()%></td>
							<td align="center" width="18%" height="30px"><%=dto.getDomain()%></td>
							<td align="center" width="20%" height="30px"><%=dto.getUser()%></td>
							<td align="center" width="12%" height="30px"><a
								href="/BPEP/showOntology?id=<%=dto.getId()%>"
								class="ontodetails" target="_blank"><s:i18n name="bpep"><s:text name="view"></s:text></s:i18n></a></td>
						</tr>
						<%
							}
								odao.closeDBConnection();
							} catch (Exception e) {
								out.print("<script>alert('数据库连接失败4!');window.location.href('/BPEP/login/login1.jsp')</script>");
							}
						%>
					</table>
				</div>
			</div>
		</div>
		<div id="content5" class="content" align="center">
			<input type="button" class="inputbutton"
				onclick="javascript:window.location.reload()" value="刷&nbsp;&nbsp;新"></input>
			<br /> <br />
			<div id="accordion2" class="accordion">
				<h3><s:i18n name="bpep"><s:text name="service_query"></s:text></s:i18n></h3>
				<div id="showdetails3" class="accordion">
					<form name="wssearch" method="post" action=""
						onsubmit="return wrongkeyWS();">
						<table class="wsmanage">
							<tr>
								<td><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n>:</td>
								<td><input type="text" name="wsname" class="wsname"
									maxlength="50" /></td>
							</tr>
							<tr>
								<td><s:i18n name="bpep"><s:text name="input"></s:text></s:i18n>:</td>
								<td><input type="text" name="wsinputinfo"
									class="wsinputinfo" /></td>
							</tr>
							<tr>
								<td><s:i18n name="bpep"><s:text name="output"></s:text></s:i18n>:</td>
								<td><input type="text" name="wsoutputinfo"
									class="wsoutputinfo" /></td>
							</tr>
							<tr>
								<td><s:i18n name="bpep"><s:text name="domain"></s:text></s:i18n>:</td>
								<td><select name="wsdomain" class="wsselect" id="wsdomain">
										<option value="traffic" selected="selected"><s:i18n name="bpep"><s:text name="traffic"></s:text></s:i18n></option>
										<option value="logistics"><s:i18n name="bpep"><s:text name="logistics"></s:text></s:i18n></option>
										<option value="weather"><s:i18n name="bpep"><s:text name="weather"></s:text></s:i18n></option>
										<option value="hotel"><s:i18n name="bpep"><s:text name="hotel"></s:text></s:i18n></option>
										<option value="dailylife"><s:i18n name="bpep"><s:text name="dailylife"></s:text></s:i18n></option>
								</select></td>
							</tr>
							<tr>
								<td><s:i18n name="bpep"><s:text name="function"></s:text></s:i18n>:</td>
								<td><input type="text" name="wsfunctioninfo"
									class="wsfunctioninfo" /></td>
							</tr>
							<tr>
								<td><input type="submit" name="submit"
									value="&nbsp;查&nbsp;询&nbsp;" class="submit" /></td>
								<td><input type="reset" name="reset"
									value="&nbsp;取&nbsp;消&nbsp;" class="reset" /></td>
							</tr>
						</table>
					</form>
				</div>
				<h3><s:i18n name="bpep"><s:text name="service_view"></s:text></s:i18n></h3>
				<div id="showdetails4" class="accordion">
					<table align="center" cellpadding="2" border="2" width="100%"
						class="wsbrowsers">
						<tr>
							<th align="center" width="100%" colspan="5" height="30px"><s:i18n name="bpep"><s:text name="allservice"></s:text></s:i18n></th>
						</tr>
						<tr>
							<td align="center" width="16%" height="30px"><s:i18n name="bpep"><s:text name="service_id"></s:text></s:i18n></td>
							<td align="center" width="40%" height="30px"><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n></td>
							<td align="center" width="16%" height="30px"><s:i18n name="bpep"><s:text name="domain"></s:text></s:i18n></td>
							<td align="center" width="18%" height="30px"><s:i18n name="bpep"><s:text name="creater"></s:text></s:i18n></td>
							<td align="center" width="10%" height="30px"><s:i18n name="bpep"><s:text name="view"></s:text></s:i18n></td>
						</tr>
						<%
							try {
								DA.connDB("bpep");
								ResultSet res;
								String sql_que = "SELECT id,name,domain,user FROM serviceinfo";
								res = DA.executeSelectSql(sql_que);
								while (res.next()) {
						%>
						<tr>
							<td align="center" width="16%" height="30px"><%=res.getString("id")%></td>
							<td align="center" width="40%" height="30px"><%=res.getString("name")%></td>
							<td align="center" width="16%" height="30px"><%=res.getString("domain")%></td>
							<td align="center" width="18%" height="30px"><%=res.getString("user")%></td>
							<td align="center" width="10%" height="30px"><a
								href="/BPEP/wsManage/wsDetail.jsp?id=<%=res.getString("id")%>"
								target="_blank" class="wsdetails"><s:i18n name="bpep"><s:text name="view"></s:text></s:i18n></a></td>
						</tr>
						<%
							}
								DA.closeDB();
							} catch (Exception e) {
								out.print("<script>alert('数据库连接失败5!');window.location.href('/BPEP/login/login1.jsp')</script>");
							}
						%>
					</table>
				</div>
			</div>
		</div>
		<div id="content6" class="content">
			<input type="button" class="inputbutton"
				onclick="javascript:window.location.reload()" value="刷&nbsp;&nbsp;新"></input>
			<br /> <br />
		</div>
	</div>

	<jsp:include page = "/style/footer.jsp"/>
	

</body>
</html>
