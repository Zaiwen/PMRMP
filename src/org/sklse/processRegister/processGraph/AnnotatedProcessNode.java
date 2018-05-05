package org.sklse.processRegister.processGraph;

import org.sklse.processRegister.db.dto.APGProcessDTO;
import org.sklse.processRegister.db.dto.ProcessDTO;
import org.utils.JsonUtil;

/*Created by Zaiwen FENG at 11 NOV 2015 to evaluate the prototype*/

public class AnnotatedProcessNode implements GraphNode, Cloneable{

    public AnnotatedProcessNode() {
        _dto = new APGProcessDTO();
    }

    public AnnotatedProcessNode(APGProcessDTO _dto) {
        this._dto = _dto;
    }

    public APGProcessDTO dto() {
        return _dto;
    }

    public void dto(APGProcessDTO _dto) {
        this._dto = _dto;
    }

    private APGProcessDTO _dto;

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
