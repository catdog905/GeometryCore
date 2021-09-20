package core.facts;

import java.util.LinkedList;
import java.util.Map;

import core.objects.GeometryObject;

public class ParallelismFact extends Fact {
    public LinkedList<GeometryObject> geometryObjects;

    public ParallelismFact(LinkedList<GeometryObject> geometryObjects) {
        this.geometryObjects = geometryObjects;
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>(geometryObjects);
    }

    @Override
    public Fact createNewSimilarCorrespondenceObject(Map<GeometryObject, GeometryObject> correspondence) {
        LinkedList<GeometryObject> newObjects = new LinkedList<>();
        for (GeometryObject obj : geometryObjects) {
            newObjects.add(correspondence.get(obj));
        }
        return new ParallelismFact(newObjects);
    }
}
