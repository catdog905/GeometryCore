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

    /***
     * Get 1 rule and all model facts and return this fact in context if it true or null if false
     * @param ruleFacts
     * @param modelFacts
     * @return
     */
    public LinkedList<GeometryFact> checkRule(LinkedList<GeometryFact> ruleFacts, ArrayList<GeometryFact> modelFacts) {
        for (GeometryFact fact: modelFacts) {
            for (GeometryFact ruleFact: ruleFacts) {
                if (fact.checkFact(ruleFact)) {
                    fact.fillInUnknowns(ruleFact);
                    break;
                }
                if (ruleFact.equals(ruleFacts.getLast())){
                    return null;
                }
            }
        }
        return ruleFacts;
    }
}
