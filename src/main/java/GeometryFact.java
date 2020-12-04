import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public abstract class GeometryFact implements Cloneable{
    public abstract boolean checkFact(GeometryFact geometryFact);
    public abstract void fillInUnknowns(GeometryFact ruleFact);
    public abstract HashMap<GeometryObject, GeometryObject> getMatchingObjects(GeometryFact templateFact, GeometryFact modelFact);
    public abstract LinkedList<GeometryObject> getSubObjects();
    public abstract void putSubObjects(LinkedList<GeometryObject> subObjects);

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
