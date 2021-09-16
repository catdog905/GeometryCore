package GeometryCore;

import java.util.HashSet;
import java.util.LinkedList;

import GeometryCore.Facts.Fact;

public class Model {
    public HashSet<Fact> facts;

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
        this.facts = facts;
    }
}
