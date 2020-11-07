import java.util.HashSet;

public class DegreeFact extends GeometryFact {
    public HashSet<GeometryObject> geometryObjects;
    public Double value;

    public DegreeFact(HashSet<GeometryObject> geometryObjects, Double value) {
        this.geometryObjects = geometryObjects;
        this.value = value;
    }
}
