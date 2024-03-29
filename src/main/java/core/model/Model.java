package core.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import core.Rule;
import core.model.facts.DebugEquivalenceFact;
import core.model.facts.Fact;

public class Model {
    final private HashSet<Fact> facts;

    public Model(HashSet<Fact> facts) {
        this.facts = facts;
    }
    public Model(Model model) {
        this.facts = new HashSet<>(model.facts);
    }

    public HashSet<Fact> facts() {
        return facts;
    }
    public boolean containsFactsEquivalentTo(Model model){
        return containsFactsEquivalentTo(model.facts);
    }
    public boolean containsFactsEquivalentTo(HashSet<Fact> theirFacts){
        return containsFactsEquivalentTo(new LinkedList<>(theirFacts));
    }
    public boolean containsFactsEquivalentTo(LinkedList<Fact> theirFacts){
        LinkedList<Fact> consequences = new LinkedList<>();
        consequences.add(new DebugEquivalenceFact());
        new Rule(theirFacts,consequences).applyToModel(this);
        return facts.stream().anyMatch(x -> x instanceof DebugEquivalenceFact);
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

    public Model deleteRepeatingFacts(){
        HashSet<Fact> newFacts = new HashSet<>(facts);
        for (Fact fact: facts) {
            if (!newFacts.contains(fact))
                continue;

            List<Fact> allSimilarFacts =  newFacts.stream().filter
                    (x -> x!=fact&&
                            x.equals(fact))
                    .collect(Collectors.toList());

            newFacts.removeAll(allSimilarFacts);
        }
       return new Model(newFacts);
    }
}
