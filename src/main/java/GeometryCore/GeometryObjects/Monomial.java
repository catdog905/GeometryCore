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
}
