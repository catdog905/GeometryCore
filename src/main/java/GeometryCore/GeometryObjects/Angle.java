package GeometryCore.GeometryObjects;

import java.util.LinkedList;
import java.util.Map;

public class Angle extends GeometryObject {
    public LinkedList<GeometryObject> geometryObjects;

    public Angle(LinkedList<GeometryObject> geometryObjects) {
        this.geometryObjects = geometryObjects;
    }

    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        LinkedList<GeometryObject> newObjects = new LinkedList<>();
        for (GeometryObject obj : geometryObjects) {
            newObjects.add(correspondence.get(obj));
        }
        return new Angle(newObjects);
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return geometryObjects;
    }
}
