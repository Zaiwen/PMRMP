package org.sklse.processRegister.processGraph;

import org.sklse.processRegister.db.dto.JoinDependencyDTO;
import org.utils.JsonUtil;

public class JoinDependencyNode implements GraphNode, IFollowingNode {
    public JoinDependencyNode() {
        _dto = new JoinDependencyDTO();
    }

    public JoinDependencyNode(JoinDependencyDTO _dto) {
        this._dto = _dto;
    }

    public JoinDependencyDTO dto() {
        return _dto;
    }

    public void dto(JoinDependencyDTO _dto) {
        this._dto = _dto;
    }

    private JoinDependencyDTO _dto;

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


    public JoinDependencyDTO get_dto() {
        return _dto;
    }

    public void set_dto(JoinDependencyDTO _dto) {
        this._dto = _dto;
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
