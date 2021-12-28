package core.model.facts.objects;

import java.util.LinkedList;
import java.util.Map;

import core.model.facts.objects.expression.monomials.Monomial;
import core.model.facts.objects.expression.monomials.MonomialEnveloper;

public class Angle extends GeometryObject {
   final public LinkedList<GeometryObject> geometryObjects;

    public Angle(LinkedList<GeometryObject> geometryObjects) {
        if (geometryObjects.size() != 2){
            throw new IllegalArgumentException();
        }
        this.geometryObjects = geometryObjects;
    }

    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        LinkedList<GeometryObject> newObjects = new LinkedList<>();
        for (GeometryObject obj : geometryObjects) {
            newObjects.add(correspondence.get(obj));
        }
        return new Angle(newObjects);
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return geometryObjects;
    }

    @Override
    public Monomial getMonomial() {
        return Monomial.buildOf(this, Angle.class, MonomialEnveloper.class);
    }
}
