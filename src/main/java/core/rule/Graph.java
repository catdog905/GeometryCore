package core.rule;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
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

    public Graph(Node node) {
        adjacencyList.put(node, new LinkedList<>());
    }

    public Graph(Graph graph) {
        this(new HashMap<>(graph.adjacencyList));
    }

    public Graph(HashMap<Node, LinkedList<Edge>> adjacencyList) {
        this.adjacencyList = adjacencyList;
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

    public LinkedList<Edge> getEdges(Node node) {
        return adjacencyList.get(node);
    }

    public LinkedList<Edge> getEdges() {
        LinkedList<Edge> edges = new LinkedList<>();
        for (LinkedList<Edge> value : adjacencyList.values())
            edges.addAll(value);
        return edges;
    }

    public LinkedList<Node> getToNodes(LinkedList<Edge> edges) {
        return edges.stream().map(x -> x.to).collect(Collectors.toCollection(LinkedList::new));
    }

    public LinkedList<Node> getNodes() {
        return new LinkedList<>(adjacencyList.keySet());
    }

    public HashMap<Class, LinkedList<Edge>> getTypedHashMapOfEdges(LinkedList<Edge> edges) {
        HashMap<Class, LinkedList<Edge>> hashMap = new HashMap<>();
        for (Edge edge : edges) {
            hashMap.putIfAbsent(edge.type, new LinkedList<>());
            hashMap.get(edge.type).add(edge);
        }
        return hashMap;
    }

    public void addNode(Node node, LinkedList<Edge> edges) {
        adjacencyList.put(node, new LinkedList<>());
        for (Edge edge : edges) {
            adjacencyList.get(edge.from).add(edge);
        }
    }

    private boolean checkBranchesCorrespondence(HashMap<Node, Node> maskToModelNodesMatch1,
                                                HashMap<Node, Node> maskToModelNodesMatch2) {
        for (Map.Entry<Node, Node> entry : maskToModelNodesMatch1.entrySet()) {
            if (maskToModelNodesMatch2.containsKey(entry.getKey()) &&
                maskToModelNodesMatch2.get(entry.getKey()) != entry.getValue())
                return false;
        }
        return true;
    }

    private Correspondence mergeBranches(Correspondence branch1, Correspondence branch2) {
        Graph newGraph = new Graph(branch1.modelSubGraph);
        HashMap<Node, Node> maskToModelMatch = new HashMap<>(branch1.maskToModelCorrespondence);
        for (Node node2 : branch2.modelSubGraph.getNodes()) {
            LinkedList<Edge> edges = branch2.modelSubGraph.getEdges(node2);
            edges.addAll(branch1.modelSubGraph.getEdges().stream().filter(x -> x.to == node2)
                    .collect(Collectors.toCollection(LinkedList::new)));
            newGraph.addNode(node2, edges);
        }
        maskToModelMatch.putAll(branch2.maskToModelCorrespondence);
        return new Correspondence(newGraph, maskToModelMatch);
    }

    private Graph mergeBranches(Node mergeNode, Graph branch) {
        LinkedList<Edge> edges = getEdges(mergeNode);
        edges.addAll(getEdges().stream().filter(x -> x.to == mergeNode)
                .collect(Collectors.toCollection(LinkedList::new)));
        Graph newBranch = new Graph(branch);
        newBranch.addNode(mergeNode, edges);
        return newBranch;
    }

    public LinkedList<Correspondence> getAllSubGraphsIsomorphicToMask(MaskGraph mask) {
        LinkedList<Correspondence> correspondenceList = new LinkedList<>();
        LinkedList<Node> usedStarts = new LinkedList<>();
        for (Node node : getNodes())
            if (node.equals(mask.getStartNode()) && !usedStarts.contains(node)) {
                LinkedList<Correspondence> curCorrespondences = dfs(mask, node, mask.getStartNode(),
                        new LinkedList<>(), new LinkedList<>(), new HashMap<>());
                correspondenceList.addAll(curCorrespondences);
                for (Correspondence correspondence : curCorrespondences) {
                    usedStarts.addAll(mask.similarStartNodes.stream()
                            .map(x -> correspondence.maskToModelCorrespondence.get(x))
                            .collect(Collectors.toCollection(LinkedList::new)));
                }
            }
        return correspondenceList;
    }

    public LinkedList<Correspondence> dfs(MaskGraph mask, Node curModelNode, Node curMaskNode,
                                 LinkedList<Node> usedModelNodes,
                                 LinkedList<Node> usedMaskNodes,
                                 HashMap<Node, Node> maskToModelNodesMatch) {
        if (usedMaskNodes.containsAll(getToNodes(mask.getEdges(curMaskNode)))) {
            return new LinkedList<>(Arrays.asList(new Correspondence(new Graph(curModelNode),
                    maskToModelNodesMatch)));
        }
        HashMap<Class, LinkedList<Edge>> modelNodeTypedHashMapOfEdge
                = getTypedHashMapOfEdges(getEdges(curModelNode));
        HashMap<Class, LinkedList<Edge>> maskNodeTypedHashMapOfEdge
                = getTypedHashMapOfEdges(getEdges(curMaskNode));
        for (Map.Entry<Class, LinkedList<Edge>> entry : modelNodeTypedHashMapOfEdge.entrySet()) {
            if (entry.getValue().size() < maskNodeTypedHashMapOfEdge.get(entry.getKey()).size())
                return new LinkedList<>();
        }
        LinkedList<LinkedList<Correspondence>> modelCorrespondenceParts = new LinkedList<>();
        for (Map.Entry<Class, LinkedList<Edge>> entry : maskNodeTypedHashMapOfEdge.entrySet()) {
            LinkedList<Correspondence> curClassSubGraphs = new LinkedList<>();
            for (Edge maskEdge : entry.getValue())
                for (Edge modelEdge : modelNodeTypedHashMapOfEdge.get(entry.getKey())){
                    LinkedList<Node> newUsedModelNodes = new LinkedList<>(usedModelNodes);
                    LinkedList<Node> newUsedMaskNodes = new LinkedList<>(usedMaskNodes);
                    newUsedModelNodes.add(curModelNode);
                    newUsedMaskNodes.add(curMaskNode);
                    HashMap<Node, Node> newMaskToModelNodesMatch = new HashMap<>();
                    newMaskToModelNodesMatch.put(curMaskNode, curModelNode);
                    curClassSubGraphs.addAll(dfs(mask, modelEdge.to, maskEdge.to, newUsedModelNodes,
                            newUsedMaskNodes, newMaskToModelNodesMatch));
                }
            modelCorrespondenceParts.add(curClassSubGraphs);
        }
        LinkedList<Correspondence> resultSubGraphList = modelCorrespondenceParts.get(0).stream()
                .map(x -> {
                    HashMap<Node, Node> newCorrespondence = new HashMap<>(x.maskToModelCorrespondence);
                    newCorrespondence.put(curMaskNode, curModelNode);
                    return new Correspondence(
                            mergeBranches(curModelNode, x.modelSubGraph), newCorrespondence);
                })
                .collect(Collectors.toCollection(LinkedList::new));
        for (int i = 1; i < modelCorrespondenceParts.size(); i++) {
            LinkedList<Correspondence> newResultSubGraphList = new LinkedList<>();
            for (Correspondence resultSubGraph : resultSubGraphList) {
                for (Correspondence subGraph : modelCorrespondenceParts.get(i)) {
                    if (checkBranchesCorrespondence(resultSubGraph.maskToModelCorrespondence,
                            subGraph.maskToModelCorrespondence))
                        newResultSubGraphList.add(mergeBranches(resultSubGraph, subGraph));
                }
            }
        }
        return resultSubGraphList;
    }

    public class Correspondence {
        public Graph modelSubGraph;
        public HashMap<Node, Node> maskToModelCorrespondence;

        public Correspondence(Graph modelSubGraph, HashMap<Node, Node> maskToModelCorrespondence) {
            this.modelSubGraph = modelSubGraph;
            this.maskToModelCorrespondence = maskToModelCorrespondence;
        }
    }
}
