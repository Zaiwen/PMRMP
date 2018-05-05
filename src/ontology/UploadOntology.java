package ontology;
import finalvariable.BasicPathVariable;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

import databaseaccess.Access;


public class UploadOntology extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public String codeToString(String str) {// 处理中文字符串的函数
		String s = str;
		try {
			byte tempB[] = s.getBytes("ISO-8859-1");
			s = new String(tempB);
			return s;
		} catch (Exception e) {
			return s;
		}
	}

	/**
	 * Constructor of the object.
	 */
	public UploadOntology() {
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
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response){
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response){
		try {
		String username = (String) request.getSession().getAttribute("Adminusername");
			String description = "";
			String oname = "";
			int type = 1;
			String domain ="";
			String tempPath = "d://tmp//";
			DiskFileItemFactory factory = new DiskFileItemFactory();// 在Action中获得JSP表单内容
			factory.setSizeThreshold(200 * 1024 * 1024);
			factory.setRepository(new File(tempPath));

			// Create a new file upload handler
			FileUpload upload = new FileUpload(factory);

			// Set overall request size constraint
			upload.setSizeMax(1263509131 * 1024);

			@SuppressWarnings({ "deprecation", "rawtypes" })
			List items = upload.parseRequest(request);
			@SuppressWarnings("rawtypes")
			Iterator iter = items.iterator();
			String filepath =BasicPathVariable.ontologyPath;
			String url;
			String filename = "";

			FileItem fileitem = null;
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				String fieldName = item.getFieldName();

				if (!item.isFormField()) {// 忽略其他不是文件域的所有表单信息
					// System.out.println("=====");
					fileitem = item;
					String name = item.getName();
					filename = name
							.substring(name.lastIndexOf(File.separator) + 1,
									name.length());
					// System.out.println("=====" + filename);

				} else {
					if (fieldName.equals("ontologyfilename")) {
						oname = new String(item.getString().getBytes(
								"iso-8859-1"));
					}
					if (fieldName.equals("description")) {
						description = new String(item.getString().getBytes(
								"iso-8859-1"));
					}
					if (fieldName.equals("ontologydomain")) {
						domain = new String(item.getString()
								.getBytes("iso-8859-1"));
					}
				}
			}
			OntologyDAO ontoDAO = new OntologyDAO();
			
			if (type == 0) {// RO
				@SuppressWarnings("unused")
				OntologyDTO ontoDTO = null;
				ontoDTO = ontoDAO.queryByDomainRO(domain);
			}
			ontoDAO.closeDBConnection();
			// TYPE
			if (type == 1) {
				// 生成上传文件的目录
				File FileUploadDir = new File(filepath+"LO//");
				if(!FileUploadDir.exists())
					FileUploadDir.mkdir();
				FileUploadDir=new File(filepath+"LO//"+username);
				if(!FileUploadDir.exists())
					FileUploadDir.mkdir();
				filepath =filepath + "LO//"+username+"//"
						+ filename;
				// System.out.println("=====ITEM" + item);
				fileitem.write(new File(filepath));// 这里的路径你可以改成你感兴趣的地方.getRealPath("/upload")

			}
					OwlAnalyzer owl = new OwlAnalyzer(filepath, oname,
							description, type, domain);
					owl.initialOntology();
					Ontology onto = owl.getOntology();
					String uri = onto.getUri();
					String fileLocation = onto.getFilepath();
					//Connection conn = DBConnector.getConnection();
					Connection conn=Access.getConnection();
					OntologyDAO dao = new OntologyDAO(conn);
					@SuppressWarnings("unused")
					OntologyDTO RO = dao.queryByDomainRO(domain);

					OntologyDTO ddto = dao.queryByFileName(fileLocation);
					// System.out.println("<<<<<"+ddto);
					if (ddto != null) {// 本体文件名重复
						request.getSession().setAttribute("wserror",
								"该本体已存在或是该本体文件名已使用。");
						url = "/ontologyManage/registerwrong.jsp";
					} else {
						if (dao.queryByUriAndUser(uri, username) == null) {// 此命名空间未被其他人使用
								StoreOntologyToDB.storeOntologyToDB(onto,
										username);
								OntologyDTO odto = dao.queryByFileName(onto
										.getFilepath());
								dao.closeDBConnection();// close
								OntologyManagement management = new OntologyManagement();
								OntologyForMoreInfoDTO odto_more = management
										.getOntologyInfo(odto.getId());
								management.closeDBConnection();// close

								request.setAttribute("ontologydto", odto_more);
								request.getSession().setAttribute(
										"ontologydto", odto_more);
								// servlet使用RequestDispatcher到jsp页面
								url = "/ontologyManage/registersuccess.jsp";
						} else {
							request.getSession().setAttribute("wserror",
									"该命名空间已存在。");
							url = "/ontologyManage/registerwrong.jsp";
						}
					}
			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(url);
			rd.forward(request, response);
		} catch (Exception e) {
			// 可以跳转出错页面
			request.getSession().setAttribute("wserror",
					"注册本体时发生错误!");
			try {
				response.sendRedirect("ontologyManage/registerwrong.jsp");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				return;
			}
			return;
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init(){
		// Put your code here
	}
}
