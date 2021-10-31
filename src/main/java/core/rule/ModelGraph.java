package core.rule;

import core.model.Model;

public class ModelGraph extends Graph {
    public ModelGraph(Model model){
        super(Graph.getNodesFromFactsList(model.facts()),
                Graph.getEdgesFromFactsList(model.facts()));
    }
}
