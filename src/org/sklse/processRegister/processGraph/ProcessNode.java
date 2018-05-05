package org.sklse.processRegister.processGraph;

import org.sklse.processRegister.db.dto.ProcessDTO;
import org.utils.JsonUtil;

public class ProcessNode implements GraphNode, Cloneable {
    public ProcessNode() {
        _dto = new ProcessDTO();
    }

    public ProcessNode(ProcessDTO _dto) {
        this._dto = _dto;
    }

    public ProcessDTO dto() {
        return _dto;
    }

    public void dto(ProcessDTO _dto) {
        this._dto = _dto;
    }

    private ProcessDTO _dto;

    public ProcessNode clone() {
        try {
            return (ProcessNode) super.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


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
