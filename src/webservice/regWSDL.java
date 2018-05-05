package webservice;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class regWSDL extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public regWSDL() {
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
		try{
		response.setContentType("text/html");
		String wsdlURL=request.getParameter("wsfileurl");
		wsdlURL=wsdlURL.trim();
		String user =(String)request.getSession().getAttribute("Adminusername");
		user=user.trim();
		System.out.println("The ws to be registered is" + wsdlURL);
		String domain = request.getParameter("wsdomain");
		domain=domain.trim();
		if(wsdlURL.length()<1){
			request.getSession().setAttribute("wserror",
					"请输入正确的wsdl文件地址!");
			response.sendRedirect("wsManage/registerwrong.jsp");
			return;
		}else{
		            WSClient.testWSDL4J(wsdlURL,user,domain);
		            response.sendRedirect("userManage/userAdmin.jsp");
		            return;
		}
		}catch(Exception e){
			request.getSession().setAttribute("wserror",
					"注册wsdl时出现异常!");
			System.out.println(e);
			try {
				response.sendRedirect("wsManage/registerwrong.jsp");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				return;
			}
			return;
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response){

		doPost(request, response);
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
