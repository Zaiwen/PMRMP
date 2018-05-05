<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head> 
<title>EPC Editor</title>
<meta charset="utf-8"/>
<link rel="stylesheet" type="text/css" href="../css/common.css">
<link rel="stylesheet" type="text/css" href="create.css">
<script type="text/javascript" src="../../lib/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript" src="create.js"></script>
</head>
<body>
	<div id="container">
		<div class="title">输入流程名</div>
		<input type="text"/>
		<div class="tip">流程名不能为空</div>
		<div class="process">
			<div class="item c-iepc">
				<img src="example-c-iepc.png"/>
				<p>C-iEPC</p>
			</div>
			<div class="item owl-s">
				<img src="example-owl-s.png"/>
				<p>OWL-S</p>
			</div>
		</div>
		<div class="button disabled">创建</div>
	</div>
</body>
</html>
