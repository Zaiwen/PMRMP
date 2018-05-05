<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.io.*,finalvariable.*"%>
<!DOCTYPE html>
<html style="overflow: hidden">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>插件开发</title>
<link rel="stylesheet" type="text/css" href="css/editor.css">
<link rel="stylesheet" type="text/css" href="css/dialog.css">
<link rel="stylesheet" type="text/css" href="css/extension-dialog.css">
<link rel="stylesheet" type="text/css" href="css/QNameView.css">
<link rel="stylesheet" type="text/css" href="css/WSDLOperationView.css">
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
<script type="text/javascript" src="lib/jquery.jstree/jquery.cookie.js"></script>
<script type="text/javascript" src="lib/jquery.jstree/jquery.hotkeys.js"></script>
<script type="text/javascript" src="lib/jquery.jstree/jquery.jstree.js"></script>
<script type="text/javascript">
	mxBasePath = "lib/mxGraph";
	var $req_dom="traffic";
	$mode = "ext-edit";
</script>
<script type="text/javascript" src="js/update.js" /></script>
<script type="text/javascript" src="lib/mxGraph/js/mxClient.js"></script>
<script type="text/javascript" src="lib/json.js"></script>
<script type="text/javascript" src="js/extension-editor.js"></script>
<script type="text/javascript" src="js/mode.js"></script>
<script type="text/javascript" src="js/extension/Extension.js"></script>
<script type="text/javascript" src="js/extension/ExtParameter.js"></script>
<script type="text/javascript" src="common-js.jsp"></script>
<script type="text/javascript" src="js/owl/OWLModel.js"></script>
<script type="text/javascript" src="js/construct/IfThenElse.js"></script>
</head>
<body>
	<div id='editor'>
		<div class='left'>
			<div class='head'>
				<div class="title">控制结构</div>
			</div>
			<div class='body'>
				<div class='tree'></div>
			</div>
		</div>
		<div class='center'>
			<div class='head'>
				<div class="title">流程图</div>
				<div class="zoom-actual" title="原始大小"></div>
				<div class="zoom-out" title="缩小"></div>
				<div class="zoom-in" title="放大"></div>
			</div>
			<div class='body'></div>
		</div>
		<div class='right'>
			<div class='head'>
				<div class="title">原子流程</div>
			</div>
			<div class='body'></div>
		</div>
		<div class="statusbar">
			<div class="extension">
				插件开发中， <span>点此</span>继续！
			</div>
		</div>
	</div>
	<jsp:include page="load-process.jsp"></jsp:include>

</body>
</html>