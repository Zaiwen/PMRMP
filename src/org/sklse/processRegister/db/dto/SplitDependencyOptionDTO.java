package org.sklse.processRegister.db.dto;

import org.sklse.processRegister.db.dto.EventDTO.Param;
import org.utils.JsonUtil;

public class SplitDependencyOptionDTO {
    private long id;

    private long splitid;
    private long following;
    private String condition = "";
    /**
     * epc id
     */
    private long tempid;

    private String param;
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

    public SplitDependencyOptionDTO() {
    }

    public SplitDependencyOptionDTO(long splitid, long following, String condition) {
        this.splitid = splitid;
        this.following = following;
        this.condition = condition;
    }

    public long getSplitid() {
        return splitid;
    }

    public void setSplitid(long id) {
        this.splitid = id;
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

    public long getTempid() {
        return tempid;
    }

    public void setTempid(long tempid) {
        this.tempid = tempid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
