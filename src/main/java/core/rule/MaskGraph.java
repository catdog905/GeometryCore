package core.rule;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import core.correspondence.CorrespondenceWithoutNullElements;
import core.correspondence.FullCorrespondence;
import core.model.facts.objects.GeometryObject;

public class MaskGraph extends Graph {
    private final Node startNode;
    private Rule rule;
    public HashSet<Node> similarStartNodes = new HashSet<>();
    public HashMap<Edge, HashSet<Edge>> similarEdges = new HashMap<>();

    public MaskGraph(Rule rule) {
        super(Graph.getGraphFromFactsList(rule.requiredFacts));
        this.rule = rule;
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

    public Graph getConsequencesGraph(Correspondence correspondence) {
        HashMap<GeometryObject, GeometryObject> geometryObjectsCorrespondence =
                new HashMap<>(correspondence.maskToModelCorrespondence.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().getFact().object,
                        e -> e.getValue().getFact().object
                )));
        return new Graph(getGraphFromFactsList(rule.consequences.stream()
            .map(x -> x.createNewSimilarObject(new CorrespondenceWithoutNullElements(
                    new FullCorrespondence(geometryObjectsCorrespondence))))
            .collect(Collectors.toCollection(LinkedList::new))));
    }
}
