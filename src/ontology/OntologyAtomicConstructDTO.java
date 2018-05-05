package ontology;

public class OntologyAtomicConstructDTO {
	private int id;
	private String administeredItemAdministrationRecord;
	private String namespace;
	private String nonLogicalSymbol;
	//type 表示 本体原子构件类型，0 代表 参考本体原子构件，1 代表 本地本体原子构件
	private int type;
	private int ontologyComponentId;

	public int getOntologyComponentId() {
		return ontologyComponentId;
	}
	public void setOntologyComponentId(int ontologyComponentId) {
		this.ontologyComponentId = ontologyComponentId;
	}
	public OntologyAtomicConstructDTO(){}
	public OntologyAtomicConstructDTO(int id,String administeredItemAdministrationRecord,
			String namespace,String nonLogicalSymbol,int type){
		this.id = id;
		this.administeredItemAdministrationRecord = administeredItemAdministrationRecord;
		this.namespace = namespace;
		this.nonLogicalSymbol = nonLogicalSymbol;
		this.type = type;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getNonLogicalSymbol() {
		return nonLogicalSymbol;
	}
	public void setNonLogicalSymbol(String nonLogicalSymbol) {
		this.nonLogicalSymbol = nonLogicalSymbol;
	}
	public String toString(){
		return "OntologyAtomicConstructDTO : " +
				"\n   id : "+this.id +
				"\n   administeredItemAdministrationRecord : "+this.administeredItemAdministrationRecord +
				"\n   namespace : "+this.namespace +
				"\n   nonLogicalSymbol : "+this.nonLogicalSymbol +
				"\n   type : "+this.type ;
	}
	public boolean equals(Object obj){
		boolean ret = false;
		if(obj instanceof OntologyAtomicConstructDTO){
			if(((OntologyAtomicConstructDTO)obj).getId() == this.id){
				ret = true;
			}
		}
		return ret;
	}
	public int hashCode(){
		return this.getId();
	}
}
