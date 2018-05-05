package ontology;

public class ConsistOfDTO {
	private int ontologyId;
	private int ontologyComponentId;
	public int getOntologyId() {
		return ontologyId;
	}
	public void setOntologyId(int ontologyId) {
		this.ontologyId = ontologyId;
	}
	public int getOntologyComponentId() {
		return ontologyComponentId;
	}
	public void setOntologyComponentId(int ontologyComponentId) {
		this.ontologyComponentId = ontologyComponentId;
	}
	public String toString(){
		return "ConsistOfDTO : " +
				"\n   ontologyId : "+this.ontologyId +
				"\n   ontologyComponentId : "+this.ontologyComponentId;
	}
}

