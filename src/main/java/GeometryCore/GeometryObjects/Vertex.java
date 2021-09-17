package GeometryCore.GeometryObjects;

import java.util.LinkedList;
import java.util.Map;

public class Vertex extends GeometryObject {
    public Vertex() {
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>();
    }

    @Override
    public GeometryObject createNewSimilarCorrespondenceObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new Vertex();
    }
}
