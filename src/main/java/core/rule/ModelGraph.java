package core.rule;

import core.model.Model;

public class ModelGraph extends Graph {
    public ModelGraph(Model model){
        super(Graph.getEdgesFromFactsList(model.facts()));
    }
}
