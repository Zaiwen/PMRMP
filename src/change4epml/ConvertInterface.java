package change4epml;

import nl.tue.tm.is.epc.EPC;
import nl.tue.tm.is.epc.EPML;

import org.sklse.processRegister.processGraph.ProcessGraph;
import org.sklse.processRegister.processGraph.ProcessGraphRegister;
import org.sklse.processRegister.util.DocumentTypeRecognizer.DocumentType;

import java.io.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ConvertInterface {

    public static long convert(File file) throws Exception {
        List<Long> converts = converts(file);
        return converts.size() == 0 ? -1 : converts.get(0);
    }

    public static List<Long> converts(File file) throws Exception {
        List<Long> result = new ArrayList<>();
        Map<String, EPC> test = EPML.loadEPML(file).getEpcs();
        Iterator<Entry<String, EPC>> iter = test.entrySet().iterator();
        Entry<String, EPC> entry;
        EPC value;
        System.out.println("EPC converting..................");
        while (iter.hasNext()) {
            entry = iter.next();
            value = entry.getValue();
            System.out.println(value.toString());
            ProcessGraphTemp pgt = EpmlMapping.mapping(value);
            pgt.Condition2Object();
            System.out.println(pgt.toString());
            ProcessGraph pg = pgt.convert2processGraph();
            pg.getPmdto().setEpcid(Long.parseLong(value.getId()));
            pg.getPmdto().setName(value.getName());
            pg.getPmdto().setCreateTime(new Date(System.currentTimeMillis()));
            pg.getPmdto().setLanguage(DocumentType.EPC2_0);
            new ProcessGraphRegister().register(pg);
            System.out.println("EPC Register finished..................");
            result.add(pg.getPmdto().getId());
        }
        return result;
    }


    public static File string2File(String res, String name) {
        String filePath = finalvariable.BasicPathVariable.processPath + name;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        File distFile = new File(filePath);
        try {

            if (!distFile.getParentFile().exists()) distFile.getParentFile().mkdirs();
            bufferedReader = new BufferedReader(new StringReader(res));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile));
            char buf[] = new char[1024];         //字符缓冲区
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return distFile;
    }

}
