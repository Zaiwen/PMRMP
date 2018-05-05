package ontology;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pengtao
 * 
 */
public class SameAsResult {
	public static final int RO = 0;
	public static final int LO = 1;
	private String namespace_RO = "";

	private String nonLogical_RO = "";

	private List<LO> list_LO = new ArrayList<LO>();

	public List<LO> getList_LO() {
		return list_LO;
	}

	public void setList_LO(List<LO> list_LO) {
		this.list_LO = list_LO;
	}

	public String getNamespace_RO() {
		return namespace_RO;
	}

	public void setNamespace_RO(String namespace_RO) {
		this.namespace_RO = namespace_RO;
	}

	public String getNonLogical_RO() {
		return nonLogical_RO;
	}

	public void setNonLogical_RO(String nonLogical_RO) {
		this.nonLogical_RO = nonLogical_RO;
	}

	public static class LO {
		private String namespace_LO = "";
		private String nonLogical_LO = "";

		public LO(String namespace_LO,String nonLogical_LO){
			this.namespace_LO=namespace_LO;
			this.nonLogical_LO=nonLogical_LO;
		}
		
		public String getNamespace_LO() {
			return namespace_LO;
		}

		public void setNamespace_LO(String namespace_LO) {
			this.namespace_LO = namespace_LO;
		}

		public String getNonLogical_LO() {
			return nonLogical_LO;
		}

		public void setNonLogical_LO(String nonLogical_LO) {
			this.nonLogical_LO = nonLogical_LO;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

