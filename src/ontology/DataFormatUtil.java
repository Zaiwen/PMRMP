package ontology;

public class DataFormatUtil {
	public static String transferStrToDBStr(String str){
		StringBuffer buffer = new StringBuffer();
		for(char tmp : str.toCharArray()){
			if(Character.isUpperCase(tmp)){
				buffer.append('#').append((tmp+"").toLowerCase());
			}else if(tmp == '#'){
				buffer.append("##");
			}else{
				buffer.append(tmp);
			}
		}
		return buffer.toString();
	}
	public static String transferDBStrToStr(String DBStr){
		StringBuffer buffer = new StringBuffer();
		boolean preIsWell = false;
		for(char tmp : DBStr.toCharArray()){
			if(preIsWell){
				buffer.append((tmp+"").toUpperCase());
				preIsWell = false;
			}else{
				if(tmp == '#')
					preIsWell = true;
				else
					buffer.append(tmp);
			}
		}
		return buffer.toString();
	}
}
