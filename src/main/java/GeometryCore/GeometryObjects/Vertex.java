package GeometryCore.GeometryObjects;

import java.util.LinkedList;
import java.util.Map;

public class Vertex extends GeometryObject {
    public Vertex() {
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return null;
    }

    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new Vertex();
    }
}
