package ontology;

import java.util.ArrayList;
import java.util.List;


public class OntologyComponent {

	private Ontology ontology;

	private int index;
	private String administered_item_administration_record;
	private String namespace;
	private String sentenceIdentifier;
	private String type;
	private List<OntologyAtomicConstruct> uses = new ArrayList<OntologyAtomicConstruct>();
	private OntologyAtomicConstruct keyAtomic;

	private String xml;

	public OntologyComponent(Ontology ontology, String uri, String xml,
			int index) {
		this.ontology = ontology;
		this.namespace = uri;
		this.xml = xml;
		this.index = index;
		this.sentenceIdentifier = "object_" + index;
	}

	/**
	 * 原子结构不存在，将原子结构的引用添加到本体和本体句子中去
	 * 
	 * @param atomatic
	 */
	public void addAtomaticToAll(OntologyAtomicConstruct atomatic) {
		ontology.getUses().add(atomatic);
		this.addAtomaticToComponent(atomatic);
	}

	/**
	 * 原子结构已存在，只将原子结构的引用添加到本体句子中去
	 * 
	 * @param atomatic
	 */
	public void addAtomaticToComponent(OntologyAtomicConstruct atomatic) {
		if (!this.uses.contains(atomatic)) {
			this.uses.add(atomatic);
		}
	}

	/**
	 * 原子结构不存在，将原子结构的引用添加到本体和本体句子中去
	 * 
	 * @param atomatic
	 */
	public void addKeyAtomaticToAll(OntologyAtomicConstruct atomatic) {
		this.addAtomaticToAll(atomatic);
		if (keyAtomic == null) {
			this.setKeyAtomic(atomatic);
		}
	}

	/**
	 * 关键原子结构已存在，只将原子结构的引用添加到本体句子中去
	 * 
	 * @param atomatic
	 */
	public void addKeyAtomaticToComponent(OntologyAtomicConstruct atomatic) {
		this.addAtomaticToComponent(atomatic);
		if (keyAtomic == null) {
			this.setKeyAtomic(atomatic);
		}
	}

	/**
	 * 原子结构是否已存在
	 * 
	 * @param atomatic
	 */
	public boolean hastAtomatic(OntologyAtomicConstruct atomatic) {
		List<OntologyAtomicConstruct> existsUses = this.ontology.getUses();
		if (existsUses.contains(atomatic)) {
			return true;
		}
		return false;
	}

	/**
	 * 通过Symbol获取原子结构
	 * 
	 * @param name
	 * @return
	 */
	public OntologyAtomicConstruct getAtomBySymbol(String name) {
		List<OntologyAtomicConstruct> existsUses = this.ontology.getUses();
		for (int i = 0; i < existsUses.size(); i++) {
			OntologyAtomicConstruct atom = existsUses.get(i);
			if (atom.getNonLogicalSymbol().equals(name)) {
				return atom;
			}
		}
		return null;
	}

	/**
	 * @return the ontology
	 */
	public Ontology getOntology() {
		return ontology;
	}

	/**
	 * @param ontology
	 *            the ontology to set
	 */
	public void setOntology(Ontology ontology) {
		this.ontology = ontology;
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
	 * @return the sentenceIdentifier
	 */
	public String getSentenceIdentifier() {
		return sentenceIdentifier;
	}

	/**
	 * @param sentenceIdentifier
	 *            the sentenceIdentifier to set
	 */
	public void setSentenceIdentifier(String sentenceIdentifier) {
		this.sentenceIdentifier = sentenceIdentifier;
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

	/**
	 * @return the keyAtomic
	 */
	public OntologyAtomicConstruct getKeyAtomic() {
		return keyAtomic;
	}

	/**
	 * @param keyAtomic
	 *            the keyAtomic to set
	 */
	public void setKeyAtomic(OntologyAtomicConstruct keyAtomic) {
		this.keyAtomic = keyAtomic;
	}

	/**
	 * @return the xml
	 */
	public String getXml() {
		return xml;
	}

	/**
	 * @param xml
	 *            the xml to set
	 */
	public void setXml(String xml) {
		this.xml = xml;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		if (this.type == null) {
			this.type = type;
		}
	}

	public String toString() {
		return " OntologyComponent Sentence " + index + " " + type + ":"
				+ this.keyAtomic;
	}

}