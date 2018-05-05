package org.sklse.processRegister.db.dto;

public class ProcessElementDTO {
    
    private long id;
    private ElementType type;

    private  String param;
    
    public enum ElementType {
        PROCESS, SEQUENCE, SPLIT, JOIN, LOOP,
    }

    public ProcessElementDTO(){}
    public ProcessElementDTO(long id,ElementType type){
        this.id = id;
        this.type = type;
    }
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public ElementType getType() {
        return type;
    }
    public void setType(ElementType type) {
        this.type = type;
    }
    public void setType(String type) {
        if(type.equalsIgnoreCase(ElementType.JOIN.toString())) {
            this.setType(ElementType.JOIN);
        } else if(type.equalsIgnoreCase(ElementType.LOOP.toString())) {
            this.setType(ElementType.LOOP);
        } else if (type.equalsIgnoreCase(ElementType.SPLIT.toString())) {
            this.setType(ElementType.SPLIT);
        } else if (type.equalsIgnoreCase(ElementType.SEQUENCE.toString())) {
            this.setType(ElementType.SEQUENCE);
        } else {
            this.setType(ElementType.PROCESS);
        }
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
