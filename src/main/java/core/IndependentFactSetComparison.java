package core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import core.facts.Fact;
import core.objects.GeometryObject;

public class IndependentFactSetComparison {
    HashSet<Fact> factsLeft, factsRight;
    HashMap<GeometryObject, GeometryObject> objectToEquivalentObject;
    HashMap<Fact, Fact> factToEquivalentFact;
    HashMap<GeometryObject, LinkedList<ObjectPath>> theirObjectToObjectPaths, ourObjectToObjectPaths;

    public IndependentFactSetComparison(
            HashSet<Fact> factsLeft, HashSet<Fact> factsRight) {
        this(factsLeft, factsRight,
                ObjectPath.extractObjectPathsFromFactSet(factsLeft),
                ObjectPath.extractObjectPathsFromFactSet(factsRight));
    }

    public IndependentFactSetComparison(
            HashSet<Fact> factsLeft, HashSet<Fact> factsRight, HashMap<GeometryObject, LinkedList<ObjectPath>> ourObjectToObjectPaths
            , HashMap<GeometryObject, LinkedList<ObjectPath>> theirObjectToObjectPaths) {
        this.factsLeft = factsLeft;
        this.factsRight = factsRight;
        this.ourObjectToObjectPaths = ourObjectToObjectPaths;
        this.theirObjectToObjectPaths = theirObjectToObjectPaths;
        objectToEquivalentObject = new HashMap<>();
        factToEquivalentFact = new HashMap<>();
    }

    public boolean EquivalenceFound() {
        return new NoOrderCollectionComparator().compare((ourFact, theirFact) ->
                new ContextFactComparison(this,new LinkedList<>(),new LinkedList<>()).compare(ourFact,theirFact),
                factsLeft, factsRight
        );
    }

}
