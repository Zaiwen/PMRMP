package org.sklse.processRegister.processGraph;

import org.sklse.processRegister.db.dto.SequenceDependencyDTO;
import org.utils.JsonUtil;

public class SequenceDependencyNode implements GraphNode, IPrecedingNode, IFollowingNode {

    public SequenceDependencyNode() {
        _dto = new SequenceDependencyDTO();
    }

    public SequenceDependencyNode(SequenceDependencyDTO dto) {
        _dto = dto;
    }

    public SequenceDependencyDTO dto() {
        return _dto;
    }

    public void dto(SequenceDependencyDTO dto) {
        _dto = dto;
    }

    private SequenceDependencyDTO _dto;

    @Override
    public long getDTOId() {
        return dto().getId();
    }

    @Override
    public void setDTOId(long id) {
        dto().setId(id);
    }

    @Override
    public void setPreceding(long preceding) {
        dto().setPreceding(preceding);
    }

    @Override
    public void setFollowing(long following) {
        dto().setFollowing(following);
    }

    @Override
    public String toString() {
          return JsonUtil.toJson(this);
    }
    
    @Override
	public long getOriginalId() {
		// TODO Auto-generated method stub
		return dto().getParamObject().getOriginalId();
		
	}
	@Override
	public void setOriginalId(long id) {
		// TODO Auto-generated method stub
		
		dto().getParamObject().setOriginalId(id);
		
	}
}
