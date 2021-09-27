package core;

import java.util.LinkedList;

import core.ContextObjectComparison;
import core.IndependentFactSetComparison;
import core.SameOrderCollectionComparator;
import core.facts.Fact;
import core.objects.GeometryObject;

public class ContextFactComparison {
    private final IndependentFactSetComparison context;
    protected LinkedList<Fact> fatherPendingFacts;
    protected LinkedList<GeometryObject> fatherPendingObjects;


    public ContextFactComparison(
            IndependentFactSetComparison context,
            LinkedList<Fact> pendingFacts, LinkedList<GeometryObject> pendingObjects) {
        this.context=context;
        this.fatherPendingFacts = pendingFacts;
        this.fatherPendingObjects = pendingObjects;
    }
    public boolean compare(Fact ourFact,Fact theirFact){
        if (!theirFact.getClass().equals(ourFact.getClass()))
            return false;
        if (context.factToEquivalentFact.containsKey(ourFact)) {
            return context.factToEquivalentFact.get(ourFact) == theirFact;
        } else if (context.factToEquivalentFact.containsKey(theirFact)) {
            return false;
        }
        LinkedList<GeometryObject> theirSubObjects = theirFact.getAllSubObjects();
        LinkedList<GeometryObject> ourSubObjects = ourFact.getAllSubObjects();
        context.factToEquivalentFact.put(ourFact, theirFact);
        context.factToEquivalentFact.put(theirFact, ourFact);
        fatherPendingFacts.add(ourFact);
        LinkedList<GeometryObject> ourObjectPends = new LinkedList<>();
        LinkedList<Fact> ourFactPends = new LinkedList<>();
        if (new SameOrderCollectionComparator().compare((ourObject, theirObject) -> {
            if (!new ContextObjectComparison(context, ourFactPends,ourObjectPends).compare(ourObject, theirObject)) {
                context.factToEquivalentFact.remove(ourFact);
                context.factToEquivalentFact.remove(theirFact);
                fatherPendingFacts.remove(ourFact);
                for (GeometryObject obj : ourObjectPends) {
                    context.objectToEquivalentObject.remove(context.objectToEquivalentObject.get(obj));
                    context.objectToEquivalentObject.remove(obj);
                }
                for (Fact facter : ourFactPends) {
                    context.factToEquivalentFact.remove(context.factToEquivalentFact.get(facter));
                    context.factToEquivalentFact.remove(facter);
                }
                return false;
            }
            return true;
        }, ourSubObjects, theirSubObjects)) {
            fatherPendingFacts.addAll(ourFactPends);
            fatherPendingObjects.addAll(ourObjectPends);
            return true;
        }
        return false;
    }
}
