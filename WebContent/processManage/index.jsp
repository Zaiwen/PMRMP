<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.io.*,finalvariable.*"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 

<%
if ( session.getAttribute("PPusername")== null && session.getAttribute("ISVusername")== null && session.getAttribute("ORGusername")== null){

		out.println("会话超时，请重新登录！");
		return;
}
	
String  process = request.getParameter("process");
String local;
if(session.getAttribute("WW_TRANS_I18N_LOCALE") == null) local  = "zh_CN";
else  local = session.getAttribute("WW_TRANS_I18N_LOCALE").toString();



		String processName = request.getParameter("process");
		session.setAttribute("process", processName);
		session.setAttribute("provider", session.getAttribute("PPusername"));
 %>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程组合</title>
<!-- 导入所需JS库 -->
<link rel="stylesheet" type="text/css"
	href="lib/jquery.ui/css/normalise.css">
<link rel="stylesheet" type="text/css"
	href="lib/jquery.ui/css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="lib/jquery.memu/memu-0.1.css">

<script type="text/javascript" src="lib/jquery/jquery-1.6.4.js"></script>
<script type="text/javascript"
	src="lib/jquery.ui/modernizr-2.0.6.min.js"></script>
<script type="text/javascript"
	src="lib/jquery.ui/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript"
	src="lib/jquery.ui/jquery.imgpreload.min.js"></script>
<script type="text/javascript"
	src="lib/jquery.ui/jquery-ui.toggleSwitch.js"></script>
<script type="text/javascript" src="lib/jquery.memu/jquery.memu-0.1.js"></script>
<script type="text/javascript" src="lib/json.js"></script>
<script type="text/javascript" src="js/process-dialog.js"></script>
<script type="text/javascript" src="js/condition-dialog.js"></script>
<script type="text/javascript" src="js/produce-dialog.js"></script>
<script type="text/javascript" src="js/select-wsdl-dialog.js"></script>
<script type="text/javascript" src="js/select-ontology-dialog.js"></script>
<script type="text/javascript" src="js/param-dialog.js"></script>
<script type="text/javascript" src="lib/jquery.jstree/jquery.cookie.js"></script>
<script type="text/javascript" src="lib/jquery.jstree/jquery.hotkeys.js"></script>
<script type="text/javascript" src="lib/jquery.jstree/jquery.jstree.js"></script>
<script type="text/javascript">
    mxBasePath = "lib/mxGraph/src";
    $mode="proc-edit";
    
</script>
<script type="text/javascript" src="lib/mxGraph/src/js/mxClient.js"></script>
<!-- 导入CSS代码 -->
<link rel="stylesheet" type="text/css" href="css/common/atomic-process-detail-window.css"/>
<link rel="stylesheet" type="text/css" href="css/common/atomic-process-graph-window.css"/>
<link rel="stylesheet" type="text/css" href="css/common/atomic-process-list-window.css"/>
<link rel="stylesheet" type="text/css" href="css/common/composite-process-graph-window.css"/>
<link rel="stylesheet" type="text/css" href="css/common/composite-process-tree-window.css"/>
<link rel="stylesheet" type="text/css" href="css/common/container.css"/>
<link rel="stylesheet" type="text/css" href="css/common/window.css"/>
<link rel="stylesheet" type="text/css" href="css/wsdl-dialog.css">
<link rel="stylesheet" type="text/css" href="css/perform-dialog.css">
<link rel="stylesheet" type="text/css" href="css/property-dialog.css">
<link rel="stylesheet" type="text/css" href="css/param-dialog.css">
<link rel="stylesheet" type="text/css"
	href="css/select-ontology-dialog.css">
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
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/string/zh-CN.js"></script>
<script type="text/javascript" src="js/condition/Condition.js"></script>
<script type="text/javascript" src="js/update.js"></script>
<script type="text/javascript" src="js/editor.js"></script>
<script type="text/javascript" src="js/test.js"></script>
<script type="text/javascript" src="js/perform-dialog.js"></script>
<%-- <script type="text/javascript" src="js/wsdl-dialog.js"></script> --%>
<script type="text/javascript" src="js/property-dialog.js"></script>
<script type="text/javascript" src="common-js.jsp"></script>
<script type="text/javascript" src="js/owl/OWLModel.js"></script>
<script type="text/javascript" src="js/construct/IfThenElse.js"></script>
<script type="text/javascript" src="js/mode.js"></script>
<script type="text/javascript" src="js/atomic-process-graph-window.js"></script>
<script type="text/javascript" src="js/atomic-process-detail-window.js"></script>
<script type = "text/javascript" >
	var language = <%="'"+ local +"'"%>;
</script>
<!-- link rel="stylesheet" type="text/css" href="2015.03.19/2015.03.19.css"/>
<script type="text/javascript" src="2015.03.19/2015.03.19.js"></script-->
</head>
<body>
	<div id='editor'>
		<div id="titlebar"><%=request.getParameter("process")%> - BPEP OWL-S Creator</div>
		<div id="menubar">
				<div id="menu-file" class="main-menu">
					<div class="label">文件</div>
					<table class="submenu">
						<tr id="menu-file-import" class="menu-item">
							<td class="left">打开...</td>
							<td class="right">Ctrl+I</td>
						</tr>
						<tr id="menu-file-import" class="menu-item">
							<td class="left save">保存</td>
							<td class="right">Ctrl+I</td>
						</tr>
						<tr id="menu-file-export" class="menu-item">
							<td class="left">重命名...</td>
							<td class="right">Ctrl+E</td>
						</tr>
						<tr id="menu-file-img" class="menu-item">
							<td class="left">导出为图片...</td>
							<td class="right"></td>
						</tr>
					</table>
				</div>
				<div id="menu-edit" class="main-menu">
					<div class="label">编辑</div>
					<table class="submenu">
						<tr id="menu-edit-undo" class="menu-item">
							<td class="left">撤销</td>
							<td class="right">Ctrl+Z</td>
						</tr>
						<tr id="menu-edit-redo" class="menu-item">
							<td class="left">恢复</td>
							<td class="right">Ctrl+Y</td>
						</tr>
						<tr class="line">
							<td></td>
							<td></td>
						</tr>
						<tr id="menu-edit-cut" class="menu-item">
							<td class="left">剪切</td>
							<td class="right">Ctrl+X</td>
						</tr>
						<tr id="menu-edit-copy" class="menu-item">
							<td class="left">复制</td>
							<td class="right">Ctrl+C</td>
						</tr>
						<tr id="menu-edit-paste" class="menu-item">
							<td class="left">粘贴</td>
							<td class="right">Ctrl+V</td>
						</tr>
						<tr id="menu-edit-delete" class="menu-item">
							<td class="left">删除</td>
							<td class="right">Del</td>
						</tr>
						<tr id="menu-edit-selectall" class="menu-item">
							<td class="left">全选</td>
							<td class="right">Ctrl+A</td>
						</tr>
					</table>
				</div>
				<div id="menu-view" class="main-menu">
					<div class="label">视图</div>
					<table class="submenu">
						<tr id="menu-view-zoom-in" class="menu-item">
							<td class="left">放大</td>
							<td class="right">Ctrl+'+'</td>
						</tr>
						<tr id="menu-view-zoom-out" class="menu-item">
							<td class="left">缩小</td>
							<td class="right">Ctrl+'-'</td>
						</tr>
					</table>
				</div>
				<div id="menu-help" class="main-menu">
					<div class="label">帮助</div>
					<table class="submenu">
						<tr class="menu-item">
							<td class="left">关于我们</td>
							<td class="right"></td>
						</tr>
					</table>
				</div>
			</div>
		<script type="text/javascript">
			$(function (){
				//$("body").css("font-family","Microsoft YaHei");
				$("#editor").css("background-color","#eee");
				$(".toolbar").css({
					"top":"34px"
				});//.remove();
				var border = "1px solid #ccc";
				$("#menubar").css({"top":"38px"}).hide();
				$("#titlebar").css({
					"background-color":"rgb(21,127,204)",
					"color":"#fff",
					"font-size":"14px",
					"font-weight":"normal", 
					"line-height":"36px",
					"text-align":"center"
				});
				$("#composite-process-tree-window").css({
					//"border":border,
					"left":"-1px",
					"width":"280px"
				});
				$("#atomic-process-list-window").css({
					//"border":border,
					"left":"auto",
					"right":"-1px",
					"width":"280px"
				});
				$(".toolbar .button").css({
					"background-color":"#999"
				});
				$(".window .head").css({
					//"background-color":"#ddd",
					//"border-bottom":"1px solid #ccc"
					//"height":"23px",
					//"line-height":"23px"
				});
				$(".window .head .title").css({
					"font-weight":"normal",
					"text-indent":"12px",
					//"color":"#555"
					//"height":"23px",
					//"line-height":"23px"
				});
			});
		</script>
		<div class='toolbar'>
			<div class='button save'><s:i18n name="bpep"><s:text name="save"></s:text></s:i18n></div>
			<!-- div class="button wsdl"><s:i18n name="bpep"><s:text name="service_view"></s:text></s:i18n></div-->
			
			<div class='button property'><s:i18n name="bpep"><s:text name="process_config"></s:text></s:i18n></div>
			<div class='button'> 
			 <a style= "color:white" href="I18NAction.action?local=zh_CN&url=index2&process=<%= process %>">中文</a> 
			 <a style= "color:white" href="I18NAction.action?local=en_US&url=index2&process=<%= process%>">English</a> 
			</div>
			<!--  div class="button change-view" onclick="javascript:change_view()">切换视图</div>
			<div class="button add-precondition">添加Precondition</div>
			<div class="button add-postcondition">添加Effect</div-->
			<div class="button print" onclick="javascript:imagePrint()">打印</div>
			
		</div>

			<div id="composite-process-tree-window" class='window left'>
				<div class='head'>
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
			<div id="composite-process-graph-window" class='window center'>
				<div class='head'>
					<div class="title"><s:i18n name="bpep"><s:text name="combine_process"></s:text></s:i18n></div>
					<div class="zoom-actual" title="原始大小"></div>
					<div class="zoom-out" title="缩小"></div>
					<div class="zoom-in" title="放大"></div>
				</div>
				<div class='body'></div>
			</div>
			<div id="atomic-process-list-window" class='window right'>
				<div class='head'>
					<div class="title"><s:i18n name="bpep"><s:text name="atomic_process"></s:text></s:i18n></div>
					<div class="remove" title="<s:i18n name="bpep"><s:text name="cancel"></s:text></s:i18n>"></div>
					<div class="add" title="<s:i18n name="bpep"><s:text name="add"></s:text></s:i18n>"></div>
				</div>
				<div class='body'></div>
			</div>
			<div id="atomic-process-graph-window" class="window">
				<div class="head">
					<div class="title">图形表示</div>
				</div>
				<div class="body"></div>
			</div>
			<div id="atomic-process-detail-window" class="window">
				<div class="head">
					<div class="title">详细</div>
				</div>
				<div class="body">
					<div class="parameter">
						<h3>输入</h3>
						<div class="label name">名称：</div>
						<div class="value name">123</div>
						
						<div class="label type">类型：</div>
						<div class="value type">string</div>
						<div class="button name">更改名称</div>
						<div class="button type">更改类型</div><br/>
					</div>
					<div class="condition">
						<h3>条件</h3>
						<div class="predicate">
							<h4>predicate：</h4>
							<div class="label name">名称</div>
							<div class="value name">123</div>
							<div class="label type">类型：</div>
							<div class="value type">string</div>
							<div class="button name">更改名称</div>
						<div class="button type">更改类型</div><br/>
						</div>
						<div class="argument1">
							<h4>argument1：</h4>
							<div class="label name">名称</div>
							<div class="value name">123</div>
							<div class="label type">类型：</div>
							<div class="value type">string</div>
							<div class="button name">更改名称</div>
							<div class="button type">更改类型</div><br/>
						</div>
						<div class="argument2">
							<h4>argument2：</h4>
							<div class="label name">名称</div>
							<div class="value name">123</div>
							
							<div class="label type">类型：</div>
							<div class="value type">string</div>
							<div class="button name">更改名称</div>
						<div class="button type">更改类型</div><br/>
						</div>
					</div>
					<div class="process">
						<div class="label">process:</div>
						<div class="value"></div>
					</div>
				</div>
			</div>
		
		<div id="statusbar" class="statusbar">
		
			<div class="view">
				<div class="view-3 selected"></div>
				<div class="view-2"></div>
				<div class="view-1"></div>
			</div>
		</div>
	</div>
	<div id="wsdl-dialog">
		<div class="container">
			<div class="url">
				<div class="label">WSDL:</div>
				<div class="value">null</div>
				<div class="button"><s:i18n name="bpep"><s:text name="select"></s:text></s:i18n></div>
			</div>
			<div class="operation"></div>
			<div class="detail"></div>
		</div>
	</div>
	<div id="process-dialog">
		<div class="name">
			<div class="label"><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n>:</div>
			<div class="value">Process1</div>
			<div class="button"><s:i18n name="bpep"><s:text name="change"></s:text></s:i18n></div>
		</div>
		<div class="precondition">
			<div class="label">Precondition</div>
			<div class="add">添加</div>
			
		</div>
		<div class="postcondition">
			<div class="label">Postcondition</div>
			<div class="add">添加</div>
		</div>
		<div class="hasInput">
			<div class="label">hasInput:</div>
			<div class="container"></div>
		</div>
		<div class="hasOutput">
			<div class="label">hasOutput:</div>
			<div class="container"></div>
		</div>
	</div>

	<!-- Produceå±æ§å¯¹è¯æ¡ -->
	<div id="produce-dialog">
		<div class="name">
			<div class="label"><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n>:</div>
			<div class="value">123</div>
			<div class="button"><s:i18n name="bpep"><s:text name="change"></s:text></s:i18n></div>
		</div>
		<div class="binding">
			<div class="label">OutputBinding:</div>
			<div class="container"></div>
		</div>
	</div>
	<!-- åæ°ç¼è¾å¯¹è¯æ¡ -->
	<div id="param-dialog">
		<div class="name">
			<div class="label"><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n>:</div>
			<div class="value"></div>
			<div class="button"><s:i18n name="bpep"><s:text name="change"></s:text></s:i18n></div>
		</div>
		<div class="type">
			<div class="label">Type:</div>
			<div class="value"></div>
			<div class="button"><s:i18n name="bpep"><s:text name="change"></s:text></s:i18n></div>
		</div>
	</div>
	<!-- å±æ§å¯¹è¯æ¡ -->
	<div id="property-dialog">
		<div class="name">
			<div class="label"><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n>：</div>
			<div class="value">123</div>
			<div class="button"><s:i18n name="bpep"><s:text name="change"></s:text></s:i18n></div>
		</div>
		<div class="input">
			<div class="label">process:hasInput</div>
			<div class='add'><s:i18n name="bpep"><s:text name="add"></s:text></s:i18n></div>
			<div class="container"></div>
		</div>
		<div class="output">
			<div class="label">process:hasOutput</div>
			<div class='add'><s:i18n name="bpep"><s:text name="add"></s:text></s:i18n></div>
			<div class="container"></div>
		</div>
	</div>
	<!-- æµç¨ä¸æ°æ®ç»å®å¯¹è¯æ¡ -->
	<div id="perform-dialog">
		<div class="name">
			<div class="label"><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n>:</div>
			<div class="value">123</div>
			<div class="button"><s:i18n name="bpep"><s:text name="change"></s:text></s:i18n></div>
		</div>
		<div class="process">
			<div class="label"><s:i18n name="bpep"><s:text name="process"></s:text></s:i18n>:</div>
			<div class="value">123</div>
			<div class="button"><s:i18n name="bpep"><s:text name="select"></s:text></s:i18n></div>
		</div>
		<div class="binding">
			<div class="label">InputBinding:</div>
			<div class="container"></div>
		</div>
	</div>
	<!-- éæ©æµç¨å¯¹è¯æ¡ -->
	<div id="select-process-dialog"></div>
	<!-- éæ©WSDLæä»¶å¯¹è¯æ¡ -->
	<div id="select-wsdl-dialog">正在获取WSDL列表……</div>
	<!-- éæ©OWLClasså¯¹è¯æ¡ -->
	<div id="select-ontology-dialog"></div>
	<!-- éæ©åæ°å¯¹è¯æ¡ -->
	<div id="select-param-dialog"></div>
	<!-- éæ©å¯ç¨äºæ°æ®ç»å®çåæ°å¯¹è¯æ¡ -->
	<div id="select-binding-dialog"></div>
	<div id="dlg-ato-proc">
		<div class="name">
			<div class="label"><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n>：</div>
			<div class="value">Test</div>
			<div class="button"><s:i18n name="bpep"><s:text name="change"></s:text></s:i18n></div>
		</div>
		<div class="precondition">
			<div class="label"><s:i18n name="bpep"><s:text name="pre_condition"></s:text></s:i18n></div>
			<div class="add">添加</div>
			<div class="content"></div>
		</div>
		<div class="postcondition">
			<div class="label"><s:i18n name="bpep"><s:text name="post_condition"></s:text></s:i18n></div>
			<textarea></textarea>
		</div>
		<div class="input">
			<div class="label"><s:i18n name="bpep"><s:text name="input"></s:text></s:i18n>：</div>
			<table>
				<tr>
					<th class="name"><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n></th>
					<th class="type"><s:i18n name="bpep"><s:text name="type"></s:text></s:i18n></th>
					<th class="rename"></th>
					<th class="anno"></th>
				</tr>
			</table>
		</div>
		<div class="output">
			<div class="label"><s:i18n name="bpep"><s:text name="output"></s:text></s:i18n>：</div>
			<table>
				<tr>
					<th class="name"><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n></th>
					<th class="type"><s:i18n name="bpep"><s:text name="type"></s:text></s:i18n></th>
					<th class="rename"></th>
					<th class="anno"></th>
				</tr>
			</table>
		</div>
	</div>
	<div id="dlg-wsdl-list">正在获取WSDL列表……</div>
	<div id="dlg-wsdl-info">
		<div class="container">
			<div class="url">
				<div class="label">WSDL:</div>
				<div class="value">null</div>
				<div class="button"><s:i18n name="bpep"><s:text name="select"></s:text></s:i18n></div>
			</div>
			<div class="operation"></div>
			<div class="detail"></div>
		</div>
	</div>
	<div id="dlg-ont-list">正在获取可用的本体列表……</div>
	<div id="dlg-ont-class"></div>
	<div id="condition-dialog">
		<div class="name">
			<div class="label"><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n>：</div>
			<div class="value">Condition</div>
			<div class="operation"><s:i18n name="bpep"><s:text name="change"></s:text></s:i18n></div>
		</div>
		<div class="expression">
			<div class="label"><s:i18n name="bpep"><s:text name="condition"></s:text></s:i18n>：</div>
			<textarea></textarea>
		</div>
	</div>
	<jsp:include page="load-process.jsp">
		<jsp:param name="provider" value="${sessionScope.PPusername}" />
	</jsp:include>


	
    <div id="cover" style="position:absolute;top:0px;bottom:0px;
    left:0px;right:0px;background:#FFFFFF;z-index:100000;"><s:i18n name="bpep"><s:text name="load"></s:text></s:i18n></div>
</body>
</html>