package ontology;

import java.util.List;

public class OntologyForMoreInfoDTO extends OntologyDTO {
	private List<OntologyComponentForMoreInfoDTO> components;
	private List<OntologyAtomicConstructForMoreInfoDTO> atomicConstructs;

	public OntologyForMoreInfoDTO() {
	}

	public OntologyForMoreInfoDTO(OntologyDTO dto) {
		super(dto.getId(), dto.getAdministeredItemAdministrationRecord(), dto
				.getUri(), dto.getOntologyName(), dto.getModelType(), dto
				.getOntologyType(), dto.getDomain(), dto.getUser());
	}

	public List<OntologyComponentForMoreInfoDTO> getComponents() {
		return components;
	}

	public void setComponents(List<OntologyComponentForMoreInfoDTO> components) {
		this.components = components;
	}

	public List<OntologyAtomicConstructForMoreInfoDTO> getAtomicConstructs() {
		return atomicConstructs;
	}

	public void setAtomicConstructs(
			List<OntologyAtomicConstructForMoreInfoDTO> atomicConstructs) {
		this.atomicConstructs = atomicConstructs;
	}

}
