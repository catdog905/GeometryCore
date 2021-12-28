package core.rule;

import java.util.LinkedList;

public class GraphContent {
    public LinkedList<Node> nodes;
    public LinkedList<Edge> edges;

    public GraphContent(LinkedList<Node> nodes, LinkedList<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }
}
