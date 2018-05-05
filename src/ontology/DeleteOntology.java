package ontology;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fileio.DeleteFile;
import finalvariable.BasicPathVariable;

public class DeleteOntology extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public DeleteOntology() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=gbk");
		PrintWriter out = response.getWriter();
		int id = Integer.parseInt(request.getParameter("id"));
		String filename=request.getParameter("name");
		String adminusername = (String) request.getSession().getAttribute(
				"Adminusername");
		
		if (adminusername == null) {
			request.getSession().setAttribute("ontodelete", "error!");
			out.write("删除失败!");
		} else{// 进行删除
			if (filename.length() < 1) {
				request.getSession().setAttribute("ontodelete", "error!");
				out.write("删除失败!");
			} else {
					String fileroot = BasicPathVariable.ontologyLO
							+ adminusername + "//";
					File file = new File(fileroot);
					if (!file.exists()) {// 文件夹不存在
						request.getSession().setAttribute("ontodelete", "error!");
						out.write("删除失败!");
					}
					fileroot = fileroot + filename;
					file = new File(fileroot);
					if (!file.exists()) {// 文件不存在
						request.getSession().setAttribute("ontodelete", "error!");
						out.write("删除失败!");
					} else {// 文件存在时删除
						//file.delete(); 注意这条语句只能删除空文件夹
						if (DeleteFile.deletefile(fileroot)) {
							OntologyManagement management = new OntologyManagement();
							OntologyDAO dao = new OntologyDAO();

							management.deleteOntology(id);
							dao.closeDBConnection();		
							management.closeDBConnection();
							request.getSession().setAttribute("ontodelete", "success!");
							out.write("删除成功!");
						} else {
							request.getSession().setAttribute("ontodelete", "error!");
							out.write("删除失败!");
						}
					}
			}
		} 
//		String url = "/register/index.jsp";
//		ServletContext sc = getServletContext();
//		RequestDispatcher rd = sc.getRequestDispatcher(url);
//		rd.forward(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}