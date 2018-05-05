package Json2Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sklse.owlseditor.json.*;

import DataStructure.*;
import SimilarityCalculation.Similarity;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import databaseaccess.Access;
import finalvariable.BasicPathVariable;

public class transferJson2Graph extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Vertice> vertices;
	private List<Edge> edges;
	private JModel model;
	private int arraySize;
	
	public transferJson2Graph() {
		setSimilarity();
	}

	public boolean isPerform(String s) {

		JPerform[] perform = model.getPerform();

		for (int i = 0; i < perform.length; i++) {
			if (perform[i].getID().equals(s)) {
				return true;
			}
		}
		return false;

	}

	public boolean isSequence(String s) {

		JSequence[] sequence = model.getSequence();

		for (int i = 0; i < sequence.length; i++) {
			if (sequence[i].getID().equals(s)) {
				return true;
			}
		}
		return false;

	}

	public boolean isSplitJoin(String s) {

		JSplitJoin[] splitjoin = model.getSplitJoin();

		for (int i = 0; i < splitjoin.length; i++) {
			if (splitjoin[i].getID().equals(s)) {
				return true;
			}
		}
		return false;

	}

	public int transferSplitJoin(int startPoint, JSplitJoin splitjoin) {

		String[] components = splitjoin.getComponents();
		int u;
		int v;

		arraySize++;
		vertices.add(new Vertice(arraySize, Type.ParallelGateway, splitjoin
				.getID() + ".start"));
		edges.add(new Edge(startPoint, arraySize));
		u = arraySize;

		arraySize++;
		vertices.add(new Vertice(arraySize, Type.ParallelGateway, splitjoin
				.getID() + ".end"));

		v = arraySize;

		for (int i = 0; i < components.length; i++) {
			if (isPerform(components[i])) {

				v++;
				JPerform perform = (JPerform) model.get(components[i]);
				arraySize++;
				vertices.add(new Vertice(arraySize, Type.Task, perform
						.getProcess()));
				edges.add(new Edge(u, arraySize));

			} else if (isSequence(components[i])) {
				v = transferSequence(u, (JSequence) model.get(components[i]));
			} else if (isSplitJoin(components[i])) {
				v = transferSplitJoin(u, (JSplitJoin) model.get(components[i]));
			}

			edges.add(new Edge(v, u + 1));

		}

		return u + 1;

	}

	public int transferSequence(int startPoint, JSequence sequence) {

		JPerform perform;
		String[] components = sequence.getComponents();
		int u = startPoint;
		int v = arraySize;

		for (int i = 0; i < components.length; i++) {
			if (isPerform(components[i])) {
				v++;
				perform = (JPerform) model.get(components[i]);
				arraySize++;
				vertices.add(new Vertice(v, Type.Task, perform.getProcess()));
				edges.add(new Edge(u, v));
			} else if (isSequence(components[i])) {
				v = transferSequence(u, (JSequence) model.get(components[i]));
			} else if (isSplitJoin(components[i])) {
				v = transferSplitJoin(u, (JSplitJoin) model.get(components[i]));
			}
			u = v;
			v = arraySize;
		}
		return v;

	}

	public BPG creatGraph(String url) throws JsonSyntaxException,
			JsonIOException, FileNotFoundException {

		Gson gson = new Gson();
		File file;
		int startPoint;
		int v;
		int finalPoint;
		JCompositeProcess composite;
		Object object;

		file = new File(url);
		model = gson.fromJson(new FileReader(file), JModel.class);
		startPoint = 0;
		v = startPoint;
		arraySize = 0;
		vertices = new ArrayList<Vertice>();
		edges = new ArrayList<Edge>();
		composite = model.getCompositeProcess();
		object = model.get(composite.getComposeOf());

		vertices.add(new Vertice(startPoint, Type.StartEvent, "Start"));

		if (isSequence(composite.getComposeOf())) {
			v = transferSequence(v, (JSequence) object);
		} else if (isSplitJoin(composite.getComposeOf())) {
			v = transferSplitJoin(v, (JSplitJoin) object);
		}

		finalPoint = v + 1;
		vertices.add(new Vertice(finalPoint, Type.EndEvent, "End"));
		edges.add(new Edge(v, finalPoint));
		return new BPG(edges, vertices);

	}
	
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void init(){
		// Put your code here
	}
	
	public  void setSimilarity(){
		int count=0;
		String processname1=null;
		String processuser1=null;
		String processname2=null;
		String processuser2=null;
		Access ac=new Access();
		Access ac2=new Access();
		try{
		ac.connDB("process");
		ac2.connDB("similarity");
		String sql1="select count(*) from processinfo where similarity=0";
		ResultSet res=ac.executeSelectSql(sql1);
		if(res.next()){
			count=res.getInt(1);
		}
		if(count>=2){
		for(int i=0;i<count;++i){
			String sql2="select name,user from processinfo where similarity=0";
			res=ac.executeSelectSql(sql2);
			if(res.next()){
				processname1=res.getString(1);
				processuser1=res.getString(2);
				if(i==count-1){
					String sql6="update processinfo set similarity=1 where name='"+processname1+"' and  user='"+processuser1+"'";
					ac.executeUpdateSql(sql6);
					break;
				}
				String sql3="update processinfo set similarity=1 where name='"+processname1+"' and  user='"+processuser1+"'";
			    ac.executeUpdateSql(sql3);
				String sql4="select name,user from processinfo where similarity=0";
				res=ac.executeSelectSql(sql4);
				while(res.next()){
						processname2=res.getString(1);
						processuser2=res.getString(2);
						String url1 = BasicPathVariable.processPath+processuser1+"//"+processname1+"//process.json";
						String url2 = BasicPathVariable.processPath+processuser2+"//"+processname2+"//process.json";
						File file1=new File(url1);
						File file2=new File(url2);
						if(file1.exists()&&file2.exists()){
						BPG bpg1, bpg2;
						bpg1 = creatGraph(url1);
						bpg2 = creatGraph(url2);
						double ns = Similarity.NodeMatchingSimilarity(bpg1, bpg2);
						double ss = Similarity.StructuralSimilarity(bpg1, bpg2);
						double bs = Similarity.BehavioralSimilarity(bpg1, bpg2);
						ns=(double)((int)(ns*10000.0+5)/10)/1000.0;
						ss=(double)((int)(ss*10000.0+5)/10)/1000.0;
						bs=(double)((int)(bs*10000.0+5)/10)/1000.0;
						String sql5="insert into similarityinfo values('"+processname1+"','"+processuser1+"','"+processname2+"','"+processuser2+"',"+ns+","+ss+","+bs+")";
						ac2.executeUpdateSql(sql5);
						}
					}
			}
			
		}
		}
		ac.closeDB();
		ac2.closeDB();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void main(String args[]){
		new transferJson2Graph();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
		String url1 = "/Users/kekebluesky/Documents/wc owls/Campus-V2.0/process.json";
		String url2 = "/Users/kekebluesky/Documents/wc owls/City-V2.0/process.json";
		/**
		String process1=Utf8code.changeCode(request.getParameter("processname1"));
		String userprocess1=Utf8code.changeCode(request.getParameter("processuser1"));
		String process2=Utf8code.changeCode(request.getParameter("processname2"));
		String userprocess2=Utf8code.changeCode(request.getParameter("processuser2"));
		
		String url1=BasicPathVariable.processPath+userprocess1+"//"+process1+"//process.json";
		String url2=BasicPathVariable.processPath+userprocess2+"//"+process2+"//process.json";
		*/
		BPG bpg1, bpg2;
		bpg1 = creatGraph(url1);
		bpg2 = creatGraph(url2);

		double ns = Similarity.NodeMatchingSimilarity(bpg1, bpg2);
		System.out.println("The node matching similarity of two graphs is "
				+ ns);

		double ss = Similarity.StructuralSimilarity(bpg1, bpg2);
		System.out.println("The structural similarity of two graphs is " + ss);

		double bs = Similarity.BehavioralSimilarity(bpg1, bpg2);
		System.out.println("The behavioral similarity of two graph is " + bs);
		//将相似性情况写入数据库
		response.sendRedirect("/BPEP/userManage/userAdmin.jsp");
		return;
		} catch (Exception e) {
			request.getSession().setAttribute("similarity", "Error!");
			try {
				response.sendRedirect("/BPEP/userManage/userAdmin.jsp");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				return;
			}
			return;
		}
	}

}
