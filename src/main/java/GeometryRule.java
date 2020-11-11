import java.util.ArrayList;
import java.util.LinkedList;

public class GeometryRule {
    public ArrayList<GeometryFact> facts;
    public ArrayList<GeometryFact> consequences;

    public GeometryRule(ArrayList<GeometryFact> facts, ArrayList<GeometryFact> consequences) {
        this.facts = facts;
        this.consequences = consequences;
    }

    public boolean checkRule(/*collection of Facts*/) {
        return true;
    }
    public void addNewFact(/*collection of Facts*/) {

    }

    public LinkedList<GeometryFact> findCoincidences(LinkedList<GeometryFact> ruleFacts, ArrayList<GeometryFact> modelFacts) {
        if (ruleFacts.size() == 0)
            return ruleFacts;
        for (GeometryFact fact: modelFacts) {
            if (fact.checkFact(ruleFacts.get(0))){
                LinkedList<GeometryFact> tempRuleFacts = new LinkedList<>(ruleFacts);
                fact.fillInUnknowns(tempRuleFacts.peek());
                tempRuleFacts.remove();
                LinkedList<GeometryFact> temp = findCoincidences(tempRuleFacts, modelFacts);
                if (temp != null) {
                    temp.addFirst(tempRuleFacts.peek());
                    return temp;
                }
            }

        }
        return null;
    }
}
