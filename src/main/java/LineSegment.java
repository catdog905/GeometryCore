import java.util.Arrays;
import java.util.HashSet;

public class LineSegment extends GeometryObject {
    public LineSegment(HashSet<Point> points) {
        this.points = points;
    }

    public LineSegment(Point first, Point second) {
        this(new HashSet<>(Arrays.asList(first, second)));
    }

    public LineSegment() {
    }

    public HashSet<Point> points;

    @Override
    public HashSet<GeometryObject> getSubObjects() {
        return new HashSet<>(points);
    }
}
