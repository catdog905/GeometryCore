package GeometryCore.GeometryObjects;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

public class Angle extends GeometryObject {
    public HashSet<GeometryObject> geometryObjects;

    public Angle(HashSet<GeometryObject> geometryObjects) {
        this.geometryObjects = geometryObjects;
    }

    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        HashSet<GeometryObject> newObjects = new HashSet<>();
        for (GeometryObject obj : geometryObjects) {
            newObjects.add(correspondence.get(obj));
        }
        return new Angle(newObjects);
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>(geometryObjects);
    }
}
