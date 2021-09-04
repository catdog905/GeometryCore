package GeometryCore.GeometryObjects;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

public class Triangle extends GeometryObject {
    public HashSet<LineSegment> lineSegments;
    public Triangle(HashSet<LineSegment> lineSegments) {
        this.lineSegments = lineSegments;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Triangle))
            return false;
        Triangle triangleUnderQuestion = (Triangle)o;

        return triangleUnderQuestion.lineSegments.containsAll(lineSegments);
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
