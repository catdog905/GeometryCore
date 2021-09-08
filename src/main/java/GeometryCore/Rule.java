package GeometryCore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import GeometryCore.Facts.Fact;
import GeometryCore.GeometryObjects.GeometryObject;
import GeometryCore.GeometryObjects.NumberEnveloper;

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
                new LinkedList<>(model.facts), new HashMap<>(), 0);
        for (Map<GeometryObject, GeometryObject> correspondence : correspondenceList) {
            model.facts.addAll(createConsequencesFacts(
                    (new CorrespondenceNotNullDecorator(correspondence)).makeFull()));
        }
    }

    private LinkedList<Fact> createConsequencesFacts(Map<GeometryObject, GeometryObject> correspondence) {
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
            LinkedList<Fact> modelFacts, Map<GeometryObject, GeometryObject> factsCorrespondence, int ruleFactsIter) {
        if (ruleFactsIter == this.requiredFacts.size())
            return new LinkedList<>(Arrays.asList(factsCorrespondence));
        LinkedList<Fact> matchedFacts = getMatchedFactsForItemElem(modelFacts, factsCorrespondence, ruleFactsIter);
        LinkedList<Map<GeometryObject, GeometryObject>> possibleSolutions = new LinkedList<>();
        for (Fact fact : matchedFacts) {
            LinkedList<Fact> modelFactsWithoutCurFact = new LinkedList<Fact>(modelFacts);
            modelFactsWithoutCurFact.remove(fact);
            Map<GeometryObject, GeometryObject> currentCorrespondence = updateCorrespondenceItemElem(
                    factsCorrespondence, fact, ruleFactsIter);
            possibleSolutions.addAll(findAllMatchedFactsSequences(
                    modelFactsWithoutCurFact, currentCorrespondence, ruleFactsIter + 1));
        }
        return possibleSolutions;
    }

    private LinkedList<Fact> getMatchedFactsForItemElem(LinkedList<Fact> model, Map<GeometryObject, GeometryObject> factsCorrespondence, int ruleFactsIter) {
        LinkedList<Fact> matchedElements = new LinkedList<>();
        for (Fact curFact : model) {
            if (!curFact.getClass().equals(requiredFacts.get(ruleFactsIter).getClass()))
                continue;
            LinkedList<? extends GeometryObject> curFactObjects = curFact.getAllSubObjects();
            Boolean isFit = true;
            for (GeometryObject obj : requiredFacts.get(ruleFactsIter).getAllSubObjects()){
                GeometryObject match = null;
                if (obj instanceof NumberEnveloper)
                    match = obj;
                else
                    match = factsCorrespondence.get(obj);
                GeometryObject finalMatch = match;
                if (match != null && curFactObjects.stream().noneMatch(x -> x == finalMatch)) {
                    isFit = false;
                    break;
                }
            }
            if (isFit)
                matchedElements.add(curFact);
        }
        return matchedElements;
    }

    private Map<GeometryObject, GeometryObject> updateCorrespondenceItemElem(Map<GeometryObject, GeometryObject> factsCorrespondence, Fact fact, int ruleFactsIter) {
        Map<GeometryObject, GeometryObject> newCorrespondence = new HashMap<>(factsCorrespondence);
        LinkedList<? extends GeometryObject> ruleFactObjects = requiredFacts.get(ruleFactsIter).getAllSubObjects();
        for (int i = 0; i < ruleFactObjects.size(); i++) {
            newCorrespondence.put(ruleFactObjects.get(i), fact.getAllSubObjects().get(i));
        }
        return newCorrespondence;
    }
}
