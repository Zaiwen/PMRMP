package ontology;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import databaseaccess.Access;

import finalvariable.BasicPathVariable;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Detail_Ontology extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init() {
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
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
	    PrintWriter out = response.getWriter();		
		
		String uri = request.getParameter("uri");
		String type = request.getParameter("type");
		

		if (type.equals("1")) {
			display_Ontology(Integer.parseInt(uri),out);
		} else if (type.equals("2")) {
			display_OntologyComponent(Integer.parseInt(uri),out);
		} else if (type.equals("3")) {
			display_OntologyAtomicComponent(Integer.parseInt(uri),out);
		}

		out.flush();
		out.close();

	}

	public void display_Ontology(int id, PrintWriter out) throws IOException {
		String filepath=BasicPathVariable.ontologyTable;
		Configuration cfg = new Configuration();
		cfg.setDefaultEncoding("utf-8");
		try {
			cfg.setDirectoryForTemplateLoading(new File(filepath+"template//"));
		} catch (IOException e) {
			out.print("Error!");
		}
		
		Template temp = cfg.getTemplate("ontology_table.ftl");
		HashMap contents = new HashMap();
		//Connection conn = DBConnector.getConnection();
		Connection conn=Access.getConnection();
		OntologyDAO odao = new OntologyDAO(conn);
		OntologyDTO odto = odao.queryById(id);
		OntologyManagement management = new OntologyManagement();
		OntologyForMoreInfoDTO odto_more = management.getOntologyInfo(odto
				.getId());
		contents.put("id", odto_more.getId());
		contents.put("content", odto_more.getUri());
		contents.put("nm", odto_more.getOntologyName());
		contents.put("modelType", odto_more.getModelType());
		String ty = odto_more.getOntologyType() == 0 ? "参考本体/reference ontology" : "本地本体/local ontology";
		contents.put("type", ty);
		
		List coms=new ArrayList();
		OntologyComponentDAO ao=new OntologyComponentDAO();
		for (OntologyComponentForMoreInfoDTO comp : odto_more
				.getComponents()) {
			HashMap h = new HashMap();			
            OntologyComponentDTO ot = ao.queryById(comp.getId());
            String nonlogicalS =ot.getKeyAtomic();	
//            String nonlogicalS="";
//            if(keyid!=0){
//                OntologyAtomicConstructDAO dd = new OntologyAtomicConstructDAO();	
//                OntologyAtomicConstructDTO ddd = dd.queryById(keyid);
//                nonlogicalS=ddd.getNonLogicalSymbol();
//                dd.closeDBConnection();
//            }else{
//                nonlogicalS="UNKNOWN";
//            }
			h.put("com", "c" + comp.getId()+": "+nonlogicalS);
			coms.add(h);
		}
		contents.put("components", coms);
		try {
			temp.process(contents, out);
		} catch (TemplateException e) {
			out.print("Error!");
		}
		odao.closeDBConnection();
		ao.closeDBConnection();
		management.closeDBConnection();
		
	}

	public void display_OntologyComponent(int id, PrintWriter out) throws IOException {
		String filepath=BasicPathVariable.ontologyTable;
		Configuration cfg = new Configuration();
		cfg.setDefaultEncoding("utf-8");
		try {
			cfg.setDirectoryForTemplateLoading(new File(filepath+"template//"));
		} catch (IOException e) {
			out.print("Error!");
		}
		
		Template temp = cfg.getTemplate("ontology_table_com.ftl");
		HashMap contents = new HashMap();
		//Connection conn = DBConnector.getConnection();
		Connection conn=Access.getConnection();
		OntologyComponentDAO odao = new OntologyComponentDAO(conn);
		OntologyComponentDTO odto_more = odao.queryById(id);
		//System.out.println(odto_more.getJoinid());//
		contents.put("component_id", odto_more.getId());
		contents.put("namespace", odto_more.getNamespace());
		contents.put("identifier", odto_more.getSentenceIdentifier());
		String type = odto_more.getType();
//		System.out.println(type);
		contents.put("component_type", type);
		List coms=new ArrayList();
		
		UsesDAO udao = new UsesDAO(conn);
		List<UsesDTO> dtos = udao.queryAllByComponentId(id);
		OntologyAtomicConstructDAO oacdao = new OntologyAtomicConstructDAO(conn);
		for (UsesDTO uu : dtos) {
			HashMap h = new HashMap();
			int te=uu.getOntologyAtomicConstructId();
			h.put("com", "ac" + te+": "+oacdao.queryById(te).getNonLogicalSymbol());
			coms.add(h);
		}
		contents.put("atomics", coms);
		try {
			temp.process(contents, out);
		} catch (TemplateException e) {
			out.print("Error!");
		}
		odao.closeDBConnection();
		udao.closeDBConnection();
		oacdao.closeDBConnection();
		
		//System.out.println("END");
	}

	public void display_OntologyAtomicComponent(int id, PrintWriter out) throws IOException {
		String filepath=BasicPathVariable.ontologyTable;
		Configuration cfg = new Configuration();
		cfg.setDefaultEncoding("utf-8");
		try {
			cfg.setDirectoryForTemplateLoading(new File(filepath+"template//"));
		} catch (IOException e) {
			out.print("Error!");
		}
		
		Template temp = cfg.getTemplate("ontology_table_atomic.ftl");
		HashMap contents = new HashMap();
		OntologyAtomicConstructDAO odao = new OntologyAtomicConstructDAO();
		OntologyAtomicConstructDTO odto_more = odao.queryById(id);
		contents.put("atomic_id", odto_more.getId());
		contents.put("namespace", odto_more.getNamespace());
		contents.put("logicalSymbol", odto_more.getNonLogicalSymbol());
			
		try {
			temp.process(contents, out);
		} catch (TemplateException e) {
			out.print("Error!");
		}		
		odao.closeDBConnection();
		//System.out.println("END");
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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
