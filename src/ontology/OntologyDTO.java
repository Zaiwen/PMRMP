package ontology;

public class OntologyDTO {
	private int id;
	private String administeredItemAdministrationRecord;
	private String uri;
	private String ontologyName;
	private String modelType;
	//ontologyType 表示 本体类型，0 代表 参考本体，1 代表 本地本体
	private int ontologyType;
	private String header;
	private String description;
	private String fileLocation;
	private String domain;
	private String user;
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFileLocation() {
		return fileLocation;
	}
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public OntologyDTO(){}
	public OntologyDTO(int id,String administeredItemAdministrationRecord
			,String uri,String ontologyName,String modelType,int ontologyType,String domain,String user){
		this.id = id;
		this.administeredItemAdministrationRecord = administeredItemAdministrationRecord;
		this.uri = uri;
		this.ontologyName = ontologyName;
		this.modelType = modelType;
		this.ontologyType = ontologyType;
		this.domain=domain;
		this.user=user;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAdministeredItemAdministrationRecord() {
		return administeredItemAdministrationRecord;
	}
	public void setAdministeredItemAdministrationRecord(
			String administeredItemAdministrationRecord) {
		this.administeredItemAdministrationRecord = administeredItemAdministrationRecord;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getOntologyName() {
		return ontologyName;
	}
	public void setOntologyName(String ontologyName) {
		this.ontologyName = ontologyName;
	}
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	public int getOntologyType() {
		return ontologyType;
	}
	public void setOntologyType(int ontologyType) {
		this.ontologyType = ontologyType;
	}

	public String toString(){
		return "OntologyDTO : " +
				"\n   id : "+this.id +
				"\n   administeredItemAdministrationRecord : "+this.administeredItemAdministrationRecord +
				"\n   uri : "+this.uri +
				"\n   ontologyName : "+this.ontologyName +
				"\n   modelType : "+this.modelType +
				"\n   ontologyType : "+this.ontologyType;
	}
}
