<%@ page language="java"
	import="java.util.*,java.sql.*,ontology.*,databaseaccess.*"
	pageEncoding="utf-8"%>
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
<title>查看本体详细信息</title>
<link rel="stylesheet" type="text/css" href="style/ontoinfo.css" />
<link rel="stylesheet" href="js/jquery-ui.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/rightbutton.js"></script>
<script type="text/javascript" src="ontologytree/web.js"></script>
<script type="text/javascript" src="ontologytree/yahoo-dom-event.js"></script>
<script type="text/javascript" src="ontologytree/dragdrop-min.js"></script>
<script type="text/javascript" src="ontologytree/container-min.js"></script>
<link rel="stylesheet" type="text/css" href="ontologytree/container.css" />
<script language=jscript>
		function ChangeStatus(o){
			if (o.parentNode){
				if (o.parentNode.className == "Opened"){
					o.parentNode.className = "Closed";
				}else{
					o.parentNode.className = "Opened";
				}
			}
		}
</script>
<script>
	$(function() {
		$("#accordion").accordion();
	});
</script>

</head>
<body class="yui-skin-sam">
	<script>
		YAHOO.namespace("example.container");
		function init() {
			// Instantiate a Panel from markup
			YAHOO.example.container.panel1 = new YAHOO.widget.Panel("panel1", {
				width : "400px",
				fixedcenter : true,
				visible : false,
				constraintoviewport : true
			});
			YAHOO.example.container.panel1.render();
		}
		YAHOO.util.Event.addListener(window, "load", init);
	</script>
	<div id="contentinfo" align="center">
		<br /> <br />
		<div id="accordion" class="accordion">
			<h3>查看本体详情:</h3>
			<div id="showdetails" class="accordion">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td>
										<table border="0" cellspacing="0" cellpadding="0">
											<%
												try {
													OntologyForMoreInfoDTO onto_more = (OntologyForMoreInfoDTO) request
															.getAttribute("ontologydto");
											%>
											<tr>
												<td>
													<div>
														本体名称:&nbsp;&nbsp;<%=onto_more.getOntologyName()%>
													</div> <br />
												</td>
											</tr>
										</table>
										<table border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td>
													<div class="TreeMenu" id="CategoryTree">
														<br />
														<ul>
															<li class="Opened"><img class="s"
																src="images/opened.gif"
																onclick="javascript:ChangeStatus(this);" /> <a
																onclick="getResult('<%=onto_more.getId()%>', 1)"
																onmousemove="this.style['color']='BLUE'"
																onmouseout="this.style['color']='BLACK'"><%=onto_more.getUri()%></a>
																<%
																	Connection conn = Access.getConnection();
																		OntologyComponentDAO ao = new OntologyComponentDAO(conn);
																		OntologyAtomicConstructDAO dd = new OntologyAtomicConstructDAO(
																				conn);
																		List<OntologyComponentDTO> coms = ao
																				.queryByOntologyId(onto_more.getId());
																		for (int i = 0; i < coms.size(); i++) {
																			OntologyComponentDTO com = coms.get(i);
																			String nonlogicalS = com.getKeyAtomic();
																%>
																<ul>
																	<li class="Closed"><img class="s"
																		src="images/closed.gif"
																		onclick="javascript:ChangeStatus(this);" /> <a
																		onclick="getResult('<%=com.getId()%>', 2)"
																		onmousemove="this.style['color']='BLUE'"
																		onmouseout="this.style['color']='BLACK'"> <%
 	if (onto_more.getUri().equals(com.getNamespace())) {
 %> <%=com.getType()%>: <%=nonlogicalS%> <%
 	} else {
 %> <%=com.getType()%>: <%=com.getNamespace()%><%=nonlogicalS%> <%
 	}
 %>
																	</a>
																		<ul>
																			<%
																				List<OntologyAtomicConstructDTO> atoms = dd
																								.queryByComponentId(com.getId());
																						for (int j = 0; j < atoms.size(); j++) {
																							OntologyAtomicConstructDTO atom = atoms.get(j);
																			%>
																			<li class="Child"><img class="s"
																				src="images/page.gif" /> <a
																				onclick="getResult('<%=atom.getId()%>', 3)"
																				onmousemove="this.style['color']='BLUE'"
																				onmouseout="this.style['color']='BLACK'"> <%
 	if (onto_more.getUri().equals(atom.getNamespace())) {
 %> <%=atom.getNonLogicalSymbol()%> <%
 	} else {
 %> <%
 	}
 %>
																			</a></li>
																			<%
																				}
																			%>
																		</ul></li>
																</ul> <%
 	}
 		ao.closeDBConnection();
 		dd.closeDBConnection();
 	} catch (Exception e) {
 		out.print("<script>alert('出现异常!');window.location.href='/BPEP/index.html'</script>");
 	}
 %></li>
														</ul>
													</div>
												</td>
											</tr>
										</table>
									</td>

								</tr>
							</table>
						</td>
					</tr>

				</table>
			</div>
		</div>
	</div>
</body>
</html>