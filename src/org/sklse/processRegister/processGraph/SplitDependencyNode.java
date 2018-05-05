package org.sklse.processRegister.processGraph;

import org.sklse.processRegister.db.dto.SplitDependencyDTO;
import org.utils.JsonUtil;

public class SplitDependencyNode implements GraphNode,IPrecedingNode {
    public SplitDependencyNode() {
        _dto = new SplitDependencyDTO();
    }
    public SplitDependencyNode(SplitDependencyDTO _dto) {
        this._dto = _dto;
    }
    public SplitDependencyDTO dto() {
        return _dto;
    }
    public void dto(SplitDependencyDTO _dto) {
        this._dto = _dto;
    }
    
    private SplitDependencyDTO _dto;

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
