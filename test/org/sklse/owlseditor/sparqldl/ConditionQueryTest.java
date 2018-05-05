package org.sklse.owlseditor.sparqldl;

import junit.framework.TestCase;

/**
 * Created by Ô¬Ê¤ÀÚ on Intellij IDEA
 *
 * @date 2015/5/15
 * @email 745861642@qq.com
 * @description
 */
public class ConditionQueryTest extends TestCase {

    public void testFindSimarityResult() throws Exception {
        double simarityResult = ConditionQuery.isSimarity("delicious", "tasty");
        assert  simarityResult>0;
    }
}