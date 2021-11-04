package core.rule;

import core.facts.Fact;

public class Edge {
    public Node from;
    public Node to;
    public Fact fact;
    public Class type;

    public Edge(Node from, Node to, Fact fact) {
        this.from = from;
        this.to = to;
        this.fact = fact;
        type = fact.getClass();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Edge))
            return false;
        return ((Edge) obj).type.equals(type);
    }
}
