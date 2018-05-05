package plugin;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import changecode.Utf8code;
import databaseaccess.Access;
import finalvariable.BasicPathVariable;

public class CreatePlugin extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public CreatePlugin() {
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
					"ISVusername");
			if (username == null) {
				request.getSession().setAttribute("pluginerror", "请登陆后再进行操作!");
				response.sendRedirect("processManage/createwrong.jsp");
				return;
			} else {
				String filename = Utf8code.changeCode(request
						.getParameter("templatename"));
				filename = filename.trim();
				if (filename.length() < 1) {
					request.getSession().setAttribute("pluginerror",
							"请输入plugin文件名!");
					response.sendRedirect("processManage/createwrong.jsp");
					return;
				} else {
					String plugindomain = request
							.getParameter("templatedomain");
					String processname =Utf8code.changeCode(request
							.getParameter("processname"));
					if(processname==null||processname.equals("")){
						processname=Utf8code.changeCode(request
								.getParameter("processname2"));
					}
					String plugintype=request.getParameter("templatetype");
					String processuser=Utf8code.changeCode(request.getParameter("processuser"));
					plugindomain = plugindomain.trim();
					String fileroot = BasicPathVariable.pluginPath + username;
					File file = new File(fileroot);
					if (!file.exists()) {// 以用户名创建文件夹
						file.mkdirs();
					}
					fileroot = fileroot + "//" + filename;
					file = new File(fileroot);
					if (!file.exists()) {// 可以创建文件
						file.mkdirs();
					} else {// 文件名已存在则报错
						request.getSession().setAttribute("pluginerror",
								"plugin文件名已存在!");
						response.sendRedirect("/BPEP/processManage/createwrong.jsp");
						return;
					}
					// 成功创建文件
					Access ac = new Access();
					ac.connDB("bpep");
					String sqlplugin = "select * from plugininfo where name='"
							+ filename + "' AND user='" + username + "'";
					ResultSet res;
					res = ac.executeSelectSql(sqlplugin);
					String processid="";
					if (!res.next()) {
						sqlplugin = "insert into plugininfo values(null,'"
								+ filename + "','" + username + "','"+plugintype+"','"
								+ plugindomain +"','" + processname+"','" + processuser+"')";
						ac.executeUpdateSql(sqlplugin);
						sqlplugin = "select id from original_process where name='"
							+ processname + "' AND user='" + processuser + "'";
						ResultSet restemp = ac.executeSelectSql(sqlplugin);
						if(restemp.next()){
							processid=restemp.getString(1);
						}
					}
					ac.closeDB();
					if(plugintype.equalsIgnoreCase("OWL-S")){
						//response.sendRedirect("/BPEP/processManage/extension-editor.jsp?funct=create&name="+filename+"&domain="+plugindomain+"&process="+processname+"&provider="+processuser);
						response.sendRedirect("/BPEP/processManage/plugin-template.jsp?type=owls&name="+filename+"&domain="+plugindomain+"&process="+processid+"&processname="+processname+"&provider="+processuser);
						return;
					}else if(plugintype.equalsIgnoreCase("EPC")){
						//response.sendRedirect("/BPEP/processManage/epc/index.jsp");
						response.sendRedirect("/BPEP/processManage/plugin-template.jsp?type=epc&name="+filename+"&domain="+plugindomain+"&process="+processid+"&processname="+processname+"&provider="+processuser);
						return;
					}else if(plugintype.equalsIgnoreCase("BPEL")){
						//response.sendRedirect("/BPEP/processManage/epc/index.jsp");
						return;
					}
					
				}
			}
		} catch (Exception e) {
			request.getSession().setAttribute("pluginerror",
					"创建plugin文件时出现异常!");
			System.err.println(e);
			try {
				response.sendRedirect("/BPEP/processManage/createwrong.jsp");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.err.println(e);
				return;
			}
			return;
		}
	}

}
