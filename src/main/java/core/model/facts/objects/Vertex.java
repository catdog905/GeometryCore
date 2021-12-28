package core.model.facts.objects;

import java.util.LinkedList;
import java.util.Map;

import core.model.facts.objects.expression.monomials.Monomial;
import core.model.facts.objects.expression.monomials.MonomialEnveloper;

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
