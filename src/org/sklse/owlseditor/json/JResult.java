package org.sklse.owlseditor.json;

import java.util.List;

/**
 * Created by yuan on 2015/3/22.
 */
public class JResult extends JConnectorContainer {
    private List<JEffect> effects;

    public List<JEffect> getEffects() {
        return effects;
    }

    public void setEffects(List<JEffect> effects) {
        this.effects = effects;
    }
}
