package GeometryCore.GeometryObjects;

import java.util.LinkedList;

public abstract class Monomial extends GeometryObject {
    LinkedList<Monomial> subObjects;

    public Monomial(LinkedList<Monomial> subObjects) {
        this.subObjects = subObjects;
    }

    public Monomial() {
        subObjects = new LinkedList<>();
    }

    //@Override
    //public LinkedList<GeometryObject> getAllSubObjects() {
    //    return new LinkedList<>(subObjects);
    //}

    //@Override
    //public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
    //    LinkedList<Monomial> newObjects = new LinkedList<>();
    //    for (Monomial obj : subObjects) {
    //        newObjects.add((Monomial) correspondence.get(obj));
    //    }
    //    return new Monomial(newObjects);
    //}
}
