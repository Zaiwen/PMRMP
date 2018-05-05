package org.sklse.processRegister.db.dto;

/*Created by Zaiwen FENG at 11 Nov 2015, for prototype evaluation*/
/*Here, we avoid to interact with database, all of work has been done in the memory*/

import ontology.IdWorker;

import org.sklse.processRegister.db.dto.ProcessDTO.Param;
import org.utils.JsonUtil;

import java.util.*;

public class APGProcessDTO implements Cloneable{

    private long id;
    private String uri;
    private String name;
    private String annotation;
    /**
     * 临时设立 中间过渡字段
     * 
     */
    private String preconditons = "";
    private String postconditions = "";
    /**
     * goto org.sklse.processRegister.db.services.ConditionService get condition entity
     */
    private long preconditionid = -1;
    private long postconditionid = -1;
    private  String param;
    private Param paramObject = new Param();
    
    /*define interface for process node*/
    private List<String> input_list = new ArrayList<String>();
    private List<String> output_list = new ArrayList<String>();
    
    public class Param {
    	/**
    	 * 原来语言模型中的ID
    	 */
    	private long originalId;

		public long getOriginalId() {
			return originalId;
		}

		public void setOriginalId(long originalId) {
			this.originalId = originalId;
		}
    }

    public Set<ConditionDTO> convertString2Object(String condition,int type) throws Exception{
    	Stack<String> result = new Stack<String>();
    	Set<ConditionDTO> conlist = new HashSet<ConditionDTO>();
    	long firstid = -1;
    	long secondid = -1;
    	int connector = -1;
    	boolean hasConnect = false;
    	ConditionDTO c ;
    	String[] con=	condition.split("#");
    	for(String s : con){
    		//System.out.println("Condition 2 Object :  S --> " + s);
    		switch(s){
    			case "(" :  result.push(s);break;
    			case ")" :  result.pop();break;
    			case " " : break;
    			case "AND" :  connector = 1001;hasConnect = true;break;
    			case "OR"  :  connector = 1002;hasConnect = true;break;
    			case "XOR" :  connector = 1003;hasConnect = true;break;
    			default :   c = new ConditionDTO();
    						c = CreatSingleCondition(s,type);
    						conlist.add(c);
    						if(hasConnect){
    							secondid = c.getId();
    							c = new ConditionDTO();
    							c = CreatCompositeCondition(firstid,secondid,connector,type);
        						conlist.add(c);
        						firstid = c.getId();
    						}
    						else firstid = c.getId();			
    		}
    	}
    	System.out.println("------------------------");
    	if(result.empty()) System.out.println("Condition is in right syntax");
    	if(type == 1001) this.preconditionid = firstid;
    	else if(type == 1002) this.postconditionid = firstid;
    	return conlist;
    }
    
    private ConditionDTO CreatSingleCondition(String s, int type) throws Exception {
    	s.replace("#", "");
    	ConditionDTO cdto = new ConditionDTO();
    	cdto.setId(IdWorker.instance.nextId());
    	cdto.setName(s);
    	cdto.setSingleType(1001);
    	cdto.setPositionType(type);
		return cdto;
	}

	private ConditionDTO CreatCompositeCondition(long firstid, long secondid, int connector,int type) throws Exception {
    	ConditionDTO cdto = new ConditionDTO();
    	List<Long> conditionIdList = new ArrayList<Long>();
    	conditionIdList.add(firstid);
    	conditionIdList.add(secondid);
    	cdto.setConditionIdList(conditionIdList);
    	cdto.setPredicate(connector);
    	cdto.setSingleType(1002);
    	cdto.setPositionType(type);
    	cdto.setId(IdWorker.instance.nextId());
    	cdto.setConditionIds(JsonUtil.toJson(conditionIdList));
    	return cdto;
		
	}

	public long getPreconditionid() {
        return preconditionid;
    }

    public void setPreconditionid(long preconditionid) {
        this.preconditionid = preconditionid;
    }

    public long getPostconditionid() {
        return postconditionid;
    }

    public void setPostconditionid(long postconditionid) {
        this.postconditionid = postconditionid;
    }

    public APGProcessDTO(){}
    public APGProcessDTO(long id,String uri,String name,
                      String annotation) {
        this.id = id;
        this.uri = uri;
        this.name = name;
        this.annotation = annotation;
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

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
    
    public List<String> getInputList(){
    	return input_list;
    }

    public void setInputList(List<String> input_list){
    	
    	this.input_list = input_list;
    }
    
    public List<String> getOutputList(){
    	return output_list;
    }
    
    public void setOutputList(List<String> output_list){
    	
    	this.output_list = output_list;
    }

    public APGProcessDTO clone(){
        try {
            return (APGProcessDTO)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return  null;
    }


    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }

	public String getPreconditions() {
		return preconditons;
	}

	public void setPreconditions(String preconditons) {
		this.preconditons = preconditons;
	}

	public String getPostconditions() {
		return postconditions;
	}

	public void setPostconditions(String postconditons) {
		this.postconditions = postconditons;
	}

	public Param getParamObject() {
		return paramObject;
	}

	public void setParamObject(Param paramObject) {
		this.paramObject = paramObject;
	}
}
