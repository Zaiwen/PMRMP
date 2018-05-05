package org.sklse.processRegister.util;

import org.sklse.processRegister.util.DocumentTypeRecognizer.DocumentType;

import java.util.HashMap;
import java.util.Map;

public class Paths {
    private Map<DocumentType, String> paths;
    
    public String getPathFor(DocumentType type) {
        return(paths.get(type));
    }
    
    // implement singleton pattern
    protected Paths() {
        paths = new HashMap<DocumentType, String>();
        paths.put(null, "webapps\\wsrr\\process\\");
        paths.put(DocumentType.BPEL2_0, paths.get(null) + "BPEL2.0\\");
        paths.put(DocumentType.OWLS1_0, paths.get(null) + "OWLS1.0\\");
    }
    private static Paths instance = null;
    
    public static Paths getInstance() {
        if (instance == null) {
            instance = new Paths();
        }
        return instance;
    }
    
    // test
    public static void main(String[] args) {
        Paths paths = Paths.getInstance();
        System.out.println(paths.getPathFor(DocumentType.BPEL2_0));
    }
}
