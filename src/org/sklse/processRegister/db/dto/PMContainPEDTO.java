package org.sklse.processRegister.db.dto;

import org.sklse.processRegister.db.dto.ProcessElementDTO.ElementType;

public class PMContainPEDTO {
    
    private long pmid;
    private long peid;
    private ElementType petype;

    private  String param;

    public PMContainPEDTO(){}
    public PMContainPEDTO(long pmid,long peid){
        this.pmid = pmid;
        this.peid = peid;
    }

    public enum ElementType {
        PROCESS, SEQUENCE, SPLIT, JOIN, SPLITOPTION,JOINOPTION,EVENT,RESOURCE
    }
    
    public long getPMId() {
        return pmid;
    }
    public void setPMId(long pmid) {
        this.pmid = pmid;
    }
    public long getPEId() {
        return peid;
    }
    public void setPEId(long peid) {
        this.peid = peid;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
	public ElementType getPetype() {
		return petype;
	}
	public void setPetype(ElementType petype) {
		this.petype = petype;
	}
	
	public void setPetype(String type) {
        if(type.equalsIgnoreCase(ElementType.JOIN.toString())) {
            this.setPetype(ElementType.JOIN);
        } else if(type.equalsIgnoreCase(ElementType.JOINOPTION.toString())) {
            this.setPetype(ElementType.JOINOPTION);
        } else if (type.equalsIgnoreCase(ElementType.SPLIT.toString())) {
            this.setPetype(ElementType.SPLIT);
        }else if(type.equalsIgnoreCase(ElementType.SPLITOPTION.toString())){
        	this.setPetype(ElementType.SPLITOPTION);
        } else if (type.equalsIgnoreCase(ElementType.SEQUENCE.toString())) {
            this.setPetype(ElementType.SEQUENCE);
        }else if(type.equalsIgnoreCase(ElementType.EVENT.toString())){
        	this.setPetype(ElementType.EVENT);
        } else {
            this.setPetype(ElementType.PROCESS);
        }
    }

}
