package ontology;

import java.sql.Connection;
import java.util.List;

import databaseaccess.Access;

public class StoreOntologyToDB {
	// 默认保存为参考本体
	public static boolean storeOntologyToDB(Ontology ontology, String user) {
		// 插入本体
		if (ontology != null) {
			//Connection conn = DBConnector.getConnection();
			Connection conn=Access.getConnection();
			OntologyDAO ontologyDAO = new OntologyDAO(conn);
			OntologyComponentDAO ontologyComponentDAO = new OntologyComponentDAO(
					conn);
// ConsistOfDAO consistOfDAO = new ConsistOfDAO(conn);
			OntologyAtomicConstructDAO ontologyAtomicConstructDAO = new OntologyAtomicConstructDAO(
					conn);
// UsesDAO usesDAO = new UsesDAO(conn);

			OntologyDTO ontologyDTO = new OntologyDTO();
			ontologyDTO.setId(ontology.getId());
			ontologyDTO.setAdministeredItemAdministrationRecord(ontology
					.getAdministered_item_administration_record());
			ontologyDTO.setModelType(ontology.getModelType());
			ontologyDTO.setOntologyType(ontology.getOntologyType());
			ontologyDTO.setOntologyName(ontology.getOntologyName());
			ontologyDTO.setUri(ontology.getUri());
			ontologyDTO.setHeader(ontology.getHeader());
			ontologyDTO.setDescription(ontology.getDescription());
			ontologyDTO.setFileLocation(ontology.getFilepath());
			ontologyDTO.setDomain(ontology.getDomain());
			ontologyDTO.setUser(user);

			boolean create_success = ontologyDAO.create(ontologyDTO);
			if (!create_success) {
				System.out.println(">>> ontology exist : "+ ontology.getOntologyName());
				return false;
			} 
			int ontologyId = ontologyDAO.queryByFileName(ontology.getFilepath()).getId();
			// 插入本体句子
			List<OntologyComponent> components = ontology.getConsistsOf();
			for (OntologyComponent component : components) {
				OntologyComponentDTO ontologyComponentDTO = new OntologyComponentDTO();
				ontologyComponentDTO
						.setAdministeredItemAdministrationRecord(component
								.getAdministered_item_administration_record());
				ontologyComponentDTO.setNamespace(component.getNamespace());
				ontologyComponentDTO.setSentenceIdentifier(component
						.getSentenceIdentifier());
				ontologyComponentDTO.setType(component.getType());
				ontologyComponentDTO.setSource(component.getXml());
				ontologyComponentDTO.setCanModify(0);
				ontologyComponentDTO.setOntologyId(ontologyId);
				//System.out.println("<<<"+component.getKeyAtomic());
				ontologyComponentDTO.setKeyAtomic(component.getKeyAtomic()==null?"":component.getKeyAtomic().getNonLogicalSymbol());

				create_success = ontologyComponentDAO
						.create(ontologyComponentDTO);

				int ontologyComponentId = ontologyComponentDAO.queryByNsAndSI(
						component.getNamespace(),
						component.getSentenceIdentifier(),ontologyId).getId();

				if (!create_success) {
					System.out.println(">>> ontology_component exist : "
							+ component.getSentenceIdentifier());
				} else {
					
					List<OntologyAtomicConstruct> atomicConstructsInComponent = component
							.getUses();
					for (OntologyAtomicConstruct atomicConstruct : atomicConstructsInComponent) {
						// 插入原子结构
						OntologyAtomicConstructDTO ontologyAtomicConstructDTO = new OntologyAtomicConstructDTO();
						ontologyAtomicConstructDTO
								.setAdministeredItemAdministrationRecord(atomicConstruct
										.getAdministered_item_administration_record());
						ontologyAtomicConstructDTO.setNamespace(atomicConstruct
								.getNamespace());
						ontologyAtomicConstructDTO
								.setNonLogicalSymbol(atomicConstruct
										.getNonLogicalSymbol());
						ontologyAtomicConstructDTO.setType(ontology
								.getOntologyType());
						ontologyAtomicConstructDTO.setOntologyComponentId(ontologyComponentId);
						create_success = ontologyAtomicConstructDAO
								.create(ontologyAtomicConstructDTO);
						if (!create_success) {
							System.out
									.println(">>> ontology_atomic_construct exist : "
											+ atomicConstruct.getNamespace()
											+ " "
											+ atomicConstruct
													.getNonLogicalSymbol());
						} 
					}
				}
			}
			ontologyDAO.closeDBConnection();
			ontologyComponentDAO.closeDBConnection();
// consistOfDAO.closeDBConnection();
			ontologyAtomicConstructDAO.closeDBConnection();
// usesDAO.closeDBConnection();

			return true;
		}

		return false;
	}

	public static String getOntologyInfo(int ontologyId) {
		StringBuffer buffer = new StringBuffer();

		return buffer.toString();

	}
}
