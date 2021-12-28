package core.model.facts.objects;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import core.model.facts.objects.expression.monomials.Monomial;
import core.model.facts.objects.expression.monomials.MonomialEnveloper;

public class LineSegment extends GeometryObject {
    final public HashSet<Vertex> vertexes;
    public LineSegment(HashSet<Vertex> vertexes) {
        if (vertexes.size() != 2)
            throw new IllegalArgumentException();
        this.vertexes = vertexes;
    }

    public LineSegment(Vertex first, Vertex second) {
        this(new HashSet<>(Arrays.asList(first, second)));
    }

    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        HashSet<Vertex> newObjects = new HashSet<>();
        for (Vertex obj : vertexes) {
            newObjects.add((Vertex) correspondence.get(obj));
        }
        return new LineSegment(newObjects);
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>(vertexes);
    }

    @Override
    public Monomial getMonomial() {
        return Monomial.buildOf(this, LineSegment.class, MonomialEnveloper.class);
    }
}
