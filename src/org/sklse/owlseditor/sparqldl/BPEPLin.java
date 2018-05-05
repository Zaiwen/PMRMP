package org.sklse.owlseditor.sparqldl;

import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.POS;
import edu.sussex.nlp.jws.ICFinder;
import edu.sussex.nlp.jws.Lin;

import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Ô¬Ê¤ÀÚ on Intellij IDEA
 *
 * @date 2015/5/15
 * @email 745861642@qq.com
 * @description
 */
public class BPEPLin extends Lin {

    private IDictionary dict;

    public BPEPLin(IDictionary iDictionary, ICFinder icFinder) {
        super(iDictionary, icFinder);
        dict = iDictionary;
    }

    @Override
    public TreeMap<String, Double> lin(String var1, String var2, String var3) {
        TreeMap var4 = new TreeMap();
        IIndexWord var5 = null;
        IIndexWord var6 = null;
        if (var3.equalsIgnoreCase("n")) {
            var5 = this.dict.getIndexWord(var1, POS.NOUN);
            var6 = this.dict.getIndexWord(var2, POS.NOUN);
        }

        if (var3.equalsIgnoreCase("v")) {
            var5 = this.dict.getIndexWord(var1, POS.VERB);
            var6 = this.dict.getIndexWord(var2, POS.VERB);
        }
        if (var3.equalsIgnoreCase("a")) {
            var5 = this.dict.getIndexWord(var1, POS.ADJECTIVE);
            var6 = this.dict.getIndexWord(var2, POS.ADJECTIVE);
        }
        if (var3.equalsIgnoreCase("r")) {
            var5 = this.dict.getIndexWord(var1, POS.ADVERB);
            var6 = this.dict.getIndexWord(var2, POS.ADVERB);
        }


        if (var5 != null && var6 != null) {
            List var7 = var5.getWordIDs();
            List var8 = var6.getWordIDs();
            int var9 = 1;

            for (Iterator var12 = var7.iterator(); var12.hasNext(); ++var9) {
                int var14 = 1;

                for (Iterator var15 = var8.iterator(); var15.hasNext(); ++var14) {
                    double var17 = this.lin(var1, var9, var2, var14, var3);
                    var4.put(var1 + "#" + var3 + "#" + var9 + "," + var2 + "#" + var3 + "#" + var14, Double.valueOf(var17));
                }
            }

            return var4;
        } else {
            return var4;
        }
    }

    @Override
    public double max(String var1, String var2, String var3) {
        double var4 = 0.0D;
        TreeMap var6 = this.lin(var1, var2, var3);
        Iterator var7 = var6.keySet().iterator();

        while(var7.hasNext()) {
            String var8 = (String)var7.next();
            double var9 = ((Double)var6.get(var8)).doubleValue();
            if(var9 > var4) {
                var4 = var9;
            }
        }

        return var4;

    }
}
