package core.rule;

import java.util.LinkedList;

import core.model.Model;

public class ModelGraph extends Graph {
    public ModelGraph(Model model){
        super(Graph.getGraphFromFactsList(model.facts()));
    }

    public void applyMask(MaskGraph maskGraph) {
        LinkedList<Correspondence> correspondences = getAllSubGraphsIsomorphicToMask(maskGraph);

    }


    public void addSubGraph(Graph graph) {
        for (Node node : graph.getNodes()) {
            addNode(node, graph.getEdges(node));
        }
    }
}
