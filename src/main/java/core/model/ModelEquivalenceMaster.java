package core.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import core.facts.Fact;
import core.objects.GeometryObject;

public class ModelEquivalenceMaster {
    HashMap<GeometryObject, GeometryObject> objectToEquivalentObject = new HashMap<>();
    HashMap<Fact, Fact> factToEquivalentFact = new HashMap<>();
    HashMap<Fact, HashSet<Fact>> ourFactToTheirNonequivalentFacts = new HashMap<>();

    HashMap<GeometryObject, LinkedList<Fact>> theirObjectToFacts, ourObjectToFacts;

    public ModelEquivalenceMaster(HashMap<GeometryObject, LinkedList<Fact>> ourObjectToFacts, HashMap<GeometryObject, LinkedList<Fact>> theirObjectToFacts) {
        this.ourObjectToFacts = ourObjectToFacts;
        this.theirObjectToFacts = theirObjectToFacts;
    }

    public boolean equivalenceToFactFound(Fact fact) {
        return factToEquivalentFact.containsKey(fact);
    }


    void certifyNonequivalency(Fact ourFact, Fact theirFact) {
        if (ourFactToTheirNonequivalentFacts.containsKey(ourFact)) {
            ourFactToTheirNonequivalentFacts.get(ourFact).add(theirFact);
        } else {
            HashSet<Fact> nonequivalentFacts = new HashSet<>();
            nonequivalentFacts.add(theirFact);
            ourFactToTheirNonequivalentFacts.put(ourFact, nonequivalentFacts);
        }
    }

    public boolean isOurFactEquivalentToTheir(Fact ourFact, Fact theirFact) {
        return isOurFactEquivalentToTheir(ourFact, theirFact, ourFact.getAllSubObjects());
    }

    public boolean isOurObjectEquivalentToTheir(GeometryObject ourObject,GeometryObject theirObject){
        if (objectToEquivalentObject.containsKey(ourObject)) {
            return objectToEquivalentObject.get(ourObject) == theirObject;
        } else if (objectToEquivalentObject.containsKey(theirObject)) {
            return false;
        }
        if (!theirObject.isEquivalentTo(ourObject))
            return false;
        boolean allFunctionsAreEquivalent = true;
        var ourFacts = ourObjectToFacts.get(ourObject);
        var theirFacts = theirObjectToFacts.get(theirObject);
        for (var ourObjectFact : ourFacts) {
            Fact equivalentFact = null;
            for (var theirObjectFact : theirFacts) {
                if (isOurFactEquivalentToTheir(ourObjectFact, theirObjectFact)) {
                    equivalentFact = theirObjectFact;
                    break;
                }
            }
            if (equivalentFact == null) {
                allFunctionsAreEquivalent = false;
                break;
            }
        }
        return allFunctionsAreEquivalent;

    }

    public boolean isOurFactEquivalentToTheir(Fact ourFact, Fact theirFact, LinkedList<? extends GeometryObject> ourSubObjects) {
        if (!theirFact.getClass().equals(ourFact.getClass()))
            return false;
        if (ourFactToTheirNonequivalentFacts.containsKey(ourFact)
                && ourFactToTheirNonequivalentFacts.get(ourFact).contains(theirFact))
            return false;
        if (factToEquivalentFact.containsKey(ourFact)) {
            return factToEquivalentFact.get(ourFact) == theirFact;
        } else if (factToEquivalentFact.containsKey(theirFact)) {
            return false;
        }
        LinkedList<? extends GeometryObject> theirSubObjects = theirFact.getAllSubObjects();
        if (theirSubObjects.size() != ourSubObjects.size()) {
            certifyNonequivalency(ourFact, theirFact);
            return false;
        }
        factToEquivalentFact.put(ourFact, theirFact);
        factToEquivalentFact.put(theirFact, ourFact);
        LinkedList<GeometryObject> ourObjectPends = new LinkedList<>();
        for (Iterator<? extends GeometryObject> ourObjectIterator = ourSubObjects.iterator(), theirObjectIterator = theirSubObjects.iterator(); theirObjectIterator.hasNext(); ) {
            var ourObject = ourObjectIterator.next();
            var theirObject = theirObjectIterator.next();
            if (isOurObjectEquivalentToTheir(ourObject,theirObject)){
                if (!objectToEquivalentObject.containsKey(ourObject)) {
                    objectToEquivalentObject.put(ourObject, theirObject);
                    objectToEquivalentObject.put(theirObject, ourObject);
                    ourObjectPends.add(ourObject);
                }
            }
            else {
                certifyNonequivalency(ourFact, theirFact);
                factToEquivalentFact.remove(ourFact);
                factToEquivalentFact.remove(theirFact);
                for (GeometryObject obj : ourObjectPends) {
                    objectToEquivalentObject.remove(objectToEquivalentObject.get(obj));
                    objectToEquivalentObject.remove(obj);
                }
                return false;
            }
        }
        return true;
    }
}
