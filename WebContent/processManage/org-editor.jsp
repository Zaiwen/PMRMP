<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="DA" class="databaseaccess.Access" scope="page" />
<%@page import="java.io.*,java.sql.*,finalvariable.*"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%
	// 检测用户是否已经登录
	//if (session.getAttribute("ISVusername") == null) {
	//	out.println("会话超时，请重新登录！");
	//	return;
	//}
	// 这个页面可以根据请求参数funct的不同有不同的功能 
	String process, provider;
	String name = request.getParameter("name");
	
	System.out.println(name);
	process = request.getParameter("process");
	
	System.out.println(process);
	provider = request.getParameter("provider");
	System.out.println(provider);
	String local;
	if(session.getAttribute("WW_TRANS_I18N_LOCALE") == null) local  = "zh_CN";
	else  local = session.getAttribute("WW_TRANS_I18N_LOCALE").toString();
	
	if (name == null) {
		out.println("请先填写要开发的插件的名称！");
		return;
	} else if (process == null) {
		out.println("未知的基础流程！");
		return;
	} else if (provider == null) {
		out.println("未知基础流程的提供者！");
		return;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="overflow:hidden">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>插件应用</title>
		<link rel="stylesheet" type="text/css" href="css/editor.css">
		<link rel="stylesheet" type="text/css" href="css/dialog.css">
		<link rel="stylesheet" type="text/css" href="css/extension-dialog.css">
		<link rel="stylesheet" type="text/css" href="css/QNameView.css">
		<link rel="stylesheet" type="text/css"
			href="css/WSDLOperationView.css">
		<link rel="stylesheet" type="text/css"
			href="lib/jquery.ui/css/normalise.css">
		<link rel="stylesheet" type="text/css"
			href="lib/jquery.ui/css/jquery-ui.css">
		<script type="text/javascript" src="lib/jquery/jquery-1.6.4.js"></script>
		<script type="text/javascript"
			src="lib/jquery.ui/modernizr-2.0.6.min.js"></script>
		<script type="text/javascript"
			src="lib/jquery.ui/jquery-ui-1.8.16.custom.min.js"></script>
		<script type="text/javascript"
			src="lib/jquery.ui/jquery.imgpreload.min.js"></script>
		<script type="text/javascript"
			src="lib/jquery.ui/jquery-ui.toggleSwitch.js"></script>
		<script type="text/javascript"
			src="lib/jquery.jstree/jquery.cookie.js"></script>
		<script type="text/javascript"
			src="lib/jquery.jstree/jquery.hotkeys.js"></script>
		<script type="text/javascript"
			src="lib/jquery.jstree/jquery.jstree.js"></script>
		<script type="text/javascript">
			mxBasePath = "lib/mxGraph";
		</script>
		<script type = "text/javascript" >
			var language = <%="'"+ local +"'"%>;
		</script>
		<script	type = "text/javascript" src = "js/editor.js"/></script>
		<script	type = "text/javascript" src = "js/update.js"/></script>
		<script type=	"text/javascript" src="lib/mxGraph/js/mxClient.js"></script>
		<script type=	"text/javascript" src="lib/json.js"></script>
		<script type=	"text/javascript" src="common-js.jsp"></script>
		<script type=	"text/javascript" src="js/perform-dialog.js"></script>
		<script type=	"text/javascript" src="js/wsdl-dialog.js"></script>
		<script type=	"text/javascript" src="js/property-dialog.js"></script>
		  <script type=	"text/javascript" src="js/owl/OWLModel.js"></script>				
		<script type=	"text/javascript" src="js/construct/IfThenElse.js"></script>
	
		

		
	</head>
	<body>
		<div id='editor'>
			<div class='toolbar'>
				<!-- div class='button save'>保存</div>-->
			<!--<div class="button wsdl">查看所有Web服务</div>-->
			
			<!--<div class="button example">example</div>-->
			<!--<div class="button ontology">查看所有本体</div>-->
			<div class='button property'><s:i18n name="bpep"><s:text name="config_process_property"></s:text></s:i18n></div>
			<div class="button apply"><s:i18n name="bpep"><s:text name="plugin_application"></s:text></s:i18n></div>
			<div class='button property'> 
			 <a style= "color:white" href="I18NAction.action?local=zh_CN&url=org_editor&name=<%= name %>&process=<%=process %>&provider=<%= provider %>">中文</a> 
			 <a style= "color:white" href="I18NAction.action?local=en_US&url=org_editor&name=<%= name %>&process=<%=process %>&provider=<%= provider %>">English</a> 
			</div>
			</div>
			<div class='left'>
				<div class='head'>
					<div class="title">
						<s:i18n name="bpep"><s:text name="control_structure"></s:text></s:i18n>
					</div>
				</div>
				<div class='body'>
					<div class='tree'></div>
				</div>
			</div>
			<div class='center'>
				<div class='head'>
					<div class="title">
						<s:i18n name="bpep"><s:text name="process_graph"></s:text></s:i18n>
					</div>
					<div class="zoom-actual" title="原始大小"></div>
					<div class="zoom-out" title="缩小"></div>
					<div class="zoom-in" title="放大"></div>
				</div>
				<div class='body'></div>
			</div>
			<div class='right'>
				<div class='head'>
					<div class="title">
						<s:i18n name="bpep"><s:text name="atomic_process"></s:text></s:i18n>
					</div>
				</div>
				<div class='body'></div>
			</div>
			<div class="statusbar">
				<div class="extension">
					<s:i18n name="bpep"><s:text name="plugin_on_develop"></s:text></s:i18n>
					<span><s:i18n name="bpep"><s:text name="click_to_go"></s:text></s:i18n></span>
				</div>
			</div>
		</div>
		<jsp:include page="load-process.jsp"></jsp:include>
		
		<div id="extension-apply-dialog" title='<s:i18n name="bpep"><s:text name="plugin_application"></s:text></s:i18n>'>
			<table>
				<tr>
					<th></th>
					<th>
						<s:i18n name="bpep"><s:text name="name"></s:text></s:i18n>
					</th>
					<th>
						<s:i18n name="bpep"><s:text name="creater"></s:text></s:i18n>
					</th>
				</tr>
				<%
					try {
					DA.connDB("bpep");
						//System.out.println("hahahahah");
						String pluginname = null;
						String pluginuser = null;
						String plugindomain = null;
						String sql = "SELECT name,user,domain FROM plugininfo";// where processname='"
//								+ process + "'and processuser='" + provider + "'";   						

						ResultSet res = DA.executeSelectSql(sql);
						while (res.next()) {
							pluginname = res.getString("name");
							System.out.println(pluginname);
							pluginuser = res.getString("user");
							System.out.println(pluginuser);
							plugindomain = res.getString("domain");
							System.out.println(plugindomain);
							
				%>
				<tr>
					<td class="check">
						<input  type='checkbox' />
					</td>
					<td class="name"><%=pluginname%></td>
					<td class="user"><%=pluginuser%></td>
				</tr>
				<%
					}
						DA.closeDB();
					} catch (Exception e) {
						out.print("<script>alert('数据库连接失败!');window.location.href('/BPEP/login/login1.jsp')</script>");
					}
				%>
			</table>
		</div>

		<script type="text/javascript" src="js/org.js"></script>

	</body>
</html>