package ontology;

import java.util.List;

public class OntologyAtomicConstructForMoreInfoDTO extends OntologyAtomicConstructDTO{
	private List<Integer> sameAsIds;
	public OntologyAtomicConstructForMoreInfoDTO(){}
	public OntologyAtomicConstructForMoreInfoDTO(OntologyAtomicConstructDTO dto){
		super(dto.getId(),dto.getAdministeredItemAdministrationRecord(),
				dto.getNamespace(),dto.getNonLogicalSymbol(),dto.getType());
	}
	public List<Integer> getSameAsIds() {
		return sameAsIds;
	}
	public void setSameAsIds(List<Integer> sameAsIds) {
		this.sameAsIds = sameAsIds;
	}
}
