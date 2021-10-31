package core.rule;

public class MaskGraph extends Graph {
    public MaskGraph(Rule rule) {
        super(Graph.getNodesFromFactsList(rule.requiredFacts),
                Graph.getEdgesFromFactsList(rule.requiredFacts));
    }
}
