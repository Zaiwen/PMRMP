package org.sklse.processRegister.db.dto;

import org.sklse.processRegister.db.dto.EventDTO.Param;
import org.utils.JsonUtil;

public class JoinDependencyOptionDTO   {
    private  long id;

    private long preceding;

    private long joinid;
    private String condition = "";
    private long tempid;
    
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
    
    public JoinDependencyOptionDTO() {
    }

    public JoinDependencyOptionDTO(long joinid, long preceding, String condition) {
        this.joinid = joinid;
        this.preceding = preceding;
        this.condition = condition;
    }

    public long getPreceding() {
        return preceding;
    }


    public void setPreceding(long preceding) {
        this.preceding = preceding;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getJoinid() {
        return joinid;
    }

    public void setJoinid(long joinid) {
        this.joinid = joinid;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public long getTempid() {
        return tempid;
    }

    public void setTempid(long tempid) {
        this.tempid = tempid;
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
