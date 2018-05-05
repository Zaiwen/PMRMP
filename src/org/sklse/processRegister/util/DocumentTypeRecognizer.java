package org.sklse.processRegister.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;

public class DocumentTypeRecognizer {
    public DocumentTypeRecognizer() {}
    public static String BPEL20_EXE_PREFIX = "http://docs.oasis-open.org/wsbpel/2.0/process/executable";
    public static String BPEL20_ABS_PREFIX = "http://docs.oasis-open.org/wsbpel/2.0/process/abstract";
    public static String OWLS10_OWL_PREFIX = "http://www.w3.org/2002/07/owl#";
    public enum DocumentType {
        BPEL2_0,
        OWLS1_0, EPC2_0,
    }
    public static DocumentType recognize(File file) throws DocumentException, UnrecognizedTypeException {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(file);           // throws DocumentException when failed
        Element root = doc.getRootElement();
        
        String defaultNs = root.getNamespaceForPrefix("").getText();
        if ( defaultNs.equalsIgnoreCase(BPEL20_EXE_PREFIX) || defaultNs.equalsIgnoreCase(BPEL20_ABS_PREFIX)) {
            return DocumentType.BPEL2_0;
        } else {
            String owlNs = root.getNamespaceForPrefix("owl").getText(),
                   processNs = root.getNamespaceForPrefix("process").getText();
            if ( owlNs.equalsIgnoreCase(OWLS10_OWL_PREFIX) && !processNs.isEmpty()) {
                return DocumentType.OWLS1_0;
            }
            else throw new UnrecognizedTypeException();
        }
    }
    public static void main(String[] args) {
        try {
            File file = new File(
                    false?
                            "C:\\Documents and Settings\\CIR\\����\\BPEL_Samples\\BPEL\\marketplace\\marketplace.bpel":
                            "C:\\Documents and Settings\\CIR\\����\\BravoAirProcess.owl");
            DocumentType type = DocumentTypeRecognizer.recognize(file);
            System.out.println(type);
            

            SAXReader reader = new SAXReader();
            Document doc = reader.read(file);           // throws DocumentException when failed
            Element root = doc.getRootElement();
            
            System.out.println(root.getNamespaceForPrefix("owl").getText());
            
            
            
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnrecognizedTypeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
