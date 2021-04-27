import java.util.Arrays;
import java.util.HashSet;

public class Ray extends GeometryObject {
    public Ray(Point point) {
        this.point = point;
    }

    public Ray() {
    }

    public Point point;

    @Override
    public HashSet<GeometryObject> getSubObjects() {
        return new HashSet<>(Arrays.asList(point));
    }
}
