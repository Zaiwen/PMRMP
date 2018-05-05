package org.sklse.epceditor;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import databaseaccess.Access;

public class EPC {
	/**
	 * 判断一个流程名是否已存在
	 * @param user 用户名
	 * @param name 流程名
	 * @return 某用户下的某流程名是否存在
	 * */
	private static boolean isExist(String user,String name) throws Exception{
		Access ac=new Access();
		ac.connDB("bpep");
		String sql="select name from epml where user=? and name=?";
		PreparedStatement st=ac.getCon().prepareStatement(sql);
		st.setString(1, user);
		st.setString(2, name);
		ResultSet rs=st.executeQuery();
		if(rs.next()){
			ac.closeDB();
			return true;
		}else{
			ac.closeDB();
			return false;
		}
	}
	/**
	 * 创建一个新的EPC流程
	 * 
	 * @param user 用户名
	 * @param name 流程名
	 * @return 创建操作是否成功，如果流程名已存在则不成功
	 * */
	public static boolean create(String user,String  name) throws Exception{
		if(isExist(user,name)){
			return false;
		}else{
			Access ac=new Access();
			String str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			ac.connDB("bpep");
			String sql = "insert into epml values(null,?,?,null,null,null,?,?,?)";
			PreparedStatement st=ac.getCon().prepareStatement(sql);
			st.setString(1, user);
			st.setString(2, name);
			st.setString(3, str);
			st.setString(4, str);
			st.setString(5, str);
			st.execute();
			ac.closeDB();
			return true;
		}
	}
	/**
	 * 获取所有用户的流程相关信息
	 * 
	 * @return 包含流程简要信息的JSON字符串[{user,name,time}]
	 * */
	public static String getList() throws Exception{
		JSONArray arr = new JSONArray();
		Access ac=new Access();
		ac.connDB("bpep");
		String sql = "select user,name,edit_time from epml";
		PreparedStatement st=ac.getCon().prepareStatement(sql);
		ResultSet rs=st.executeQuery();
		while(rs.next()){
			JSONObject obj=new JSONObject();
			obj.put("user", rs.getString(1));
			obj.put("name", rs.getString(2));
			obj.put("time", rs.getDate(3) + " " +rs.getTime(3));
			arr.put(obj);
		}
		return arr.toString();
	}
	/**
	 * 获取某个用户所属的所有EPC流程的相关信息
	 * 
	 * @param user 用户名
	 * @return 包含所有流程简要信息的JSON字符串[{name,time}]
	 * */
	public static String getList(String user) throws Exception{
		JSONArray arr = new JSONArray();
		Access ac=new Access();
		ac.connDB("bpep");
		String sql = "select name,edit_time from epml where user=?";
		PreparedStatement st=ac.getCon().prepareStatement(sql);
		st.setString(1, user);
		ResultSet rs=st.executeQuery();
		while(rs.next()){
			JSONObject obj=new JSONObject();
			obj.put("name", rs.getString(1));
			obj.put("time", rs.getDate(2) + " " +rs.getTime(2));
			arr.put(obj);
		}
		ac.closeDB();
		return arr.toString();
	}
	/**
	 * 对已有的EPC流程进行重命名，如果该流程不存在或新名称已被使用，则操作失败返回false
	 * 
	 * @param user EPC流程所属的用户
	 * @param oldName EPC流程原本的名称
	 * @param newName 更改后的名称
	 * @return 重命名操作是否成功
	 * */
	public static boolean rename(String user,String oldName,String newName) throws Exception{
		if(isExist(user,newName)||(!isExist(user,oldName))){
			return false;
		}else{
			Access ac=new Access();
			ac.connDB("bpep");
			String sql = "update epml set name=? where user=? and name=?";
			PreparedStatement st=ac.getCon().prepareStatement(sql);
			st.setString(1, newName);
			st.setString(2, user);
			st.setString(3, oldName);
			st.execute();
			ac.closeDB();
			return true;
		}
		
	}
	/**
	 * 保存流程（同时包含JSON与EPML两种格式），如果流程不存在，则创建该流程,该操作会更新edit_time字段
	 * 
	 * @param user 用户名
	 * @param name 流程名
	 * @param json JSON字符串
	 * @param epml XML字符串
	 * @throws Exception 
	 * */
	public static void save(String user,String name,String json,String epml) throws Exception{
		if(!isExist(user,name)){
			create(user,name);
		}
		Access ac=new Access();
		ac.connDB("bpep");
		String sql = "update epml set json=?,epml=?,edit_time=? where user=? and name=?";
		PreparedStatement st=ac.getCon().prepareStatement(sql);
		st.setString(1, json);
		st.setString(2, epml);
		st.setString(3, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		st.setString(4, user);
		st.setString(5, name);
		st.execute();
		ac.closeDB();
	}
	/**
	 * 打开某个流程，该操作会刷新view_time
	 * 
	 * @param user 用户名
	 * @param name 流程名
	 * @return 包含该流程详细信息的JSON字符串
	 * @throws Exception 
	 * */
	public static String open(String user,String name) throws Exception{
		if(!isExist(user,name)){
			throw new Exception("流程不存在user=" +user +"&name="+name);
		}
		Access ac=new Access();
		ac.connDB("bpep");
		String sql = "select json from epml where user=? and name=?";
		PreparedStatement st=ac.getCon().prepareStatement(sql);
		st.setString(1, user);
		st.setString(2, name);
		ResultSet rs=st.executeQuery();
		String json=rs.getString(1);
		// 更新view_time字段
		sql="update epml set view_time=?";
		st=ac.getCon().prepareStatement(sql);
		st.setString(1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		ac.closeDB();
		return json;
	}
	
	public static void main(String args[]) throws Exception{
		/*----------isNameExist------------*/
//		System.out.println("----------isNameExist-----------");
//		System.out.println("test\thello epc\t--\t"+isExist("hello epc","test"));
//		System.out.println("test\thello world\t--\t"+isExist("hello world","test"));
//		System.out.println("demo\thello epc\t--\t"+isExist("hello epc","demo"));
//		System.out.println(create("test","fist epml"));
//		System.out.println(getList("test"));
		System.out.println(rename("test","123","4567"));
		
	}
}
