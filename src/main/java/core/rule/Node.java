package core.rule;


import core.model.facts.ExistFact;
import core.model.facts.objects.GeometryObject;

public class Node {
    private ExistFact fact;
    public Node(ExistFact existFact) {
        fact = existFact;
    }

    public Node(GeometryObject geometryObject){
        this(new ExistFact(geometryObject));
    }

    public boolean isomorphic(Node obj) {
        return fact.object.getClass().equals(((Node) obj).fact.object.getClass());
    }

    public ExistFact getFact() {
        return fact;
    }

    @Override
    public int hashCode() {
        return fact.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.equals(fact);
    }
}
