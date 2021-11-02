package core.rule;

import java.util.HashSet;
import java.util.LinkedList;

public class MaskGraph extends Graph {
    private final Node startNode;
    public HashSet<Node> similarStartNodes = new HashSet<>();

    public MaskGraph(Rule rule) {
        super(Graph.getEdgesFromFactsList(rule.requiredFacts));
        startNode = getNodes().get(0);
        LinkedList<Correspondence> selfCorrespondences = getAllSubGraphsIsomorphicToMask(this);
        for (Correspondence correspondence : selfCorrespondences)
            similarStartNodes.add(correspondence.maskToModelCorrespondence.get(startNode));
    }

    public Node getStartNode() {
        return startNode;
    }
}
