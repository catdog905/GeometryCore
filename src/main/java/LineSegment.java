import java.util.Arrays;
import java.util.HashSet;

public class LineSegment extends GeometryObject {
    public LineSegment(HashSet<Point> points, Distance distanceFact) {
        this.points = points;
        this.distanceFact = distanceFact;
    }

    public LineSegment(Point first, Point second) {
        this(new HashSet<>(Arrays.asList(first, second)));
    }

    public LineSegment(HashSet<Point> points) {
        this.points = points;
    }

    public LineSegment() {
    }

    public HashSet<Point> points;
    public Distance distanceFact;
}
