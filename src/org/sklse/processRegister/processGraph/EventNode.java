package org.sklse.processRegister.processGraph;

import org.sklse.processRegister.db.dto.EventDTO;
import org.utils.JsonUtil;

public class EventNode implements GraphNode {
    public EventNode() {
        _dto = new EventDTO();
    }
    public EventNode(EventDTO _dto) {
        this._dto = _dto;
    }
    public EventDTO dto() {
        return _dto;
    }
    public void dto(EventDTO _dto) {
        this._dto = _dto;
    }
    
    private EventDTO _dto;

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
