package ontology;

import java.util.ArrayList;
import java.util.List;

public class Ontology {

	public static final int RO=0;
	public static final int LO=1;
	private int id;
	private String administered_item_administration_record;
	private String uri;
	private String ontologyName;
	private int ontologyType;//RO or LO
	private String modelType;//OWL or KF...
	private String description;
	private String domain;
	private String filepath;
	private List<OntologyComponent> consistsOf = new ArrayList<OntologyComponent>();
	private List<OntologyAtomicConstruct> uses = new ArrayList<OntologyAtomicConstruct>();

	private String header;

	/**
	 * @param administered_item_administration_record
	 * @param uri
	 * @param ontologyName
	 * @param modelType
	 * @param consistsOf
	 */

	public Ontology(String ontologyName, String filepath, String description,
			String uri, String modelType, int ontologyType,	 String domain) {
		this.ontologyName = ontologyName;
		this.filepath = filepath;
		this.description = description;
		this.uri = uri;
		this.modelType = modelType;
		this.ontologyType=ontologyType;
		this.domain=domain;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id=id;
	}

	/**
	 * @return the administered_item_administration_record
	 */
	public String getAdministered_item_administration_record() {
		return administered_item_administration_record;
	}

	/**
	 * @param administered_item_administration_record
	 *            the administered_item_administration_record to set
	 */
	public void setAdministered_item_administration_record(
			String administered_item_administration_record) {
		this.administered_item_administration_record = administered_item_administration_record;
	}

	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uri
	 *            the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @return the ontologyName
	 */
	public String getOntologyName() {
		return ontologyName;
	}

	/**
	 * @param ontologyName
	 *            the ontologyName to set
	 */
	public void setOntologyName(String ontologyName) {
		this.ontologyName = ontologyName;
	}

	/**
	 * @return the modelType
	 */
	public String getModelType() {
		return modelType;
	}

	/**
	 * @param modelType
	 *            the modelType to set
	 */
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	/**
	 * @return the consistsOf
	 */
	public List<OntologyComponent> getConsistsOf() {
		return consistsOf;
	}

	/**
	 * @param consistsOf
	 *            the consistsOf to set
	 */
	public void setConsistsOf(List<OntologyComponent> consistsOf) {
		this.consistsOf = consistsOf;
	}

	/**
	 * @return the uses
	 */
	public List<OntologyAtomicConstruct> getUses() {
		return uses;
	}

	/**
	 * @param uses
	 *            the uses to set
	 */
	public void setUses(List<OntologyAtomicConstruct> uses) {
		this.uses = uses;
	}

	public String toString() {
		return "Ontology:" + uri;
	}

	public String getXML() {
		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\"?>\n");
		xml
				.append("<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n");
		xml.append(header + "\n");
		for (OntologyComponent component : consistsOf) {
			xml.append(component.getXml() + "\n");
		}
		xml.append("</rdf:RDF>");
		return xml.toString();
	}

	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * @param header
	 *            the header to set
	 */
	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the filepath
	 */
	public String getFilepath() {
		return filepath;
	}

	/**
	 * @param filepath
	 *            the filepath to set
	 */
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	/**
	 * @return the ontologyType
	 */
	public int getOntologyType() {
		return ontologyType;
	}

	/**
	 * @param ontologyType the ontologyType to set
	 */
	public void setOntologyType(int ontologyType) {
		this.ontologyType = ontologyType;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
}

