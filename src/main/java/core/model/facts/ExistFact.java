package core.model.facts;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

import core.model.facts.objects.GeometryObject;

public class ExistFact extends Fact{
    final public GeometryObject object;

    public ExistFact(GeometryObject object) {
        this.object = object;
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>(Arrays.asList(object));
    }

    @Override
    public Fact createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new ExistFact(object.createNewSimilarObject(correspondence)); //TODO: fix hack
    }
}
