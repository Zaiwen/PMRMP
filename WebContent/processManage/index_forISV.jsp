<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.io.*,finalvariable.*"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 

<%
if (  session.getAttribute("ISVusername")== null){

		out.println("会话超时，请重新登录！");
		return;
}else{

		String process_provider = "chenliang";
		
		
		
		session.setAttribute("provider", process_provider);
		
}	

String local;
if(session.getAttribute("WW_TRANS_I18N_LOCALE") == null) local  = "zh_CN";
else  local = session.getAttribute("WW_TRANS_I18N_LOCALE").toString();


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
<script type="text/javascript" src="lib/jquery/jquery-1.6.4.js"></script>
<script type="text/javascript"
	src="lib/jquery.ui/modernizr-2.0.6.min.js"></script>
<script type="text/javascript"
	src="lib/jquery.ui/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript"
	src="lib/jquery.ui/jquery.imgpreload.min.js"></script>
<script type="text/javascript"
	src="lib/jquery.ui/jquery-ui.toggleSwitch.js"></script>
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
    mxBasePath = "lib/mxGraph";
    $mode="proc-edit";
    
</script>
<script type="text/javascript" src="lib/mxGraph/js/mxClient.js"></script>
<!-- 导入CSS代码 -->
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
<script type="text/javascript" src="js/update.js"></script>
<script type="text/javascript" src="js/editor.js"></script>
<script type="text/javascript" src="js/perform-dialog.js"></script>
<script type="text/javascript" src="js/wsdl-dialog.js"></script>
<script type="text/javascript" src="js/property-dialog.js"></script>
<script type="text/javascript" src="common-js.jsp"></script>
<script type="text/javascript" src="js/owl/OWLModel.js"></script>
<script type="text/javascript" src="js/construct/IfThenElse.js"></script>
<script type="text/javascript" src="js/mode.js"></script>
<script type = "text/javascript" >
	var language = <%="'"+ local +"'"%>;
</script>
</head>
<body onselectstart="return false">
	<div id='editor'>
		<div class='toolbar'>
			<div class='button save'><s:i18n name="bpep"><s:text name="save"></s:text></s:i18n></div>
			<div class="button wsdl"><s:i18n name="bpep"><s:text name="service_view"></s:text></s:i18n></div>
			
			<div class='button property'><s:i18n name="bpep"><s:text name="process_config"></s:text></s:i18n></div>
			<div class='button'> 
			 <a style= "color:white" href="I18NAction.action?local=zh_CN&url=indexISV">中文</a> 
			 <a style= "color:white" href="I18NAction.action?local=en_US&url=indexISV">English</a> 
			</div>
		</div>
		<div class='left'>
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
		<div class='center'>
			<div class='head'>
				<div class="title"><s:i18n name="bpep"><s:text name="combine_process"></s:text></s:i18n></div>
				<div class="zoom-actual" title="原始大小"></div>
				<div class="zoom-out" title="缩小"></div>
				<div class="zoom-in" title="放大"></div>
			</div>
			<div class='body'></div>
		</div>
		<div class='right'>
			<div class='head'>
				<div class="title"><s:i18n name="bpep"><s:text name="atomic_process"></s:text></s:i18n></div>
				<div class="remove" title="<s:i18n name="bpep"><s:text name="delete"></s:text></s:i18n>"></div>
				<div class="add" title="<s:i18n name="bpep"><s:text name="add"></s:text></s:i18n>"></div>
			</div>
			<div class='body'></div>
		</div>
		<div class="statusbar"></div>
	</div>
	<!-- å¯¼å¥WSDLå¯¹è¯æ¡ -->
	<div id="wsdl-dialog">
		<div class="container">
			<div class="url">
				<div class="label">WSDL:</div>
				<div class="value">null</div>
				<div class="button"><s:i18n name="bpep"><s:text name="select"></s:text></s:i18n></div>
			</div>
			<div class="operation"></div>
			<div class="detail"></div>
		</div>
	</div>
	<!-- Processå±æ§å¯¹è¯æ¡ -->
	<div id="process-dialog">
		<div class="name">
			<div class="label">Name:</div>
			<div class="value">Process1</div>
			<div class="button"><s:i18n name="bpep"><s:text name="change"></s:text></s:i18n></div>
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
			<div class="label">Name:</div>
			<div class="value">123</div>
			<div class="button"><s:i18n name="bpep"><s:text name="change"></s:text></s:i18n></div>
		</div>
		<div class="binding">
			<div class="label">OutputBinding:</div>
			<div class="container"></div>
		</div>
	</div>
	<!-- åæ°ç¼è¾å¯¹è¯æ¡ -->
	<div id="param-dialog">
		<div class="name">
			<div class="label">Name:</div>
			<div class="value"></div>
			<div class="button"><s:i18n name="bpep"><s:text name="change"></s:text></s:i18n></div>
		</div>
		<div class="type">
			<div class="label">Type:</div>
			<div class="value"></div>
			<div class="button"><s:i18n name="bpep"><s:text name="change"></s:text></s:i18n></div>
		</div>
	</div>
	<!-- å±æ§å¯¹è¯æ¡ -->
	<div id="property-dialog">
		<div class="name">
			<div class="label">Name：</div>
			<div class="value">123</div>
			<div class="button"><s:i18n name="bpep"><s:text name="change"></s:text></s:i18n></div>
		</div>
		<div class="input">
			<div class="label">process:hasInput</div>
			<div class='add'><s:i18n name="bpep"><s:text name="add"></s:text></s:i18n></div>
			<div class="container"></div>
		</div>
		<div class="output">
			<div class="label">process:hasOutput</div>
			<div class='add'><s:i18n name="bpep"><s:text name="add"></s:text></s:i18n></div>
			<div class="container"></div>
		</div>
	</div>
	<!-- æµç¨ä¸æ°æ®ç»å®å¯¹è¯æ¡ -->
	<div id="perform-dialog">
		<div class="name">
			<div class="label">Name:</div>
			<div class="value">123</div>
			<div class="button"><s:i18n name="bpep"><s:text name="add"></s:text></s:i18n></div>
		</div>
		<div class="process">
			<div class="label">Process:</div>
			<div class="value">123</div>
			<div class="button"><s:i18n name="bpep"><s:text name="add"></s:text></s:i18n></div>
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
			<textarea></textarea>
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
					<th class="anno">%</th>
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
				<div class="button"><s:i18n name="bpep"><s:text name="select"></s:text></s:i18n></div>
			</div>
			<div class="operation"></div>
			<div class="detail"></div>
		</div>
	</div>
	<div id="dlg-ont-list">正在获取可用的本体列表……</div>
	<div id="dlg-ont-class"></div>
	<div id="condition-dialog">
		<div class="name">
			<div class="label"><s:i18n name="bpep"><s:text name="name"></s:text></s:i18n>：</div>
			<div class="value">Condition</div>
			<div class="operation"><s:i18n name="bpep"><s:text name="change"></s:text></s:i18n></div>
		</div>
		<div class="expression">
			<div class="label"><s:i18n name="bpep"><s:text name="condition"></s:text></s:i18n>：</div>
			<textarea></textarea>
		</div>
	</div>
	<jsp:include page="load-process.jsp"></jsp:include>


	
    <div id="cover" style="position:absolute;top:0px;bottom:0px;
    left:0px;right:0px;background:#FFFFFF;z-index:100000;"><s:i18n name="bpep"><s:text name="load"></s:text></s:i18n></div>
</body>
</html>