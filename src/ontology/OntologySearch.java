package ontology;

import java.util.ArrayList;
import java.util.List;

public class OntologySearch {
//	public static List<OntologyComponentDTO> searchsSameAsComponent(
//			String namespace, String nonLogicalSymbol) {
//		List<OntologyComponentDTO> components = new ArrayList<OntologyComponentDTO>();
//		OntologyComponentDAO ontologyComponentDAO = new OntologyComponentDAO();
//		SameAsOfComponentDAO sameAsOfComponentDAO = new SameAsOfComponentDAO();
//
//		List<OntologyComponentDTO> ontologyComponentDTOs = ontologyComponentDAO
//				.queryByKeyAtomic(namespace, nonLogicalSymbol);
//		Set<Integer> componentIds = new HashSet<Integer>();
//		for (OntologyComponentDTO ontologyComponentDTO : ontologyComponentDTOs) {
//			List<Integer> ids = sameAsOfComponentDAO
//					.getSameIds(ontologyComponentDTO.getJoinid());
//			for (Integer id : ids) {
//				if (!componentIds.contains(id))
//					componentIds.add(id);
//			}
//		}
//		for (OntologyComponentDTO ontologyComponentDTO : ontologyComponentDTOs)
//			if (componentIds.contains(ontologyComponentDTO.getJoinid()))
//				componentIds.remove(ontologyComponentDTO.getJoinid());
//		for (Integer id : componentIds)
//			components.add(ontologyComponentDAO.queryById(id));
//
//		ontologyComponentDAO.closeDBConnection();
//		sameAsOfComponentDAO.closeDBConnection();
//		return components;
//	}

	public static List<OntologyAtomicConstructDTO> searchSameAsAtomicConstruct(
			String namespace, String nonLogicalSymbol) {
		List<OntologyAtomicConstructDTO> atomicConstructs = new ArrayList<OntologyAtomicConstructDTO>();
		DomainOntologyImpl doi = new DomainOntologyImpl();
		OntologyDAO dao = new OntologyDAO();
		int onto_id = dao.queryByUri(namespace).getId();
		dao.closeDBConnection();
		@SuppressWarnings("unused")
		List<SameAsResult> res = doi.getSameAS(onto_id, nonLogicalSymbol);
		
//		OntologyAtomicConstructDAO ontologyAtomicConstructDAO = new OntologyAtomicConstructDAO();
//		Connection conn = DBConnector.getConnection();
//		SameAsOfAtomicConstructDAO sameAsOfAtomicConstructDAO = new SameAsOfAtomicConstructDAO(
//				conn);
//
//		List<OntologyAtomicConstructDTO> ontologyAtomicConstructDTOs = ontologyAtomicConstructDAO
//				.queryByNSAndNLS(namespace, nonLogicalSymbol);
//		Set<Integer> atomicConstructIds = new HashSet<Integer>();
//		for (OntologyAtomicConstructDTO ontologyAtomicConstructDTO : ontologyAtomicConstructDTOs) {
//			List<Integer> ids = sameAsOfAtomicConstructDAO
//					.getSameIds(ontologyAtomicConstructDTO.getJoinid());
//			for (Integer id : ids) {
//				if (!atomicConstructIds.contains(id))
//					atomicConstructIds.add(id);
//			}
//		}
//		for (OntologyAtomicConstructDTO ontologyAtomicConstructDTO : ontologyAtomicConstructDTOs)
//			if (atomicConstructIds.contains(ontologyAtomicConstructDTO.getJoinid()))
//				atomicConstructIds.remove(ontologyAtomicConstructDTO.getJoinid());
//		for (Integer id : atomicConstructIds)
//			atomicConstructs.add(ontologyAtomicConstructDAO.queryById(id));
//
//		ontologyAtomicConstructDAO.closeDBConnection();
//		sameAsOfAtomicConstructDAO.closeDBConnection();
//		DBConnector.closeConnection(conn);
		return atomicConstructs;
	}

	public static void main(String[] args) {

	}

}
