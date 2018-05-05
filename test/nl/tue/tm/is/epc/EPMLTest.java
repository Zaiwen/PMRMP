package nl.tue.tm.is.epc;

import junit.framework.TestCase;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by 袁胜磊 on Intellij IDEA
 *
 * @date 2015/4/29
 * @email 745861642@qq.com
 * @description
 */
public class EPMLTest extends TestCase {

    public void testLoadEPML() throws Exception {
        Map<String, EPC> test = EPML.loadEPML("E:\\workspace\\Remco'sSearcher\\models\\modelpairs\\1Be_1y63.epml").getEpcs();
        assert test.size()>0;
        Iterator<Map.Entry<String, EPC>> iterator = test.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, EPC> next = iterator.next();
            EPC value = next.getValue();
            System.out.println(value.toString());
        }
    }
}