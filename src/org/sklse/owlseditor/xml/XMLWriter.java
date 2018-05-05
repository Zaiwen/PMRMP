package org.sklse.owlseditor.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.sklse.owlseditor.json.JModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class XMLWriter {

	private XPlugin plugins = null;

	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder;
	Document document;

	String fileName = null;

	public XMLWriter(XPlugin plugins, String file) {

		this.plugins = plugins;

		factory = DocumentBuilderFactory.newInstance();

		try {
			builder = factory.newDocumentBuilder();
			this.document = builder.newDocument();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.fileName = file;
		createXml(plugins);
	}

	public void createXml(XPlugin plugins) {

		Element pe = this.document.createElement("Pluggable_Extension");
		this.document.appendChild(pe);

		// name属性
		Element Name = this.document.createElement("name");
		if (plugins.getName() != null) {
			Name.appendChild(this.document.createTextNode(plugins.getName()));

		}
		pe.appendChild(Name);

		// Extensional_Point属性
		Element EP = this.document.createElement("Extensional_Point");
		if (plugins.getExtensional_Point() != null) {
			Element vq = this.document.createElement("Variable_Query");
					
						
						for(int i=0;i<this.plugins.getExtensional_Point().getVariable_Query().getInput().length;i++){
						
						Element Input = this.document.createElement("Input_"+i);
						Input
								.appendChild(this.document
										.createTextNode(plugins
												.getExtensional_Point()
												.getVariable_Query().getInput()[i]));
						vq.appendChild(Input);
					}
					for(int i=0;i<this.plugins.getExtensional_Point().getVariable_Query().getOutput().length;i++){
						Element Output = this.document.createElement("Output_"+i);
						Output
								.appendChild(this.document
										.createTextNode(plugins
												.getExtensional_Point()
												.getVariable_Query()
												.getOutput()[i]));
						vq.appendChild(Output);
					}

					EP.appendChild(vq);

				
				Element sd = this.document.createElement("SPARQL_DL");
				for (int k = 0; k < plugins.getExtensional_Point()
						.getSPARQL_DL().length; k++) {

					Element elememt = this.document.createElement("spardl" + k);
					elememt.appendChild(this.document.createTextNode(plugins
							.getExtensional_Point().getSPARQL_DL()[k]));
					sd.appendChild(elememt);

				}
				EP.appendChild(sd);

			}
		
		pe.appendChild(EP);

		// Extensional_Fragment属性
		Element EF = this.document.createElement("Extensional_Fragment");
		
			if (plugins.getExtensional_Fragment().getExtension_pattern() != null) {
				Element ep = this.document.createElement("Extension_pattern");
				ep.appendChild(this.document.createTextNode(plugins
						.getExtensional_Fragment().getExtension_pattern()));
				EF.appendChild(ep);
			}

			for (int k = 0; k < plugins.getExtensional_Fragment().getInput().length; k++) {
				Element input = this.document.createElement("Input");
				if (plugins.getExtensional_Fragment().getInput()[k]
						.getName() != null) {
					Element name = this.document.createElement("Name");
					name.appendChild(this.document.createTextNode(plugins
							.getExtensional_Fragment().getInput()[k]
							.getName()));
					input.appendChild(name);
				}
				if (plugins.getExtensional_Fragment().getInput()[k]
						.getBinding() != null) {
					Element From_variable = this.document
							.createElement("Binding");
					From_variable
							.appendChild(this.document.createTextNode(plugins
									.getExtensional_Fragment().getInput()[k]
									.getBinding()));
					input.appendChild(From_variable);
				}
				EF.appendChild(input);
			}
			for (int k = 0; k < plugins.getExtensional_Fragment()
					.getOutput().length; k++) {
				Element output = this.document.createElement("Output");
				if (plugins.getExtensional_Fragment().getOutput()[k]
						.getName() != null) {
					Element name = this.document.createElement("Name");
					name.appendChild(this.document.createTextNode(plugins
							.getExtensional_Fragment().getOutput()[k]
							.getName()));
					output.appendChild(name);
				}
				if (plugins.getExtensional_Fragment().getOutput()[k]
						.getBinding() != null) {
					Element To_variable = this.document
							.createElement("Binding");
					To_variable
							.appendChild(this.document
									.createTextNode(plugins
											.getExtensional_Fragment()
											.getOutput()[k].getBinding()));
					output.appendChild(To_variable);
				}
				EF.appendChild(output);
			}
		
				Element Wsdl_Grounding = this.document
						.createElement("Wsdl_Grounding");
				if (this.plugins.getExtensional_Fragment().getWsdl_Grounding().getWsdl_Location()!= null) {
					Element Wsdl_Location = this.document
							.createElement("Wsdl_Location");
					Wsdl_Location
							.appendChild(this.document.createTextNode(plugins
									.getExtensional_Fragment()
									.getWsdl_Grounding().getWsdl_Location()));
					Wsdl_Grounding.appendChild(Wsdl_Location);
				}
				if (plugins.getExtensional_Fragment().getWsdl_Grounding()
						.getOperation() != null) {
					Element Operation = this.document
							.createElement("Operation");
					Operation
							.appendChild(this.document.createTextNode(plugins
									.getExtensional_Fragment()
									.getWsdl_Grounding().getOperation()));
					Wsdl_Grounding.appendChild(Operation);
				}
				if (plugins.getExtensional_Fragment().getWsdl_Grounding()
						.getPortType() != null) {
					Element PortType = this.document.createElement("portType");
					PortType
							.appendChild(this.document.createTextNode(plugins
									.getExtensional_Fragment()
									.getWsdl_Grounding().getPortType()));
					Wsdl_Grounding.appendChild(PortType);
				}
				if (plugins.getExtensional_Fragment().getWsdl_Grounding()
						.getInputMessageName() != null) {
					Element InputMessageName = this.document
							.createElement("InputMessageName");
					InputMessageName
							.appendChild(this.document.createTextNode(plugins
									.getExtensional_Fragment()
									.getWsdl_Grounding()
									.getInputMessageName()));
					Wsdl_Grounding.appendChild(InputMessageName);
				}
				if (plugins.getExtensional_Fragment().getWsdl_Grounding()
						.getOutputMessageName() != null) {
					Element OutputMessageName = this.document
							.createElement("OutputMessageName");
					OutputMessageName
							.appendChild(this.document.createTextNode(plugins
									.getExtensional_Fragment()
									.getWsdl_Grounding()
									.getOutputMessageName()));
					Wsdl_Grounding.appendChild(OutputMessageName);
				

				EF.appendChild(Wsdl_Grounding);
			}

		
		pe.appendChild(EF);

		writeXML(this.document, fileName);

	}

	public void writeXML(Document doc, String file) {
		try {
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty("indent", "yes");
			t.transform(new DOMSource(doc), new StreamResult(
					new FileOutputStream(file)));

		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Gson gson = new Gson();
		File file = new File("./json/123.json");
		XPlugin plugins = null;
		try {
			plugins = gson.fromJson(new FileReader(file), XPlugin.class);

		} catch (JsonSyntaxException e) {

			e.printStackTrace();
		} catch (JsonIOException e) {

			e.printStackTrace();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		new XMLWriter(plugins, "String");
	}
}
