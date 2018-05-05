package process;

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

public class DeleteProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteProcess() {
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
		String ppusername = (String) request.getSession().getAttribute(
				"PPusername");
		String adminusername = (String) request.getSession().getAttribute(
				"Adminusername");
		String filename = Utf8code.changeCode(request.getParameter("processname"));
		filename = filename.trim();
		String fileuser = Utf8code.changeCode(request.getParameter("processuser"));
		fileuser = fileuser.trim();
		
		if (ppusername == null && adminusername == null) {
			request.getSession().setAttribute("processdelete", "请登陆后再进行操作!");
			response.sendRedirect("processManage/deletewrong.jsp");
			return;
		} else if (ppusername != null) {// process provider进行删除
			if (filename.length() < 1) {
				request.getSession().setAttribute("processdelete",
						"process文件名不合法!");
				response.sendRedirect("processManage/deletewrong.jsp");
				return;
			} else if (fileuser.equals(ppusername)) {
					String fileroot = BasicPathVariable.processPath
							+ ppusername + "//";
					File file = new File(fileroot);
					if (!file.exists()) {// 文件夹不存在
						request.getSession().setAttribute("processdelete",
								"您没有创建process流程!");
						response.sendRedirect("processManage/deletewrong.jsp");
						return;
					}
					fileroot = fileroot + filename;
					file = new File(fileroot);
					if (!file.exists()) {// process文件不存在
						request.getSession().setAttribute("processdelete",
								"不存在该process文件!");
						response.sendRedirect("processManage/deletewrong.jsp");
						return;
					} else {// process文件存在时删除
						//file.delete(); 注意这条语句只能删除空文件夹
						if (DeleteFile.deletefile(fileroot)) {
							Access ac=new Access();
							ac.connDB("bpep");
							String sqlprocess="select * from processinfo where name='"+filename+"' AND user='"+fileuser+"'";
							ResultSet res;
							res=ac.executeSelectSql(sqlprocess);
							if(res.next()){
								sqlprocess="delete from processinfo where name='"+filename+"' AND user='"+fileuser+"'";
								ac.executeUpdateSql(sqlprocess);
							}
							ac.closeDB();
							response.sendRedirect("userManage/userPP.jsp");
						} else {
							request.getSession().setAttribute("processdelete",
									"删除process文件失败!");
							response.sendRedirect("processManage/deletewrong.jsp");
						}
						return;
					}
			}else{//文件名与username不一致时，不允许删除
				request.getSession().setAttribute("processdelete",
						"您没有权限删除此process文件!");
				response.sendRedirect("processManage/deletewrong.jsp");
				return;
			}
		} else if (adminusername != null) {// administrator进行删除
			if (filename.length() < 1) {
				request.getSession().setAttribute("processdelete",
						"process文件名不合法!");
				response.sendRedirect("processManage/deletewrong.jsp");
				return;
			} else{
					String fileroot = BasicPathVariable.processPath
							+ fileuser + "//";
					File file = new File(fileroot);
					if (!file.exists()) {// 文件夹不存在
						request.getSession().setAttribute("processdelete",
								"该用户没有创建process流程!");
						response.sendRedirect("processManage/deletewrong.jsp");
						return;
					}
					fileroot = fileroot + filename;
					file = new File(fileroot);
					if (!file.exists()) {// process文件不存在
						request.getSession().setAttribute("processdelete",
								"不存在该process文件!");
						response.sendRedirect("processManage/deletewrong.jsp");
						return;
					} else {// process文件存在时删除
						//file.delete(); 注意这条语句只能删除空文件夹
						if (DeleteFile.deletefile(fileroot)) {
							Access ac=new Access();
							ac.connDB("bpep");
							String sqlprocess="select * from processinfo where name='"+filename+"' AND user='"+fileuser+"'";
							ResultSet res;
							res=ac.executeSelectSql(sqlprocess);
							if(res.next()){
								sqlprocess="delete from processinfo where name='"+filename+"' AND user='"+fileuser+"'";
								ac.executeUpdateSql(sqlprocess);
							}
							ac.closeDB();
							response.sendRedirect("userManage/userAdmin.jsp");
							return;
						} else {
							request.getSession().setAttribute("processdelete",
									"删除process文件失败!");
							response.sendRedirect("processManage/deletewrong.jsp");
							return;
						}
					}
			}
		}
		} catch (Exception e) {
			request.getSession().setAttribute("processdelete",
					"删除process文件时出现异常!");
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
