package org.sklse.owlseditor.sparqldl;

import edu.sussex.nlp.jws.ICFinder;
import edu.sussex.nlp.jws.JWS;
import edu.sussex.nlp.jws.Lin;

/**
 * Created by Ô¬Ê¤ÀÚ on Intellij IDEA
 *
 * @date 2015/5/15
 * @email 745861642@qq.com
 * @description
 */
public class BPEPJWS extends JWS {


    private String icfilename = "";

    public BPEPJWS(String s, String s1, String s2) {
        super(s, s1, s2);
        icfilename = s + "/" + s1 + "/WordNet-InfoContent-" + s1 + "/" + s2;
    }

    public BPEPJWS(String var1, String var2) {
        super(var1, var2);
        icfilename =  var1 + "/" + var2 + "/WordNet-InfoContent-" + var2 + "/ic-semcor.dat";
    }

    Lin bpepLin;

    @Override
    public Lin getLin() {
        if (bpepLin == null) {
            bpepLin = new BPEPLin(super.getDictionary(), new ICFinder(this.icfilename));
        }
        return bpepLin;
    }
}
