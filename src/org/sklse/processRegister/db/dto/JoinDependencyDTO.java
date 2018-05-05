package org.sklse.processRegister.db.dto;

import org.sklse.processRegister.db.dto.EventDTO.Param;
import org.utils.JsonUtil;

public class JoinDependencyDTO   {
    
    private long id;
    private JoinType type;
    private boolean sync;
    private long following;

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
    
    public enum JoinType {
        AND, OR, XOR,
    }

    public JoinDependencyDTO(){}
    public JoinDependencyDTO(long id,JoinType type,boolean sync,long following){
        this.id = id;
        this.type = type;
        this.sync = sync;
        this.following = following;
    }
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public JoinType getType() {
        return type;
    }
    public void setType(JoinType type) {
        this.type = type;
    }
    public void setType(String type) {
        if(type.equalsIgnoreCase(JoinType.XOR.toString())) {
            this.setType(JoinType.XOR);
        } else if ( type.equalsIgnoreCase(JoinType.OR.toString())) {
            this.setType(JoinType.OR);
        } else {
            this.setType(JoinType.AND);
        }
    }
    public boolean getSync() {
        return sync;
    }
    public void setSync(boolean sync) {
        this.sync = sync;
    }

    public boolean isSync() {
        return sync;
    }


    public long getFollowing() {
        return following;
    }

   
    public void setFollowing(long following) {
        this.following = following;
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
