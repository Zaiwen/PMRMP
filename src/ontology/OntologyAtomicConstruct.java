package ontology;


public class OntologyAtomicConstruct {

	private int index;

	private String administered_item_administration_record;
	private String namespace;
	private String nonLogicalSymbol;

	public OntologyAtomicConstruct(String symbol, String ns, int index) {
		// this.administered_item_administration_record =
		// administered_item_administration_record;
		this.nonLogicalSymbol = symbol;
		this.namespace = ns;
		this.index = index;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
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
	 * @return the namespace
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * @param namespace
	 *            the namespace to set
	 */
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	/**
	 * @return the nonLogicalSymbol
	 */
	public String getNonLogicalSymbol() {
		return nonLogicalSymbol;
	}

	/**
	 * @param nonLogicalSymbol
	 *            the nonLogicalSymbol to set
	 */
	public void setNonLogicalSymbol(String nonLogicalSymbol) {
		this.nonLogicalSymbol = nonLogicalSymbol;
	}

	public String toString() {
		return "  OntologyAtomicConstruct " + index + ":" + nonLogicalSymbol
				+ " " + namespace;
	}
}
