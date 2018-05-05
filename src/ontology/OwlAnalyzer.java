package ontology;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class OwlAnalyzer {
	private File file;

	private String ontologyName;

	private int ontologyType;// ro or lo

	private String modelType = "OWL";// owl or others

	private String description;

	private String domain;

	private Document document;

	private Ontology ontology;

	public OwlAnalyzer(String fileName, String ontologyName,
			String description, int ontologyType, String domain) {
		this.file = new File(fileName);
		this.ontologyName = ontologyName;
		this.description = description;
		this.ontologyType = ontologyType;
		this.domain = domain;
		initModel();
	}

	public OwlAnalyzer(File file) {

	}

	public void initModel() {
		try {
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(file);// GET XML
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化本体
	 */
	public void initialOntology() {
		Element root = document.getRootElement();
		if (root.getNodeType() == Element.ELEMENT_NODE) {
			String path = file.getAbsolutePath();
			ontology = new Ontology(ontologyName, path.substring(path
					.lastIndexOf(File.separator) + 1), description, root
					.getNamespaceForPrefix("").getURI(), modelType,
					ontologyType, domain);
		}

		@SuppressWarnings("rawtypes")
		List sons = root.elements();
		for (int i = 0; i < sons.size(); i++) {
			Element son = (Element) sons.get(i);
			//System.out.println(son.getName());
			if (son.getName().equals("Ontology")) {
				// System.out.println(son.asXML());
				ontology.setHeader(son.asXML());
			} else {
				initialOntologyComponent(son, i);
			}
		}
	}

	/**
	 * 初始化本体句子
	 * 
	 * @param element
	 * @param index
	 */
	private void initialOntologyComponent(Element element, int index) {
		OntologyComponent component = new OntologyComponent(ontology, ontology
				.getUri(), element.asXML(), index);
		initialOntologyAtom(component, element);
		ontology.getConsistsOf().add(component);
	}

	/**
	 * 初始化本体原子结构
	 * 
	 * @param component
	 * @param element
	 */
	private void initialOntologyAtom(OntologyComponent component,
			Element element) {
		// 结点名称成为OntologyAtomicConstruct
		component.setType(element.getName());
		switch (WordType.getNodeType(element.getName())) {
		case WordType.INGOREDSCHEMA:// 该忽略的结点如label
			if (element.getNodeType() == Element.ELEMENT_NODE) {
				@SuppressWarnings("rawtypes")
				Iterator sons = element.elementIterator();
				while (sons.hasNext()) {
					Element son = (Element) sons.next();
					initialOntologyAtom(component, son);
				}
			}
			return;
		case WordType.DEFINEDSCHEMA:
			break;
		case WordType.SELFTDEFINED:// 自定义的结点
			component.setType("Individual");
			OntologyAtomicConstruct atomatic = component
					.getAtomBySymbol(element.getName());
			if (atomatic == null) {
				atomatic = new OntologyAtomicConstruct(element.getName(),
						ontology.getUri(), component.getOntology().getUses()
								.size());
				component.addAtomaticToAll(atomatic);
			} else {
				component.addAtomaticToComponent(atomatic);
			}
			break;
		}

		// 结点属性成为OntologyAtomicConstruct, 并成为关键原子结构
		@SuppressWarnings("rawtypes")
		List attrs = element.attributes();
		if (attrs.size() > 0) {
			OntologyAtomicConstruct atomatic = null;
			String s = ((Attribute) attrs.get(0)).getValue();
			//System.out.println("=="+ WordType.getAttrType(s, ontology.getUri()) + " " + s);
			switch (WordType.getAttrType(s, ontology.getUri())) {
			case WordType.INNER_DEF:// 内部定义类型
				atomatic = new OntologyAtomicConstruct(s, ontology.getUri(),
						component.getOntology().getUses().size());
				break;
			case WordType.INNER_REF:// 内部定义类型的引用
				if (s.startsWith("#")) {
					s = s.substring(1);
					atomatic = new OntologyAtomicConstruct(s,
							ontology.getUri(), component.getOntology()
									.getUses().size());
				} else {
					s = s.substring(ontology.getUri().length());
					atomatic = new OntologyAtomicConstruct(s,
							ontology.getUri(), component.getOntology()
									.getUses().size());
				}
				break;
			case WordType.OUTER:// 外部引用类型
				String prefix = s.substring(0, s.indexOf('#') + 1);
				String name = s.substring(s.indexOf('#') + 1);
				atomatic = new OntologyAtomicConstruct(name, prefix, component
						.getOntology().getUses().size());
				break;
			case WordType.INGOREDATTR:// 要忽略的引用类型，如int
				break;
			}
			OntologyAtomicConstruct temp = component.getAtomBySymbol(s);
			if (temp == null) {// 不存在此原子结构
				if (atomatic != null) {
					component.addKeyAtomaticToAll(atomatic);
				}
			} else {
				atomatic = temp;
				component.addKeyAtomaticToComponent(atomatic);
			}
		}

		// 结点内容
		if (element.isTextOnly() && !element.getText().equals("")) {
			String s = element.getText();
			OntologyAtomicConstruct temp = component.getAtomBySymbol(s);
			OntologyAtomicConstruct atomatic = new OntologyAtomicConstruct(
					element.getText(), ontology.getUri(), component
							.getOntology().getUses().size());
			if (temp == null) {// 不存在此原子结构
				if (atomatic != null) {
					component.addAtomaticToAll(atomatic);
				}
			} else {
				atomatic = temp;
				component.addAtomaticToComponent(atomatic);
			}
		}
		// 下一层结点
		if (element.getNodeType() == Element.ELEMENT_NODE) {
			@SuppressWarnings("rawtypes")
			Iterator sons = element.elementIterator();
			while (sons.hasNext()) {
				Element son = (Element) sons.next();
				initialOntologyAtom(component, son);
			}
		}
	}

	public OntologyComponent parseXml(String xml) {
		SAXReader saxReader = new SAXReader();
		try {
			document = saxReader.read(new ByteArrayInputStream(new String(xml
					.getBytes(), "utf-8").getBytes()));
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		OntologyComponent component = new OntologyComponent(ontology, ontology
				.getUri(), document.getRootElement().asXML(), ontology
				.getConsistsOf().size() + 1);
		initialOntologyAtom(component, document.getRootElement());
		return component;
	}

	/**
	 * @return the ontology
	 */
	public Ontology getOntology() {
		return ontology;
	}

	/**
	 * 检测OWL文件的合法性 描述：文件中如果存在<owl:Class rdf:ID="EntityConcept"/>和 <owl:Class
	 * rdf:ID="OperationConcept"/> 则返回为true 否则，为false
	 * 
	 * @param file
	 * @return
	 */
	public static boolean OwlIsLegal(String file) {

		SAXReader sr = new SAXReader();
		Document doc;
		boolean isEntity = false;
		boolean isOperation = false;
		String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";

		try {
			doc = sr.read(file);
			Element root = doc.getRootElement();
			@SuppressWarnings("rawtypes")
			List sons = root.elements();
			for (int i = 0; i < sons.size(); i++) {
				Element son = (Element) sons.get(i);

				if (son.getName().equals("Class")) {					
					@SuppressWarnings("rawtypes")
					Iterator attrss = son.attributeIterator();
					if (attrss.hasNext()
							&& son.attribute(0).getName().equals("ID")) {
//						 System.out.println("<<<<<<<<<<" + son.getName()
//						 + ">>>>>>>>>"+son.attribute(0).getValue());
						String value = son.attribute("ID").getValue();
						String nsURI = son.attribute("ID").getNamespaceURI();
						if (value.equals("EntityConcept") && nsURI.equals(rdf)) {
							isEntity = true;
						}
						if (value.equals("OperationConcept")
								&& nsURI.equals(rdf)) {
							isOperation = true;
						}
					}

					if (isEntity == true && isOperation == true) {
						return true;
					} else {
						@SuppressWarnings("rawtypes")
						Iterator sons0 = son.elementIterator();
						while (sons0.hasNext()) {
							Element son0 = (Element) sons0.next();

							if (son0.getName().equals("subClassOf")) {
								@SuppressWarnings("rawtypes")
								Iterator sons1 = son0.elementIterator();
								while (sons1.hasNext()) {
									Element son1 = (Element) sons1.next();
									if (son1.getName().equals("Class")) {
										@SuppressWarnings("rawtypes")
										Iterator attrs = son1
												.attributeIterator();
										while (attrs.hasNext()) {
											Attribute att = (Attribute) attrs
													.next();
											if (att.getName().equals("ID")
													&& att.getNamespaceURI()
															.equals(rdf)) {
												if (att.getValue().equals(
														"EntityConcept")) {
													isEntity = true;
												}
												if (att.getValue().equals(
														"OperationConcept")) {
													isOperation = true;
												}
												if (isEntity == true
														&& isOperation == true) {													
													return true;
												}
											}
										}
									}
								}
							}
						}
					}

				}
			}
			return false;

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}
}
