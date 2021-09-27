package core;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.stream.Collectors;

import core.facts.Fact;


public class Model {
    public HashSet<Fact> facts;
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
}
