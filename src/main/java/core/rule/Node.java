package core.rule;

import core.facts.ExistFact;
import core.objects.GeometryObject;

public class Node {
    private ExistFact fact;
    public Node(ExistFact existFact) {
        fact = existFact;
    }

    public Node(GeometryObject geometryObject){
        this(new ExistFact(geometryObject));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Node))
            return false;
        return fact.object.getClass().equals(((Node) obj).fact.object.getClass());
    }
}
