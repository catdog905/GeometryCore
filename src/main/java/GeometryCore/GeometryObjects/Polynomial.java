package GeometryCore.GeometryObjects;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class Polynomial extends Monomial{
    public Polynomial(LinkedList<Monomial> subObjects) {
        super(subObjects);
        if (subObjects.size() < 2){
            throw new RuntimeException("Illegal constructor for polynomial");
        }
    }

    public Polynomial(Monomial... subObjects) {
        this(new LinkedList<>(Arrays.asList(subObjects)));
    }

    @Override
    public LinkedList<Polynomial> getAllSubObjects() {
        return (LinkedList<Polynomial>) super.getAllSubObjects();
    }

    @Override
    public Polynomial createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        LinkedList<Monomial> newObjects = new LinkedList<>();
        for (GeometryObject obj : super.getAllSubObjects()) {
            newObjects.add((Monomial) correspondence.get(obj));
        }
        return new Polynomial(newObjects);
    }
}
