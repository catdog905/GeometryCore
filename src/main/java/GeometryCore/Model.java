package GeometryCore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import GeometryCore.Facts.Fact;
import GeometryCore.GeometryObjects.GeometryObject;

public class Model {
    private HashSet<Fact> facts;
    private HashMap<Rule, Map<GeometryObject, GeometryObject>> checkedRuleRequiredFactCases
            = new HashMap<>();

    public Model(HashSet<Fact> facts) {
        this.facts = facts;
    }

    public void addSetOfFacts(
            HashSet<Fact> facts, Rule rule , Map<GeometryObject, GeometryObject> ruleCase) {
        this.facts.addAll(facts);
        checkedRuleRequiredFactCases.put(rule, ruleCase);
    }

    public boolean haveRuleAlreadyApplied(Rule rule, Map<GeometryObject, GeometryObject> ruleCase) {
        for (Map.Entry<Rule, Map<GeometryObject, GeometryObject>> caseEntry :
                checkedRuleRequiredFactCases.entrySet())
            if (rule == caseEntry.getKey())
                if (caseEntry.getValue().entrySet().stream()
                        .filter(x -> ruleCase.get(x.getKey()) == x.getValue())
                        .count() == caseEntry.getValue().size())
                    return true;
        return false;
    }

    public HashSet<Fact> getFacts() {
        return facts;
    }

    public void setFacts(HashSet<Fact> facts) {
        this.facts = facts;
    }
}
