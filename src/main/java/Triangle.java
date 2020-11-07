import java.util.HashSet;

public class Triangle extends Plane {
    public Triangle(HashSet<LineSegment> lineSegments) {
        this.lineSegments = lineSegments;
    }

    public Triangle() {
    }

    public HashSet<LineSegment> lineSegments;
}
