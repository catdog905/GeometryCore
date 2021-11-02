package core.rule;

public class MaskGraph extends Graph {
    private Node startNode;
    public MaskGraph(Rule rule) {
        super(Graph.getNodesFromFactsList(rule.requiredFacts),
                Graph.getEdgesFromFactsList(rule.requiredFacts));
        startNode = getNodes().get(0);
    }

    public Node getStartNode() {
        return startNode;
    }
}
