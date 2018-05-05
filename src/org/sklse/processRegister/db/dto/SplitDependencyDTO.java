package org.sklse.processRegister.db.dto;

import org.sklse.processRegister.db.dto.EventDTO.Param;
import org.utils.JsonUtil;

public class SplitDependencyDTO   {

    private long id;
    private SplitType type;
    private boolean sync;
    private long preceding;
    private  String param;
    private Param paramObject = new Param();
    
    public class Param {
    	private long originalId;

		public long getOriginalId() {
			return originalId;
		}

		public void setOriginalId(long originalId) {
			this.originalId = originalId;
		}
    }

    public enum SplitType {
        AND, OR, XOR,
    }

    public SplitDependencyDTO(){}
    public SplitDependencyDTO(long id,SplitType type,boolean sync,long preceding){
        this.id = id;
        this.type = type;
        this.sync = sync;
        this.preceding = preceding;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public SplitType getType() {
        return type;
    }
    public void setType(SplitType type) {
        this.type = type;
    }
    public void setType(String type) {
        this.type = SplitType.valueOf(type);
    }
    public boolean getSync() {
        return sync;
    }
    public void setSync(boolean sync) {
        this.sync = sync;
    }

    public boolean isSync() {
        return sync;
    }


    public long getPreceding() {
        return preceding;
    }


    public void setPreceding(long preceding) {
        this.preceding = preceding;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
	public Param getParamObject() {
		return paramObject;
	}
	public void setParamObject(Param paramObject) {
		this.paramObject = paramObject;
	}
}
