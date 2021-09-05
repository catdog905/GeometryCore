package GeometryCore.GeometryObjects;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class Polynomial extends Monomial{
    public Polynomial(LinkedList<Monomial> subObjects) {
        super(subObjects);
    }

    public Polynomial(Monomial... subObjects) {
        this(new LinkedList<>(Arrays.asList(subObjects)));
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>(subObjects);
    }

    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        LinkedList<Monomial> newObjects = new LinkedList<>();
        for (Monomial obj : subObjects) {
            newObjects.add((Monomial) correspondence.get(obj));
        }
        return new Polynomial(newObjects);
    }
}
