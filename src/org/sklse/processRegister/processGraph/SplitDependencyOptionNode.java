package org.sklse.processRegister.processGraph;

import org.sklse.processRegister.db.dto.SplitDependencyOptionDTO;
import org.utils.JsonUtil;

public class SplitDependencyOptionNode implements GraphNode, IFollowingNode {
    public SplitDependencyOptionNode() {
        _dto = new SplitDependencyOptionDTO();
    }

    public SplitDependencyOptionNode(SplitDependencyOptionDTO _dto) {
        this._dto = _dto;
    }

    public SplitDependencyOptionDTO dto() {
        return _dto;
    }

    public void dto(SplitDependencyOptionDTO _dto) {
        this._dto = _dto;
    }

    private SplitDependencyOptionDTO _dto;

    @Override
    public long getDTOId() {
        return dto().getId();
    }

    @Override
    public void setDTOId(long id) {
        dto().setId(id);
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
