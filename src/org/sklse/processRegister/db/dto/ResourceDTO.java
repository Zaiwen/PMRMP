package org.sklse.processRegister.db.dto;

import org.sklse.processRegister.db.dto.EventDTO.Param;
import org.utils.JsonUtil;

public class ResourceDTO {
    
    private long id;
    private String uri;
    private String annotation;
    private String name;
    private String description;

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

    public ResourceDTO(){}
    public ResourceDTO(long id, String name, String uri, String description,
                            String annotation) {
        this.id = id;
        this.uri = uri;
        this.annotation = annotation;
        this.name = name;
        this.description = description;
    }
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
    public String getAnnotation() {
        return annotation;
    }
    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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
