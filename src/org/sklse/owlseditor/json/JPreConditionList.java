package org.sklse.owlseditor.json;

import java.util.List;

/**
 * Created by yuan on 2015/3/23.
 */
public class JPreConditionList extends JConnectorContainer {
    private List<JPreCondition> conditions = null;

    public List<JPreCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<JPreCondition> conditions) {
        this.conditions = conditions;
    }


}
