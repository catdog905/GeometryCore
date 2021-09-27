package core;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import core.facts.Fact;
import core.objects.GeometryObject;


public class Model {
    private HashSet<Fact> facts;
    private HashSet<Pair<Rule, Map<GeometryObject, GeometryObject>>> checkedRuleRequiredFactCases
            = new HashSet<>();
    public boolean isEquivalentTo(Model model){
        return isEquivalentTo(model,0);
    }

    public boolean isEquivalentTo(Model model,int rec){
        HashSet<Fact> ourFacts = facts;
        HashSet<Fact> theirFacts = model.facts;
        return new IndependentFactSetComparison(
                ourFacts,theirFacts
        ).EquivalenceFound();
    }

    public LinkedList<Fact> getFactsOfType(Class<? extends Fact> classToFind){
        return facts.stream().filter(x -> x.getClass().equals(classToFind)).collect(Collectors.toCollection(LinkedList::new));
    }

    public boolean containsFactOfType(Class<? extends Fact> classToFind){
        return getFactsOfType(classToFind).size()>0;
    }

    @Override
    public boolean equals(Object o) {

        if (o == null )
            return false;

        //Same link
        if (this == o)
            return true;

        if (!o.getClass().equals(getClass()))
            return false;

        Model model = (Model) o;

        // HashSet<>::containsAll for some reason doesn't seem to call equals, so I have
        // to convert it to LinkedList<>, which does.
        LinkedList<Fact> theirFacts = new LinkedList<>(model.facts);
        LinkedList<Fact> ourFacts = new LinkedList<>(facts);

        return ourFacts.size()==theirFacts.size()&&
                ourFacts.containsAll(theirFacts);
    }
    public Model(HashSet<Fact> facts) {
        this.facts = new HashSet<>(facts);
    }

    public void addSetOfFacts(
            HashSet<Fact> facts, Rule rule , Map<GeometryObject, GeometryObject> ruleCase) {
        this.facts.addAll(facts);
        checkedRuleRequiredFactCases.add(new Pair(rule, ruleCase));
    }

    public boolean haveRuleAlreadyApplied(Rule rule, Map<GeometryObject, GeometryObject> ruleCase) {
        for (Pair<Rule, Map<GeometryObject, GeometryObject>> caseEntry :
                checkedRuleRequiredFactCases)
            if (rule == caseEntry.first)
                if (caseEntry.second.entrySet().stream()
                        .filter(x -> ruleCase.get(x.getKey()) == x.getValue())
                        .count() == caseEntry.second.size())
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
