package org.sklse.processRegister.db.dto;

import org.sklse.processRegister.db.dto.EventDTO.Param;
import org.utils.JsonUtil;

public class SequenceDependencyDTO {

    private long id;
    private long preceding;
    private long following;
    private String name;
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

    public SequenceDependencyDTO() {
    }

    public SequenceDependencyDTO(long id, long preceding, long following) {
        this.id = id;
        this.preceding = preceding;
        this.following = following;
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


    public long getFollowing() {
        return following;
    }


    public void setFollowing(long following) {
        this.following = following;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
