package org.sklse.processRegister.db.dto;

import org.sklse.processRegister.db.enums.SearchExtensibilityPointTypeEnum;
import org.utils.JsonUtil;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ԭ袁胜磊 on Intellij IDEA
 * Revised by Zaiwen FENG on NOV 2015
 *
 * @date 2015/3/30
 * @email 745861642@qq.com;zwfeng@whu.edu.cn
 * @decription
 */
public class PluginInfoDTO {

    private long id;
    /**
     * 插件名称
     */
    private String name;
    /**
     * 插件对应的url
     */
    private String url;
    /**
     * 描述信息
     */
    private String annotation;
    /**
     * 插件作者
     */
    private long author;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 插件领域 {@link org.sklse.processRegister.db.enums.ProcessFieldEnum}
     */
    private int field;
    /**
     * 控制流扩展模式{@link org.sklse.processRegister.db.enums.ControllFlowExtensionPatternEnum}
     */
    private int controllFlowExtensionPattern;
    /**
     * 数据流扩展模式{@link org.sklse.processRegister.db.enums.DataFlowExtensionPartternEnum}
     */
    private int dataFlowExtensionParttern;

    /**
     * 插件基于流程语言{@link org.sklse.processRegister.db.enums.ProcessTypeEnum}
     */
    private int processType;
    /**
     * 扩展字段
     */
    private String pluginParam;

    /**
     * 查找插入点的类型{@link org.sklse.processRegister.db.enums.SearchExtensibilityPointTypeEnum}
     */
    private int SearchExtensibilityPointType;

    /**
     * 插件对应的processid;
     */
    private long processId;
    /**
     * 插件查找的条件1
     * <p/>
     * SearchExtensibilityPointType：interfaceQuery时此字段将被映射为inputs
     * SearchExtensibilityPointType：conditionQuery 时此字段将被映射为preconditionID 和连接符
     */
    private String queryStr1;
    /**
     * 插件查找的条件2
     * <p/>
     * SearchExtensibilityPointType：interfaceQuery时此字段将被映射为outputs
     * SearchExtensibilityPointType：conditionQuery 时此字段将被映射为postconditionID 和连接符
     */
    private String queryStr2;
    /**
     * 对应queryStr1
     */
    private List<String> inputList = new ArrayList<String>();
    /**
     * 对应queryStr2
     */
    private List<String> outputList = new ArrayList<String>();

    private ProcessDTO processDTO;

    private  ConditionDTO preCondition;

    private  ConditionDTO postCondition;
    
    /*This field is added by Zaiwen FENG at 12 NOV 2015 to evaluate the prototype*/
    private APGProcessDTO annotatedProcessDTO;
    
    
    /*This function is added by Zaiwen FENG at 12 NOV 2015 to evaluate the prototype*/
    public APGProcessDTO getAPGProcessDTO(){
    	
    	return annotatedProcessDTO;
    }
    
    /*This function is added by Zaiwen FENG at 12 NOV 2015 to evaluate the prototype*/
    
    public void setAPGProcessDTO(APGProcessDTO annotatedProcessDTO){
    	
    	this.annotatedProcessDTO = annotatedProcessDTO;
    }

    public ConditionDTO getPostCondition() {
        return postCondition;
    }

    public void setPostCondition(ConditionDTO postCondition) {
        this.postCondition = postCondition;
    }

    public ConditionDTO getPreCondition() {
        return preCondition;
    }

    public void setPreCondition(ConditionDTO preCondition) {
        this.preCondition = preCondition;
    }

    public ProcessDTO getProcessDTO() {
        return processDTO;
    }

    public void setProcessDTO(ProcessDTO processDTO) {
        this.processDTO = processDTO;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }
    
    public void setInputList(List<String> input_list){
    	this.inputList = input_list;
    }

    public List<String> getInputList() {

        return inputList;
    }

    public void setOutputList(List<String> output_list){
    	
    	this.outputList = output_list;
    	
    }
    
    public List<String> getOutputList() {

        return outputList;
    }


    public String getQueryStr1() {
        return queryStr1;
    }

    public void setQueryStr1(String queryStr1) {
        this.queryStr1 = queryStr1;
    }

    public String getQueryStr2() {
        return queryStr2;
    }

    public void setQueryStr2(String queryStr2) {
        this.queryStr2 = queryStr2;
    }

    public int getSearchExtensibilityPointType() {
        return SearchExtensibilityPointType;
    }

    public void setSearchExtensibilityPointType(int searchExtensibilityPointType) {
        SearchExtensibilityPointType = searchExtensibilityPointType;
    }

    public long getProcessId() {
        return processId;
    }

    public void setProcessId(long processId) {
        this.processId = processId;
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }

    public int getDataFlowExtensionParttern() {
        return dataFlowExtensionParttern;
    }

    public void setDataFlowExtensionParttern(int dataFlowExtensionParttern) {
        this.dataFlowExtensionParttern = dataFlowExtensionParttern;
    }

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


    public int getControllFlowExtensionPattern() {
        return controllFlowExtensionPattern;
    }

    public void setControllFlowExtensionPattern(int controllFlowExtensionPattern) {
        this.controllFlowExtensionPattern = controllFlowExtensionPattern;
    }

    public int getField() {
        return field;
    }

    public void setField(int field) {
        this.field = field;
    }

    public int getProcessType() {
        return processType;
    }

    public void setProcessType(int processType) {
        this.processType = processType;
    }

    public String getPluginParam() {
        return pluginParam;
    }

    public void setPluginParam(String pluginParam) {
        this.pluginParam = pluginParam;
    }
}
