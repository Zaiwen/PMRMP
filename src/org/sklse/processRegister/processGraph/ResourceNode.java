package org.sklse.processRegister.processGraph;

import org.sklse.processRegister.db.dto.ResourceDTO;
import org.utils.JsonUtil;

public class ResourceNode implements GraphNode {
    public ResourceNode() {
        _dto = new ResourceDTO();
    }
    public ResourceNode(ResourceDTO _dto) {
        this._dto = _dto;
    }
    public ResourceDTO dto() {
        return _dto;
    }
    public void dto(ResourceDTO _dto) {
        this._dto = _dto;
    }
    
    private ResourceDTO _dto;

    @Override
    public long getDTOId() {
        return dto().getId();
    }

    @Override
    public void setDTOId(long id) {
        dto().setId(id);
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
