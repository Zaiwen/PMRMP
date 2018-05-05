package org.sklse.processRegister.db.dto;

public class ProcessResourceDTO {
    private  long id;
    
    private long rid;
    private long pid;
    private PRRelation relation;

    private  String param;
    
    public enum PRRelation {
        CREATES, CONSUMES, USES,
    }

    public ProcessResourceDTO(){}
    public ProcessResourceDTO(long rid, long pid, PRRelation relation){
        this.rid = rid;
        this.pid = pid;
        this.relation = relation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRid() {
        return rid;
    }
    public void setRid(long rid) {
        this.rid = rid;
    }
    public long getPid() {
        return pid;
    }
    public void setPid(long pid) {
        this.pid = pid;
    }
    public PRRelation getRelation() {
        return relation;
    }
    public void setRelation(PRRelation relation) {
        this.relation = relation;
    }
    public void setRelation(String relation) {
        this.relation = PRRelation.valueOf(relation);
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
