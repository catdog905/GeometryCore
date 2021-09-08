package GeometryCore.GeometryObjects;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class Monomial extends GeometryObject {
    private LinkedList<Monomial> subObjects;

    public Monomial(LinkedList<Monomial> subObjects) {
        this.subObjects = subObjects;
    }

    public Monomial(Monomial... subObjects) {
        this.subObjects = new LinkedList<>(Arrays.asList(subObjects));
    }

    public Monomial() {
        subObjects = new LinkedList<>();
    }

    public Monomial(Monomial factor1, Monomial factor2) {
        subObjects = new LinkedList<>(Arrays.asList(factor1, factor2));
    }

    public Monomial(Monomial monomial, LinkedList<Monomial> subObjAddTo) {
        LinkedList<Monomial> temp = new LinkedList<>();
        temp.addAll(subObjAddTo);
        temp.add(monomial);
        subObjects = temp;
    }

    @Override
    public LinkedList<? extends GeometryObject> getAllSubObjects() {
        return subObjects;
    }

    @Override
    public Monomial createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        LinkedList<Monomial> newObjects = new LinkedList<>();
        for (Monomial obj : subObjects) {
            newObjects.add((Monomial) correspondence.get(obj));
        }
        return new Monomial(newObjects);
    }
}
