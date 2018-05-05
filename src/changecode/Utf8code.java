package changecode;

import java.io.UnsupportedEncodingException;

public class Utf8code {
	public static String changeCode(String value) throws UnsupportedEncodingException{
		return new String(value.getBytes("ISO-8859-1"), "utf-8");
	}

}
