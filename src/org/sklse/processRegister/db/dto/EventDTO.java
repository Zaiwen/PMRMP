package org.sklse.processRegister.db.dto;

import org.utils.JsonUtil;

public class EventDTO {
    
    private long id;
    private String name;
    private String uri;
    private String description;
    private String annotation;

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
    
    

    public EventDTO(){
        name = "";
        uri = "";
        description = "";
        annotation = "";
    }
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }

    public boolean equals(Object obj){
        if(obj instanceof EventDTO && ((EventDTO)obj).getId() == this.id){
            return true;
        } else {
            return false;
        }
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

	public Param getParamObject() {
		return paramObject;
	}

	public void setParamObject(Param paramObject) {
		this.paramObject = paramObject;
	}
}
