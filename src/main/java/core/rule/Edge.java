package core.rule;

import core.facts.Fact;

public class Edge {
    public Node from;
    public Node to;
    private Fact fact;

    public Edge(Node from, Node to, Fact fact) {
        this.from = from;
        this.to = to;
        this.fact = fact;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Edge))
            return false;
        return ((Edge) obj).fact.getClass() == fact.getClass();
    }
}
