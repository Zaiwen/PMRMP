package nl.tue.tm.is.epc;

import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 袁胜磊
 * @date 2015/3/4
 * @description
 */
public class EPML {

    Map<String,EPC> epcs;

    public EPML() {
        epcs=new HashMap<String, EPC>();
    }

	public static EPML loadEPML(File file) {
		 EPML result = new EPML();

	        DefaultHandler handler = new EPMLParser(result);
	        SAXParserFactory factory = SAXParserFactory.newInstance();

	        try {
	            SAXParser saxParser = factory.newSAXParser();
	            saxParser.parse(file, handler);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return result;
	}
    

    public static EPML loadEPML(String fileLocation){
        EPML result = new EPML();

        DefaultHandler handler = new EPMLParser(result);
        SAXParserFactory factory = SAXParserFactory.newInstance();

        try {
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(new File(fileLocation), handler);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public Map<String, EPC> getEpcs() {
        return epcs;
    }

    public void setEpcs(Map<String, EPC> epcs) {
        this.epcs = epcs;
    }





}
