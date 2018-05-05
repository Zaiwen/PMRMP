<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.io.*,change4epml.*,java.text.*,java.sql.*,java.util.*,org.json.*,databaseaccess.*"%>
<%@ page import="org.sklse.processRegister.expander.impl.*"%>
<%
/**
 * 该接口可以接收三个参数
 * -- name 流程名（不能为空）
 * -- content 流程内容（不能为空）
 * -- lastVersion 此流程的上个版本的版本号（可选，默认为0）
 * 返回JSON格式的键值对，包含
 * -- code 操作码，对应操作的结果
 * ----- 0 保存成功
 * ----- 1 name参数为空
 * ----- 2 content参数为空
 * ----- 3 lastVersion参数不正确
 * ----- 4 没有Process Provider用户权限
 * ----- 5 其它未知错误
 * -- version 保存后的版本号
 */
 	JSONObject obj = new JSONObject();
	try{
		if(session.getAttribute("PPusername")!=null){
			String content = request.getParameter("content");
			String name = request.getParameter("name");
			String lastVersion=request.getParameter("lastVersion");
			String user = session.getAttribute("PPusername").toString();
			// 参数处理
			if(null==name||"".equals(name)){
				obj.put("code",1);
				return;
			}
			if(null==content||"".equals(content)){
				obj.put("code",2);
				return;
			}
			int _lastVersion = 0;
			if(!(null==lastVersion||"".equals(lastVersion))){
				try{
					_lastVersion=Integer.parseInt(lastVersion);
				}catch(Exception e){
					obj.put("code",3);
					return;
				}
			}
			// 连接数据库
		long version = OriginalProcessServiceImpl.instance.getMaxVersion(user,name);
			// 插入新的记录
			version ++;
			OriginalProcessServiceImpl.instance.insertOriginalProcess(user,name,content,version,_lastVersion);
			obj.put("code",0);
			obj.put("version",version);
		}else{
			obj.put("code",4);
		}
	}catch(Exception e){
		e.printStackTrace();
		obj.put("code",5);
	}finally{
		out.print(obj);
	}
%>