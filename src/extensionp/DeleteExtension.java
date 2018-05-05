package extensionp;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import changecode.Utf8code;
import databaseaccess.Access;
import fileio.DeleteFile;
import finalvariable.BasicPathVariable;

public class DeleteExtension extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public DeleteExtension() {
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
		try{
		response.setContentType("text/html; charset=utf-8");
		String orgusername = (String) request.getSession().getAttribute(
				"ORGusername");
		String adminusername = (String) request.getSession().getAttribute(
				"Adminusername");
		String filename = Utf8code.changeCode(request.getParameter("extensionname"));
		filename = filename.trim();
		String fileuser = Utf8code.changeCode(request.getParameter("extensionuser"));
		fileuser = fileuser.trim();
		
		if (orgusername == null && adminusername == null) {
			request.getSession().setAttribute("extensiondelete", "请登陆后再进行操作!");
			response.sendRedirect("processManage/deletewrong.jsp");
			return;
		} else if (orgusername != null) {// 进行删除
			if (filename.length() < 1) {
				request.getSession().setAttribute("extensiondelete",
						"extension文件名不合法!");
				response.sendRedirect("processManage/deletewrong.jsp");
				return;
			} else if (fileuser.equals(orgusername)) {
					String fileroot = BasicPathVariable.extProcessPath
							+ orgusername + "//";
					File file = new File(fileroot);
					if (!file.exists()) {// 文件夹不存在
						request.getSession().setAttribute("extensiondelete",
								"您没有创建extension流程!");
						response.sendRedirect("processManage/deletewrong.jsp");
						return;
					}
					fileroot = fileroot + filename;
					file = new File(fileroot);
					if (!file.exists()) {// 文件不存在
						request.getSession().setAttribute("extensiondelete",
								"不存在该extension文件!");
						response.sendRedirect("processManage/deletewrong.jsp");
						return;
					} else {// 文件存在时删除
						//file.delete(); 注意这条语句只能删除空文件夹
						if (DeleteFile.deletefile(fileroot)) {
							Access ac=new Access();
							ac.connDB("bpep");
							String sqlextension="select * from extensioninfo where name='"+filename+"' AND user='"+fileuser+"'";
							ResultSet res;
							res=ac.executeSelectSql(sqlextension);
							if(res.next()){
								sqlextension="delete from extensioninfo where name='"+filename+"' AND user='"+fileuser+"'";
								ac.executeUpdateSql(sqlextension);
							}
							ac.closeDB();
							response.sendRedirect("userManage/userORG.jsp");
						} else {
							request.getSession().setAttribute("extensiondelete",
									"删除extension文件失败!");
							response.sendRedirect("processManage/deletewrong.jsp");
						}
						return;
					}
			}else{//文件名与username不一致时，不允许删除
				request.getSession().setAttribute("extensiondelete",
						"您没有权限删除此extension文件!");
				response.sendRedirect("processManage/deletewrong.jsp");
				return;
			}
		} else if (adminusername != null) {// administrator进行删除
			if (filename.length() < 1) {
				request.getSession().setAttribute("extensiondelete",
						"extension文件名不合法!");
				response.sendRedirect("processManage/deletewrong.jsp");
				return;
			} else{
					String fileroot = BasicPathVariable.extProcessPath
							+ fileuser + "//";
					File file = new File(fileroot);
					if (!file.exists()) {// 文件夹不存在
						request.getSession().setAttribute("extensiondelete",
								"该用户没有创建extension流程!");
						response.sendRedirect("processManage/deletewrong.jsp");
						return;
					}
					fileroot = fileroot + filename;
					file = new File(fileroot);
					if (!file.exists()) {//文件不存在
						request.getSession().setAttribute("extensiondelete",
								"不存在该extension文件!");
						response.sendRedirect("processManage/deletewrong.jsp");
						return;
					} else {// 文件存在时删除
						//file.delete(); 注意这条语句只能删除空文件夹
						if (DeleteFile.deletefile(fileroot)) {
							Access ac=new Access();
							ac.connDB("bpep");
							String sqlextension="select * from extensioninfo where name='"+filename+"' AND user='"+fileuser+"'";
							ResultSet res;
							res=ac.executeSelectSql(sqlextension);
							if(res.next()){
								sqlextension="delete from extensioninfo where name='"+filename+"' AND user='"+fileuser+"'";
								ac.executeUpdateSql(sqlextension);
							}
							ac.closeDB();
							response.sendRedirect("userManage/userAdmin.jsp");
							return;
						} else {
							request.getSession().setAttribute("extensiondelete",
									"删除extension文件失败!");
							response.sendRedirect("processManage/deletewrong.jsp");
							return;
						}
					}
			}
		}
		} catch (Exception e) {
			request.getSession().setAttribute("extensiondelete",
					"删除extension文件时出现异常!");
			try {
				response.sendRedirect("processManage/deletewrong.jsp");
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
}
