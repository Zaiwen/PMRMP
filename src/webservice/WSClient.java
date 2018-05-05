package webservice;

import java.util.Iterator;
import java.util.List;
import org.mindswap.wsdl.ext.*;
import databaseaccess.Access;

public class WSClient {

	public static void testWSDL4J(String URL, String user, String domain) throws Exception{
		int i = 0;
		int j = 0;
		int k = 0;
		int keyindex;
		WSDLService service=WSDLService.createService(URL);
			Access acce = new Access();
			acce.connDB("bpep");
			String sql_se = "INSERT INTO serviceinfo "
					+ "(name,wsdllocation,domain,user) values" + "('"
					+ service.getName() + "','"
					+ service.getFileURI() + "','" + domain + "','"
					+ user + "');";
			keyindex = acce.executeUpdateSql2(sql_se);
			Iterator<WSDLOperation> iter = service.getOperations().iterator();
			// System.out.println(serviceInfo.getName()+"的URL="+serviceInfo.getWsdllocation());
			// System.out.println(serviceInfo.getName());
			while (iter.hasNext()) {
				i++;
				WSDLOperation oper = (WSDLOperation) iter.next();
				String sql_op = "INSERT INTO operationinfo "
						+ "(wsid,opid,NameSpaceURI,inputMessageName,outputMessageName) values"
						+ "(" + keyindex + "," + (i - 1) + ",'"
						+ service.getNamespaceURI() + "','"
						+ oper.getInputMessageName().split("#")[1] + "','"
						+ oper.getOutputMessageName().split("#")[1] + "');";
				acce.executeUpdateSql(sql_op);
				// System.out.println(oper.getNamespaceURI());
				// System.out.println(oper.getInputMessageName());
				// System.out.println(oper.getOutputMessageName());
				List<?> inps = oper.getInputs();
				List<?> outps = oper.getOutputs();
				if (inps.size() == 0) {
					// System.out.println("执行此操作不需要输入任何参数!");
				} else {
					// System.out.println("此操作所需的输入参数为:");
					for (Iterator<?> iterator1 = inps.iterator(); iterator1.hasNext();) {
						j++;
						WSDLParameter element = (WSDLParameter) iterator1.next();
						
						String sql_input = "INSERT INTO inputinfo "
								+ "(wsid,opid,name,kind, inid) values" + "("
								+ keyindex + "," + (i - 1) + ",'"
								+ element.getName().split("#")[1] + "','" + element.getType().getLocalPart() + "','"
								+   (j-1)+ "');";
						acce.executeUpdateSql2(sql_input);
					}
				}
				if (outps.size() == 0) {
					// System.out.println("执行此操作不返回任何参数!");
				} else {
					// System.out.println("此操作的输出参数为:");
					for (Iterator<?> iterator2 = outps.iterator(); iterator2
							.hasNext();) {
						k++;
						WSDLParameter element = (WSDLParameter) iterator2
								.next();
						String sql_output = "INSERT INTO outputinfo "
								+ "(wsid,opid,name,kind, outid) values" + "("
								+ keyindex + "," + (i - 1) + ",'"
								+ element.getName().split("#")[1] + "','" + element.getType().getLocalPart()+ "','"
								+ (k-1) +"');";
						acce.executeUpdateSql2(sql_output);
					}
				}
			}
			acce.closeDB();
	}
}
