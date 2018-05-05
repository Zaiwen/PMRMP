<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.io.*,finalvariable.*"%>
<%
	String basePath =BasicPathVariable.processPath
			+ session.getAttribute("PPusername") + "//";
			
	System.out.println("basePath is " + basePath);
			
	String processName = request.getParameter("process");
	session.setAttribute("processName", processName);
	StringBuffer fileContent = new StringBuffer("");
	try { 
		File dir = new File(basePath + processName);
		File file = new File(basePath + processName + "\\process.json");
		dir.mkdirs();
		if (!file.exists()) {
			fileContent = new StringBuffer("{\"anyOrder\":[],"
					+ "\"choice\":[]," + "\"sequence\":"
					+ "[{\"ID\":\"Sequence_1\","
					+ "\"components\":[]}]," + "\"splitJoin\":[],"
					+ "\"perform\":[]," + "\"produce\":[],"
					+ "\"atomicProcess\":[]," + "\"compositeProcess\":"
					+ "{\"ID\":\"CompositeProcess_1\","
					+ "\"input\":[],\"output\":[],\"composeOf\":\"Sequence_1\"},"
					+ "\"input\":[],\"output\":[],"
					+ "\"inputBinding\":[]," + "\"outputBinding\":[],"
					+ "\"inputMessageMap\":[],"
					+ "\"outputMessageMap\":[]}");
		} else {
			BufferedReader rd = new BufferedReader(new FileReader(file));
			String str = null;
			while ((str = rd.readLine()) != null) {
				fileContent.append(str);
			}
			rd.close();
		}
	} catch (Exception e) {
		out.println("流程名不合法！");
		return;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看流程</title>
<link rel="stylesheet" type="text/css" href="css/process-viewer.css">
<script type="text/javascript">
	mxBasePath = "lib/mxGraph";
</script>
<script type="text/javascript" src="lib/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript"
	src="lib/jquery.ui/jquery.imgpreload.min.js"></script>
<script type="text/javascript" src="lib/json.js"></script>
<script type="text/javascript" src="lib/mxGraph/js/mxClient.js"></script>
<script type="text/javascript" src="js/proccess-viewer.js"></script>
<script type="text/javascript">
<%@include file="js/binding/InputBinding.js"%>
<%@include file="js/binding/OutputBinding.js"%>
<%@include file="js/construct/SequenceConstruct.js"%>
<%@include file="js/construct/AnyOrderConstruct.js"%>
<%@include file="js/construct/ChoiceConstruct.js"%>
<%@include file="js/construct/SplitJoinConstruct.js"%>
<%@include file="js/construct/IfThenElseConstruct.js"%>
<%@include file="js/construct/ProduceConstruct.js"%>
<%@include file="js/construct/PerformConstruct.js"%>
<%@include file="js/grounding/InputMessageMap.js"%>
<%@include file="js/grounding/OutputMessageMap.js"%>
<%@include file="js/owl/OntClass.js"%>
<%@include file="js/owl/OntModel.js"%>
<%@include file="js/owl/OWLModel.js"%>
<%@include file="js/parameter/Input.js"%>
<%@include file="js/parameter/Output.js"%>
<%@include file="js/process/AtomicProcess.js"%>
<%@include file="js/process/CompositeProcess.js"%>
<%@include file="js/view/WSDLOperationEditor.js"%>
<%@include file="js/view/QNameView.js"%>
<%@include file="js/view/OntModelTreeView.js"%>
<%@include file="js/view/WSDLOperationView.js"%>
<%@include file="js/wsdl/QName.js"%>
<%@include file="js/wsdl/WSDLParameter.js"%>
<%@include file="js/wsdl/WSDLOperation.js" %>
<%@include file="js/wsdl/WSDLService.js" %>
</script>
</head>
<body onselectstart="return false">
	<div class="head">
		<div class="title">流程视图</div>
		<div class="zoom-actual" title="实际大小"></div>
		<div class="zoom-out" title="缩小"></div>
		<div class="zoom-in" title="放大"></div>
	</div>
	<div class="body"></div>
</body>
</html>