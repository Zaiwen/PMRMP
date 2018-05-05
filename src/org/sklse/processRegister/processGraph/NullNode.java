package org.sklse.processRegister.processGraph;

public class NullNode implements GraphNode {

	
	private long id;
	private long preid;
	private long postid;
	
	public long getDTOId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setDTOId(long id) {
		this.id = id;
	}

	public void setId(long id){
		this.id = id;
	}
	
	public long getId(){
		return id;
	}

	public long getPreid() {
		return preid;
	}

	public void setPreid(long preid) {
		this.preid = preid;
	}

	public long getPostid() {
		return postid;
	}

	public void setPostid(long postid) {
		this.postid = postid;
	}

	@Override
	public long getOriginalId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setOriginalId(long id) {
		// TODO Auto-generated method stub
		
	}

}
