package org.sklse.processRegister.processGraph;

import org.sklse.processRegister.db.dto.LoopDependencyDTO;
import org.utils.JsonUtil;

public class LoopStartNode implements GraphNode {
    public LoopStartNode() {
        _dto = new LoopDependencyDTO();
        _end = null;
    }
    public LoopStartNode(LoopDependencyDTO _dto) {
        this._dto = _dto;
        _end = null;
    }
    public LoopDependencyDTO dto() {
        return _dto;
    }
    public void dto(LoopDependencyDTO _dto) {
        this._dto = _dto;
    }
    public LoopEndNode end() {
        return _end;
    }
    public void end(LoopEndNode _end) {
        this._end = _end;
    }
    
    private LoopDependencyDTO _dto;
    private LoopEndNode _end;

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
		return 0;
	}
	@Override
	public void setOriginalId(long id) {
		// TODO Auto-generated method stub
		
	}
}
