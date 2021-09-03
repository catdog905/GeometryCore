package GeometryCore.GeometryObjects;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class Ray extends GeometryObject {
    public Vertex vertex;
    public Ray(Vertex vertex) {
        this.vertex = vertex;
    }

    public Ray() {
    }

    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new Ray((Vertex) correspondence.get(vertex));
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>(Arrays.asList(vertex));
    }
}
