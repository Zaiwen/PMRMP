package change4epml;


import nl.tue.tm.is.epc.EPC;
import nl.tue.tm.is.epc.EPML;

import org.sklse.processRegister.expander.*;
import org.sklse.processRegister.processGraph.LoadProcessGraph;
import org.sklse.processRegister.processGraph.ProcessGraph;
import org.sklse.processRegister.processGraph.ProcessGraphRegister;
import org.sklse.processRegister.util.DocumentTypeRecognizer.DocumentType;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Test {	
	
    public static void main(String[] args) throws Exception {

    	
    	ProcessGraphIOService intance = ProcessGraphIOService.intance;
    	
    	
    	
    	/*
        int count = 0;
        count++;
        Map<String, EPC> test = EPML.loadEPML("C:\\ALLEPCs.epml").getEpcs();
        Iterator<Entry<String, EPC>> iter = test.entrySet().iterator();
        Entry<String, EPC> entry;
        EPC value;
        System.out.println("EPC ..................");
        
        ProcessGraphIOService intance = ProcessGraphIOService.intance;
      	ProcessGraph processGraph = intance.ProcessGraphLoad(530103278189821967L);
        intance.ProcessGraphrRgister(processGraph);
        
        while (iter.hasNext()) {
            entry = iter.next();
            value = entry.getValue();
            //if(checkBan(value.getId())) continue;
            System.out.println("EPCID : " + value.getId());
            System.out.println(value.toString());
            ProcessGraphTemp pgt = EpmlMapping.mapping(value);
            if(pgt == null) continue;
            System.out.println(pgt.toString());
            ProcessGraph pg = pgt.convert2processGraph();
            pg.getPmdto().setEpcid(Long.parseLong(value.getId()));
            pg.getPmdto().setName(value.getName());
            pg.getPmdto().setCreateTime(new Date(System.currentTimeMillis()));
            pg.getPmdto().setLanguage(DocumentType.EPC2_0);
            System.out.println(pg.debug());
            new ProcessGraphRegister().register(pg);
            EPC e = ProcessGraph2Epml.Convert(pg);
            System.out.println(e.toString());
            System.out.println(e.getNodes().size());
            System.out.println(e.Epc2Epml(e));
            ++count;
            if (count == 1) break;
        }
       
	*/
    }
}
