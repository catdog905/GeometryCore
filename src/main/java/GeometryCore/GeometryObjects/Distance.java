package GeometryCore.GeometryObjects;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

public class Distance extends GeometryObject {
    public HashSet<GeometryObject> geometryObjects;

    public Distance(HashSet<GeometryObject> geometryObjects) {
        this.geometryObjects = geometryObjects;
    }

    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        HashSet<GeometryObject> newObjects = new HashSet<>();
        for (GeometryObject obj : geometryObjects) {
            newObjects.add(correspondence.get(obj));
        }
        return new Distance(newObjects);
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>(geometryObjects);
    }
}
