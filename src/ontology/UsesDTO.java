package ontology;

public class UsesDTO {
	private int ontologyComponentId;
	private int ontologyAtomicConstructId;
	public int getOntologyComponentId() {
		return ontologyComponentId;
	}
	public void setOntologyComponentId(int ontologyComponentId) {
		this.ontologyComponentId = ontologyComponentId;
	}
	public int getOntologyAtomicConstructId() {
		return ontologyAtomicConstructId;
	}
	public void setOntologyAtomicConstructId(int ontologyAtomicConstructId) {
		this.ontologyAtomicConstructId = ontologyAtomicConstructId;
	}
	public String toString(){
		return "UsesDTO : " +
				"\n   ontologyComponentId : "+this.ontologyComponentId +
				"\n   ontologyAtomicConstructId : "+this.ontologyAtomicConstructId;
	}
}
