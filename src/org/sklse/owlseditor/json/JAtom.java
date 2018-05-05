package org.sklse.owlseditor.json;

import java.util.List;

/**
 * Created by yuan on 2015/3/22.
 */
public class JAtom {
    private String predicate;
    private List<argument> arguments;

    public String getPredicate() {
        return predicate;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

    public List<argument> getArguments() {
        return arguments;
    }

    public void setArguments(List<argument> arguments) {
        this.arguments = arguments;
    }

    class argument {
        String name;
        String type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
