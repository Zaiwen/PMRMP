package ontology;
import java.util.List;


public class OntologyComponentForMoreInfoDTO extends OntologyComponentDTO{
	
	private List<UsesDTO> uses;
	private List<Integer> sameAsIds;
	public OntologyComponentForMoreInfoDTO(){}
	public OntologyComponentForMoreInfoDTO(OntologyComponentDTO dto){
		super(dto.getId(),dto.getAdministeredItemAdministrationRecord(),
				dto.getNamespace(),dto.getSentenceIdentifier(),dto.getType());
	}
	public List<UsesDTO> getUses() {
		return uses;
	}
	public void setUses(List<UsesDTO> uses) {
		this.uses = uses;
	}
	public List<Integer> getSameAsIds() {
		return sameAsIds;
	}
	public void setSameAsIds(List<Integer> sameAsIds) {
		this.sameAsIds = sameAsIds;
	}
	
}
