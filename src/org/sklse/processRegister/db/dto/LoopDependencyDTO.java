package org.sklse.processRegister.db.dto;

import org.sklse.processRegister.db.dto.EventDTO.Param;
import org.utils.JsonUtil;

public class LoopDependencyDTO  {
    
    private long id;
    private long preceding;
    private long entryPoint;
    private long following;
    private LoopType type;
    private String condition;
    private String param;
 private Param paramObject;
    
    class Param {
    	private long originalId;

		public long getOriginalId() {
			return originalId;
		}

		public void setOriginalId(long originalId) {
			this.originalId = originalId;
		}
    }

    public enum LoopType {
        PRE_TEST, POST_TEST,
    }

    public LoopDependencyDTO(){}
    public LoopDependencyDTO(long id, long preceding, long entryPoint, long following,
            LoopType type, String condition) {
        this.id = id;
        this.preceding = preceding;
        this.entryPoint = entryPoint;
        this.following = following;
        this.type = type;
        this.condition = condition;
    }
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }


    public long getPreceding() {
        return preceding;
    }


    public void setPreceding(long preceding) {
        this.preceding = preceding;
    }

    public long getEntryPoint() {
        return entryPoint;
    }
    public void setEntryPoint(long entryPoint) {
        this.entryPoint = entryPoint;
    }


    public long getFollowing() {
        return following;
    }

    public void setFollowing(long following) {
        this.following = following;
    }

    public String getCondition() {
        return condition;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }
    public LoopType getType() {
        return type;
    }
    public void setType(LoopType type) {
        this.type = type;
    }
    public void setType(String type) {
        if(type.equalsIgnoreCase(LoopType.PRE_TEST.toString())) {
            this.setType(LoopType.PRE_TEST);
        } else {
            this.setType(LoopType.POST_TEST);
        }
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
