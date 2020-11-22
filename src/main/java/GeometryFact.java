import java.util.ArrayList;

public abstract class GeometryFact {
    public abstract boolean checkFact(GeometryFact geometryFact);
    public abstract void fillInUnknowns(GeometryFact ruleFact);
}
