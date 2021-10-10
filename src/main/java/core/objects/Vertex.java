package core.objects;

import java.util.LinkedList;
import java.util.Map;

import core.objects.expression.Monomial;
import core.objects.expression.MonomialEnveloper;

public class Vertex extends GeometryObject {
    public Vertex() {
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>();
    }

    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new Vertex();
    }

    @Override
    public Monomial getMonomial() {
        return Monomial.buildOf(this, Vertex.class, MonomialEnveloper.class);
    }
}
