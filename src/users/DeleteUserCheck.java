package users;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import changecode.Utf8code;

import databaseaccess.Access;

public class DeleteUserCheck extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeleteUserCheck() {
		super();
	}
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response){
        try{
		response.setContentType("text/html; charset=utf-8");
		String name=Utf8code.changeCode(request.getParameter("name"));
		String databasetype=(String)(request.getParameter("databasetype"));
		Access ac=new Access();
		ResultSet rs;
		String sql0="select *  From "+databasetype+"  where name='" + name
				+ "'";
			ac.connDB("Users");
			rs = ac.executeSelectSql(sql0);
			if (!rs.next()) {
				ac.closeDB();
				request.getSession().setAttribute("deleteerror","删除失败!");
				response.sendRedirect("userManage/userAdmin.jsp");
				return;
			}
			else{
				String sql = "delete From "+databasetype+"  where name='" + name
						+ "'";
		        ac.executeUpdateSql(sql);
				ac.closeDB();
				response.sendRedirect("userManage/userAdmin.jsp");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("deleteerror", "删除时出现异常!");
			try {
				response.sendRedirect("userManage/userAdmin.jsp");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				return;
			}
			return;
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {

		doGet(request, response);
	}
	
	public void init(){
		// Put your code here
	}

}
