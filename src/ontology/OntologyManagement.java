package ontology;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import databaseaccess.Access;


public class OntologyManagement {
	private ConsistOfDAO consistOfDAO;
	private OntologyDAO ontologyDAO;
	private OntologyAtomicConstructDAO ontologyAtomicConstructDAO;
	private OntologyComponentDAO ontologyComponentDAO;
	private UsesDAO usesDAO;
	private SameAsOfAtomicConstructDAO sameAsOfAtomicConstructDAO;

	public OntologyManagement() {
		//Connection conn = DBConnector.getConnection();
		Connection conn=Access.getConnection();
		consistOfDAO = new ConsistOfDAO(conn);
		ontologyDAO = new OntologyDAO(conn);
		ontologyAtomicConstructDAO = new OntologyAtomicConstructDAO(conn);
		ontologyComponentDAO = new OntologyComponentDAO(conn);
		usesDAO = new UsesDAO(conn);
		sameAsOfAtomicConstructDAO = new SameAsOfAtomicConstructDAO(conn);
		
	}

	public OntologyForMoreInfoDTO getOntologyInfo(int ontologyId) {
		OntologyForMoreInfoDTO ontology = null;

		OntologyDTO onto_tmp = ontologyDAO.queryById(ontologyId);
		if (onto_tmp != null) {
			ontology = new OntologyForMoreInfoDTO(onto_tmp);
			List<ConsistOfDTO> consistOfs = consistOfDAO
					.queryAllByOntologyId(ontologyId);
			List<OntologyComponentForMoreInfoDTO> components = new ArrayList<OntologyComponentForMoreInfoDTO>();
			final Set<OntologyAtomicConstructForMoreInfoDTO> atomicConstructs = new HashSet<OntologyAtomicConstructForMoreInfoDTO>();
			for (ConsistOfDTO consistOf : consistOfs) {
				OntologyComponentDTO comp_tmp = ontologyComponentDAO
						.queryById(consistOf.getOntologyComponentId());
				OntologyComponentForMoreInfoDTO component = new OntologyComponentForMoreInfoDTO(
						comp_tmp);
				List<UsesDTO> uses = usesDAO.queryAllByComponentId(component
						.getId());
				component.setUses(uses);
//				component.setSameAsIds(sameAsOfComponentDAO
//						.getSameIds(component.getJoinid()));
				for (UsesDTO use : uses) {
					OntologyAtomicConstructForMoreInfoDTO atomicConstruct = new OntologyAtomicConstructForMoreInfoDTO(
							ontologyAtomicConstructDAO.queryById(use
									.getOntologyAtomicConstructId()));
//					atomicConstruct.setSameAsIds(sameAsOfAtomicConstructDAO
//							.getSameIds(atomicConstruct.getJoinid()));
					if (!atomicConstructs.contains(atomicConstruct)) {
						atomicConstructs.add(atomicConstruct);
					}
				}
				components.add(component);
			}
			ontology.setComponents(components);
			ontology
					.setAtomicConstructs(new ArrayList<OntologyAtomicConstructForMoreInfoDTO>() {
						private static final long serialVersionUID = 1L;

						{
							this.addAll(atomicConstructs);
						}
					});
		}

		return ontology;
	}

	//MODIFIED
	public boolean deleteOntology(int ontologyId) {
		boolean ret = true;
		
		List<OntologyComponentDTO> coms = ontologyComponentDAO.queryByOntologyId(ontologyId);
		for(OntologyComponentDTO com: coms){
			List<OntologyAtomicConstructDTO> onts = ontologyAtomicConstructDAO.queryByComponentId(com.getId());
			for(OntologyAtomicConstructDTO ont: onts){
				ontologyAtomicConstructDAO.delete(ont.getId());
			}
			ontologyComponentDAO.delete(com.getId());
		}
		ret = ontologyDAO.delete(ontologyId);
		

//		List<ConsistOfDTO> consistOfs = consistOfDAO
//				.queryAllByOntologyId(ontologyId);
//		for (ConsistOfDTO consistOf : consistOfs) {
//			consistOfDAO.delete(consistOf);
//			System.out.println(">>> delete consistOf :"
//					+ consistOf.getOntologyId() + ","
//					+ consistOf.getOntologyComponentId());
//		}
//		if (ontologyDAO.delete(ontologyId)) {
//			System.out.println(">>> delete ontology : " + ontologyId);
//			for (ConsistOfDTO consistOf : consistOfs) {
//				int refCountOfCons = consistOfDAO.queryAllByComponentId(
//						consistOf.getOntologyComponentId()).size();
//				if (refCountOfCons == 0) {
//					ontologyComponentDAO.delete(consistOf
//							.getOntologyComponentId());
//					System.out.println(">>> delete component : "
//							+ consistOf.getOntologyComponentId());
//					List<UsesDTO> uses = usesDAO
//							.queryAllByComponentId(consistOf
//									.getOntologyComponentId());
//					for (UsesDTO use : uses) {
//						usesDAO.delete(use);
//						System.out.println(">>> delete uses :"
//								+ use.getOntologyComponentId() + ","
//								+ use.getOntologyAtomicConstructId());
//					}
//					for (UsesDTO use : uses) {
//						int refCountOfUses = usesDAO
//								.queryAllByAtomicConstructId(
//										use.getOntologyAtomicConstructId())
//								.size();
//						if (refCountOfUses == 0) {
//							ontologyAtomicConstructDAO.delete(use
//									.getOntologyAtomicConstructId());
//							System.out.println(">>> delete atomic construct : "
//									+ use.getOntologyAtomicConstructId());
//						}
//					}
//				}
//			}
//
//		} else {
//			ret = false;
//		}
		return ret;
	}

	public void closeDBConnection() {
		consistOfDAO.closeDBConnection();
		ontologyDAO.closeDBConnection();
		ontologyAtomicConstructDAO.closeDBConnection();
		ontologyComponentDAO.closeDBConnection();
		usesDAO.closeDBConnection();
		sameAsOfAtomicConstructDAO.closeDBConnection();
		
	}

	public void finalize() throws Throwable {
		super.finalize();
		this.closeDBConnection();
	}

	public String generateOWLByReferOntologyComponents(
			List<OntologyComponentDTO> components) {
		StringBuffer buffer = new StringBuffer(
				"<owl:Ontology xmlns:owl=\"http://www.w3.org/2002/07/owl#\"/>\n");
		if (components != null) {
			for (OntologyComponentDTO component : components) {
				buffer.append(component.getSource()).append("\n");
			}
		}
		buffer.append("</owl:Ontology>");
		return buffer.toString();
	}
}
