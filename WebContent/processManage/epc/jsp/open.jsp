<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.io.*,java.text.*,java.sql.*,java.util.*,databaseaccess.*,org.json.*,org.sklse.processRegister.db.dto.*,org.sklse.processRegister.db.services.impl.*,org.sklse.processRegister.processGraph.*,finalvariable.*,java.sql.*,databaseaccess.*,org.sklse.processRegister.expander.impl.*,nl.tue.tm.is.epc.*"%>
<%
/**
 * 此接口接受两个参数
 * process 	流程的ID，为长整型
 * extension插件的ID，为长整型（可选）
 * 返回值为包含以下键值对的JSON对象：
 * -- code 所有操作最终的状态码，包含很多情况
 * ----- 000 打开基础成功
 * ----- 001 流程组合成功
 * ----- 100 process参数不存在
 * ----- 101 process参数格式错误
 * ----- 102 process对应的流程不存在
 * ----- 201 extension参数格式错误
 * ----- 202 extension对应的插件不存在
 * ----- 300 插件组合时出错
 * ----- 400 用户未登录
 * ----- 500 未知错误
 * -- name 流程名
 * -- create_time 当前版本流程的创建时间
 * -- view_time 当前版本流程最后一次浏览的时间
 * -- version 当前流程的版本号
 * -- content epml格式的流程内容
 * -- extended 扩展后的流程的内容‘
 * -- extendNumber 扩展点的个数
 */
	JSONObject obj=new JSONObject();
	try{
		// 检测用户是否登录，只有登录的用户才有权限打开流程
		if(session.getAttribute("PPusername")==null
			&& session.getAttribute("ISVusername")==null
			&& session.getAttribute("ORGusername")==null
			&& session.getAttribute("Adminusername")==null){
			obj.put("code", 400);
		}else{
			// 获取参数
			String process = request.getParameter("process");
			String extension = request.getParameter("extension");
			long processId = -1L;
			long extensionId = -1L;
			long graph = -1L;
			String sql = "select name,content,version,create_time,graph from original_process where id=?";
			// process 参数处理
			if(null == process || "".equals(process)){
				obj.put("code",100);
			}else{
				Access ac = new Access();
				try{
					processId=Long.parseLong(process);
					ac.connDB("bpep");
					PreparedStatement pst=ac.getCon().prepareStatement(sql);
					pst.setLong(1,processId);
					ResultSet rs = pst.executeQuery();
					if(rs.next()){
						obj.put("code",000);
						obj.put("name",rs.getString(1));
						obj.put("content",rs.getString(2));
						obj.put("version",rs.getLong(3));
						obj.put("create_time",rs.getDate(4)+" "+rs.getTime(4));
						graph=rs.getLong(5);
					}else{
						obj.put("code",102);
					}
				}catch(NumberFormatException e){
					obj.put("code", 101);
				}catch(Exception e){
					e.printStackTrace();
					obj.put("code", 500);
				}finally{
					ac.closeDB();
				}
			}
			// extension 参数处理
			if(null == extension || "".equals(extension)){
				
			}else{
				// extension参数 格式转化
				try{
					extensionId=Long.parseLong(extension);
					if(graph != -1L){
						EPC epc=new EPC();
						ProcessGraphIOServiceImpl pgio=new ProcessGraphIOServiceImpl();
						ProcessGraphExpanderImpl pgex = new ProcessGraphExpanderImpl();
						PluginInfoServiceImpl plugs=new PluginInfoServiceImpl();
						ProcessGraph pg=pgio.ProcessGraphLoad(graph);
						PluginInfoDTO plug = plugs.getPluginInfoDTO(extensionId);
						List<ProcessNode> list=pgex.getExtensibilityPoint(pg, plug);
						pgex.expandProcessGraph(pg,list , plug);
						String epml=epc.Epc2Epml(pgio.ProcessGraphToEpc(pg));
					 	System.out.println(pg.getAllProcessNodes().size());
						obj.put("code",001);
						obj.put("extended",epml);
						obj.put("extendNumber",list.size());
					}
				}catch(NumberFormatException e){
					obj.put("code", 201);
				}catch(Exception e){
					e.printStackTrace();
					obj.put("code", 500);
				}
			}
			
		}
	}finally{
		out.print(obj.toString());
	}
%>