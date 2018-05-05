package org.sklse.processRegister.db.dto;

public class ProcessEventDTO {
    private  long id;
    private PERelation relation;
    private long pid;
    private long eid;

    private  String param;
    
    public enum PERelation {
        PRODUCES, TRIGGEREDBY,
    }
    
    public ProcessEventDTO(){}
    public ProcessEventDTO(PERelation relation,long pid,long eid){
        this.relation = relation;
        this.pid = pid;
        this.eid = eid;
    }
    
    public PERelation getRelation() {
        return relation;
    }
    public void setRelation(PERelation relation) {
        this.relation = relation;
    }
    public void setRelation(String relation) {
        this.setRelation(PERelation.valueOf(relation));
    }
    public long getPid() {
        return pid;
    }
    public void setPid(long pid) {
        this.pid = pid;
    }
    public long getEid() {
        return eid;
    }
    public void setEid(long eid) {
        this.eid = eid;
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
}
