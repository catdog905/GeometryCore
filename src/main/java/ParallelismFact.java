import java.util.HashSet;

public class ParallelismFact extends GeometryFact {
    public HashSet<GeometryObject> geometryObjects;

    public ParallelismFact(HashSet<GeometryObject> geometryObjects) {
        this.geometryObjects = geometryObjects;
    }
}
