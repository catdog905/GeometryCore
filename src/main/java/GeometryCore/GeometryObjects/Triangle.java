package GeometryCore.GeometryObjects;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

public class Triangle extends GeometryObject {
    public HashSet<LineSegment> lineSegments;
    public Triangle(HashSet<LineSegment> lineSegments) {
        if (lineSegments.size() != 3){
            throw new RuntimeException("Illegal constructor for triangle: count of line segments must be 3, not "
                    + lineSegments.size());
        }
        this.lineSegments = lineSegments;
    }

    public Triangle(LineSegment ab, LineSegment bc, LineSegment ac) {
        this(new HashSet<>(Arrays.asList(ab, bc, ac)));
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
