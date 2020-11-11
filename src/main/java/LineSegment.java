import java.util.HashSet;

public class LineSegment extends GeometryObject {
    public LineSegment(HashSet<Point> points, Distance distanceFact) {
        this.points = points;
        this.distanceFact = distanceFact;
    }

    public LineSegment(HashSet<Point> points) {
        this.points = points;
    }

    public LineSegment() {
    }

    public HashSet<Point> points;
    public Distance distanceFact;
}
