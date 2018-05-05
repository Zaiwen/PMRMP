package org.sklse.processRegister.db.dto;

import org.sklse.processRegister.util.DocumentTypeRecognizer.DocumentType;

import java.sql.Date;

public class ProcessModelDTO {

    private long id;
    private String name;
    private String uri;
    private DocumentType language;
    private long pid;
    private String annotation;
    private long epcid;

    /**
     * 版本
     */
    private long version;
    /**
     * 领域 {@link org.sklse.processRegister.db.enums.ProcessFieldEnum}
     */
    private int field;

    /**
     * 插件作者
     */
    private long author;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 扩展字段
     */
    private String param;


    public long getAuthor() {
        return author;
    }

    public void setAuthor(long author) {
        this.author = author;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public int getField() {
        return field;
    }

    public void setField(int field) {
        this.field = field;
    }



    public ProcessModelDTO() {
    }

    public ProcessModelDTO(long id, String name, String uri,
                           DocumentType language, long pid, String annotation) {
        this.id = id;
        this.name = name;
        this.uri = uri;
        this.language = language;
        this.pid = pid;
        this.annotation = annotation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public DocumentType getLanguage() {
        return language;
    }

    public void setLanguage(DocumentType language) {
        this.language = language;
    }

    public void setLanguage(String language) {
        this.language = DocumentType.valueOf(language);
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

	public long getEpcid() {
		return epcid;
	}

	public void setEpcid(long epcid) {
		this.epcid = epcid;
	}


}
