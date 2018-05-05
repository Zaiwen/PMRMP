package databaseaccess;

public class UsersData {
	private int id;
	private String name;
	private String pass;
	private int permission;
	private String email;
	private String address;
	private String details;
	private String reasons;
	public int getId(){
		return this.id;
	}
	public void setId(int id){
		this.id=id;
	}
	public String getName(){
		return this.name;
	}
	public void setNmae(String name){
		this.name=name;
	}
	public String getPass(){
		return this.pass;
	}
	public void setPass(String pass){
		this.pass=pass;
	}
	public int getPermission(){
		return this.permission;
	}
	public void setPermission(int permission){
		this.permission=permission;
	}
	public String getEmail(){
		return this.email;
	}
	public void setEmail(String email){
		this.email=email;
	}
	public String getAddress(){
		return this.address;
	}
	public void setAddress(String address){
		this.address=address;
	}
	public String getDetails(){
		return this.details;
	}
	public void setDetails(String details){
		this.details=details;
	}
	public String getReasons(){
		return this.reasons;
	}
	public void setReasons(String reasons){
		this.reasons=reasons;
	}
	public UsersData(){}
	public UsersData(int id,String name,String pass,int permission,String email,String address,String details,String reasons){
		this.id=id;
		this.name=name;
		this.pass=pass;
		this.permission=permission;
		this.email=email;
		this.address=address;
		this.details=details;
		this.reasons=reasons;
	}

}
