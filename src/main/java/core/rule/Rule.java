package core.rule;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import core.correspondence.CorrespondenceWithoutNullElements;
import core.correspondence.FactMatcher;
import core.facts.Fact;
import core.model.Model;
import core.objects.GeometryObject;
import core.objects.benchmark.Benchmarkable;

/*
all consequences facts have to be from this.facts scope
 */
public class Rule implements Benchmarkable{
    public LinkedList<Fact> requiredFacts;
    public LinkedList<Fact> consequences;
    public String name;
    public Rule(LinkedList<Fact> requiredFacts, LinkedList<Fact> consequences, String name) {
        this.requiredFacts = requiredFacts;
        this.consequences = consequences;
        this.name=name;
    }

    public Rule(LinkedList<Fact> requiredFacts, LinkedList<Fact> consequences) {
        this(requiredFacts,consequences,"Untitled rule");
    }

    @Override
    public void outputBenchmarks(){

        System.out.println();
        System.out.println("---------------");
        System.out.println(name);
        System.out.println("");

        Benchmarkable.super.outputBenchmarks();


        System.out.println("---------------");
        System.out.println();
    }

    public Model applyToModel(Model model) {
        startExecution("applying rule");
        MaskGraph maskGraph = new MaskGraph(this);
        ModelGraph modelGraph = new ModelGraph(model);
        LinkedList<Graph.Correspondence> correspondences =
                modelGraph.getAllSubGraphsIsomorphicToMask(maskGraph);
        for (Graph.Correspondence correspondence : correspondences)
            modelGraph.addSubGraph(maskGraph.getConsequencesGraph(correspondence));
        endExecution("applying rule");
        return new Model(modelGraph);
    }

    private LinkedList<Fact> createConsequencesFacts(Map<GeometryObject, GeometryObject> correspondence) {
        LinkedList<Fact> consequenceFact = new LinkedList<>();
        for (Fact fact : consequences) {
            consequenceFact.add(fact.createNewSimilarObject(
                    new CorrespondenceWithoutNullElements(correspondence)));
        }
        return consequenceFact;
    }

    /*
    Check rule algorithm:
        find matched elements from modelFacts to this.facts[0]
        if it equals 0 and facts equal 0 then we can return
        else we get this method from this.facts tail

     P.S: all this.facts have to find matched element from modelFacts scope
     */
    private LinkedList<Map<GeometryObject, GeometryObject>> findAllMatchedFactsSequences(
            LinkedList<Fact> modelFacts, CorrespondenceWithoutNullElements factsCorrespondence, int ruleFactsIter) {
        increaseCriterion("findAllMatchedFactsSequences");
        if (ruleFactsIter == this.requiredFacts.size())
            return new LinkedList<>(Arrays.asList(factsCorrespondence));
        LinkedList<Fact> matchedFacts = getMatchedFactsForItemElem(modelFacts, factsCorrespondence, ruleFactsIter);
        LinkedList<Map<GeometryObject, GeometryObject>> possibleSolutions = new LinkedList<>();
        for (Fact fact : matchedFacts) {
            LinkedList<Fact> modelFactsWithoutCurFact = new LinkedList<>(modelFacts);
            modelFactsWithoutCurFact.remove(fact);
            CorrespondenceWithoutNullElements currentCorrespondence = updateCorrespondenceItemElem(
                    factsCorrespondence, fact, ruleFactsIter);
            possibleSolutions.addAll(findAllMatchedFactsSequences(
                    modelFactsWithoutCurFact, currentCorrespondence, ruleFactsIter + 1));
        }
        return possibleSolutions;
    }

    private LinkedList<Fact> getMatchedFactsForItemElem(LinkedList<Fact> model,
                                                        CorrespondenceWithoutNullElements factsCorrespondence, int ruleFactsIter) {
        return model.stream()
                .filter(x -> new FactMatcher(requiredFacts.get(ruleFactsIter), factsCorrespondence)
                .checkMatch(x)).collect(Collectors.toCollection(LinkedList::new));
    }

    private CorrespondenceWithoutNullElements updateCorrespondenceItemElem(Map<GeometryObject, GeometryObject> factsCorrespondence, Fact fact, int ruleFactsIter) {
        CorrespondenceWithoutNullElements newCorrespondence = new CorrespondenceWithoutNullElements(factsCorrespondence);
        LinkedList<? extends GeometryObject> ruleFactObjects = requiredFacts.get(ruleFactsIter).getAllSubObjects();
        for (int i = 0; i < ruleFactObjects.size(); i++) {
            newCorrespondence.put(ruleFactObjects.get(i), fact.getAllSubObjects().get(i));
        }
        return newCorrespondence;
    }
}
