<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.io.*,java.text.*,java.sql.*,java.util.*,databaseaccess.*,org.json.*"%>
<%
	if(session.getAttribute("PPusername")!=null){
		String user = session.getAttribute("PPusername").toString();
		String name = request.getParameter("name");
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		Access ac=new Access();
		ac.connDB("bpep");
		String sql = "select content from original_process where user=? and name=? and user_type=?";
		PreparedStatement st=ac.getCon().prepareStatement(sql);
		st.setString(1, user);
		st.setString(2, name);
		st.setString(3, "Process Provider");
		ResultSet rs=st.executeQuery();
		if(rs.next()){
			String content=rs.getString(1);
			// 更新view_time字段
			sql="update original_process set view_time=? where user=? and name=? and user_type=?";
			st=ac.getCon().prepareStatement(sql);
			st.setString(1, time);
			st.setString(2, user);
			st.setString(3, name);
			st.setString(4, "Process Provider");
			ac.closeDB();
			out.print(content);
		}else{
			out.print("false");
		}
	}
%>