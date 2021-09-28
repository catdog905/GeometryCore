package core;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import core.correspondence.CorrespondenceNotNullDecorator;
import core.correspondence.FactMatcher;
import core.facts.Fact;
import core.objects.GeometryObject;

/*
all consequences facts have to be from this.facts scope
 */
public class Rule {
    public LinkedList<Fact> requiredFacts;
    public LinkedList<Fact> consequences;

    public Rule(LinkedList<Fact> requiredFacts, LinkedList<Fact> consequences) {
        this.requiredFacts = requiredFacts;
        this.consequences = consequences;
    }

    public void applyToModel(Model model) {
        LinkedList<Map<GeometryObject, GeometryObject>> correspondenceList = findAllMatchedFactsSequences(
                new LinkedList<>(model.facts), new CorrespondenceNotNullDecorator(), 0);
        for (Map<GeometryObject, GeometryObject> correspondence : correspondenceList) {
            model.facts.addAll(createConsequencesFacts(
                    (new CorrespondenceNotNullDecorator(correspondence)).makeFull()));
        }
    }

    private LinkedList<Fact> createConsequencesFacts(CorrespondenceNotNullDecorator correspondence) {
        LinkedList<Fact> consequenceFact = new LinkedList<>();
        for (Fact fact : consequences) {
            consequenceFact.add(fact.createNewSimilarObject(correspondence));
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
            LinkedList<Fact> modelFacts, CorrespondenceNotNullDecorator factsCorrespondence, int ruleFactsIter) {
        if (ruleFactsIter == this.requiredFacts.size())
            return new LinkedList<>(Arrays.asList(factsCorrespondence));
        LinkedList<Fact> matchedFacts = getMatchedFactsForItemElem(modelFacts, factsCorrespondence, ruleFactsIter);
        LinkedList<Map<GeometryObject, GeometryObject>> possibleSolutions = new LinkedList<>();
        for (Fact fact : matchedFacts) {
            LinkedList<Fact> modelFactsWithoutCurFact = new LinkedList<>(modelFacts);
            modelFactsWithoutCurFact.remove(fact);
            CorrespondenceNotNullDecorator currentCorrespondence = updateCorrespondenceItemElem(
                    factsCorrespondence, fact, ruleFactsIter);
            possibleSolutions.addAll(findAllMatchedFactsSequences(
                    modelFactsWithoutCurFact, currentCorrespondence, ruleFactsIter + 1));
        }
        return possibleSolutions;
    }

    private LinkedList<Fact> getMatchedFactsForItemElem(LinkedList<Fact> model,
            CorrespondenceNotNullDecorator factsCorrespondence, int ruleFactsIter) {
        return model.stream()
                .filter(x -> new FactMatcher(requiredFacts.get(ruleFactsIter), factsCorrespondence)
                .checkMatch(x)).collect(Collectors.toCollection(LinkedList::new));
    }

    private CorrespondenceNotNullDecorator updateCorrespondenceItemElem(Map<GeometryObject, GeometryObject> factsCorrespondence, Fact fact, int ruleFactsIter) {
        CorrespondenceNotNullDecorator newCorrespondence = new CorrespondenceNotNullDecorator(factsCorrespondence);
        LinkedList<GeometryObject> ruleFactObjects = requiredFacts.get(ruleFactsIter).getAllSubObjects();
        for (int i = 0; i < ruleFactObjects.size(); i++) {
            newCorrespondence.put(ruleFactObjects.get(i), fact.getAllSubObjects().get(i));
        }
        return newCorrespondence;
    }
}
