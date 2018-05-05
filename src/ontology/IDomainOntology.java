package ontology;

import java.util.List;

public interface IDomainOntology {

	/**
	 * 获得指定领域本体文件的存储路径
	 * 
	 * @param domainOntologyid
	 *            领域ID
	 * @return 本体文件的路径
	 */
	public String getDomainOntologyFilePath(int domainOntologyid);

	// /**
	// * @param id
	// * 构件或原子构件的ID
	// * @param nonLogicalSymbol
	// * @return
	// */
	// public List searchSameAS(int id, String nonLogicalSymbol);

	/**
	 * 获得该领域的参考本体
	 * 
	 * @param domain
	 *            领域
	 * @return
	 */
	public OntologyDTO getROByDomain(String domain);

	/**
	 * 获得基于该参考本体的本地本体
	 * 
	 * @param id
	 *            参考本体ID
	 * @return
	 */
	public List<OntologyDTO> getLOsBaseRO(int id);

	/**
	 * 获得指定领域本体的所有SameAs构件，领域本体可以是RO也可以是LO
	 * 
	 * @param domainOntologyID
	 *            指定领域本体的id
	 */
	public List<SameAsResult> getSameAS(int domainOntologyID,
			String nonLogicalSymbol);

}

