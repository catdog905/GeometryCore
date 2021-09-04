package GeometryCore.GeometryObjects;

import java.util.LinkedList;
import java.util.Map;

public class Angle extends GeometryObject {
    public LinkedList<GeometryObject> geometryObjects;

    public Angle(LinkedList<GeometryObject> geometryObjects) {
        if (geometryObjects.size() != 2)
            throw new RuntimeException("Angle must contain only 2 subObjects");
        if (!(geometryObjects.get(0) instanceof LineSegment) || !(geometryObjects.get(1) instanceof LineSegment)) {
            throw new RuntimeException("Angle's subObjects must be lineSegments");
        }
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
