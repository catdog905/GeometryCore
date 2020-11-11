import java.util.HashSet;

public class Angle extends GeometryObject {
    public HashSet<GeometryObject> geometryObjects;

    public Angle(HashSet<GeometryObject> geometryObjects) {
        this.geometryObjects = geometryObjects;
    }
}
