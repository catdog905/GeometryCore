package core.model.facts;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import core.model.facts.objects.GeometryObject;

public class ParallelismFact extends Fact {
    final public HashSet<GeometryObject> geometryObjects;

    public ParallelismFact(HashSet<GeometryObject> geometryObjects) {
        this.geometryObjects = geometryObjects;
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>(geometryObjects);
    }

    @Override
    public Fact createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        LinkedList<GeometryObject> newObjects = new LinkedList<>();
        for (GeometryObject obj : geometryObjects) {
            newObjects.add(correspondence.get(obj));
        }
        return new ParallelismFact(new HashSet<>(newObjects));
    }
}
