package org.sklse.owlseditor.util;

public class Utils {
	public static String getXml(String str) {
		str = str.trim().replaceAll("&", "&amp;");
		str = str.trim().replaceAll("<", "&lt;");
		str = str.trim().replaceAll(">", "&gt;");
		str = str.trim().replaceAll("'", "&apos;");
		str = str.trim().replaceAll("\\\"", "&quot;");
		return str;
	}
}
