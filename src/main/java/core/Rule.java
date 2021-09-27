package core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import core.facts.Fact;
import core.objects.GeometryObject;
import core.objects.numbers.NumberEnveloper;

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
                new LinkedList<>(model.getFacts()), new HashMap<>(), 0);
        for (Map<GeometryObject, GeometryObject> correspondence : correspondenceList) {
            if (model.haveRuleAlreadyApplied(this, correspondence))
                continue;
            CorrespondenceNotNullDecorator curCorrespodence =
                    (new CorrespondenceNotNullDecorator(correspondence)).makeFull(consequences);
            //for (Entry<GeometryObject, GeometryObject> entry : curCorrespodence.entrySet())
            //    if (curCorrespodence.entrySet().stream().anyMatch(
            //            x -> x.getKey() != entry.getKey() && x.getValue() == entry.getValue())) {
            //        curCorrespodence = null;
            //        break;
            //    }
            if (curCorrespodence == null)
                continue;
            LinkedList<Fact> newFacts = createConsequencesFacts(curCorrespodence);
            //for (Fact fact : newFacts)
             //   if (model.getFacts().stream().map(x -> !fact.isTheSameFact(x))
            //            .reduce(true, (subtotal, el) -> subtotal && el))
            model.addSetOfFacts(new HashSet<>(newFacts), this, correspondence);
        }
    }

    private LinkedList<Fact> createConsequencesFacts(Map<GeometryObject, GeometryObject> correspondence) {
        LinkedList<Fact> consequenceFact = new LinkedList<>();
        for (Fact fact : consequences) {
            consequenceFact.add(fact.createNewSimilarCorrespondenceObject(correspondence));
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
            if (!checkSimilarityOf2Facts(curFact, requiredFacts.get(ruleFactsIter)))
                continue;

            LinkedList<GeometryObject> curFactObjects = curFact.getAllSubObjects();
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
        LinkedList<GeometryObject> ruleFactObjects = requiredFacts.get(ruleFactsIter).getAllSubObjects();
        LinkedList<GeometryObject> modelFactObjects = fact.getAllSubObjects();
        for (int i = 0; i < ruleFactObjects.size(); i++) {
            int finalI = i;
            GeometryObject correspondenceObj = modelFactObjects.stream()
                    .filter(x -> x.getClass() == ruleFactObjects.get(finalI).getClass())
                    .findAny().orElse(null);
            modelFactObjects.remove(correspondenceObj);
            newCorrespondence.put(ruleFactObjects.get(i), correspondenceObj);
        }
        return newCorrespondence;
    }

    private Boolean checkSimilarityOf2Facts (Fact fact1 , Fact fact2) {
        if (fact1.getClass() != fact2.getClass())
            return false;
        HashMap<Class<?>, Integer> fact1TypeCounter = new HashMap<>();
        HashMap<Class<?>, Integer> fact2TypeCounter = new HashMap<>();
        for (GeometryObject obj: fact1.getAllSubObjects()) {
            Integer num = fact1TypeCounter.get(obj.getClass());
            if (num == null)
                fact1TypeCounter.put(obj.getClass(), 1);
            else
                fact1TypeCounter.put(obj.getClass(), num + 1);
        }
        for (GeometryObject obj: fact2.getAllSubObjects()) {
            Integer num = fact2TypeCounter.get(obj.getClass());
            if (num == null)
                fact2TypeCounter.put(obj.getClass(), 1);
            else
                fact2TypeCounter.put(obj.getClass(), num + 1);
        }
        for (Entry<Class<?>, Integer> entry : fact1TypeCounter.entrySet()) {
            if (!entry.getValue().equals(fact2TypeCounter.get(entry.getKey())))
                return false;
        }
        return true;
    }
}
