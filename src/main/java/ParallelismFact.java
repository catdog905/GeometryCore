import java.util.HashSet;

public class ParallelismFact extends GeometryFact {
    public HashSet<GeometryObject> geometryObjects;

    public ParallelismFact(HashSet<GeometryObject> geometryObjects) {
        this.geometryObjects = geometryObjects;
    }

    @Override
    public boolean checkFact(GeometryFact geometryFact) {
        for (GeometryObject object: ((ParallelismFact) geometryFact).geometryObjects) {
            if (!geometryObjects.contains(object) && !(object instanceof AnyObject))
                return false;
        }
        return true;
    }
}
