package core.objects;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

public class LineSegment extends GeometryObject {
    public LinkedList<Vertex> vertexes;
    public LineSegment(HashSet<Vertex> vertexes) {
        this.vertexes = new LinkedList<>(vertexes);
    }

    public LineSegment(Vertex first, Vertex second) {
        this(new HashSet<>(Arrays.asList(first, second)));
    }

    @Override
    public GeometryObject createNewSimilarCorrespondenceObject(Map<GeometryObject, GeometryObject> correspondence) {
        HashSet<Vertex> newObjects = new HashSet<>();
        for (Vertex obj : vertexes) {
            newObjects.add((Vertex) correspondence.get(obj));
        }
        return new LineSegment(newObjects);
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        if (vertexes == null)
            return new LinkedList<>();
        return new LinkedList<>(vertexes);
    }
}
