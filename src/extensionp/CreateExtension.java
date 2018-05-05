package extensionp;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import changecode.Utf8code;
import databaseaccess.Access;
import finalvariable.BasicPathVariable;

public class CreateExtension extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public CreateExtension() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void init(){
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			String username = (String) request.getSession().getAttribute(
					"ORGusername");
			if (username == null) {
				request.getSession().setAttribute("extensionerror", "请登陆后再进行操作!");
				response.sendRedirect("processManage/createwrong.jsp");
				return;
			} else {
				String filename = Utf8code.changeCode(request
						.getParameter("extensionname"));
				filename = filename.trim();
				if (filename.length() < 1) {
					request.getSession().setAttribute("extensionerror",
							"请输入extension文件名!");
					response.sendRedirect("processManage/createwrong.jsp");
					return;
				} else {
					String processname =Utf8code.changeCode(request
							.getParameter("processname"));
					String processuser=Utf8code.changeCode(request.getParameter("processuser"));
					String fileroot = BasicPathVariable.extProcessPath+ username;
					File file = new File(fileroot);
					if (!file.exists()) {// 以用户名创建文件夹
						file.mkdirs();
					}
					fileroot = fileroot + "//" + filename;
					file = new File(fileroot);
					if (!file.exists()) {// 可以创建process文件
						file.mkdirs();
					} else {// 文件名已存在则报错
						request.getSession().setAttribute("extensionerror",
								"extension文件名已存在!");
						response.sendRedirect("/BPEP/processManage/createwrong.jsp");
						return;
					}
					// 成功创建文件
					Access ac = new Access();
					ac.connDB("bpep");
					String sqlextension = "select * from extensioninfo where name='"
							+ filename + "' AND user='" + username + "'";
					ResultSet res;
					res = ac.executeSelectSql(sqlextension);
					if (!res.next()) {
						sqlextension = "insert into extensioninfo values(null,'"
								+ filename + "','" + username +"','" + processname+"','" + processuser+"')";
						ac.executeUpdateSql(sqlextension);
					}
					ac.closeDB();
					response.sendRedirect("/BPEP/processManage/org-editor.jsp?name="+filename+"&process="+processname+"&provider="+processuser);
					
					return;
				}
			}
		} catch (Exception e) {
			
					
			request.getSession().setAttribute("extensionerror",
					"创建extension文件时出现异常!");
			try {
				response.sendRedirect("/BPEP/processManage/createwrong.jsp");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				return;
			}
			return;
		}
	}

}
