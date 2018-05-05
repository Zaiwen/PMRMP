<%@ page
	import="databaseaccess.Access,java.util.*,org.json.*,java.sql.*,org.sklse.processRegister.db.dto.ConditionDTO,org.sklse.processRegister.db.dao.ConditionDAO"
	pageEncoding="UTF-8"%>
<%@ page import="org.sklse.processRegister.db.ProcessGraphFactory,ontology.IdWorker"%>
<%@ page
	import="org.sklse.processRegister.expander.ProcessGraphExpander"%>
<%@ page
	import="org.sklse.processRegister.expander.ProcessGraphIOService"%>
<%@ page import="org.sklse.processRegister.processGraph.ProcessGraph"%>
<%@ page import="org.sklse.processRegister.processGraph.ProcessNode"%>
<%@ page
	import="org.sklse.processRegister.expander.OriginalProcessService"%>
<%-- Condition相似性查询 --%>
<%
    String provider = request.getParameter("Provider");
    String process = request.getParameter("Process");
    String precondition = request.getParameter("Precondition");
    String postcondition = request.getParameter("Postcondition");
    ConditionDTO preconditions = null;
    ConditionDTO postconditions = null;
    try {
    if (precondition == null || precondition.trim().equals("")) {

    } else {
        	preconditions=    ProcessGraphFactory.createSingleCondition(precondition, ConditionDTO.PositionTypeEnum.pre);
    }
    if (postcondition == null || postcondition.trim().equals("")) {

    } else {
        	postconditions=ProcessGraphFactory.createSingleCondition(postcondition, ConditionDTO.PositionTypeEnum.post);
    }
    if(preconditions==null && postconditions==null){
    	JSONObject obj = new JSONObject();
        obj.put("errors", "请输入precondition或者postcondition...");
        out.print(obj);
        return;
    }
    long graph = 0;
    graph = OriginalProcessService.instance.getGraphIdByUserAndName(provider, process);
    List<String> str = new ArrayList<String>();
    
        ProcessGraph processGraph = ProcessGraphIOService.intance.ProcessGraphLoad(graph);
        List<ProcessNode> processNodes = ProcessGraphExpander.instance.conditionQuery(processGraph, preconditions, postconditions);
        if (processNodes.size() > 0) {
            for (ProcessNode processNode : processNodes) {
                str.add(processNode.dto().getName());
            }
            ConditionDAO conditionDAO=ConditionDAO.getDAO();
            JSONObject obj = new JSONObject();
            if(preconditions!=null){
            	obj.put("preID",conditionDAO.create(preconditions)+"");
            }
            if(postconditions!=null){
            	obj.put("postID",conditionDAO.create(postconditions)+"");
            }
            JSONArray arr = new JSONArray();
            for (String i : str) {
                arr.put(i);
            }
            obj.put("ConditionIDs", arr);
            out.print(obj);
        }else{
        	JSONObject obj = new JSONObject();
            obj.put("errors", "没有找到符合条件的原子流程...");
            out.print(obj);
            return;
        }
    } catch (Exception e) {
        e.printStackTrace();
        JSONObject obj = new JSONObject();
        obj.put("errors", "出现异常...");
        out.print(obj);
    }
%>

