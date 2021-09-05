package GeometryCore.GeometryObjects;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

public class Triangle extends GeometryObject {
    public HashSet<LineSegment> lineSegments;
    public Triangle(HashSet<LineSegment> lineSegments) {

        if (lineSegments.size() != 3){
            throw new RuntimeException("Illegal constructor for triangle");
        }
        this.lineSegments = lineSegments;
    }

    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        HashSet<LineSegment> newObjects = new HashSet<>();
        for (LineSegment obj : lineSegments) {
            newObjects.add((LineSegment) correspondence.get(obj));
        }
        return new Triangle(newObjects);
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>(lineSegments);
    }
}
