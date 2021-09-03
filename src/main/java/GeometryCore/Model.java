package GeometryCore;

import java.util.HashSet;

import GeometryCore.Facts.Fact;

public class Model {
    public HashSet<Fact> facts;

    public Model(HashSet<Fact> facts) {
        this.facts = facts;
    }
}
