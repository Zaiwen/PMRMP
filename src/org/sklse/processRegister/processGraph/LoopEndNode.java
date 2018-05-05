package org.sklse.processRegister.processGraph;

import org.sklse.processRegister.db.dto.LoopDependencyDTO;
import org.utils.JsonUtil;

public class LoopEndNode implements GraphNode {
    public LoopEndNode() {
        
    }
    public LoopEndNode(LoopStartNode startNode) throws LoopNodeException {
        _dto = startNode.dto();
        _start = startNode;
        if(startNode.end()==null) {
            startNode.end(this);
        } else throw new LoopNodeException();
    }
    public LoopDependencyDTO dto() {
        return _dto;
    }
    public void dto(LoopDependencyDTO _dto) {
        this._dto = _dto;
    }
    public LoopStartNode start() {
        return _start;
    }
    public void start(LoopStartNode _start) {
        this._start = _start;
    }

    @Override
    public long getDTOId() {
        return dto().getId();
    }

    @Override
    public void setDTOId(long id) {
        dto().setId(id);
    }

    public class LoopNodeException extends Exception {
        private static final long serialVersionUID = 1L;
    }
    
    
    private LoopDependencyDTO _dto;
    private LoopStartNode _start;

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
