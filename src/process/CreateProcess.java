package process;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import changecode.Utf8code;

import databaseaccess.Access;

import finalvariable.BasicPathVariable;

public class CreateProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CreateProcess() {
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
					"PPusername");
			if (username == null) {
				request.getSession().setAttribute("processerror", "请登陆后再进行操作!");
				response.sendRedirect("processManage/createwrong.jsp");
				return;
			} else {
				String filename = Utf8code.changeCode(request
						.getParameter("processname"));
				filename = filename.trim();
				if (filename.length() < 1) {
					request.getSession().setAttribute("processerror",
							"请输入process文件名!");
					response.sendRedirect("processManage/createwrong.jsp");
					return;
				} else {
					String processdomain = request
							.getParameter("processdomain");
					processdomain = processdomain.trim();
					String fileroot = BasicPathVariable.processPath + username;
					File file = new File(fileroot);
					if (!file.exists()) {// 以用户名创建文件夹
						file.mkdirs();
					}
					fileroot = fileroot + "//" + filename;
					file = new File(fileroot);
					if (!file.exists()) {// 可以创建process文件
						file.mkdirs();
					} else {// 文件名已存在则报错
						request.getSession().setAttribute("processerror",
								"process文件名已存在!");
						response.sendRedirect("/BPEP/processManage/createwrong.jsp");
						return;
					}
					// 成功创建文件
					Access ac = new Access();
					ac.connDB("bpep");
					String sqlprocess = "select * from processinfo where name='"
							+ filename + "' AND user='" + username + "'";
					ResultSet res;
					res = ac.executeSelectSql(sqlprocess);
					if (!res.next()) {
						sqlprocess = "insert into processinfo values('"
								+ filename + "','" + username + "','"
								+ processdomain + "',0)";
						ac.executeUpdateSql(sqlprocess);
					}
					ac.closeDB();
					response.sendRedirect("/BPEP/processManage/index.jsp?process="
							+ filename);
					return;
				}
			}
		} catch (Exception e) {
			
			System.err.println(e);
			request.getSession().setAttribute("processerror",
					"创建process文件时出现异常!");
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
