package change4epml;

public class Edge {
	
	private long srcid;
	private long tgtid;
	private long id;
	private long originalId;
	
	public Edge(){}
	
	public Edge(long src,long tgt,int id){
		this.srcid = src;
		this.tgtid = tgt;
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long l) {
		this.id = l;
	}

	public long getSrcid() {
		return srcid;
	}

	public void setSrcid(long srcid) {
		this.srcid = srcid;
	}

	public long getTgtid() {
		return tgtid;
	}

	public void setTgtid(long tgtid) {
		this.tgtid = tgtid;
	}

	public long getOriginalId() {
		return originalId;
	}

	public void setOriginalId(long originalId) {
		this.originalId = originalId;
	}

}
