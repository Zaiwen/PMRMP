package ontology;

public class OntologyComponentDTO {
	private int id;
	private String administeredItemAdministrationRecord;
	private String namespace;
	private String sentenceIdentifier;
	//type 表示 本体构件类型，0 代表 参考本体构件，1 代表 本地本体构件
	private String type="";
	private String source;
	private String keyAtomic;
	private int canModify=0;
	private int ontologyId;

	
	public int getOntologyId() {
		return ontologyId;
	}
	public void setOntologyId(int ontologyId) {
		this.ontologyId = ontologyId;
	}
	/**
	 * @return the canModify
	 */
	public int getCanModify() {
		return canModify;
	}
	/**
	 * @param canModify the canModify to set
	 */
	public void setCanModify(int canModify) {
		this.canModify = canModify;
	}
	public String getKeyAtomic() {
		return keyAtomic;
	}
	public void setKeyAtomic(String keyAtomic) {
		this.keyAtomic = keyAtomic;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public OntologyComponentDTO(){}
	public OntologyComponentDTO(int id,String administeredItemAdministrationRecord,
			String namespace,String sentenceIdentifier,String type){
		this.id = id;
		this.administeredItemAdministrationRecord = administeredItemAdministrationRecord;
		this.namespace = namespace;
		this.sentenceIdentifier = sentenceIdentifier;
		this.type = type;
	}
	
	public String getSentenceIdentifier() {
		return sentenceIdentifier;
	}
	public void setSentenceIdentifier(String sentenceIdentifier) {
		this.sentenceIdentifier = sentenceIdentifier;
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
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String toString(){
		return "OntologyComponentDTO : " +
				"\n   id : "+this.id +
				"\n	  administeredItemAdministrationRecord : "+this.administeredItemAdministrationRecord +
				"\n	  namespace : "+this.namespace +
				"\n	  sentenceIdentifier : "+this.sentenceIdentifier +
				"\n	  type : "+this.type +
				"\n	  source : "+this.source+
				"\n	  keyAtomicId : "+this.keyAtomic+
				"\n	  canmodify : "+this.canModify ;
	}
}
