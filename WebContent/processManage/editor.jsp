<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="databaseaccess.Access"%>
<%@ page import="java.io.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="org.json.*"%>
<%@ page import="finalvariable.*"%>
<%
	String action=request.getParameter("action");
	if("proc-edit".equals(action))
	{
	
	}
	else if("wsdl-list".equals(action))
	{			
		try {
			Access DA = new Access();
			DA.connDB("webservice");
			String sql = "select wsdllocation from serviceinfo";
			ResultSet res = DA.executeSelectSql(sql);
			JSONArray arr = new JSONArray();
			while (res.next()) {
				arr.put(res.getString(1));
			}
			out.print(arr);
			DA.closeDB();
		} catch (Exception e) {
			System.out.println(e);
		}
		return ;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程组合</title>
<!-- 导入所需JS库 -->
<link rel="stylesheet" type="text/css" href="lib/jquery.ui/css/normalise.css">
<link rel="stylesheet" type="text/css" href="lib/jquery.ui/css/jquery-ui.css">
<script type="text/javascript" src="lib/jquery/jquery-1.6.4.js"></script>
<script type="text/javascript" src="lib/jquery.ui/modernizr-2.0.6.min.js"></script>
<script type="text/javascript" src="lib/jquery.ui/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="lib/jquery.ui/jquery.imgpreload.min.js"></script>
<script type="text/javascript" src="lib/jquery.ui/jquery-ui.toggleSwitch.js"></script>
<script type="text/javascript" src="lib/json.js"></script>
<script type="text/javascript" src="lib/jquery.jstree/jquery.cookie.js"></script>
<script type="text/javascript" src="lib/jquery.jstree/jquery.hotkeys.js"></script>
<script type="text/javascript" src="lib/jquery.jstree/jquery.jstree.js"></script>
<script type="text/javascript">
	$mode = "<%=action%>"
    mxBasePath = "lib/mxGraph"
</script>
<script type="text/javascript" src="lib/mxGraph/js/mxClient.js"></script>
<!-- 导入CSS代码 -->
<link rel="stylesheet" type="text/css" href="css/wsdl-dialog.css">
<link rel="stylesheet" type="text/css" href="css/perform-dialog.css">
<link rel="stylesheet" type="text/css" href="css/property-dialog.css">
<link rel="stylesheet" type="text/css" href="css/param-dialog.css">
<link rel="stylesheet" type="text/css" href="css/select-ontology-dialog.css">
<link rel="stylesheet" type="text/css" href="css/common.css">
<link rel="stylesheet" type="text/css" href="css/editor.css">
<link rel="stylesheet" type="text/css" href="css/QNameView.css">
<link rel="stylesheet" type="text/css" href="css/WSDLOperationView.css">
<link rel="stylesheet" type="text/css" href="css/process-dialog.css">
<link rel="stylesheet" type="text/css" href="css/condition-dialog.css">
<link rel="stylesheet" type="text/css" href="css/produce-dialog.css">
<link rel="stylesheet" type="text/css" href="css/select-wsdl-dialog.css">
<link rel="stylesheet" type="text/css"
	href="css/WSDLOperationEditor.css">
<link rel="stylesheet" type="text/css"
	href="css/SelectOntClassDialog.css">
<link rel="stylesheet" type="text/css"
	href="css/select-process-dialog.css">
<link rel="stylesheet" type="text/css"
	href="css/select-param-dialog.css">
<link rel="stylesheet" type="text/css"
	href="css/select-binding-dialog.css">
<!-- 导入JS代码 -->
<script type="text/javascript" src="js/update.js"></script>
<script type="text/javascript" src="js/editor.js"></script>
<script type="text/javascript" src="js/perform-dialog.js"></script>
<script type="text/javascript" src="js/wsdl-dialog.js"></script>
<script type="text/javascript" src="js/property-dialog.js"></script>
<script type="text/javascript" src="common-js.jsp"></script>
<script type="text/javascript" src="js/owl/OWLModel.js"></script>
<script type="text/javascript" src="js/mode.js"></script>
<script type="text/javascript" src="js/process-dialog.js"></script>
<script type="text/javascript" src="js/condition-dialog.js"></script>
<script type="text/javascript" src="js/produce-dialog.js"></script>
<script type="text/javascript" src="js/select-wsdl-dialog.js"></script>
<script type="text/javascript" src="js/select-ontology-dialog.js"></script>
<script type="text/javascript" src="js/param-dialog.js"></script>
<!--- 对jquery UI dialog 样式的更改，暂时写在这里 -->
<style type="text/css">
div.ui-corner-all{
	border-radius:0px;
}

div.ui-dialog{
	border:6px solid RGB(21, 127, 204);
	padding:0px;
}

div.ui-dialog .ui-dialog-titlebar{
	background-color:RGB(21, 127, 204) !important;
	margin:0px;
	padding:4px 0px 10px 0px;
}

div.ui-dialog .ui-dialog-titlebar .ui-dialog-title{
	color:#FFFFFF;
	font-size:14px !important;
	font-weight:bold;
	line-height:16px;
	margin:0px;
	padding:0px;
	text-indent:12px;
	text-shadow:0px 0px 0px #000000;
}

div.ui-dialog .ui-dialog-titlebar-close{
	background-image:url("img/close.png");
	cursor:default;
	top:3px;
	right:-5px;

}

div.ui-dialog .ui-dialog-titlebar-close:hover{
	background-image:url("img/close-hover.png");
}

div.ui-dialog .ui-dialog-titlebar-close:active{
	background-image:url("img/close-active.png");
}

button.ui-button, div.ui-button{
	background:RGB(21, 127, 204) !important;
	border:none !important;
	border-radius:0px !important;
	box-shadow:0px 0px 0px #000000 !important;
	margin:0px 18px !important;
	padding:4px 18px !important;
}

button.ui-button span{
	text-shadow:0px 0px 0px #000000 !important;
}

</style>
</head>
<body>
	<div id='editor'>
		<div class='toolbar'>
			<div class='button save'>保存</div>
			<div class="button wsdl">查看已导入的Web服务</div>
			<div class="button ontology">查看所有本体</div>
			<div class='button property'>组合流程属性</div>
			<div class="button extension">插件开发</div>
			<div class="button apply">插件应用</div>
		</div>
		<div class='left'>
			<div class='head'>
				<div class="title">树形视图</div>
				<div class='perform item' title='Create Perform'></div>
				<div class='sequence item' title='Create Sequence'></div>
				<div class='split-join item' title='Create Split+Join'></div>
				<div class='any-order item' title='Create AnyOrder'></div>
				<div class='choice item' title='Create Choice'></div>
				<div class='if-then-else item' title='Create If-Then-Else'></div>
				<div class='repeat-until item' title='Create Repeat-While'></div>
				<div class='repeat-while item' title='Create Repeat-Until'></div>
				<div class='produce item' title='Create Produce'></div>
				<div class='delete item' title='Delete selected Node'></div>
			</div>
			<div class='body'>
				<div class='tree'></div>
			</div>
		</div>
		<div class='center'>
			<div class='head'>
				<div class="title">组合流程</div>
				<div class="zoom-actual" title="原始大小"></div>
				<div class="zoom-out" title="缩小"></div>
				<div class="zoom-in" title="放大"></div>
			</div>
			<div class='body'></div>
		</div>
		<div class='right'>
			<div class='head'>
				<div class="title">原子流程</div>
				<div class="remove" title="删除"></div>
				<div class="add" title="添加"></div>
			</div>
			<div class='body'></div>
		</div>
		<div class="statusbar"></div>
	</div>
	<div id="dlg-wsdl-list">正在获取WSDL列表……</div>
	<div id="dlg-wsdl-info">
		<div class="container">
			<div class="url">
				<div class="label">WSDL:</div>
				<div class="value">null</div>
				<div class="button">选择</div>
			</div>
			<div class="operation"></div>
			<div class="detail"></div>
		</div>
	</div>
	<div id="dlg-ato-proc">
		<div class="name">
			<div class="label">名称：</div>
			<div class="value">Test</div>
			<div class="button">更改</div>
		</div>
		<div class="input">
			<div class="label">输入：</div>
			<table>
				<tr>
					<th class="name">名称</th>
					<th class="type">类型</th>
					<th class="rename"></th>
					<th class="anno"></th>
				</tr>
			</table>
		</div>
		<div class="output">
			<div class="label">输出：</div>
			<table>
				<tr>
					<th class="name">名称</th>
					<th class="type">类型</th>
					<th class="rename"></th>
					<th class="anno"></th>
				</tr>
			</table>
		</div>
	</div>
	<div id="dlg-ont-list">正在获取可用的本体列表……</div>
	<div id="dlg-ont-class"></div>
		<div id="perform-dialog">
		<div class="name">
			<div class="label">Name:</div>
			<div class="value">123</div>
			<div class="button">更改</div>
		</div>
		<div class="process">
			<div class="label">Process:</div>
			<div class="value">123</div>
			<div class="button">选择</div>
		</div>
		<div class="binding">
			<div class="label">InputBinding:</div>
			<div class="container"></div>
		</div>
	</div>
	<div id="condition-dialog">
		<div class="name">
			<div class="label">名称：</div>
			<div class="value">Condition</div>
			<div class="operation">更改</div>
		</div>
		<div class="expression">
			<div class="label">条件：</div>
			<textarea></textarea>
		</div>
	</div>
	<jsp:include page="load-process.jsp"></jsp:include>
</body>
</html>