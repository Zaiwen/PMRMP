package ontology;

public class SameAsOfAtomicConstructDTO {
	private String namespace_RO;	
	private String non_logical_symboll_RO;
	private String namespace_LO;	
	private String non_logical_symboll_LO;

	
	public String getNamespace_RO() {
		return namespace_RO;
	}


	public void setNamespace_RO(String namespace_RO) {
		this.namespace_RO = namespace_RO;
	}


	public String getNon_logical_symboll_RO() {
		return non_logical_symboll_RO;
	}


	public void setNon_logical_symboll_RO(String non_logical_symboll_RO) {
		this.non_logical_symboll_RO = non_logical_symboll_RO;
	}


	public String getNamespace_LO() {
		return namespace_LO;
	}


	public void setNamespace_LO(String namespace_LO) {
		this.namespace_LO = namespace_LO;
	}


	public String getNon_logical_symboll_LO() {
		return non_logical_symboll_LO;
	}


	public void setNon_logical_symboll_LO(String non_logical_symboll_LO) {
		this.non_logical_symboll_LO = non_logical_symboll_LO;
	}


	public String toString(){
		return "SameAsOfAtomicConstructDTO : " +
		"\n   non_logical_symboll_RO : "+this.non_logical_symboll_RO +
		"\n   non_logical_symboll_LO : "+this.non_logical_symboll_LO;
	}
}

