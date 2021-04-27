import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class GeometryRule {
    public LinkedList<GeometryFact> facts;
    public LinkedList<GeometryFact> consequences;

    public GeometryRule(LinkedList<GeometryFact> facts, LinkedList<GeometryFact> consequences) {
        this.facts = facts;
        this.consequences = consequences;
    }

    public LinkedList<GeometryFact> addNewFact(LinkedList<GeometryFact> modelFacts) {
        HashMap<GeometryObject, GeometryObject> matchingObjects = new HashMap<>();
        LinkedList<GeometryFact> newNamespaceFacts = applyModeNamespaceToRule(modelFacts);
        for (int i = 0; i < facts.size(); i++) {
            matchingObjects.putAll(facts.get(i).getMatchingObjects(facts.get(i), newNamespaceFacts.get(i))); //TODO Problem place(+Helper class?)
        }
        for (GeometryFact fact: consequences) {
            LinkedList<GeometryObject> newSubObjects = new LinkedList<>();
            for (GeometryObject object: fact.getSubObjects()) {
                newSubObjects.add(matchingObjects.get(object));
            }
            fact.putSubObjects(newSubObjects);
        }
        modelFacts.addAll(consequences);
        return modelFacts;
    }

    /***
     * Get 1 rule and all model facts and return this fact in context if it true or null if false
     * @param modelFacts
     * @return
     */
    public LinkedList<GeometryFact> applyModeNamespaceToRule(LinkedList<GeometryFact> modelFacts) {
        LinkedList<GeometryFact> newNamespace = new LinkedList<GeometryFact>();
        for (GeometryFact fact:facts) {
            try {
                newNamespace.add((GeometryFact) fact.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        for (GeometryFact fact: modelFacts) {
            for (GeometryFact ruleFact: newNamespace) {
                if (fact.checkFact(ruleFact)) {
                    fact.fillInUnknowns(ruleFact);
                    break;
                }
                if (ruleFact.equals(newNamespace.getLast())){
                    return null;
                }
            }
        }
        return newNamespace;
    }
}
