import java.util.HashSet;

public class Distance extends GeometryObject {
    public HashSet<GeometryObject> geometryObjects;

    public Distance(HashSet<GeometryObject> geometryObjects) {
        this.geometryObjects = geometryObjects;
    }

    @Override
    public HashSet<GeometryObject> getSubObjects() {
        return new HashSet<>(geometryObjects);
    }
}
