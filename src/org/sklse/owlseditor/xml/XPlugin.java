package org.sklse.owlseditor.xml;

public class XPlugin {
	private String Name = null;
	private XExtensional_Point Extensional_Point=null;
	private XExtensional_Fragment Extensional_Fragment=null;
	public XExtensional_Point getExtensional_Point() {
		return Extensional_Point;
	}
	public void setExtensional_Point(XExtensional_Point extensionalPoint) {
		Extensional_Point = extensionalPoint;
	}
	
	
	public XExtensional_Fragment getExtensional_Fragment() {
		return Extensional_Fragment;
	}
	public void setExtensional_Fragment(XExtensional_Fragment extensionalFragment) {
		Extensional_Fragment = extensionalFragment;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name = name;
	}
	
	
	
}