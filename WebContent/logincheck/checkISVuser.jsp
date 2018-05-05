<%@ page language="java" import="java.util.*,java.sql.*" pageEncoding="utf-8"%>
<jsp:useBean id="DA" class="databaseaccess.Access" scope="page"/>
<%
request.setCharacterEncoding("utf-8");
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>业务过程扩展人员登陆返回信息</title>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/rightbutton.js"></script>
</head>
<body>

<div id="content" align="center">
	<%
	  try{
	  
	  
	  String security=request.getParameter("check");
	  security=security.trim();
	  if(!security.equalsIgnoreCase((String)session.getAttribute("randomCode")))
	  {
		  out.print("<script>alert('验证码不正确!');window.location.href='/BPEP/login/login1.jsp'</script>");
		  return;
	  }
	  else{
	  String name=request.getParameter("username");
	  name=name.trim();
	  String password=request.getParameter("userpassword");
	  password=password.trim();
	  if(name==null||name.equals("")||password==null||password.equals(""))
	  {
		  out.print("<script>alert('用户名或密码不正确!');window.location.href='/BPEP/login/login1.jsp'</script>");
		  return;
	  }
	  else{
	  
	  ResultSet rs;
	  String usertype="bpep";
	  
	  String sql="select name,pass,permission From isvuser where name='"+name+"'";
	  
	  DA.connDB(usertype);
		
	 
		
	  rs=DA.executeSelectSql(sql);
	  
	  
	  if(rs.next())
	  {
	      String pass=rs.getString(2);
	      if(password.equals(pass)){
	    	 int admitpermission=Integer.parseInt(rs.getString(3));
	    	 if(admitpermission==0){
	    		 DA.closeDB();
	    		 out.println("<br><br><h2>Independent Software Vender登录失败!<br><br>(未经管理员认证通过,请等待!)</h2>");
	    		 out.print("<script>alert('Independent Software Vender登录失败!(未经管理员认证通过,请等待!)');window.location.href='/BPEP/login/login1.jsp'</script>");
	    		 return;
	    	 }
	    	 else if(admitpermission==1){
	    	 if(session.getAttribute("ISVusername")!=null&&(session.getAttribute("ISVusername")==name||session.getAttribute("ISVusername").equals(name))){
	    		 DA.closeDB();
	    		   out.println("<br><br><h2>Independent Software Vender登录失败!<br><br>(用户已在线!)</h2>");
	    		   out.print("<script>alert('Independent Software Vender登录失败!(用户已在线!)');window.location.href='/BPEP/login/login1.jsp'</script>");
	    		   return;
	    	       }
	    	 else{
	    		  if(session.getAttribute("Adminusername")!=null||session.getAttribute("PPusername")!=null||session.getAttribute("ORGusername")!=null){
	    			   if(session.getAttribute("Adminusername")!=null){
	    				   DA.closeDB();
	    				   out.println("<br><br><h2>Independent Software Vender登录失败!<br><br>(您以Admin身份登陆了!不能以ISV身份登陆!)</h2>");
	    				   out.print("<script>alert('Independent Software Vender登录失败!(您以Admin身份登陆了!不能以ISV身份登陆!)');window.location.href='/BPEP/login/login1.jsp'</script>");
	    				   return;
	    			    }
	    			  else  if(session.getAttribute("PPusername")!=null){
	    				  DA.closeDB();
	    			    	out.println("<br><br><h2>Independent Software Vender登录失败!<br><br>(您以PP身份登陆了!不能以ISV身份登陆!)</h2>");
	    			    	out.print("<script>alert('Independent Software Vender登录失败!(您以PP身份登陆了!不能以ISV身份登陆!)');window.location.href='/BPEP/login/login1.jsp'</script>");
	    			    	return;
	    			     }
	    			    else{
	    			    	DA.closeDB();
	    			    	out.println("<br><br><h2>Independent Software Vender登录失败!<br><br>(您以ORG身份登陆了!不能以ISV身份登陆!)</h2>");
	    			    	out.print("<script>alert('Independent Software Vender登录失败!(您以ORG身份登陆了!不能以ISV身份登陆!)');window.location.href='/BPEP/login/login1.jsp'</script>");
	    			    	return;
	    			      }
	    		      }
	    	      else{
	    	               session.setAttribute("ISVusername", name);
	    	               DA.closeDB();
	    	               out.print("<script>window.location.href='/BPEP/userManage/userISV.jsp'</script>");
	                }
	    	    }
	    	   }
	        }
	       else{
	    	   session.setAttribute("randomCode", null);
	    	    session.setAttribute("ISVusername",null);
	    	    DA.closeDB();
	            out.println("<br><br><h2>Independent Software Vender登录失败!<br><br>(用户名或密码错误!)</h2>");
	            out.print("<script>alert('Independent Software Vender登录失败!(用户名或密码错误!)');window.location.href='/BPEP/login/login1.jsp'</script>");
	            return;
	        }  
	  }
	  else{
		   session.setAttribute("randomCode", null);
		    session.setAttribute("ISVusername",null);
		    DA.closeDB();
	        out.println("<br><br><h2>Independent Software Vender登录失败!<br><br>(用户名或密码错误!)</h2>");
	        out.print("<script>alert('Independent Software Vender登录失败!(用户名或密码错误!)');window.location.href='/BPEP/login/login1.jsp'</script>");
	        return;
	  }
	  DA.closeDB();
	  }
	  }
	  }catch(SQLException se){
		  out.print("<script>alert('验证过程发生错误(database related error)!');window.location.href='/BPEP/login/login1.jsp'</script>");
	  }catch(Exception e){
		  out.print("<script>alert('验证过程发生错误!');window.location.href='/BPEP/login/login1.jsp'</script>");
	  }
	  
	   
	 %>
</div>

</body>
</html>
