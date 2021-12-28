package core.rule;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import core.model.facts.ExistFact;
import core.model.facts.Fact;
import core.model.facts.objects.GeometryObject;


public class Graph {
    protected HashMap<Node, LinkedList<Edge>> adjacencyList = new HashMap<>();
    public Graph(LinkedList<Node> nodes, LinkedList<Edge> edges) {
        for (Node node : nodes) {
            LinkedList<Edge> tempList = edges.stream().filter(x -> x.from == node)
                    .collect(Collectors.toCollection(LinkedList::new));
            adjacencyList.put(node, tempList);
        }
    }

    public Graph(GraphContent content) {
        this(content.nodes, content.edges);
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

    public static GraphContent getGraphFromFactsList(Collection<Fact> list) {
        LinkedList<Fact> notExistFacts = list.stream().filter(x -> !(x instanceof ExistFact))
                .collect(Collectors.toCollection(LinkedList::new));
        LinkedList<Edge> edges = new LinkedList<>();
        HashMap<GeometryObject, Node> nodes = new HashMap<>();
        for (Fact fact : notExistFacts) {
            for (GeometryObject object : fact.getAllSubObjects()) {
                nodes.putIfAbsent(object, new Node(object));
            }
        }
        for (Fact fact :  list.stream().filter(x -> x instanceof ExistFact)
                .collect(Collectors.toCollection(LinkedList::new))) {
            nodes.putIfAbsent(((ExistFact)fact).object, new Node(((ExistFact)fact).object));
        }
        for (Fact fact : notExistFacts) {
            LinkedList<? extends GeometryObject> subObjects = fact.getAllSubObjects();
            for (GeometryObject to : subObjects) {
                for (GeometryObject from : subObjects) {
                    if (to == from)
                        continue;
                    edges.add(new Edge(nodes.get(to), nodes.get(from), fact));
                }
            }
        }
        return new GraphContent(new LinkedList<>(nodes.values()), edges);
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
        adjacencyList.putIfAbsent(node, new LinkedList<>());
        for (Edge edge : edges) {
            if (adjacencyList.containsKey(edge.from))
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
        HashMap<Edge, Edge> maskToModelEdgesMatch =
                new HashMap<>(branch1.maskToModelEdgesCorrespondence);
        maskToModelEdgesMatch.putAll(branch2.maskToModelEdgesCorrespondence);
        return new Correspondence(newGraph, maskToModelMatch, maskToModelEdgesMatch);
    }

    private Graph mergeBranches(Node mergeNode, Graph branch) {
        LinkedList<Edge> edges = getEdges(mergeNode).stream()
                .filter(x -> branch.getNodes().contains(x.to))
                .collect(Collectors.toCollection(LinkedList::new));
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
            if (node.isomorphic(mask.getStartNode()) && !usedStarts.contains(node)) {
                LinkedList<Correspondence> curCorrespondences = dfs(mask, node, mask.getStartNode(),
                        new LinkedList<>(), new LinkedList<>(), new HashMap<>(), new HashMap<>());
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
                                 HashMap<Node, Node> maskToModelNodesMatch,
                                 HashMap<Edge, Edge> maskToModelEdgesMatch) {
        if (usedMaskNodes.containsAll(getToNodes(mask.getEdges(curMaskNode)))) {
            HashMap<Node, Node> newMaskToModelNodesMatch =
                    new HashMap<>(maskToModelNodesMatch);
            newMaskToModelNodesMatch.put(curMaskNode, curModelNode);
            return new LinkedList<>(Arrays.asList(new Correspondence(new Graph(curModelNode),
                    newMaskToModelNodesMatch, maskToModelEdgesMatch)));
        }
        HashMap<Class, LinkedList<Edge>> modelNodeTypedHashMapOfEdge
                = getTypedHashMapOfEdges(getEdges(curModelNode).stream()
                    .filter(x -> !usedModelNodes.contains(x.to))
                    .collect(Collectors.toCollection(LinkedList::new)));
        HashMap<Class, LinkedList<Edge>> maskNodeTypedHashMapOfEdge
                = getTypedHashMapOfEdges(mask.getEdges(curMaskNode).stream()
                    .filter(x -> !usedMaskNodes.contains(x.to))
                    .collect(Collectors.toCollection(LinkedList::new)));
        if (!modelNodeTypedHashMapOfEdge.keySet().containsAll(maskNodeTypedHashMapOfEdge.keySet())) {
            return new LinkedList<>();
        }
        for (Map.Entry<Class, LinkedList<Edge>> entry : maskNodeTypedHashMapOfEdge.entrySet()) {
            if (entry.getValue().size() > modelNodeTypedHashMapOfEdge.get(entry.getKey()).size())
                return new LinkedList<>();
        }
        LinkedList<LinkedList<Correspondence>> modelCorrespondenceParts = new LinkedList<>();
        for (Map.Entry<Class, LinkedList<Edge>> entry : maskNodeTypedHashMapOfEdge.entrySet()) {
            LinkedList<Correspondence> curClassSubGraphs = new LinkedList<>();
            HashSet<Edge> maskEdges = new HashSet<>(entry.getValue());
            for (Map.Entry<Edge, HashSet<Edge>> edgeEntry : mask.similarEdges.entrySet()){
                HashSet<Edge> finalMaskEdges = maskEdges;
                maskEdges = maskEdges.stream().map(x -> {
                    if (edgeEntry.getValue().contains(x) && finalMaskEdges.contains(edgeEntry.getKey()))
                        return edgeEntry.getKey();
                    else
                        return x;
                }).collect(Collectors.toCollection(HashSet::new));
            }
            for (Edge maskEdge : maskEdges)
                for (Edge modelEdge : modelNodeTypedHashMapOfEdge.get(entry.getKey())){
                    LinkedList<Node> newUsedModelNodes = new LinkedList<>(usedModelNodes);
                    LinkedList<Node> newUsedMaskNodes = new LinkedList<>(usedMaskNodes);
                    newUsedModelNodes.add(curModelNode);
                    newUsedMaskNodes.add(curMaskNode);
                    HashMap<Node, Node> newMaskToModelNodesMatch =
                            new HashMap<>(maskToModelNodesMatch);
                    newMaskToModelNodesMatch.put(curMaskNode, curModelNode);
                    HashMap<Edge, Edge> newMaskToModelEdgesMatch =
                            new HashMap<>(maskToModelEdgesMatch);
                    newMaskToModelEdgesMatch.put(maskEdge, modelEdge);
                    curClassSubGraphs.addAll(dfs(mask, modelEdge.to, maskEdge.to, newUsedModelNodes,
                            newUsedMaskNodes, newMaskToModelNodesMatch, newMaskToModelEdgesMatch));
                }
            modelCorrespondenceParts.add(curClassSubGraphs);
        }
        LinkedList<Correspondence> resultSubGraphList = modelCorrespondenceParts.get(0).stream()
                .map(x -> new Correspondence(
                            mergeBranches(curModelNode, x.modelSubGraph), x.maskToModelCorrespondence,
                            x.maskToModelEdgesCorrespondence))
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
        public HashMap<Edge, Edge> maskToModelEdgesCorrespondence;

        public Correspondence(Graph modelSubGraph, HashMap<Node, Node> maskToModelCorrespondence,
                              HashMap<Edge, Edge> maskToModelEdgesCorrespondence) {
            this.modelSubGraph = modelSubGraph;
            this.maskToModelCorrespondence = maskToModelCorrespondence;
            this.maskToModelEdgesCorrespondence = maskToModelEdgesCorrespondence;
        }
    }
}
