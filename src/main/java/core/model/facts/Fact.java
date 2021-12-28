package core.model.facts;
import java.util.LinkedList;
import java.util.Map;

import core.model.facts.objects.GeometryObject;

public abstract class Fact {
    @Override
    public boolean equals(Object o) {
        if (o == null )
            return false;

        //Same link
        if (this == o)
            return true;

        if (!o.getClass().equals(getClass()))
            return false;

        Fact fact = (Fact) o;
        var theirSubObjects = fact.getAllSubObjects();
        var ourSubObjects = getAllSubObjects();
        return theirSubObjects.size()==ourSubObjects.size()&&theirSubObjects.containsAll(ourSubObjects);
    }

    public abstract LinkedList<? extends GeometryObject> getAllSubObjects();
    public abstract Fact createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence);
}
