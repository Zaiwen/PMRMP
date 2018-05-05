package nl.tue.tm.is.epc;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Test {

	public static void main(String[]args){
		Map <String,EPC> test = EPML.loadEPML("C:\\1.epml").getEpcs();
		Iterator<Entry<String, EPC>> iter = test.entrySet().iterator();
		Entry<String, EPC> entry;
		String key;
		EPC value;
		while (iter.hasNext()) {
		    entry = iter.next();
		    key = entry.getKey();
		    value = (EPC)entry.getValue();
		    
		    System.out.println(value.toString());
		}
		
	}
	
	
		
	}
