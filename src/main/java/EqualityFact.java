import java.util.HashSet;

public class EqualityFact extends GeometryFact {
    public HashSet<GeometryObject> geometryObjects;

    public EqualityFact(HashSet<GeometryObject> geometryObjects) {
        this.geometryObjects = geometryObjects;
    }

    @Override
    public boolean checkFact(GeometryFact geometryFact) {
        for (GeometryObject object: ((EqualityFact)geometryFact).geometryObjects) {
            if (!geometryObjects.contains(object) && !(object instanceof AnyObject))
                return false;
        }
        return true;
    }
}
