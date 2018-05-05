package DataStructure;
public class Vertice {
	public int No;
	public Type type;
	public String label;
	
	public Vertice(int No){
		this.No = No;
	}
	public Vertice(int No, Type type){
		this.No = No;
		this.type = type;
		this.label = null;
	}
	
	public Vertice(int No, Type type, String label){
		this.No = No;
		this.type = type;
		this.label = label;
	}
	
	public void setAttributes(Type type, String label){
		this.type = type;
		this.label = label;
	}
	
	public void setType(Type type){
		this.type = type;
	}
	
	public void setLabel(String label){
		this.label = label;
	}
}
