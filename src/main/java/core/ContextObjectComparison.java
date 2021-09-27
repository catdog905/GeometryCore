package core;

import java.util.LinkedList;

import core.facts.Fact;
import core.objects.GeometryObject;
import core.objects.RaisedInThePower;
import core.objects.numbers.GeometryNumber;

public class ContextObjectComparison {

    public IndependentFactSetComparison context;
    protected LinkedList<Fact> fatherPendingFacts;
    protected LinkedList<GeometryObject> fatherPendingObjects;


    public ContextObjectComparison(
            IndependentFactSetComparison context, LinkedList<Fact> fatherPendingFacts, LinkedList<GeometryObject> fatherPendingObjects) {
        this.context = context;
        this.fatherPendingFacts = fatherPendingFacts;
        this.fatherPendingObjects = fatherPendingObjects;
    }

    public boolean compare(GeometryObject ourObject, GeometryObject theirObject) {
        if (context.objectToEquivalentObject.containsKey(ourObject)) {
            return context.objectToEquivalentObject.get(ourObject) == theirObject;
        } else if (context.objectToEquivalentObject.containsKey(theirObject)) {
            return false;
        }
        if (!theirObject.getClass().equals(ourObject.getClass()))
            return false;
        var ourPaths = context.ourObjectToObjectPaths.get(ourObject);
        var theirPaths = context.theirObjectToObjectPaths.get(theirObject);
        theirPaths = new LinkedList<>(theirPaths);
        if (ourPaths.size() != theirPaths.size())
            return false;
        context.objectToEquivalentObject.put(ourObject, theirObject);
        context.objectToEquivalentObject.put(theirObject, ourObject);
        fatherPendingObjects.add(ourObject);
        if (ourObject instanceof GeometryNumber) {
            return ((GeometryNumber) ourObject).number.equals(((GeometryNumber) theirObject).number);
        }


        LinkedList<GeometryObject> ourSubObjects = ourObject.getAllSubObjects(), theirSubObjects = theirObject.getAllSubObjects();
        CollectionComparator collectionComparator = ourObject instanceof RaisedInThePower ?
                new SameOrderCollectionComparator() : new NoOrderCollectionComparator();

        if (!collectionComparator.compare((subj1, subj2) -> {
            LinkedList<GeometryObject> lowLevelObjectPends = new LinkedList<>();
            LinkedList<Fact> lowLevelFactPends = new LinkedList<>();
            if (new ContextObjectComparison(context, lowLevelFactPends, lowLevelObjectPends).compare(subj1, subj2)) {
                fatherPendingObjects.addAll(lowLevelObjectPends);
                fatherPendingFacts.addAll(lowLevelFactPends);
                return true;
            } else {
                for (GeometryObject obj : lowLevelObjectPends) {
                    context.objectToEquivalentObject.remove(context.objectToEquivalentObject.get(obj));
                    context.objectToEquivalentObject.remove(obj);
                }
                for (Fact fact : lowLevelFactPends) {
                    context.factToEquivalentFact.remove(context.factToEquivalentFact.get(fact));
                    context.factToEquivalentFact.remove(fact);
                }
                return false;
            }


        }, ourSubObjects, theirSubObjects))
            return false;

        return new NoOrderCollectionComparator().compare((ourObjectPath, theirObjectPath) -> {
            LinkedList<GeometryObject> lowLevelObjectPends = new LinkedList<>();
            LinkedList<Fact> lowLevelFactPends = new LinkedList<>();
            if (new ContextFactComparison(context, lowLevelFactPends, lowLevelObjectPends).compare(ourObjectPath.fact, theirObjectPath.fact)) {
                if (new SameOrderCollectionComparator().compare((obj1, obj2) -> {
                    return new ContextObjectComparison(context, lowLevelFactPends, lowLevelObjectPends).compare(obj1, obj2);
                }, ourObjectPath.objectPath, theirObjectPath.objectPath)) {
                    fatherPendingObjects.addAll(lowLevelObjectPends);
                    fatherPendingFacts.addAll(lowLevelFactPends);
                    return true;
                } else {
                    for (GeometryObject obj : lowLevelObjectPends) {
                        context.objectToEquivalentObject.remove(context.objectToEquivalentObject.get(obj));
                        context.objectToEquivalentObject.remove(obj);
                    }
                    for (Fact fact : lowLevelFactPends) {
                        context.factToEquivalentFact.remove(context.factToEquivalentFact.get(fact));
                        context.factToEquivalentFact.remove(fact);
                    }
                }
                return true;
            } else {
                for (GeometryObject obj : lowLevelObjectPends) {
                    context.objectToEquivalentObject.remove(context.objectToEquivalentObject.get(obj));
                    context.objectToEquivalentObject.remove(obj);
                }
                for (Fact fact : lowLevelFactPends) {
                    context.factToEquivalentFact.remove(context.factToEquivalentFact.get(fact));
                    context.factToEquivalentFact.remove(fact);
                }
                return false;
            }
        }, ourPaths, theirPaths);
    }
}
