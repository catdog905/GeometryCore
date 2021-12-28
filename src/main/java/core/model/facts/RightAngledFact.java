package core.model.facts;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

import core.model.facts.objects.GeometryObject;

public class RightAngledFact extends Fact {
    final public GeometryObject object;

    public RightAngledFact(GeometryObject object) {
        this.object = object;
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>(Arrays.asList(object));
    }

    @Override
    public Fact createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new RightAngledFact(correspondence.get(object));
    }
}
