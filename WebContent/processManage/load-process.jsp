<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.io.*,finalvariable.*"%>
<%
	String process = (String)session.getAttribute("process");
	System.out.println(process);
	String provider = (String)session.getAttribute("provider");
	System.out.println(provider);
	String filePath = BasicPathVariable.processPath + provider + "//"+ process + "//";
	System.out.println(filePath);
	StringBuffer fileContent = new StringBuffer("");
	try {
		File dir = new File(filePath);
		File file = new File(filePath + "process.json");
		dir.mkdirs();
		if (!file.exists()) {
			fileContent = new StringBuffer(
					"{\"anyOrder\":[],"
							+ "\"choice\":[],"
							+ "\"sequence\":"
							+ "[{\"ID\":\"Sequence_1\","
							+ "\"components\":[]}],"
							+ "\"splitJoin\":[],"
							+ "\"perform\":[],"
							+ "\"produce\":[],"
							+ "\"atomicProcess\":[],"
							+ "\"compositeProcess\":"
							+ "{\"ID\":\"CompositeProcess_1\","
							+ "\"input\":[],\"output\":[],\"composeOf\":\"Sequence_1\"},"
							+ "\"input\":[],\"output\":[],"
							+ "\"inputBinding\":[],"
							+ "\"outputBinding\":[],"
							+ "\"inputMessageMap\":[],"
							+ "\"outputMessageMap\":[]}");
		} else {
			BufferedReader rd = new BufferedReader(new FileReader(file));
			
			String str = null;
			while ((str = rd.readLine()) != null) {
				System.out.println(str);
				fileContent.append(str);
			}
			rd.close();
		}
	} catch (Exception e) {
		out.println("流程名不合法！");
		return;
	}
%>
<script type="text/javascript">
	
	
	OWLModel.fromJson(<%=fileContent%>);
	
	
	Redraw();
	UpdateProcessView();
	UpdateTreeView();
	
</script>