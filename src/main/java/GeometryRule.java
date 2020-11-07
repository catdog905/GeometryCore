import java.util.ArrayList;

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
}
