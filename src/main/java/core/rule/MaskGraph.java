package core.rule;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

public class MaskGraph extends Graph {
    private final Node startNode;
    public HashSet<Node> similarStartNodes = new HashSet<>();
    public HashMap<Edge, HashSet<Edge>> similarEdges = new HashMap<>();

    public MaskGraph(Rule rule) {
        super(Graph.getEdgesFromFactsList(rule.requiredFacts));
        startNode = getNodes().get(0);
        for (LinkedList<Edge> edges : adjacencyList.values()) {
            for (Edge edge : edges)
                similarEdges.put(edge, new HashSet<>());
        }
        LinkedList<Correspondence> selfCorrespondences = getAllSubGraphsIsomorphicToMask(this);
        for (Correspondence correspondence : selfCorrespondences) {
            similarStartNodes.add(correspondence.maskToModelCorrespondence.get(startNode));
            for (Map.Entry<Edge, Edge> entry :
                    correspondence.maskToModelEdgesCorrespondence.entrySet()) {
                if (entry.getKey() != entry.getValue())
                        similarEdges.get(entry.getKey()).add(entry.getValue());
            }
        }
    }

    public Node getStartNode() {
        return startNode;
    }
}
