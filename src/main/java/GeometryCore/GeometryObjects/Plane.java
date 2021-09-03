package GeometryCore.GeometryObjects;

import java.util.LinkedList;
import java.util.Map;

public class Plane extends GeometryObject {
    public Plane() {
    }
    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new Plane();
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return null;
    }
}
