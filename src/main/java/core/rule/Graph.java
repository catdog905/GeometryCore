package core.rule;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;

import core.facts.ExistFact;
import core.facts.Fact;
import core.objects.GeometryObject;

public class Graph {
    private HashMap<Node, LinkedList<Edge>> adjacencyList = new HashMap<>();
    public Graph(LinkedList<Node> nodes, LinkedList<Edge> edges) {
        for (Node node : nodes) {
            LinkedList<Edge> tempList = edges.stream().filter(x -> x.from == node)
                    .collect(Collectors.toCollection(LinkedList::new));
            adjacencyList.put(node, tempList);
        }
    }

    public static LinkedList<Node> getNodesFromFactsList(Collection<Fact> list) {
        return list.stream().filter(x -> x instanceof ExistFact)
                .map(x -> new Node((ExistFact) x))
                .collect(Collectors.toCollection(LinkedList::new));
    }
    public static LinkedList<Edge> getEdgesFromFactsList(Collection<Fact> list) {
        LinkedList<Fact> notExistFacts =  list.stream().filter(x -> !(x instanceof ExistFact))
                .collect(Collectors.toCollection(LinkedList::new));
        LinkedList<Edge> edges = new LinkedList<>();
        for (Fact fact : notExistFacts) {
            LinkedList<? extends GeometryObject> subObjects = fact.getAllSubObjects();
            for (GeometryObject to : subObjects) {
                for (GeometryObject from : subObjects) {
                    if (to == from)
                        continue;
                    edges.add(new Edge(new Node(to), new Node(from), fact));
                }
            }
        }
        return edges;
    }
}
